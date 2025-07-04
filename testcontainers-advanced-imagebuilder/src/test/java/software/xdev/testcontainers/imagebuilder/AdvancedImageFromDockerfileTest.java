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

import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import software.xdev.testcontainers.imagebuilder.compat.DockerfileCOPYParentsEmulator;


class AdvancedImageFromDockerfileTest
{
	// Not it can't be written as a method reference (same method name)
	@SuppressWarnings({"java:S1612", "PMD.LambdaCanBeMethodReference"})
	@Test
	void simpleCheck()
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
				"*.md",
				"*.cmd",
				"/renovate.json5",
				// We need to keep the pom.xml as maven can't resolve the modules otherwise
				"testcontainers-advanced-imagebuilder/src/**",
				"testcontainers-advanced-imagebuilder-demo/src/**"
			)
			.withDockerFilePath(Paths.get("../testcontainers-advanced-imagebuilder-demo/Dockerfile"))
			.withBaseDir(Paths.get("../"))
			.withDockerFileLinesModifier(new DockerfileCOPYParentsEmulator());
		
		Assertions.assertDoesNotThrow(() -> builder.get());
	}
}
