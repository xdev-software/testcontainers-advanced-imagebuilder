package software.xdev.testcontainers.imagebuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.junit.jupiter.api.Assumptions;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;

import software.xdev.testcontainers.imagebuilder.compat.DockerfileCOPYParentsEmulator;
import software.xdev.testcontainers.imagebuilder.transfer.fcm.FileLinesContentModifier;


public abstract class AbstractBuildTest
{
	protected void checkIfDockerIsPresentOrAbort()
	{
		try
		{
			// noinspection resource
			DockerClientFactory.instance().client();
		}
		catch(final IllegalStateException iex)
		{
			Assumptions.abort("Failed to find docker environment: " + iex.getMessage());
		}
	}
	
	protected <I extends AbstractImageFromDockerfile<I>> I configureDefault(final I builder)
	{
		return builder
			.withLoggerForBuild(LoggerFactory.getLogger("container.build"))
			.withDockerFilePath(Paths.get("../testcontainers-advanced-imagebuilder-demo/Dockerfile"))
			.withBaseDir(Paths.get("../"))
			.withCreateTransferFilesCache(true)
			.configureFilesToTransferHandler(h -> h
				.withPostGitIgnoreLines(
					// Ignore files that aren't related to the built code
					".git/**",
					".config/**",
					".github/**",
					".idea/**",
					".run/**",
					"*.md",
					"*.cmd",
					"/renovate.json5",
					"testcontainers-advanced-imagebuilder/**",
					"testcontainers-advanced-imagebuilder-demo/**"
				)
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
				))
			);
	}
}
