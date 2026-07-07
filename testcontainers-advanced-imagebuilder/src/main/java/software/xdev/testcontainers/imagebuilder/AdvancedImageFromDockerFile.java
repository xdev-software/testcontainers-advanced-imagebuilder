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

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.images.builder.traits.BuildContextBuilderTrait;
import org.testcontainers.images.builder.traits.ClasspathTrait;
import org.testcontainers.images.builder.traits.DockerfileTrait;
import org.testcontainers.images.builder.traits.FilesTrait;
import org.testcontainers.images.builder.traits.StringsTrait;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;

import software.xdev.testcontainers.imagebuilder.log.LoggingBuildImageResultCallback;
import software.xdev.testcontainers.imagebuilder.transfer.FastFilePathRelativizer;
import software.xdev.testcontainers.imagebuilder.transfer.TransferFilesCreator;


/**
 * Fork of {@link org.testcontainers.images.builder.ImageFromDockerfile} to fix the following problems:
 * <ul>
 * 	<li><a href="https://github.com/testcontainers/testcontainers-java/issues/3093">testcontainers-java#3093</a></li>
 * 	<li><a href="https://github.com/testcontainers/testcontainers-java/issues/3238">testcontainers-java#3238</a></li>
 * 	<li>{@link TransferFilesCreator}</li>
 * 	<li>Fixed: Logger was not controllable (because it was generic)</li>
 *  <li>Deprecated-Stuff replaced / removed</li>
 * </ul>
 *
 * @author AB
 * @see org.testcontainers.images.builder.ImageFromDockerfile
 */
