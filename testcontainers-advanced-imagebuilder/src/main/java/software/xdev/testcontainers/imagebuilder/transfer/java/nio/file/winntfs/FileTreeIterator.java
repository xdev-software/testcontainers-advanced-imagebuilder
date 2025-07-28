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

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs.FileTreeWalker.Event;


/**
 * An {@code Iterator} to iterate over the nodes of a file tree.
 * <p>
 * {@snippet lang = java:
 *     try (FileTreeIterator iterator = new FileTreeIterator(start, maxDepth, options)) {
 *         while (iterator.hasNext()) {
 *             Event ev = iterator.next();
 *             Path path = ev.file();
 *             BasicFileAttributes attrs = ev.attributes();
 *         }
 *     }
 *}
 */
public class FileTreeIterator implements Iterator<Event>, Closeable
{
	private final FileTreeWalker walker;
	private Event next;
	
	/**
	 * Creates a new iterator to walk the file tree starting at the given file.
	 *
	 * @throws IllegalArgumentException if {@code maxDepth} is negative
	 * @throws IOException              if an I/O errors occurs opening the starting file
	 * @throws SecurityException        if the security manager denies access to the starting file
	 * @throws NullPointerException     if {@code start} or {@code options} is {@code null} or the options array
	 *                                  contains a {@code null} element
	 */
	FileTreeIterator(final Path start, final int maxDepth, final FileVisitOption... options)
		throws IOException
	{
		this.walker = new FileTreeWalker(List.of(options), maxDepth);
		this.next = this.walker.walk(start);
		assert this.next.type() == FileTreeWalker.EventType.ENTRY
			|| this.next.type() == FileTreeWalker.EventType.START_DIRECTORY;
		
		// IOException if there a problem accessing the starting file
		final IOException ioe = this.next.ioeException();
		if(ioe != null)
		{
			throw ioe;
		}
	}
	
	private void fetchNextIfNeeded()
	{
		if(this.next == null)
		{
			FileTreeWalker.Event ev = this.walker.next();
			while(ev != null)
			{
				final IOException ioe = ev.ioeException();
				if(ioe != null)
				{
					throw new UncheckedIOException(ioe);
				}
				
				// END_DIRECTORY events are ignored
				if(ev.type() != FileTreeWalker.EventType.END_DIRECTORY)
				{
					this.next = ev;
					return;
				}
				ev = this.walker.next();
			}
		}
	}
	
	@Override
	public boolean hasNext()
	{
		if(!this.walker.isOpen())
		{
			throw new IllegalStateException();
		}
		this.fetchNextIfNeeded();
		return this.next != null;
	}
	
	@Override
	public Event next()
	{
		if(!this.walker.isOpen())
		{
			throw new IllegalStateException();
		}
		this.fetchNextIfNeeded();
		if(this.next == null)
		{
			throw new NoSuchElementException();
		}
		final Event result = this.next;
		this.next = null;
		return result;
	}
	
	@Override
	public void close()
	{
		this.walker.close();
	}
}
