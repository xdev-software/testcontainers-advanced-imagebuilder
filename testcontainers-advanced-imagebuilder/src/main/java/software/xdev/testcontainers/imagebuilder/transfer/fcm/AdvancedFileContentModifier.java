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
import java.nio.file.Path;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;


public interface AdvancedFileContentModifier<T> extends FileContentModifier
{
	@Override
	default InputStream apply(final Path sourcePath, final String targetPath, final TarArchiveEntry tarArchiveEntry)
		throws IOException
	{
		if(!this.shouldApply(sourcePath, targetPath, tarArchiveEntry))
		{
			return null;
		}
		
		final T original = this.readOriginal(sourcePath, targetPath, tarArchiveEntry);
		final T created = this.modify(original, sourcePath, targetPath, tarArchiveEntry);
		if(this.isIdentical(original, created))
		{
			return null;
		}
		
		return this.createInputStream(this.createInputStreamData(created), tarArchiveEntry);
	}
	
	default boolean shouldApply(final Path sourcePath, final String targetPath, final TarArchiveEntry tarArchiveEntry)
	{
		return true;
	}
	
	T readOriginal(Path sourcePath, String targetPath, TarArchiveEntry tarArchiveEntry) throws IOException;
	
	T modify(T lines, Path sourcePath, String targetPath, TarArchiveEntry tarArchiveEntry) throws IOException;
	
	default boolean isIdentical(final T original, final T created)
	{
		return original.equals(created);
	}
	
	byte[] createInputStreamData(T data) throws IOException;
	
	default ByteArrayInputStream createInputStream(
		final byte[] data,
		final TarArchiveEntry tarArchiveEntry)
	{
		tarArchiveEntry.setSize(data.length);
		return new ByteArrayInputStream(data);
	}
}
