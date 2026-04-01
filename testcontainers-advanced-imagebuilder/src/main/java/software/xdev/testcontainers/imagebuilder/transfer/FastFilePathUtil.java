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
package software.xdev.testcontainers.imagebuilder.transfer;

import java.nio.file.Path;


/**
 * Fork of {@link org.testcontainers.shaded.com.github.dockerjava.core.util.FilePathUtil} to improve performance
 */
public final class FastFilePathUtil
{
	// Original code uses Path.toURI() or similar code for file which is extremely slow (150x) because
	// it queries the file attributes for each file (on Windows)
	public static String relativize(final Path baseDir, final Path file)
	{
		final String path = baseDir.relativize(file).toString();
		return !"/".equals(baseDir.getFileSystem().getSeparator())
			? path.replace(baseDir.getFileSystem().getSeparator(), "/")
			: path;
	}
	
	private FastFilePathUtil()
	{
	}
}
