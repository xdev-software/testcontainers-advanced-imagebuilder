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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
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
import org.testcontainers.shaded.com.github.dockerjava.core.util.FilePathUtil;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.utility.Base58;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.DockerLoggerFactory;
import org.testcontainers.utility.ImageNameSubstitutor;
import org.testcontainers.utility.LazyFuture;
import org.testcontainers.utility.ResourceReaper;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;


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
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "PMD.GodClass"})
public class AdvancedImageFromDockerFile
	extends LazyFuture<String>
	implements
	BuildContextBuilderTrait<AdvancedImageFromDockerFile>,
	ClasspathTrait<AdvancedImageFromDockerFile>,
	FilesTrait<AdvancedImageFromDockerFile>,
	StringsTrait<AdvancedImageFromDockerFile>,
	DockerfileTrait<AdvancedImageFromDockerFile>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(AdvancedImageFromDockerFile.class);
	protected final String dockerImageName;
	
	protected final boolean deleteOnExit;
	protected final Map<String, Transferable> transferables = new HashMap<>();
	protected final Map<String, String> buildArgs = new HashMap<>();
	protected Logger loggerForBuild;
	protected Optional<Path> dockerFilePath = Optional.empty();
	protected Optional<Path> baseDir = Optional.empty();
	protected Optional<Path> baseDirRelativeIgnoreFile = Optional.of(Paths.get(".gitignore"));
	protected List<String> additionalIgnoreLines = new ArrayList<>();
	protected Optional<String> target = Optional.empty();
	protected final Set<Consumer<BuildImageCmd>> buildImageCmdModifiers = new LinkedHashSet<>();
	protected Set<String> dependencyImageNames = Collections.emptySet();
	
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
		this.dockerImageName = dockerImageName;
		this.deleteOnExit = deleteOnExit;
	}
	
	@Override
	public AdvancedImageFromDockerFile withFileFromTransferable(final String path, final Transferable transferable)
	{
		final Transferable oldValue = this.transferables.put(path, transferable);
		
		if(oldValue != null)
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
	
	@SuppressWarnings("checkstyle:MagicNumber")
	@Override
	protected String resolve()
	{
		final Logger logger = Optional.ofNullable(this.loggerForBuild)
			.orElseGet(() -> DockerLoggerFactory.getLogger(this.dockerImageName));
		
		final DockerClient dockerClient = DockerClientFactory.instance().client();
		
		this.log().info("Starting resolving image[name='{}']", this.dockerImageName);
		
		try
		{
			// We have to use pipes to avoid high memory consumption since users might want to build huge images
			final PipedInputStream in = new PipedInputStream();
			final PipedOutputStream out = new PipedOutputStream(in);
			
			final BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(in);
			
			this.configure(buildImageCmd);
			
			final Map<String, String> labels = new HashMap<>();
			if(buildImageCmd.getLabels() != null)
			{
				labels.putAll(buildImageCmd.getLabels());
			}
			this.deleteImageOnExitIfRequired(labels);
			labels.putAll(DockerClientFactory.DEFAULT_LABELS);
			buildImageCmd.withLabels(labels);
			
			this.prePullDependencyImages(this.dependencyImageNames);
			
			this.log().info("Starting building image[name='{}']", this.dockerImageName);
			final long buildStartTime = System.currentTimeMillis();
			
			final BuildImageResultCallback exec = buildImageCmd.exec(this.getBuildImageResultCallback(logger));
			
			final long bytesToDockerDaemon = this.getBytesToDockerDaemon(out);
			
			if(this.log().isInfoEnabled())
			{
				this.log().info(
					"Transferred {} manually (not actually) to Docker daemon",
					FileUtils.byteCountToDisplaySize(bytesToDockerDaemon));
			}
			if(this.log().isWarnEnabled() && bytesToDockerDaemon > FileUtils.ONE_MB * 50)
			{
				this.log().warn(
					"A large amount of data was sent to the Docker daemon ({})."
						+ " Consider using a .dockerignore file for better performance.",
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
			
			for(final Entry<String, Transferable> entry : this.transferables.entrySet())
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
	
	@SuppressWarnings({"java:S3776", "PMD.CognitiveComplexity"}) // 30 LoC are not that hard to read...
	protected BuildImageResultCallback getBuildImageResultCallback(final Logger logger)
	{
		return new BuildImageResultCallback()
		{
			private final List<String> notFlushedString = new ArrayList<>();
			
			@Override
			public void onNext(final BuildResponseItem item)
			{
				super.onNext(item);
				
				if(item.isErrorIndicated())
				{
					if(logger.isInfoEnabled())
					{
						logger.info(removeEnd(String.join("", this.notFlushedString), "\n"));
					}
					this.notFlushedString.clear();
					
					logger.error(item.getErrorDetail() != null ? item.getErrorDetail().getMessage() : "<null>");
				}
				else if(item.getStream() != null)
				{
					final String details = item.getStream();
					
					this.notFlushedString.add(details);
					
					if(details.endsWith("\n") || this.notFlushedString.size() > 1000)
					{
						if(logger.isInfoEnabled())
						{
							logger.info(removeEnd(String.join("", this.notFlushedString), "\n"));
						}
						this.notFlushedString.clear();
					}
				}
			}
		};
	}
	
	// From Apache Commons Lang3 - StringUtils
	protected static String removeEnd(final String str, final String remove)
	{
		if(str == null || str.isEmpty() || remove == null || remove.isEmpty())
		{
			return str;
		}
		if(str.endsWith(remove))
		{
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}
	
	protected void configure(final BuildImageCmd buildImageCmd)
	{
		this.log().info("Configuring...");
		buildImageCmd.withTags(new HashSet<>(Collections.singletonList(this.getDockerImageName())));
		
		this.dockerFilePath.ifPresent(p -> {
			buildImageCmd.withDockerfilePath(FilePathUtil.relativize(this.baseDir.orElse(p.getParent()), p));
			
			final AdvancedParsedDockerfile parsedDockerFile = new AdvancedParsedDockerfile(p);
			
			this.log().info("Resolving dependency images...");
			this.dependencyImageNames = this.fullyResolveDependencyImages(
				parsedDockerFile.getDependencyImageNames(),
				parsedDockerFile.getArguments());
			
			if(!this.dependencyImageNames.isEmpty())
			{
				// if we'll be pre-pulling images, disable the built-in pull as it is not necessary and will fail for
				// authenticated registries
				buildImageCmd.withPull(false);
			}
		});
		
		// NOTE: Testcontainers internal .dockerignore processor is completely broken
		// -> We use our own docker/gitignore processor here
		if(this.baseDir.isPresent())
		{
			this.log().info(
				"Calculating files to transfer to docker[baseDir={},baseDirRelativeIgnoreFile={}]",
				this.baseDir.get(),
				this.baseDirRelativeIgnoreFile.orElse(null));
			final TransferFilesCreator tfc =
				new TransferFilesCreator(this.baseDir.get(), this.baseDirRelativeIgnoreFile.orElse(null));
			
			final List<Path> filesToTransfer = tfc.getFilesToTransfer(this.additionalIgnoreLines);
			
			this.log().info("{}x files will be transferred", filesToTransfer.size());
			if(this.log().isDebugEnabled())
			{
				filesToTransfer.forEach(p -> this.log().debug("Will transmit: '{}'", p));
			}
			
			this.log().info("Building InputStream with docker-context...");
			buildImageCmd.withTarInputStream(tfc.getAllFilesToTransferAsTarInputStream(filesToTransfer));
			this.log().info("InputStream handed over to Docker");
		}
		this.baseDir.ifPresent(d -> buildImageCmd.withBaseDirectory(d.toFile()));
		
		this.buildArgs.forEach(buildImageCmd::withBuildArg);
		this.target.ifPresent(buildImageCmd::withTarget);
		this.buildImageCmdModifiers.forEach(hook -> hook.accept(buildImageCmd));
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
			.forEach(e -> resolvedArgs.put(e.getKey(), e.getValue().get()));
		
		final Set<String> resolvedDependencyImages = new HashSet<>();
		
		fileDependencyImages
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
			.forEach(resolvedDependencyImages::add);
		
		return resolvedDependencyImages;
	}
	
	protected void prePullDependencyImages(final Set<String> imagesToPull)
	{
		imagesToPull
			.stream()
			.map(imageName -> CompletableFuture.runAsync(() -> {
				try
				{
					this.log().info(
						"Pre-emptively checking local images for '{}', referenced via a Dockerfile."
							+ " If not available, it will be pulled.",
						imageName);
					new RemoteDockerImage(DockerImageName.parse(imageName))
						.withImageNameSubstitutor(ImageNameSubstitutor.noop())
						.get();
				}
				catch(final Exception e)
				{
					this.log().warn(
						"Unable to pre-fetch an image ({}) depended upon by Dockerfile -"
							+ " image build will continue but may fail. Exception message was: {}",
						imageName,
						e.getMessage());
				}
			}))
			.toList()
			.forEach(CompletableFuture::join);
	}
	
	protected Logger log()
	{
		return LOGGER;
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
	
	public Optional<Path> getDockerFilePath()
	{
		return this.dockerFilePath;
	}
	
	public AdvancedImageFromDockerFile withDockerFilePath(final Path dockerFilePath)
	{
		this.dockerFilePath = Optional.ofNullable(dockerFilePath);
		return this;
	}
	
	public Optional<Path> getBaseDir()
	{
		return this.baseDir;
	}
	
	public AdvancedImageFromDockerFile withBaseDir(final Path baseDir)
	{
		this.baseDir = Optional.of(baseDir);
		return this;
	}
	
	public Optional<Path> getBaseDirRelativeIgnoreFile()
	{
		return this.baseDirRelativeIgnoreFile;
	}
	
	public AdvancedImageFromDockerFile withBaseDirRelativeIgnoreFile(final Path baseDirRelativeIgnoreFile)
	{
		this.baseDirRelativeIgnoreFile = Optional.ofNullable(baseDirRelativeIgnoreFile);
		return this;
	}
	
	public List<String> getAdditionalIgnoreLines()
	{
		return this.additionalIgnoreLines;
	}
	
	public AdvancedImageFromDockerFile withAdditionalIgnoreLines(final String... additionalIgnoreLines)
	{
		this.additionalIgnoreLines = Arrays.asList(additionalIgnoreLines);
		return this;
	}
	
	public AdvancedImageFromDockerFile withTarget(final String target)
	{
		this.target = Optional.of(target);
		return this;
	}
	
	public AdvancedImageFromDockerFile withBuildImageCmdModifier(final Consumer<BuildImageCmd> modifier)
	{
		this.buildImageCmdModifiers.add(modifier);
		return this;
	}
	
	public boolean isDeleteOnExit()
	{
		return this.deleteOnExit;
	}
	
	public Set<String> getDependencyImageNames()
	{
		return this.dependencyImageNames;
	}
	
	public String getDockerImageName()
	{
		return this.dockerImageName;
	}
	
	public Map<String, Transferable> getTransferables()
	{
		return this.transferables;
	}
	
	public Map<String, String> getBuildArgs()
	{
		return this.buildArgs;
	}
}
