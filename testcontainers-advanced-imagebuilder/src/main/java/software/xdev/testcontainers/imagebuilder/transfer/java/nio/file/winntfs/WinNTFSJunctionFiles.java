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
/*
 * Copyright (c) 2013, 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileStore;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @see software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs
 */
public final class WinNTFSJunctionFiles
{
	private static final Logger LOG = LoggerFactory.getLogger(WinNTFSJunctionFiles.class);
	
	@SuppressWarnings("checkstyle:MagicNumber")
	public static boolean shouldBeApplied(final Path path)
	{
		// JDK-8364277 was fixed in Java 26
		if(Runtime.version().feature() >= 26)
		{
			LOG.info("WindowsNTFSJunctionFix is no longer required "
				+ "as this is a Java version where JDK-8364277 is fixed");
			return false;
		}
		try
		{
			final FileStore store = Files.getFileStore(path);
			return "WindowsFileStore".equals(store.getClass().getSimpleName())
				&& "NTFS".equalsIgnoreCase(store.type());
		}
		catch(final IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}
	
	@SuppressWarnings("checkstyle:IllegalCatch")
	public static Stream<Path> find(
		final Path start,
		final int maxDepth,
		final BiPredicate<Path, BasicFileAttributes> matcher,
		final FileVisitOption... options)
		throws IOException
	{
		final FileTreeIterator iterator = new FileTreeIterator(start, maxDepth, options);
		try
		{
			final Spliterator<FileTreeWalker.Event> spliterator =
				Spliterators.spliteratorUnknownSize(iterator, Spliterator.DISTINCT);
			return StreamSupport.stream(spliterator, false)
				.onClose(iterator::close)
				.filter(entry -> matcher.test(entry.file(), entry.attributes()))
				.map(FileTreeWalker.Event::file);
		}
		catch(final Error | RuntimeException e)
		{
			iterator.close();
			throw e;
		}
	}
	
	private WinNTFSJunctionFiles()
	{
	}
}
