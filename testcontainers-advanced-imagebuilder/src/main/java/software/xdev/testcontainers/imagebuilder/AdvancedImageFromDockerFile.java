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
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.images.RemoteDockerImage;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.images.builder.traits.BuildContextBuilderTrait;
import org.testcontainers.images.builder.traits.ClasspathTrait;
import org.testcontainers.images.builder.traits.DockerfileTrait;
import org.testcontainers.images.builder.traits.FilesTrait;
import org.testcontainers.images.builder.traits.StringsTrait;
import org.testcontainers.utility.Base58;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.DockerLoggerFactory;
import org.testcontainers.utility.ImageNameSubstitutor;
import org.testcontainers.utility.LazyFuture;
import org.testcontainers.utility.ResourceReaper;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;

import software.xdev.testcontainers.imagebuilder.concurrent.ImageBuilderExecutorServiceHolder;
import software.xdev.testcontainers.imagebuilder.log.LoggingBuildImageResultCallback;
import software.xdev.testcontainers.imagebuilder.transfer.DockerFileLineModifier;
import software.xdev.testcontainers.imagebuilder.transfer.FastFilePathRelativzer;
import software.xdev.testcontainers.imagebuilder.transfer.FilesToTransferHandler;
import software.xdev.testcontainers.imagebuilder.transfer.FilesToTransferInputStreamFactory;
import software.xdev.testcontainers.imagebuilder.transfer.TransferArchiveTARCompressor;
import software.xdev.testcontainers.imagebuilder.transfer.TransferFilesCreator;
import software.xdev.testcontainers.imagebuilder.transfer.fcm.DockerFileContentModifier;


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
@SuppressWarnings({
	"OptionalUsedAsFieldOrParameterType",
	"PMD.GodClass",
	"PMD.MoreThanOneLogger",
	"java:S1133"
})
public class AdvancedImageFromDockerFile
	extends LazyFuture<String>
	implements
	BuildContextBuilderTrait<AdvancedImageFromDockerFile>,
	ClasspathTrait<AdvancedImageFromDockerFile>,
	FilesTrait<AdvancedImageFromDockerFile>,
	StringsTrait<AdvancedImageFromDockerFile>,
	DockerfileTrait<AdvancedImageFromDockerFile>
{
	protected String dockerImageName;
	protected Logger defaultLogger;
	
	protected boolean deleteOnExit;
	protected final Map<String, Transferable> explicitTransferables = new HashMap<>();
	protected final Map<String, String> buildArgs = new HashMap<>();
	protected Logger loggerForBuild;
	protected Optional<Path> optDockerFilePath = Optional.empty();
	protected Optional<Path> optBaseDir = Optional.empty();
	protected Optional<String> target = Optional.empty();
	protected final Set<Consumer<BuildImageCmd>> buildImageCmdModifiers = new LinkedHashSet<>();
	protected boolean disablePull;
	
	protected FilesToTransferHandler filesToTransferHandler = new FilesToTransferHandler();
	
	protected boolean createTransferFilesCache;
	protected FilesToTransferInputStreamFactory transferFileCache;
	
	@SuppressWarnings("checkstyle:MagicNumber")
	public AdvancedImageFromDockerFile()
	{
		this("testcontainers/" + Base58.randomString(16).toLowerCase());
	}
	
	public AdvancedImageFromDockerFile(final String dockerImageName)
	{
		this(dockerImageName, true);
	}
	
	public AdvancedImageFromDockerFile(final String dockerImageName, final boolean deleteOnExit)
	{
		this(
			dockerImageName,
			deleteOnExit,
			LoggerFactory.getLogger(AdvancedImageFromDockerFile.class.getName() + "." + dockerImageName));
	}
	
	public AdvancedImageFromDockerFile(final String dockerImageName, final boolean deleteOnExit, final Logger logger)
	{
		this.dockerImageName = dockerImageName;
		this.deleteOnExit = deleteOnExit;
		this.defaultLogger = logger;
	}
	
	@SuppressWarnings("checkstyle:MagicNumber")
	@Override
	protected String resolve()
	{
		final Logger logger = Optional.ofNullable(this.loggerForBuild)
			.orElseGet(() -> DockerLoggerFactory.getLogger(this.dockerImageName));
		
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
			
			final Map<String, String> labels = new HashMap<>();
			if(buildImageCmd.getLabels() != null)
			{
				labels.putAll(buildImageCmd.getLabels());
			}
			this.deleteImageOnExitIfRequired(labels);
			labels.putAll(DockerClientFactory.DEFAULT_LABELS);
			buildImageCmd.withLabels(labels);
			
			if(!this.disablePull)
			{
				this.prePullDependencyImages(configurationState.getExternalDependencyImageNames());
			}
			
			this.log().info("Starting building image[name='{}']", this.dockerImageName);
			final long buildStartTime = System.currentTimeMillis();
			
			final BuildImageResultCallback exec = buildImageCmd.exec(this.getBuildImageResultCallback(logger));
			
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
	
	@SuppressWarnings("deprecation") // There is no alternative and it's also used in the default implementation
	protected void deleteImageOnExitIfRequired(final Map<String, String> labels)
	{
		if(!this.deleteOnExit)
		{
			return;
		}
		
		this.log().debug("Registering image for cleanup when finished");
		labels.putAll(ResourceReaper.instance().getLabels());
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
			buildImageCmd.withDockerfilePath(FastFilePathRelativzer.relativize(
				this.optBaseDir.orElse(dockerFilePath.getParent()),
				dockerFilePath));
			
			this.prepareImagePull(buildImageCmd, dockerFilePath, state);
		});
		
		this.optBaseDir.ifPresent(baseDir -> {
			buildImageCmd.withTarInputStream(this.createTarInputStream(baseDir));
			buildImageCmd.withBaseDirectory(baseDir.toFile());
		});
		
		this.buildArgs.forEach(buildImageCmd::withBuildArg);
		this.target.ifPresent(buildImageCmd::withTarget);
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
	
	@SuppressWarnings("java:S2095")
	protected InputStream createTarInputStream(final Path baseDir)
	{
		if(this.transferFileCache != null)
		{
			this.log().info("Using cached transfer files InputStream");
			return this.transferFileCache.filesToTransfer();
		}
		
		final FilesToTransferInputStreamFactory factory =
			this.filesToTransferHandler.create(
				this.log(),
				baseDir,
				this.optDockerFilePath.orElseGet(() -> Path.of("Dockerfile")),
				!this.createTransferFilesCache);
		if(this.createTransferFilesCache)
		{
			this.transferFileCache = factory;
		}
		return factory.filesToTransfer();
	}
	
	protected Set<String> fullyResolveDependencyImages(
		final Set<String> fileDependencyImages,
		final Map<String, Optional<String>> fileArgs)
	{
		final Map<String, String> resolvedArgs = new HashMap<>(this.buildArgs);
		
		fileArgs.entrySet()
			.stream()
			.filter(e -> e.getValue().isPresent())
			.filter(e -> !resolvedArgs.containsKey(e.getKey()))
			.forEach(e -> resolvedArgs.put(e.getKey(), e.getValue().orElseThrow()));
		
		return fileDependencyImages
			.stream()
			.map(imgName -> {
				if(!imgName.contains("$"))
				{
					return imgName;
				}
				
				String newImgName = imgName;
				for(final Entry<String, String> entry : resolvedArgs.entrySet())
				{
					for(final String replacePattern : Arrays.asList("${%s}", "$%s"))
					{
						newImgName =
							newImgName.replace(String.format(replacePattern, entry.getKey()), entry.getValue());
					}
				}
				return newImgName;
			})
			.collect(Collectors.toSet());
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
	
	protected void prePullDependencyImages(final Set<String> imagesToPull)
	{
		imagesToPull
			.stream()
			.filter(this::canImageNameBePulled)
			.map(imageName -> CompletableFuture.runAsync(() -> {
				try
				{
					this.log().info(
						"Pre-emptively checking local images for '{}', referenced via a Dockerfile."
							+ " If not available, it will be pulled.",
						imageName);
					new RemoteDockerImage(DockerImageName.parse(imageName))
						.withImageNameSubstitutor(ImageNameSubstitutor.noop())
						.get(10, TimeUnit.MINUTES);
				}
				catch(final Exception e)
				{
					this.log().warn(
						"Unable to pre-fetch an image ({}) depended upon by Dockerfile -"
							+ " image build will continue but may fail. Exception message was: {}",
						imageName,
						e.getMessage());
				}
			}, this.executorServiceForPrePull()))
			.toList()
			.forEach(CompletableFuture::join);
	}
	
	protected ExecutorService executorServiceForPrePull()
	{
		return ImageBuilderExecutorServiceHolder.instance();
	}
	
	protected boolean canImageNameBePulled(final String imageName)
	{
		// scratch is reserved
		return !"scratch".equals(imageName);
	}
	
	protected Logger log()
	{
		return this.defaultLogger;
	}
	
	public String build(final Duration timeout)
	{
		try
		{
			return this.get(timeout.toSeconds(), TimeUnit.SECONDS);
		}
		catch(final TimeoutException ex)
		{
			throw new IllegalStateException("Timed out while building " + this.dockerImageName, ex);
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
	
	public AdvancedImageFromDockerFile withLoggerForBuild(final Logger loggerForBuild)
	{
		this.loggerForBuild = loggerForBuild;
		return this;
	}
	
	public AdvancedImageFromDockerFile withDockerImageName(final String dockerImageName)
	{
		this.dockerImageName = dockerImageName;
		return this;
	}
	
	public AdvancedImageFromDockerFile withDefaultLogger(final Logger defaultLogger)
	{
		this.defaultLogger = defaultLogger;
		return this;
	}
	
	public AdvancedImageFromDockerFile withDeleteOnExit(final boolean deleteOnExit)
	{
		this.deleteOnExit = deleteOnExit;
		return this;
	}
	
	public AdvancedImageFromDockerFile withBuildArg(final String key, final String value)
	{
		this.buildArgs.put(key, value);
		return this;
	}
	
	public AdvancedImageFromDockerFile withBuildArgs(final Map<String, String> args)
	{
		this.buildArgs.putAll(args);
		return this;
	}
	
	public AdvancedImageFromDockerFile withDockerFilePath(final Path dockerFilePath)
	{
		this.optDockerFilePath = Optional.ofNullable(dockerFilePath);
		return this;
	}
	
	public AdvancedImageFromDockerFile withBaseDir(final Path baseDir)
	{
		this.optBaseDir = Optional.ofNullable(baseDir);
		return this;
	}
	
	public AdvancedImageFromDockerFile withTarget(final String target)
	{
		this.target = Optional.of(target);
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
	
	public AdvancedImageFromDockerFile withDisablePull(final boolean disablePull)
	{
		this.disablePull = disablePull;
		return this;
	}
	
	public AdvancedImageFromDockerFile withCreateTransferFilesCache(
		final boolean createTransferFilesCache)
	{
		this.createTransferFilesCache = createTransferFilesCache;
		return this;
	}
	
	public AdvancedImageFromDockerFile withTransferFileCache(
		final FilesToTransferInputStreamFactory transferFileCache)
	{
		this.transferFileCache = transferFileCache;
		return this;
	}
	
	public AdvancedImageFromDockerFile withFilesToTransferHandler(final FilesToTransferHandler filesToTransferHandler)
	{
		this.filesToTransferHandler = Objects.requireNonNull(filesToTransferHandler);
		return this;
	}
	
	public FilesToTransferHandler filesToTransferHandler()
	{
		return this.filesToTransferHandler;
	}
	
	public AdvancedImageFromDockerFile configureFilesToTransferHandler(final Consumer<FilesToTransferHandler> c)
	{
		c.accept(this.filesToTransferHandler);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withBaseDirRelativeIgnoreFile(final Path baseDirRelativeIgnoreFile)
	{
		this.filesToTransferHandler.withBaseDirRelativeIgnoreFile(baseDirRelativeIgnoreFile);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withPreGitIgnoreLines(final String... preGitIgnoreLines)
	{
		this.filesToTransferHandler.withPreGitIgnoreLines(preGitIgnoreLines);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withIgnoreFileLineFilter(final Predicate<String> ignoreFileLineFilter)
	{
		this.filesToTransferHandler.withIgnoreFileLineFilter(ignoreFileLineFilter);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withPostGitIgnoreLines(final String... postGitIgnoreLines)
	{
		this.filesToTransferHandler.withPostGitIgnoreLines(postGitIgnoreLines);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withAlwaysTransferRelativPaths(final Set<String> alwaysTransferPaths)
	{
		this.filesToTransferHandler.withAlwaysTransferRelativPaths(alwaysTransferPaths);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withAlwaysTransferDockerfilePath(final boolean alwaysTransferDockerfilePath)
	{
		this.filesToTransferHandler.withAlwaysTransferDockerfilePath(alwaysTransferDockerfilePath);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withTransferFilesCreatorSupplier(
		final BiFunction<Path, Path, TransferFilesCreator> transferFilesCreatorSupplier)
	{
		this.filesToTransferHandler.withTransferFilesCreatorSupplier(transferFilesCreatorSupplier);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withTransferArchiveTARCompressor(
		final TransferArchiveTARCompressor transferArchiveTARCompressor)
	{
		this.filesToTransferHandler.withTransferArchiveTARCompressor(transferArchiveTARCompressor);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withTransferArchiveTARCompressorCustomizer(
		final Consumer<TransferArchiveTARCompressor> customizer)
	{
		this.filesToTransferHandler.withTransferArchiveTARCompressorCustomizer(customizer);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withDockerFileContentModifierSupplier(
		final TriFunction<Path, List<DockerFileLineModifier>, Collection<String>, DockerFileContentModifier>
			dockerFileContentModifierSupplier)
	{
		this.filesToTransferHandler.withDockerFileContentModifierSupplier(dockerFileContentModifierSupplier);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withDockerFileLinesModifier(
		final DockerFileLineModifier dockerFileLinesModifier)
	{
		this.filesToTransferHandler.withDockerFileLinesModifier(dockerFileLinesModifier);
		return this;
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public AdvancedImageFromDockerFile withUseWinNTFSJunctionFixIfApplicable(
		final boolean useWinNTFSJunctionFixIfApplicable)
	{
		this.filesToTransferHandler.withUseWinNTFSJunctionFixIfApplicable(useWinNTFSJunctionFixIfApplicable);
		return this;
	}
	
	// endregion
}
