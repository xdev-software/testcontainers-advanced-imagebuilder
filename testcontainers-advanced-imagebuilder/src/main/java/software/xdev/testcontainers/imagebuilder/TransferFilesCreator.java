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
package software.xdev.testcontainers.imagebuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.testcontainers.shaded.com.github.dockerjava.core.util.CompressArchiveUtil;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;


/**
 * Helper class for copying the docker-context inside a container, based on a (.git-)ignore file.
 * <p>
 * This class only exists because TestContainers default .dockerignore resolver isn't working well: It's slow (needs
 * ~30s) and works incorrectly/misses files.<br/> NOTE: This processor is a lot better (needs ~5s) but it's not perfect.
 * Complex gitignore patterns might be applied incorrectly.
 * </p>
 *
 * @author AB
 * @see org.testcontainers.shaded.com.github.dockerjava.core.dockerfile.Dockerfile.ScannedResult
 */
public class TransferFilesCreator
{
	private final Path baseDir;
	private final Path ignoreFileRelativeToBaseDir;
	
	public TransferFilesCreator(final Path baseDir, final Path ignoreFileRelativeToBaseDir)
	{
		this.baseDir = Objects.requireNonNull(baseDir);
		this.ignoreFileRelativeToBaseDir = ignoreFileRelativeToBaseDir;
	}
	
	public List<Path> getFilesToTransfer(final Collection<String> additionalIgnoreLines)
	{
		try
		{
			final Set<String> ignoreLines = new HashSet<>(additionalIgnoreLines.stream().toList());
			if(this.ignoreFileRelativeToBaseDir != null)
			{
				ignoreLines.addAll(Files.readAllLines(this.baseDir.resolve(this.ignoreFileRelativeToBaseDir)));
			}
			
			final List<String> ignorePatterns = this.buildIgnorePatterns(ignoreLines);
			
			final List<PathMatcher> ignoreMatchers = ignorePatterns.stream()
				.map(s -> FileSystems.getDefault().getPathMatcher("glob:" + s))
				.toList();
			
			return this.walkFilesAndDetermineTransfer(ignoreMatchers);
		}
		catch(final IOException ioe)
		{
			throw new UncheckedIOException(ioe);
		}
	}
	
	protected List<String> buildIgnorePatterns(final Set<String> ignoreLines)
	{
		return ignoreLines.stream()
			.filter(Objects::nonNull)
			.filter(s -> !s.isBlank())
			.map(String::trim)
			.filter(s -> !s.startsWith("#"))
			.map(s -> {
				// NOTE: This is not perfect, but it's good enough
				String fixedIgnorePath = s;
				if(s.startsWith("."))
				{
					fixedIgnorePath = "**" + s;
				}
				else if(s.startsWith("*."))
				{
					fixedIgnorePath = "*" + s;
				}
				else if(!s.startsWith("/") && !s.startsWith("*") && !s.startsWith("!"))
				{
					fixedIgnorePath = "**/" + s;
				}
				if(!s.endsWith("**"))
				{
					fixedIgnorePath += s.endsWith("*") ? "*" : "**";
				}
				return fixedIgnorePath;
			})
			.toList();
	}
	
	protected List<Path> walkFilesAndDetermineTransfer(final List<PathMatcher> ignoreMatchers) throws IOException
	{
		try(final Stream<Path> walk = Files.walk(this.baseDir))
		{
			return walk
				.filter(Files::isRegularFile)
				.filter(file -> {
					final Path relativePath = Paths.get("/").resolve(this.baseDir.relativize(file));
					return ignoreMatchers.stream().noneMatch(m -> m.matches(relativePath));
				})
				.toList();
		}
	}
	
	@SuppressWarnings({"java:S2095", "resource"}) // Can't close an InputStream that is returned...
	public InputStream getAllFilesToTransferAsTarInputStream(final List<Path> filesToTransfer)
	{
		File dockerFolderTar = null;
		try
		{
			final String archiveNameWithOutExtension = UUID.randomUUID().toString();
			dockerFolderTar = CompressArchiveUtil.archiveTARFiles(
				this.baseDir.toFile(),
				filesToTransfer.stream().map(Path::toFile).toList(),
				archiveNameWithOutExtension);
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
