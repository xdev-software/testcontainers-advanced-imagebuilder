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
package software.xdev.testcontainers.imagebuilder.compat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class DockerfileCOPYParentsEmulatorTest
{
	@Test
	void simpleCheck()
	{
		final List<String> lines = new DockerfileCOPYParentsEmulator().modify(
			List.of(
				"# syntax=docker/dockerfile:1-labs",
				"FROM alpine:3",
				"COPY --parents mvnw .mvn/** --abc ./",
				"COPY --parents **/pom.xml ./",
				"COPY --parents abc/def.txt ./",
				"COPY --parents ./d/e/** ./",
				"COPY ./d/e/** ./", // Keep original
				"COPY --parents ./it/mvnw ./it/.mvn/** ./xx"
			), Set.of(
				"mvnw",
				".mvn/wrapper/maven-wrapper.properties",
				"Dockerfile",
				"pom.xml",
				"a/pom.xml",
				"a/b/pom.xml",
				"a/b/c/pom.xml",
				"abc/def.txt",
				"ignoreme.txt",
				"d/e/example.txt",
				"it/mvnw",
				"it/.mvn/wrapper/maven-wrapper.properties"
			));
		Assertions.assertIterableEquals(
			List.of(
				"# syntax=docker/dockerfile:1-labs",
				"FROM alpine:3",
				"COPY mvnw --abc ./mvnw",
				"COPY .mvn/wrapper/maven-wrapper.properties --abc ./.mvn/wrapper/maven-wrapper.properties",
				"COPY a/b/c/pom.xml ./a/b/c/pom.xml",
				"COPY a/b/pom.xml ./a/b/pom.xml",
				"COPY a/pom.xml ./a/pom.xml",
				"COPY pom.xml ./pom.xml",
				"COPY abc/def.txt ./abc/def.txt",
				"COPY d/e/example.txt ./d/e/example.txt",
				"COPY ./d/e/** ./", // Keep original
				"COPY ./it/mvnw ./xx/it/mvnw",
				"COPY it/.mvn/wrapper/maven-wrapper.properties ./xx/it/.mvn/wrapper/maven-wrapper.properties"
			),
			lines);
	}
}
