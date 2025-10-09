package software.xdev;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.slf4j.LoggerFactory;

import software.xdev.testcontainers.imagebuilder.AdvancedImageFromDockerFile;
import software.xdev.testcontainers.imagebuilder.compat.DockerfileCOPYParentsEmulator;
import software.xdev.testcontainers.imagebuilder.transfer.fcm.FileLinesContentModifier;


public final class Application
{
	@SuppressWarnings({"java:S106", "PMD.SystemPrintln"}) // Just a demo
	public static void main(final String[] args)
	{
		final AdvancedImageFromDockerFile builder = new AdvancedImageFromDockerFile("dynamically-built")
			.withLoggerForBuild(LoggerFactory.getLogger("container.build"))
			.withPostGitIgnoreLines(
				// Ignore files that aren't related to the built code
				".git/**",
				".config/**",
				".github/**",
				".idea/**",
				".run/**",
				".md",
				".cmd",
				"/renovate.json5",
				"testcontainers-advanced-imagebuilder/**",
				"testcontainers-advanced-imagebuilder-demo/**"
			)
			.withDockerFilePath(Paths.get("../testcontainers-advanced-imagebuilder-demo/Dockerfile"))
			.withBaseDir(Paths.get("../"))
			.withDockerFileLinesModifier(new DockerfileCOPYParentsEmulator())
			// Only copy the required maven modules and remove the not required ones
			.withTransferArchiveTARCompressorCustomizer(c -> c.withContentModifier(
				new FileLinesContentModifier()
				{
					@Override
					public boolean shouldApply(
						final Path sourcePath,
						final String targetPath,
						final TarArchiveEntry tarArchiveEntry)
					{
						return "pom.xml".equals(targetPath);
					}
					
					@Override
					public List<String> modify(
						final List<String> lines,
						final Path sourcePath,
						final String targetPath,
						final TarArchiveEntry tarArchiveEntry)
					{
						return lines.stream()
							// Only keep the dummy-app submodule as this is only needed for building
							.filter(s -> !(s.contains("<module>testcontainers-advanced-imagebuilder")
								&& !s.contains("<module>testcontainers-advanced-imagebuilder-dummy-app")))
							.toList();
					}
					
					@Override
					public boolean isIdentical(final List<String> original, final List<String> created)
					{
						return original.size() == created.size();
					}
				}
			));
		
		final String imageName;
		try
		{
			imageName = builder.get(5, TimeUnit.MINUTES);
		}
		catch(final TimeoutException e)
		{
			System.out.println("Timed out: " + e.getMessage());
			System.exit(1);
			return;
		}
		
		System.out.println("Successfully build " + imageName);
	}
	
	private Application()
	{
	}
}
