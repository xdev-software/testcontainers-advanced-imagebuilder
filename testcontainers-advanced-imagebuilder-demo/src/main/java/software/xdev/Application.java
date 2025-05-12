package software.xdev;

import java.nio.file.Paths;

import org.slf4j.LoggerFactory;

import software.xdev.testcontainers.imagebuilder.AdvancedImageFromDockerFile;


public final class Application
{
	@SuppressWarnings({"java:S106", "PMD.SystemPrintln"}) // Just a demo
	public static void main(final String[] args)
	{
		final AdvancedImageFromDockerFile builder = new AdvancedImageFromDockerFile("dynamically-built")
			.withLoggerForBuild(LoggerFactory.getLogger("container.build"))
			.withAdditionalIgnoreLines(
				// Ignore files that aren't related to the built code
				".git/**",
				".config/**",
				".github/**",
				".idea/**",
				".run/**",
				".md",
				".cmd",
				"/renovate.json5",
				// We need to keep the pom.xml as maven can't resolve the modules otherwise
				"testcontainers-advanced-imagebuilder/src/**",
				"testcontainers-advanced-imagebuilder/test/**",
				"testcontainers-advanced-imagebuilder-demo/src/**"
			)
			.withDockerFilePath(Paths.get("../testcontainers-advanced-imagebuilder-demo/Dockerfile"))
			.withBaseDir(Paths.get("../"));
		
		final String imageName = builder.get();
		
		System.out.println("Successfully build " + imageName);
	}
	
	private Application()
	{
	}
}
