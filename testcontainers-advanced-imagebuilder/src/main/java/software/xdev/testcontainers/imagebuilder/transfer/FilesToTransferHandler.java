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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;

import software.xdev.testcontainers.imagebuilder.transfer.fcm.DockerFileContentModifier;


public class FilesToTransferHandler
{
	protected Optional<Path> optBaseDirRelativeIgnoreFile = Optional.of(Paths.get(".gitignore"));
	protected Set<String> preGitIgnoreLines = new LinkedHashSet<>();
	protected Predicate<String> ignoreFileLineFilter = l -> true;
	protected Set<String> postGitIgnoreLines = new LinkedHashSet<>();
	protected boolean alwaysTransferDockerfilePath = true;
	protected Set<String> alwaysTransferRelativePaths = Set.of();
	protected BiFunction<Path, Path, TransferFilesCreator> transferFilesCreatorSupplier =
		DefaultTransferFilesCreator::new;
	protected TransferArchiveTARCompressor transferArchiveTARCompressor = new TransferArchiveTARCompressor();
	protected Consumer<TransferArchiveTARCompressor> transferArchiveTARCompressorCustomizer;
	protected TriFunction<Path, List<DockerFileLineModifier>, Collection<String>, DockerFileContentModifier>
		dockerFileContentModifierSupplier = DockerFileContentModifier::new;
	protected List<DockerFileLineModifier> dockerFileLinesModifiers = new ArrayList<>();
	protected boolean useWinNTFSJunctionFixIfApplicable;
	
	public FilesToTransferInfo create(
		final Logger log,
		final Path baseDir,
		final Path dockerFilePath,
		final boolean immediatelyFreeUpWhenReadFinished)
	{
		final Path baseDirRelativeIgnoreFile = this.optBaseDirRelativeIgnoreFile.orElse(null);
		log.info(
			"Calculating files to transfer to docker[baseDir={},baseDirRelativeIgnoreFile={}]",
			baseDir,
			baseDirRelativeIgnoreFile);
		
		final Set<String> alwaysIncludePaths = new HashSet<>(this.alwaysTransferRelativePaths);
		if(this.alwaysTransferDockerfilePath)
		{
			alwaysIncludePaths.add(FastFilePathRelativizer.relativize(baseDir, dockerFilePath));
		}
		
		final long startTransferMs = System.currentTimeMillis();
		
		// NOTE: Testcontainers internal .dockerignore processor is completely broken
		// -> We use our own docker/gitignore processor here
		final TransferFilesCreator tfc = this.transferFilesCreatorSupplier.apply(
			baseDir,
			baseDirRelativeIgnoreFile);
		final Map<Path, String> filesToTransfer = tfc.determineFilesToTransfer(
			this.preGitIgnoreLines,
			this.ignoreFileLineFilter,
			this.postGitIgnoreLines,
			alwaysIncludePaths,
			this.useWinNTFSJunctionFixIfApplicable);
		
		log.info(
			"{}x files will be transferred (determination took {}ms)",
			filesToTransfer.size(),
			System.currentTimeMillis() - startTransferMs);
		
		if(log.isDebugEnabled())
		{
			filesToTransfer.forEach((a, r) -> log.debug("Will transmit: '{}' -> '{}'", a, r));
		}
		
		log.info("Building FilesToTransferInfo with docker-context...");
		final long startInputStreamBuildMs = System.currentTimeMillis();
		
		if(!this.dockerFileLinesModifiers.isEmpty())
		{
			log.info("Dockerfile lines modifiers are active: {}", this.dockerFileLinesModifiers);
			final DockerFileContentModifier dockerFileContentModifier =
				this.dockerFileContentModifierSupplier.apply(
					dockerFilePath,
					this.dockerFileLinesModifiers,
					filesToTransfer.values());
			if(dockerFileContentModifier != null)
			{
				this.transferArchiveTARCompressor.withContentModifier(dockerFileContentModifier);
			}
		}
		
		if(this.transferArchiveTARCompressorCustomizer != null)
		{
			this.transferArchiveTARCompressorCustomizer.accept(this.transferArchiveTARCompressor);
		}
		
		final FilesToTransferInfo factory =
			tfc.getAllFilesToTransferAsTarInputStreamFactory(
				filesToTransfer,
				this.transferArchiveTARCompressor,
				immediatelyFreeUpWhenReadFinished);
		
		log.info(
			"Built FilesToTransferInfo, took {}ms",
			System.currentTimeMillis() - startInputStreamBuildMs);
		
		return factory;
	}
	
