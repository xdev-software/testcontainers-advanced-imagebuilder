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

import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.images.RemoteDockerImage;
import org.testcontainers.utility.Base58;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.DockerLoggerFactory;
import org.testcontainers.utility.ImageNameSubstitutor;
import org.testcontainers.utility.LazyFuture;
import org.testcontainers.utility.ResourceReaper;

import software.xdev.testcontainers.imagebuilder.concurrent.ImageBuilderExecutorServiceHolder;
import software.xdev.testcontainers.imagebuilder.transfer.DockerFileLineModifier;
import software.xdev.testcontainers.imagebuilder.transfer.FilesToTransferHandler;
import software.xdev.testcontainers.imagebuilder.transfer.FilesToTransferInfo;
import software.xdev.testcontainers.imagebuilder.transfer.TransferArchiveTARCompressor;
import software.xdev.testcontainers.imagebuilder.transfer.TransferFilesCreator;
import software.xdev.testcontainers.imagebuilder.transfer.fcm.DockerFileContentModifier;


@SuppressWarnings({
	"OptionalUsedAsFieldOrParameterType",
	"PMD.GodClass",
	"PMD.MoreThanOneLogger",
	"java:S1133"
})
public abstract class AbstractImageFromDockerfile<S extends AbstractImageFromDockerfile<S>>
	extends LazyFuture<String>
{
	protected String dockerImageName;
	protected Logger defaultLogger;
	
	protected boolean deleteOnExit;
	protected final Map<String, String> buildArgs = new HashMap<>();
	protected Logger loggerForBuild;
	protected Optional<Path> optDockerFilePath = Optional.empty();
	protected Optional<Path> optBaseDir = Optional.empty();
	protected Optional<String> optTarget = Optional.empty();
	protected boolean disablePull;
	
	protected FilesToTransferHandler filesToTransferHandler = new FilesToTransferHandler();
	
	protected boolean createTransferFilesCache;
	protected FilesToTransferInfo transferFileCache;
	
	@SuppressWarnings("checkstyle:MagicNumber")
	protected AbstractImageFromDockerfile()
	{
		this("testcontainers/" + Base58.randomString(16).toLowerCase());
	}
	
	protected AbstractImageFromDockerfile(final String dockerImageName)
	{
		this(dockerImageName, true);
	}
	
	protected AbstractImageFromDockerfile(final String dockerImageName, final boolean deleteOnExit)
	{
		this(
			dockerImageName,
			deleteOnExit,
			Optional.empty());
	}
	
	protected AbstractImageFromDockerfile(
		final String dockerImageName,
		final boolean deleteOnExit,
		final Optional<Logger> optLogger)
	{
		this.dockerImageName = dockerImageName;
		this.deleteOnExit = deleteOnExit;
		this.defaultLogger = optLogger != null
			? optLogger.orElseGet(() ->
			LoggerFactory.getLogger(this.getClass().getName() + "." + dockerImageName))
			: null;
	}
	
	protected Logger calcLoggerForBuild()
	{
		return Optional.ofNullable(this.loggerForBuild)
			.orElseGet(() -> DockerLoggerFactory.getLogger(this.dockerImageName));
	}
	
	@SuppressWarnings({"java:S2095", "resouce"})
	protected FilesToTransferInfo calcFileTransferInfo(final Path baseDir)
	{
		if(this.transferFileCache != null)
		{
			this.log().info("Using cached transfer files InputStream");
			return this.transferFileCache;
		}
		
		final FilesToTransferInfo fti =
			this.filesToTransferHandler.create(
				this.log(),
				baseDir,
				this.optDockerFilePath.orElseGet(() -> Path.of("Dockerfile")),
				!this.createTransferFilesCache);
		if(this.createTransferFilesCache)
		{
			this.transferFileCache = fti;
		}
		return fti;
	}
	
	@SuppressWarnings("deprecation") // There is no alternative and it's also used in the default implementation
	protected Map<String, String> createDefaultLabels()
	{
		final Map<String, String> labels = new HashMap<>();
		
		if(this.deleteOnExit)
		{
			this.log().debug("Registering image for cleanup when finished");
			labels.putAll(ResourceReaper.instance().getLabels());
		}
		
		labels.putAll(DockerClientFactory.DEFAULT_LABELS);
		return labels;
	}
	
	protected Set<String> fullyResolveDependencyImages(final Path dockerFile)
	{
		final AdvancedParsedDockerfile parsedDockerFile = new AdvancedParsedDockerfile(dockerFile);
		return this.fullyResolveDependencyImages(
			parsedDockerFile.getExternalImageNames(),
			parsedDockerFile.getArguments());
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
				for(final Map.Entry<String, String> entry : resolvedArgs.entrySet())
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
	
	protected void prePullDependencyImages(final Set<String> imagesToPull)
	{
		if(this.disablePull)
		{
			return;
		}
		
		imagesToPull
			.stream()
			.filter(this::canImageNameBePulled)
			.map(imageName -> CompletableFuture.runAsync(
				() -> {
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
				}, this.executorService()))
			.toList()
			.forEach(CompletableFuture::join);
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
	
	protected ExecutorService executorService()
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
	
	@Override
	protected abstract String resolve();
	
	/**
	 * Creates a copy of the image-builder that will build the exact same image.
	 * <p>
	 * NOTE: Requires {@link #createTransferFilesCache} to be set to <code>true</code> and an initial build, so that
	 * {@link #transferFileCache} is seeded.
	 * </p>
	 */
	public abstract S copyForExactRebuild(final String dockerImageName);
	
	protected S copyForExactRebuild(final BiFunction<String, Boolean, S> createNewFunc, final String dockerImageName)
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
		
		final S image = createNewFunc.apply(dockerImageName, this.deleteOnExit)
			.withBuildArgs(this.buildArgs)
			.withLoggerForBuild(this.loggerForBuild)
			.withCreateTransferFilesCache(false)
			.withTransferFileCache(this.transferFileCache)
			.withDisablePull(true);
		
		this.optDockerFilePath.ifPresent(image::withDockerFilePath);
		this.optBaseDir.ifPresent(image::withBaseDir);
		this.optTarget.ifPresent(image::withTarget);
		
		return image;
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
	
	public S withLoggerForBuild(final Logger loggerForBuild)
	{
		this.loggerForBuild = loggerForBuild;
		return this.self();
	}
	
	public S withDockerImageName(final String dockerImageName)
	{
		this.dockerImageName = dockerImageName;
		return this.self();
	}
	
	public S withDefaultLogger(final Logger defaultLogger)
	{
		this.defaultLogger = defaultLogger;
		return this.self();
	}
	
	public S withDeleteOnExit(final boolean deleteOnExit)
	{
		this.deleteOnExit = deleteOnExit;
		return this.self();
	}
	
	public S withBuildArg(final String key, final String value)
	{
		this.buildArgs.put(key, value);
		return this.self();
	}
	
	public S withBuildArgs(final Map<String, String> args)
	{
		this.buildArgs.putAll(args);
		return this.self();
	}
	
	public S withDockerFilePath(final Path dockerFilePath)
	{
		this.optDockerFilePath = Optional.ofNullable(dockerFilePath);
		return this.self();
	}
	
	public S withBaseDir(final Path baseDir)
	{
		this.optBaseDir = Optional.ofNullable(baseDir);
		return this.self();
	}
	
	public S withTarget(final String target)
	{
		this.optTarget = Optional.of(target);
		return this.self();
	}
	
	public S withDisablePull(final boolean disablePull)
	{
		this.disablePull = disablePull;
		return this.self();
	}
	
	public S withCreateTransferFilesCache(
		final boolean createTransferFilesCache)
	{
		this.createTransferFilesCache = createTransferFilesCache;
		return this.self();
	}
	
	public S withTransferFileCache(
		final FilesToTransferInfo transferFileCache)
	{
		this.transferFileCache = transferFileCache;
		return this.self();
	}
	
	public S withFilesToTransferHandler(final FilesToTransferHandler filesToTransferHandler)
	{
		this.filesToTransferHandler = Objects.requireNonNull(filesToTransferHandler);
		return this.self();
	}
	
	public FilesToTransferHandler filesToTransferHandler()
	{
		return this.filesToTransferHandler;
	}
	
	public S configureFilesToTransferHandler(final Consumer<FilesToTransferHandler> c)
	{
		c.accept(this.filesToTransferHandler);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withBaseDirRelativeIgnoreFile(final Path baseDirRelativeIgnoreFile)
	{
		this.filesToTransferHandler.withBaseDirRelativeIgnoreFile(baseDirRelativeIgnoreFile);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withPreGitIgnoreLines(final String... preGitIgnoreLines)
	{
		this.filesToTransferHandler.withPreGitIgnoreLines(preGitIgnoreLines);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withIgnoreFileLineFilter(final Predicate<String> ignoreFileLineFilter)
	{
		this.filesToTransferHandler.withIgnoreFileLineFilter(ignoreFileLineFilter);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withPostGitIgnoreLines(final String... postGitIgnoreLines)
	{
		this.filesToTransferHandler.withPostGitIgnoreLines(postGitIgnoreLines);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withAlwaysTransferRelativPaths(final Set<String> alwaysTransferPaths)
	{
		this.filesToTransferHandler.withAlwaysTransferRelativPaths(alwaysTransferPaths);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withAlwaysTransferDockerfilePath(final boolean alwaysTransferDockerfilePath)
	{
		this.filesToTransferHandler.withAlwaysTransferDockerfilePath(alwaysTransferDockerfilePath);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withTransferFilesCreatorSupplier(
		final BiFunction<Path, Path, TransferFilesCreator> transferFilesCreatorSupplier)
	{
		this.filesToTransferHandler.withTransferFilesCreatorSupplier(transferFilesCreatorSupplier);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withTransferArchiveTARCompressor(
		final TransferArchiveTARCompressor transferArchiveTARCompressor)
	{
		this.filesToTransferHandler.withTransferArchiveTARCompressor(transferArchiveTARCompressor);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withTransferArchiveTARCompressorCustomizer(
		final Consumer<TransferArchiveTARCompressor> customizer)
	{
		this.filesToTransferHandler.withTransferArchiveTARCompressorCustomizer(customizer);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withDockerFileContentModifierSupplier(
		final TriFunction<Path, List<DockerFileLineModifier>, Collection<String>, DockerFileContentModifier>
			dockerFileContentModifierSupplier)
	{
		this.filesToTransferHandler.withDockerFileContentModifierSupplier(dockerFileContentModifierSupplier);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withDockerFileLinesModifier(
		final DockerFileLineModifier dockerFileLinesModifier)
	{
		this.filesToTransferHandler.withDockerFileLinesModifier(dockerFileLinesModifier);
		return this.self();
	}
	
	/**
	 * @deprecated Use {@link #configureFilesToTransferHandler(Consumer)}
	 */
	@Deprecated(since = "3.0.0")
	public S withUseWinNTFSJunctionFixIfApplicable(
		final boolean useWinNTFSJunctionFixIfApplicable)
	{
		this.filesToTransferHandler.withUseWinNTFSJunctionFixIfApplicable(useWinNTFSJunctionFixIfApplicable);
		return this.self();
	}
	
	// endregion
	
	public String getDockerImageName()
	{
		return this.dockerImageName;
	}
	
	@SuppressWarnings("unchecked")
	protected S self()
	{
		return (S)this;
	}
}
