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
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;

import software.xdev.testcontainers.imagebuilder.compat.DockerfileCOPYParentsEmulator;
import software.xdev.testcontainers.imagebuilder.transfer.fcm.FileLinesContentModifier;


class AdvancedImageFromDockerfileTest
{
	// Not it can't be written as a method reference (same method name)
	@SuppressWarnings({"java:S1612", "PMD.LambdaCanBeMethodReference"})
	@Test
	void simpleCheck()
	{
		try
		{
			DockerClientFactory.instance().client();
		}
		catch(final IllegalStateException iex)
		{
			Assumptions.abort("Failed to find docker environment: " + iex.getMessage());
		}
		
		final AdvancedImageFromDockerFile builder = new AdvancedImageFromDockerFile("dynamically-built")
			.withLoggerForBuild(LoggerFactory.getLogger("container.build"))
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
		
		Assertions.assertDoesNotThrow(() -> builder.get(5, TimeUnit.MINUTES));
	}
}
