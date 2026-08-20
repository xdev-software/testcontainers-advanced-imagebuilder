package software.xdev;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.slf4j.LoggerFactory;

import software.xdev.testcontainers.imagebuilder.AdvancedImageFromDockerFile;
import software.xdev.testcontainers.imagebuilder.compat.DockerfileCOPYParentsEmulator;
import software.xdev.testcontainers.imagebuilder.transfer.fcm.FileLinesContentModifier;


public final class Application
{
	@SuppressWarnings({
		"java:S106",
		"java:S4507",
		"PMD.SystemPrintln",
		"PMD.AvoidPrintStackTrace"
	}) // Just a demo
	public static void main(final String[] args)
	{
		final AdvancedImageFromDockerFile builder = new AdvancedImageFromDockerFile("dynamically-built")
			.withLoggerForBuild(LoggerFactory.getLogger("container.build"))
			.withDockerFilePath(Paths.get("../testcontainers-advanced-imagebuilder-demo/Dockerfile"))
			.withBaseDir(Paths.get("../"))
			.configureFilesToTransferHandler(h -> h
				.withDockerFileLinesModifier(new DockerfileCOPYParentsEmulator())
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
				))
			);
		
		try
		{
			final String imageName = builder.build(Duration.ofMinutes(5));
			System.out.println("Successfully build " + imageName);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private Application()
	{
	}
}
