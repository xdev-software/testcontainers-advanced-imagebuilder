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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;


/**
 * Forked from {@link org.testcontainers.shaded.com.github.dockerjava.core.util.CompressArchiveUtil} to allow file
 * manipulation
 */
public class TransferArchiveTARCompressor
{
	private final List<FileContentModifier> fileContentModifiers = new ArrayList<>();
	
	public TransferArchiveTARCompressor withContentModifier(final FileContentModifier modifier)
	{
		this.fileContentModifiers.add(modifier);
		return this;
	}
	
	public File archiveTARFiles(
		final File base,
		final Iterable<Path> filesPaths,
		final String archiveNameWithOutExtension) throws IOException
	{
		final File tarFile = new File(FileUtils.getTempDirectoryPath(), archiveNameWithOutExtension + ".tar");
		tarFile.deleteOnExit();
		
		try(final TarArchiveOutputStream tos =
			new TarArchiveOutputStream(new GZIPOutputStream(new BufferedOutputStream(
				new FileOutputStream(tarFile)))))
		{
			tos.setLongFileMode(3);
			tos.setBigNumberMode(2);
			
			for(final Path filePath : filesPaths)
			{
				this.addFileToTar(tos, filePath, FastFilePathUtil.relativize(base.toPath(), filePath));
			}
		}
		
		return tarFile;
	}
	
	@SuppressWarnings("checkstyle:MagicNumber")
	protected void addFileToTar(
		final TarArchiveOutputStream tarArchiveOutputStream,
		final Path file,
		final String entryName
	) throws IOException
	{
		try
		{
			if(Files.isSymbolicLink(file))
			{
				final TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(entryName, (byte)50);
				tarArchiveEntry.setLinkName(Files.readSymbolicLink(file).toString());
				tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
				return;
			}
			
			final TarArchiveEntry tarArchiveEntry =
				(TarArchiveEntry)tarArchiveOutputStream.createArchiveEntry(file.toFile(), entryName);
			if(file.toFile().canExecute())
			{
				tarArchiveEntry.setMode(tarArchiveEntry.getMode() | 493);
			}
			
			if(!file.toFile().isFile())
			{
				tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
				return;
			}
			
			try(final InputStream input = this.createInputStreamForFile(file, tarArchiveEntry))
			{
				// put it after it was modified
				tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
				IOUtils.copy(input, tarArchiveOutputStream);
			}
		}
		finally
		{
			tarArchiveOutputStream.closeArchiveEntry();
		}
	}
	
	protected InputStream createInputStreamForFile(final Path filePath, final TarArchiveEntry tarArchiveEntry)
		throws IOException
	{
		for(final FileContentModifier fcm : this.fileContentModifiers)
		{
			final InputStream is = fcm.apply(filePath, tarArchiveEntry);
			if(is != null)
			{
				return is;
			}
		}
		return new BufferedInputStream(Files.newInputStream(filePath));
	}
}
