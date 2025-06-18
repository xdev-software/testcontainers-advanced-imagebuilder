/*
 * Copyright © 2024 XDEV Software (https://xdev.software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.xdev.testcontainers.imagebuilder.transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import software.xdev.testcontainers.imagebuilder.jgit.ignore.FastIgnoreRule;
import software.xdev.testcontainers.imagebuilder.jgit.ignore.IgnoreNode;


/**
 * Helper class for copying the docker-context inside a container, based on a (.git-)ignore file.
 * <p>
 * This class only exists because TestContainers default .dockerignore resolver isn't working well: It's slow (needs
 * ~30s) and works incorrectly/misses files.
 * </p>
 * <p>
 * Utilizes {@link IgnoreNode} for ignoring.
 * </p>
 *
 * @author AB
 * @see org.testcontainers.shaded.com.github.dockerjava.core.dockerfile.Dockerfile.ScannedResult
 */
public class DefaultTransferFilesCreator implements TransferFilesCreator
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTransferFilesCreator.class);
	
	private final Path baseDir;
	private final Path ignoreFileRelativeToBaseDir;
	
	public DefaultTransferFilesCreator(
		final Path baseDir,
		final Path ignoreFileRelativeToBaseDir)
	{
		this.baseDir = Objects.requireNonNull(baseDir);
		this.ignoreFileRelativeToBaseDir = ignoreFileRelativeToBaseDir;
	}
	
	@Override
	public Map<Path, String> determineFilesToTransfer(
		final Set<String> preGitIgnoreLines,
		final Predicate<String> ignoreFileLineFilter,
		final Set<String> postGitIgnoreLines,
		final Set<String> alwaysIncludedRelativePaths)
	{
		try
		{
			final Set<String> ignoreLines = new LinkedHashSet<>(preGitIgnoreLines);
			if(this.ignoreFileRelativeToBaseDir != null)
			{
				ignoreLines.addAll(Files.readAllLines(this.baseDir.resolve(this.ignoreFileRelativeToBaseDir))
					.stream()
					.filter(ignoreFileLineFilter)
					.toList());
			}
			ignoreLines.addAll(postGitIgnoreLines);
			
			final IgnoreNode ignoreNode = this.createIgnoreNode(ignoreLines);
			
			return this.walkFilesAndDetermineTransfer(
				ignoreNode,
				alwaysIncludedRelativePaths);
		}
		catch(final IOException ioe)
		{
			throw new UncheckedIOException(ioe);
		}
	}
	
	protected IgnoreNode createIgnoreNode(final Set<String> ignoreLines)
	{
		return new IgnoreNode(ignoreLines.stream()
			.filter(Objects::nonNull)
			.filter(s -> !s.isBlank())
			.filter(s -> !s.startsWith("#"))
			.map(pattern -> {
				try
				{
					final FastIgnoreRule rule = new FastIgnoreRule();
					rule.parse(pattern);
					return rule;
				}
				catch(final Exception ex)
				{
					LOG.warn("Failed to parse {}", pattern, ex);
					return null;
				}
			})
			.filter(Objects::nonNull)
			.toList());
	}
	
	protected Map<Path, String> walkFilesAndDetermineTransfer(
		final IgnoreNode ignoreNode,
		final Set<String> alwaysIncludedRelativePaths) throws IOException
	{
		try(final Stream<Path> walk = Files.find(
			this.baseDir,
			Integer.MAX_VALUE,
			// Ignore directories
			(path, attrs) -> attrs.isRegularFile()))
		{
			final Map<String, Boolean> cachedDirectoryOutcome = new ConcurrentHashMap<>();
			
			// First collect then stream to improve performance
			// https://stackoverflow.com/questions/33596618/how-can-i-get-a-parallel-stream-of-files-walk/33597291#comment54977780_33597291
			return walk
				.toList()
				.stream()
				.parallel()
				.map(file -> this.determineFileForTransfer(
					ignoreNode,
					alwaysIncludedRelativePaths,
					file,
					cachedDirectoryOutcome))
				.filter(Objects::nonNull)
				.sorted(Map.Entry.comparingByValue()) // Sort by relative path
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (l, r) -> r, LinkedHashMap::new));
		}
	}
	
	@SuppressWarnings("java:S2789")
	protected Map.Entry<Path, String> determineFileForTransfer(
		final IgnoreNode ignoreNode,
		final Set<String> alwaysIncludedRelativePaths,
		final Path file,
		final Map<String, Boolean> cachedDirectoryOutcome)
	{
		final String relativePath = FastFilePathUtil.relativize(this.baseDir, file);
		
		// Initial file check
		final Optional<Map.Entry<Path, String>> outcomeFile = this.shouldIgnore(
			ignoreNode,
			alwaysIncludedRelativePaths,
			file,
			relativePath,
			relativePath,
			false);
		if(outcomeFile != null)
		{
			return outcomeFile.orElse(null);
		}
		
		// Check was inconclusive -> Check parent dirs
		
		final List<String> processedDirs = new ArrayList<>();
		final Map.Entry<Path, String> outcomeDirs = this.determineParentDirectoryForTransfer(
			ignoreNode,
			alwaysIncludedRelativePaths,
			file,
			cachedDirectoryOutcome,
			relativePath,
			processedDirs);
		processedDirs.forEach(d -> cachedDirectoryOutcome.put(d, outcomeDirs != null));
		return outcomeDirs;
	}
	
	@SuppressWarnings("java:S2789")
	protected Map.Entry<Path, String> determineParentDirectoryForTransfer(
		final IgnoreNode ignoreNode,
		final Set<String> alwaysIncludedRelativePaths,
		final Path file,
		final Map<String, Boolean> cachedDirectoryOutcome,
		final String relativePath,
		final List<String> processedDirs)
	{
		String currentParentDirPath = this.parentDirectory(relativePath);
		while(currentParentDirPath != null)
		{
			// Check cache
			final Boolean cachedOutcome = cachedDirectoryOutcome.get(currentParentDirPath);
			if(cachedOutcome != null)
			{
				return cachedOutcome ? Map.entry(file, relativePath) : null;
			}
			
			processedDirs.add(currentParentDirPath);
			
			final Optional<Map.Entry<Path, String>> outcome = this.shouldIgnore(
				ignoreNode,
				alwaysIncludedRelativePaths,
				file,
				relativePath,
				currentParentDirPath,
				true);
			if(outcome != null)
			{
				return outcome.orElse(null);
			}
			
			currentParentDirPath = this.parentDirectory(currentParentDirPath);
		}
		
		// Default: Include
		return Map.entry(file, relativePath);
	}
	
	protected String parentDirectory(final String dir)
	{
		final int dirSepIndex = dir.lastIndexOf('/');
		return dirSepIndex != -1
			? dir.substring(0, dirSepIndex)
			: null;
	}
	
	@SuppressWarnings("java:S2789")
	protected Optional<Map.Entry<Path, String>> shouldIgnore(
		final IgnoreNode ignoreNode,
		final Set<String> alwaysIncludedRelativePaths,
		final Path file,
		final String relativePath,
		final String relativeWorkingPath,
		final boolean isDirectory
	)
	{
		if(alwaysIncludedRelativePaths.contains(relativeWorkingPath))
		{
			return Optional.of(Map.entry(file, relativePath));
		}
		final IgnoreNode.MatchResult result = ignoreNode.isIgnored(relativeWorkingPath, isDirectory);
		if(result == IgnoreNode.MatchResult.NOT_IGNORED)
		{
			return Optional.of(Map.entry(file, relativePath));
		}
		else if(result == IgnoreNode.MatchResult.IGNORED)
		{
			return Optional.empty();
		}
		// NULL -> Continue
		return null;
	}
	
	@Override
	@SuppressWarnings({"java:S2095", "resource"}) // Can't close an InputStream that is returned...
	public InputStream getAllFilesToTransferAsTarInputStream(
		final Collection<Path> filesToTransfer,
		final TransferArchiveTARCompressor transferArchiveTARCompressor)
	{
		File dockerFolderTar = null;
		try
		{
			dockerFolderTar = transferArchiveTARCompressor.archiveTARFiles(
				this.baseDir.toFile(),
				filesToTransfer,
				UUID.randomUUID().toString());
			final File dockerFolderTarInner = dockerFolderTar;
			final FileInputStream tarInputStream = FileUtils.openInputStream(dockerFolderTar);
			return new InputStream()
			{
				@Override
				public int available() throws IOException
				{
					return tarInputStream.available();
				}
				
				@Override
				public int read() throws IOException
				{
					return tarInputStream.read();
				}
				
				@Override
				public int read(final byte[] buff, final int offset, final int len) throws IOException
				{
					return tarInputStream.read(buff, offset, len);
				}
				
				@Override
				public void close()
				{
					IOUtils.closeQuietly(tarInputStream);
					FileUtils.deleteQuietly(dockerFolderTarInner);
				}
			};
		}
		catch(final IOException ioe)
		{
			FileUtils.deleteQuietly(dockerFolderTar);
			throw new UncheckedIOException(ioe);
		}
	}
}