	// region with
	
	public FilesToTransferHandler withBaseDirRelativeIgnoreFile(final Path baseDirRelativeIgnoreFile)
	{
		this.optBaseDirRelativeIgnoreFile = Optional.ofNullable(baseDirRelativeIgnoreFile);
		return this;
	}
	
	public FilesToTransferHandler withPreGitIgnoreLines(final String... preGitIgnoreLines)
	{
		this.preGitIgnoreLines = new LinkedHashSet<>(List.of(preGitIgnoreLines));
		return this;
	}
	
	public FilesToTransferHandler withIgnoreFileLineFilter(
		final Predicate<String> ignoreFileLineFilter)
	{
		this.ignoreFileLineFilter = ignoreFileLineFilter;
		return this;
	}
	
	public FilesToTransferHandler withPostGitIgnoreLines(final String... postGitIgnoreLines)
	{
		this.postGitIgnoreLines = new LinkedHashSet<>(List.of(postGitIgnoreLines));
		return this;
	}
	
	public FilesToTransferHandler withAlwaysTransferRelativPaths(
		final Set<String> alwaysTransferPaths)
	{
		this.alwaysTransferRelativePaths = new HashSet<>(Objects.requireNonNull(alwaysTransferPaths));
		return this;
	}
	
	public FilesToTransferHandler withAlwaysTransferDockerfilePath(
		final boolean alwaysTransferDockerfilePath)
	{
		this.alwaysTransferDockerfilePath = alwaysTransferDockerfilePath;
		return this;
	}
	
	public FilesToTransferHandler withTransferFilesCreatorSupplier(
		final BiFunction<Path, Path, TransferFilesCreator> transferFilesCreatorSupplier)
	{
		this.transferFilesCreatorSupplier = Objects.requireNonNull(transferFilesCreatorSupplier);
		return this;
	}
	
	public FilesToTransferHandler withTransferArchiveTARCompressor(
		final TransferArchiveTARCompressor transferArchiveTARCompressor)
	{
		this.transferArchiveTARCompressor = Objects.requireNonNull(transferArchiveTARCompressor);
		return this;
	}
	
	public FilesToTransferHandler withTransferArchiveTARCompressorCustomizer(
		final Consumer<TransferArchiveTARCompressor> customizer)
	{
		this.transferArchiveTARCompressorCustomizer = customizer;
		return this;
	}
	
	public FilesToTransferHandler withDockerFileContentModifierSupplier(
		final TriFunction<Path, List<DockerFileLineModifier>, Collection<String>, DockerFileContentModifier>
			dockerFileContentModifierSupplier)
	{
		this.dockerFileContentModifierSupplier = Objects.requireNonNull(dockerFileContentModifierSupplier);
		return this;
	}
	
	public FilesToTransferHandler withDockerFileLinesModifier(
		final DockerFileLineModifier dockerFileLinesModifier)
	{
		this.dockerFileLinesModifiers.add(dockerFileLinesModifier);
		return this;
	}
	
	/**
	 * Should the fix for a crash when encountering Windows NTFS Junctions be applied if applicable?
	 * <p>
	 * See {@link software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs} for details
	 * </p>
	 */
	public FilesToTransferHandler withUseWinNTFSJunctionFixIfApplicable(
		final boolean useWinNTFSJunctionFixIfApplicable)
	{
		this.useWinNTFSJunctionFixIfApplicable = useWinNTFSJunctionFixIfApplicable;
		return this;
	}
	
	// endregion
}
