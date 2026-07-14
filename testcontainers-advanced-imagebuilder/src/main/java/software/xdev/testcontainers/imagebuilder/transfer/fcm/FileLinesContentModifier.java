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
package software.xdev.testcontainers.imagebuilder.transfer.fcm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;


public interface FileLinesContentModifier extends AdvancedFileContentModifier<List<String>>
{
	@Override
	default List<String> readOriginal(
		final Path sourcePath,
		final String targetPath,
		final TarArchiveEntry tarArchiveEntry) throws IOException
	{
		return Files.readAllLines(sourcePath);
	}
	
	@Override
	default byte[] createInputStreamData(final List<String> data)
	{
		return String.join("\n", data).getBytes(StandardCharsets.UTF_8);
	}
}
