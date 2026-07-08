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
package software.xdev.testcontainers.imagebuilder.buildxnative;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.Duration;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import software.xdev.testcontainers.imagebuilder.AbstractBuildTest;


class NativeAdvancedImageFromDockerfileTest extends AbstractBuildTest
{
	// Not it can't be written as a method reference (same method name)
	@Test
	void simpleCheck()
	{
		this.checkIfDockerIsPresentOrAbort();
		
		final NativeAdvancedImageFromDockerfile builder = this.configureDefault(
				new NativeAdvancedImageFromDockerfile("dynamically-built"))
			.withBuildArg("CACHE_BUSTER", "native-simpleCheck");
		
		assertDoesNotThrow(() -> builder.build(Duration.ofMinutes(5)));
	}
	
	@DisabledOnOs(OS.WINDOWS) // Has no effect on Windows
	@Test
	void checkGitHubCaching()
	{
		if(System.getenv("GITHUB_ACTIONS") == null)
		{
			Assumptions.abort("Not running on GitHub Actions");
		}
		
		this.checkIfDockerIsPresentOrAbort();
		
		final NativeAdvancedImageFromDockerfile builder = this.configureDefault(
				new NativeAdvancedImageFromDockerfile("dynamically-built-gha"))
			.withBuildArg("CACHE_BUSTER", "native-checkGitHubCaching")
			.withCacheFrom("type=gha,scope=test-native-gha-1")
			.withCacheTo("type=gha,mode=max,scope=test-native-gha-1");
		
		assertDoesNotThrow(() -> builder.build(Duration.ofMinutes(5)));
	}
}
