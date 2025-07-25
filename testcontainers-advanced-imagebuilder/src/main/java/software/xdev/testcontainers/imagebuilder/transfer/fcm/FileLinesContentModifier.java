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