public class AdvancedImageFromDockerFile
	extends AbstractImageFromDockerfile<AdvancedImageFromDockerFile>
	implements
	BuildContextBuilderTrait<AdvancedImageFromDockerFile>,
	ClasspathTrait<AdvancedImageFromDockerFile>,
	FilesTrait<AdvancedImageFromDockerFile>,
	StringsTrait<AdvancedImageFromDockerFile>,
	DockerfileTrait<AdvancedImageFromDockerFile>
{
	protected final Map<String, Transferable> explicitTransferables = new HashMap<>();
	protected final Set<Consumer<BuildImageCmd>> buildImageCmdModifiers = new LinkedHashSet<>();
	
	public AdvancedImageFromDockerFile()
	{
	}
	
	public AdvancedImageFromDockerFile(final String dockerImageName)
	{
		super(dockerImageName);
	}
	
	public AdvancedImageFromDockerFile(final String dockerImageName, final boolean deleteOnExit)
	{
		super(dockerImageName, deleteOnExit);
	}
	
	public AdvancedImageFromDockerFile(
		final String dockerImageName,
		final boolean deleteOnExit,
		final Optional<Logger> optLogger)
	{
		super(dockerImageName, deleteOnExit, optLogger);
	}
	
	@SuppressWarnings("checkstyle:MagicNumber")
	@Override
	protected String resolve()
	{
		@SuppressWarnings("resource")
		final DockerClient dockerClient = DockerClientFactory.instance().client();
		
		this.log().info("Starting resolving image[name='{}']", this.dockerImageName);
		
		try
		{
			// We have to use pipes to avoid high memory consumption since users might want to build huge images
			final PipedInputStream in = new PipedInputStream();
			final PipedOutputStream out = new PipedOutputStream(in);
			
			final BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(in);
			
			final ConfigurationState configurationState = this.configure(buildImageCmd);
			
			final Map<String, String> labels = this.createDefaultLabels();
			if(buildImageCmd.getLabels() != null)
			{
				labels.putAll(buildImageCmd.getLabels());
			}
			buildImageCmd.withLabels(labels);
			
			this.prePullDependencyImages(configurationState.getExternalDependencyImageNames());
			
			this.log().info("Starting building image[name='{}']", this.dockerImageName);
			final long buildStartTime = System.currentTimeMillis();
			
			final BuildImageResultCallback exec = buildImageCmd.exec(
				this.getBuildImageResultCallback(this.calcLoggerForBuild()));
			
			final long bytesToDockerDaemon = this.getBytesToDockerDaemon(out);
			
			if(this.log().isDebugEnabled())
			{
				this.log().debug(
					"Transferred {} manually (not actually) to Docker daemon",
					FileUtils.byteCountToDisplaySize(bytesToDockerDaemon));
			}
			
			exec.awaitImageId();
			
			this.log().info(
				"Building of image[name='{}'] was done in {}ms",
				this.dockerImageName,
				System.currentTimeMillis() - buildStartTime);
			
			return this.dockerImageName;
		}
		catch(final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
	
	protected long getBytesToDockerDaemon(final PipedOutputStream out) throws IOException
	{
		long bytesToDockerDaemon = 0L;
		
		// To build an image, we have to send the context to Docker in TAR archive format
		try(final TarArchiveOutputStream tarArchive = new TarArchiveOutputStream(new GZIPOutputStream(out)))
		{
			tarArchive.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
			
			for(final Entry<String, Transferable> entry : this.explicitTransferables.entrySet())
			{
				final Transferable transferable = entry.getValue();
				final String destination = entry.getKey();
				transferable.transferTo(tarArchive, destination);
				bytesToDockerDaemon += transferable.getSize();
			}
			tarArchive.finish();
		}
		return bytesToDockerDaemon;
	}
	
	protected BuildImageResultCallback getBuildImageResultCallback(final Logger logger)
	{
		return new LoggingBuildImageResultCallback(logger);
	}
	
	protected ConfigurationState configure(final BuildImageCmd buildImageCmd)
	{
		this.log().info("Configuring...");
		
		final ConfigurationState state = this.createNewConfigurationState();
		
		buildImageCmd.withTags(new HashSet<>(Collections.singletonList(this.dockerImageName)));
		
		this.optDockerFilePath.ifPresent(dockerFilePath -> {
			buildImageCmd.withDockerfilePath(FastFilePathRelativizer.relativize(
				this.optBaseDir.orElse(dockerFilePath.getParent()),
				dockerFilePath));
			
			this.prepareImagePull(buildImageCmd, dockerFilePath, state);
		});
		
		this.optBaseDir.ifPresent(baseDir -> {
			buildImageCmd.withTarInputStream(this.calcFileTransferInfo(baseDir).filesToTransfer());
			buildImageCmd.withBaseDirectory(baseDir.toFile());
		});
		
		this.buildArgs.forEach(buildImageCmd::withBuildArg);
		this.optTarget.ifPresent(buildImageCmd::withTarget);
		this.buildImageCmdModifiers.forEach(hook -> hook.accept(buildImageCmd));
		
		return state;
	}
	
	protected ConfigurationState createNewConfigurationState()
	{
		return new ConfigurationState();
	}
	
	protected static class ConfigurationState
	{
		protected Set<String> externalDependencyImageNames = Set.of();
		
		protected Set<String> getExternalDependencyImageNames()
		{
			return this.externalDependencyImageNames;
		}
		
		protected void setExternalDependencyImageNames(final Set<String> externalDependencyImageNames)
		{
			this.externalDependencyImageNames = externalDependencyImageNames;
		}
	}
	
	protected void prepareImagePull(
		final BuildImageCmd buildImageCmd,
		final Path dockerFile,
		final ConfigurationState state)
	{
		if(this.disablePull)
		{
			buildImageCmd.withPull(false);
			return;
		}
		
		final AdvancedParsedDockerfile parsedDockerFile = new AdvancedParsedDockerfile(dockerFile);
		
		this.log().info("Resolving dependency images...");
		final Set<String> externalDependencyImageNames = this.fullyResolveDependencyImages(
			parsedDockerFile.getExternalImageNames(),
			parsedDockerFile.getArguments());
		
		if(!externalDependencyImageNames.isEmpty())
		{
			// if we'll be pre-pulling images, disable the built-in pull as it is not necessary and will fail for
			// authenticated registries
			buildImageCmd.withPull(false);
		}
		
		state.setExternalDependencyImageNames(externalDependencyImageNames);
	}
	
	public AdvancedImageFromDockerFile copyForIntermediateTag(final String target)
	{
		return this.copyForIntermediateTag(this.dockerImageName + "-" + target, target);
	}
	
	public AdvancedImageFromDockerFile copyForIntermediateTag(
		final String dockerImageName, final String target)
	{
		if(!this.createTransferFilesCache)
		{
			throw new IllegalStateException(
				"createTransferFilesCache must be true to execute this operation");
		}
		if(this.transferFileCache == null)
		{
			throw new IllegalStateException("No transferFileCache. Did you build this image?");
		}
		
		return new AdvancedImageFromDockerFile(dockerImageName, this.deleteOnExit)
			// Copy defaults
			.withExplicitTransferables(this.explicitTransferables)
			.withBuildArgs(this.buildArgs)
			.withLoggerForBuild(this.loggerForBuild)
			.withDockerFilePath(this.optDockerFilePath.orElse(null))
			.withBaseDir(this.optBaseDir.orElse(null))
			.withBuildImageCmdModifiers(this.buildImageCmdModifiers)
			// Special
			.withCreateTransferFilesCache(false)
			.withTransferFileCache(this.transferFileCache)
			.withTarget(target)
			.withDisablePull(true);
	}
	
	public void cleanCreatedTransferFilesCache()
	{
		if(this.createTransferFilesCache && this.transferFileCache != null)
		{
			this.transferFileCache.close();
			this.transferFileCache = null;
		}
	}
	
	// region with
	
	public AdvancedImageFromDockerFile withExplicitTransferables(final Map<String, Transferable> transferables)
	{
		this.explicitTransferables.putAll(transferables);
		return this;
	}
	
	@Override
	public AdvancedImageFromDockerFile withFileFromTransferable(final String path, final Transferable transferable)
	{
		if(this.explicitTransferables.put(path, transferable) != null)
		{
			this.log().warn("overriding previous mapping for '{}'", path);
		}
		return this;
	}
	
	public AdvancedImageFromDockerFile withBuildImageCmdModifiers(
		final Collection<Consumer<BuildImageCmd>> modifiers)
	{
		this.buildImageCmdModifiers.addAll(modifiers);
		return this;
	}
	
	public AdvancedImageFromDockerFile withBuildImageCmdModifier(final Consumer<BuildImageCmd> modifier)
	{
		this.buildImageCmdModifiers.add(modifier);
		return this;
	}
	
	// endregion
}
