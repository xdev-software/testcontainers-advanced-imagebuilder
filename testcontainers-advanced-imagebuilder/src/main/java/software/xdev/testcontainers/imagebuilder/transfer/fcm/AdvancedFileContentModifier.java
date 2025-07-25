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
