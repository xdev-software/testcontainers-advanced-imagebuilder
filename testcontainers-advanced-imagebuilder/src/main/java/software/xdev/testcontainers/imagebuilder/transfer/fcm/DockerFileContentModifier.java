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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import software.xdev.testcontainers.imagebuilder.transfer.DockerFileLineModifier;


public class DockerFileContentModifier implements FileContentModifier
{
	protected final Path dockerFilePath;
	protected final List<DockerFileLineModifier> linesModifiers;
	protected final Set<String> allRelativeFilePaths;
	
	public DockerFileContentModifier(
		final Path dockerFilePath,
		final List<DockerFileLineModifier> linesModifiers,
		final Collection<String> allRelativeFilePaths)
	{
		this.dockerFilePath = dockerFilePath;
		this.linesModifiers = linesModifiers;
		this.allRelativeFilePaths = new HashSet<>(allRelativeFilePaths);
	}
	
	@Override
	public InputStream apply(
		final Path sourcePath,
		final String targetPath,
		final TarArchiveEntry tarArchiveEntry) throws IOException
	{
		if(!this.dockerFilePath.equals(sourcePath))
		{
			return null;
		}
		
		List<String> lines = Files.readAllLines(sourcePath);
		
		for(final DockerFileLineModifier lineModifier : this.linesModifiers)
		{
			lines = lineModifier.modify(lines, this.allRelativeFilePaths);
		}
		
		final byte[] data = String.join("\n", lines).getBytes(StandardCharsets.UTF_8);
		tarArchiveEntry.setSize(data.length);
		return new ByteArrayInputStream(data);
	}
}
