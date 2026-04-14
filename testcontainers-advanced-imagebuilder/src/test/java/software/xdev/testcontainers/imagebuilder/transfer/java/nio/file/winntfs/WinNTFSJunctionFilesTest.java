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
package software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;


class WinNTFSJunctionFilesTest
{
	@Test
	@EnabledOnOs(OS.WINDOWS)
	void junctionNoCrash(@TempDir final Path tempDir) throws IOException
	{
		final Path dummyDir = tempDir.resolve("dummy/");
		final Path sub = dummyDir.resolve("sub/");
		Files.createDirectories(sub);
		
		if(!WinNTFSJunctionFiles.shouldBeApplied(tempDir))
		{
			Assumptions.abort("Test is not applicable");
		}
		
		final Path testFile = sub.resolve("test.txt");
		Files.createFile(testFile);
		
		final String junctionName = "junction";
		doMklink(junctionName, dummyDir, sub);
		
		final Path junction = sub.resolve(junctionName);
		
		final BasicFileAttributes junctionAttrs =
			Files.readAttributes(junction, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		assertAll(
			() -> assertFalse(junctionAttrs.isRegularFile()),
			() -> assertTrue(junctionAttrs.isDirectory()),
			() -> assertFalse(junctionAttrs.isSymbolicLink()),
			() -> assertTrue(junctionAttrs.isOther())
		);
		
		final Exception exception = assertThrows(
			UncheckedIOException.class,
			() -> Files.find(
					dummyDir,
					Integer.MAX_VALUE,
					(file, attrs) -> attrs.isRegularFile())
				.toList()
		);
		assertInstanceOf(FileSystemException.class, exception.getCause());
		
		final List<Path> files = WinNTFSJunctionFiles.find(
				dummyDir,
				Integer.MAX_VALUE,
				(file, attrs) -> attrs.isRegularFile())
			.toList();
		
		assertEquals(1, files.size());
		assertEquals(testFile, files.get(0));
	}
	
	@SuppressWarnings("PMD.SystemPrintln")
	private static void doMklink(final String name, final Path target, final Path workdir) throws IOException
	{
		final ProcessBuilder pb = new ProcessBuilder();
		pb.command("cmd.exe", "/c", "mklink /J " + name + " \"" + target.toAbsolutePath() + "\"");
		pb.directory(workdir.toFile());
		pb.redirectErrorStream(true);
		final Process p = pb.start();
		final BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while(true)
		{
			line = r.readLine();
			if(line == null)
			{
				break;
			}
			System.out.println(line);
		}
	}
}
