package software.xdev.testcontainers.imagebuilder.buildxnative;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import software.xdev.testcontainers.imagebuilder.AbstractImageFromDockerfile;
import software.xdev.testcontainers.imagebuilder.transfer.FastFilePathRelativizer;
import software.xdev.testcontainers.imagebuilder.transfer.FilesToTransferInfo;


/**
 * Builds the image using a new <code>docker build</code> process.
 * <p>
 * Please note that the build process is running completely isolated of Testcontainers.
 * </p>
 * <p>
 * <a href="https://docs.docker.com/reference/cli/docker/buildx/build/">Docker Docs</a>
 * </p>
 */
public class NativeAdvancedImageFromDockerfile
	extends AbstractImageFromDockerfile<NativeAdvancedImageFromDockerfile>
{
	protected List<String> baseCommand = List.of("docker", "build");
	protected List<String> additionalArgs = new ArrayList<>();
	
	protected Duration timeout = Duration.ofMinutes(5);
	
	public NativeAdvancedImageFromDockerfile()
	{
	}
	
	public NativeAdvancedImageFromDockerfile(final String dockerImageName)
	{
		super(dockerImageName);
	}
	
	public NativeAdvancedImageFromDockerfile(final String dockerImageName, final boolean deleteOnExit)
	{
		super(dockerImageName, deleteOnExit);
	}
	
	public NativeAdvancedImageFromDockerfile(
		final String dockerImageName,
		final boolean deleteOnExit,
		final Optional<Logger> optLogger)
	{
		super(dockerImageName, deleteOnExit, optLogger);
	}
	
	protected List<String> buildCommand(final Optional<String> optDockerFilePath)
	{
		final List<String> commandArgs = new ArrayList<>(16
			+ this.additionalArgs.size()
			+ this.buildArgs.size() * 2
			+ (optDockerFilePath.isPresent() ? 2 : 0)
			+ (this.optTarget.isPresent() ? 2 : 0)
		);
		
		commandArgs.addAll(this.baseCommand);
		
		optDockerFilePath.ifPresent(path ->
		{
			commandArgs.add("-f");
			commandArgs.add(path);
		});
		
		this.optTarget.ifPresent(target ->
		{
			commandArgs.add("--target");
			commandArgs.add(target);
		});
		
		this.addKVsToCommand(this.createDefaultLabels(), "--label", commandArgs);
		this.addKVsToCommand(this.buildArgs, "--build-arg", commandArgs);
		
		commandArgs.add("-t");
		commandArgs.add(this.dockerImageName);
		
		commandArgs.addAll(this.additionalArgs);
		
		// Read from in
		commandArgs.add("-");
		
		return commandArgs;
	}
	
	protected void addKVsToCommand(
		final Map<String, String> kvs,
		final String key,
		final List<String> commandArgs)
	{
		kvs.entrySet()
			.stream()
			.map(e ->
				e.getValue() != null
					? e.getKey() + "=" + e.getValue()
					: e.getKey())
			.flatMap(arg -> Stream.of(key, arg))
			.forEach(commandArgs::add);
	}
	
	@Override
	protected String resolve()
	{
		final Path baseDir = this.optBaseDir.orElseThrow(() -> new IllegalStateException("baseDir is required"));
		
		if(!this.disablePull)
		{
			this.optDockerFilePath
				.map(this::fullyResolveDependencyImages)
				.ifPresent(this::prePullDependencyImages);
		}
		
		final FilesToTransferInfo filesToTransferInfo = this.calcFileTransferInfo(baseDir);
		
		final File tempWorkingDir = new File(FileUtils.getTempDirectoryPath(), UUID.randomUUID().toString());
		tempWorkingDir.deleteOnExit();
		if(!tempWorkingDir.mkdir())
		{
			throw new IllegalStateException("TempDir did already exist" + tempWorkingDir);
		}
		this.log().debug("Will use {} as temporary working directory", tempWorkingDir);
		
		this.log().info("Starting building image[name='{}']", this.dockerImageName);
		final long buildStartTime = System.currentTimeMillis();
		
		final ProcessBuilder pb = new ProcessBuilder(this.buildCommand(
			this.optDockerFilePath
				.map(dockerFilePath -> FastFilePathRelativizer.relativize(
					this.optBaseDir.orElse(dockerFilePath.getParent()),
					dockerFilePath))
				.filter(relativePath -> !"Dockerfile".equalsIgnoreCase(relativePath))
		));
		pb.directory(tempWorkingDir);
		pb.redirectInput(filesToTransferInfo.source());
		pb.redirectErrorStream(true);
		
		final Process p;
		try
		{
			p = pb.start();
		}
		catch(final IOException e)
		{
			throw new UncheckedIOException(e);
		}
		final Logger buildLogger = this.calcLoggerForBuild();
		this.logStream(p.getInputStream(), buildLogger::info);
		
		try
		{
			if(!p.waitFor(this.timeout.toMillis(), TimeUnit.MILLISECONDS))
			{
				p.destroy();
				throw new IllegalStateException("Build process for " + this.dockerImageName + " timed out");
			}
			
			final int exitCode = p.exitValue();
			if(exitCode != 0)
			{
				throw new IllegalStateException(
					"Build process for " + this.dockerImageName + " exited with code " + exitCode);
			}
			
			this.log().info(
				"Building of image[name='{}'] was done in {}ms",
				this.dockerImageName,
				System.currentTimeMillis() - buildStartTime);
		}
		catch(final InterruptedException iex)
		{
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Got interrupted", iex);
		}
		finally
		{
			CompletableFuture.runAsync(
				() -> {
					try
					{
						filesToTransferInfo.reportConsumed();
						FileUtils.deleteQuietly(tempWorkingDir);
					}
					catch(final Exception ex)
					{
						this.log().warn("Cleanup failed", ex);
					}
				}, this.executorService());
		}
		
		return this.dockerImageName;
	}
	
	protected void logStream(final InputStream src, final Consumer<String> logFunc)
	{
		CompletableFuture.runAsync(
			() ->
			{
				try
				{
					final Scanner scanner = new Scanner(src);
					while(scanner.hasNextLine())
					{
						logFunc.accept(scanner.nextLine());
					}
				}
				catch(final Exception ex)
				{
					this.log().warn("Logging failed", ex);
				}
			}, this.executorService());
	}
	
	// region with
	
	public NativeAdvancedImageFromDockerfile withBaseCommand(final List<String> baseCommand)
	{
		this.baseCommand = baseCommand;
		return this;
	}
	
	public NativeAdvancedImageFromDockerfile withTimeout(final Duration timeout)
	{
		this.timeout = timeout;
		return this;
	}
	
	public NativeAdvancedImageFromDockerfile withAdditionalArgs(final String... args)
	{
		return this.withAdditionalArgs(List.of(args));
	}
	
	public NativeAdvancedImageFromDockerfile withAdditionalArgs(final List<String> args)
	{
		this.additionalArgs.addAll(args);
		return this;
	}
	
	// endregion
}
