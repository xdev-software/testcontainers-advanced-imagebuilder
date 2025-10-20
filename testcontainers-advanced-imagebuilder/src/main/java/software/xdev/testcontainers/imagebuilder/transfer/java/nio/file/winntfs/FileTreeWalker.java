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
 * Copyright (c) 2007, 2024, Oracle and/or its affiliates. All rights reserved.
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Walks a file tree, generating a sequence of events corresponding to the files in the tree.
 * <p>
 * {@snippet lang = java:
 *     Path top = ...
 *     Set<FileVisitOption> options = ...
 *     int maxDepth = ...
 *
 *     try (FileTreeWalker walker = new FileTreeWalker(options, maxDepth)) {
 *         FileTreeWalker.Event ev = walker.walk(top);
 *         do {
 *             process(ev);
 *             ev = walker.next();
 *         } while (ev != null);
 *     }
 *}
 *
 * @see Files#walkFileTree
 */
@SuppressWarnings({"PMD.GodClass", "PMD.CognitiveComplexity"})
public class FileTreeWalker implements Closeable
{
	private final boolean followLinks;
	private final LinkOption[] linkOptions;
	private final int maxDepth;
	private final ArrayDeque<DirectoryNode> stack = new ArrayDeque<>();
	private boolean closed;
	
	
	/**
	 * The element on the walking stack corresponding to a directory node.
	 */
	private static class DirectoryNode
	{
		private final Path dir;
		private final Object key;
		private final DirectoryStream<Path> stream;
		private final Iterator<Path> iterator;
		private boolean skipped;
		
		DirectoryNode(final Path dir, final Object key, final DirectoryStream<Path> stream)
		{
			this.dir = dir;
			this.key = key;
			this.stream = stream;
			this.iterator = stream.iterator();
		}
		
		Path directory()
		{
			return this.dir;
		}
		
		Object key()
		{
			return this.key;
		}
		
		DirectoryStream<Path> stream()
		{
			return this.stream;
		}
		
		Iterator<Path> iterator()
		{
			return this.iterator;
		}
		
		void skip()
		{
			this.skipped = true;
		}
		
		boolean skipped()
		{
			return this.skipped;
		}
	}
	
	
	/**
	 * The event types.
	 */
	enum EventType
	{
		/**
		 * Start of a directory
		 */
		START_DIRECTORY,
		/**
		 * End of a directory
		 */
		END_DIRECTORY,
		/**
		 * An entry in a directory
		 */
		ENTRY
	}
	
	
	/**
	 * Events returned by the {@link #walk} and {@link #next} methods.
	 */
	record Event(EventType type, Path file, BasicFileAttributes attributes, IOException ioeException)
	{
		Event(final EventType type, final Path file, final BasicFileAttributes attrs)
		{
			this(type, file, attrs, null);
		}
		
		Event(final EventType type, final Path file, final IOException ioe)
		{
			this(type, file, null, ioe);
		}
	}
	
	/**
	 * Creates a {@code FileTreeWalker}.
	 *
	 * @throws IllegalArgumentException if {@code maxDepth} is negative
	 * @throws ClassCastException       if {@code options} contains an element that is not a {@code FileVisitOption}
	 * @throws NullPointerException     if {@code options} is {@code null} or the options array contains a {@code null}
	 *                                  element
	 */
	@SuppressWarnings("PMD.ExhaustiveSwitchHasDefault")
	FileTreeWalker(final Collection<FileVisitOption> options, final int maxDepth)
	{
		boolean fl = false;
		for(final FileVisitOption option : options)
		{
			// will throw NPE if options contains null
			switch(option)
			{
				case FOLLOW_LINKS:
					fl = true;
					break;
				default:
					throw new AssertionError("Should not get here");
			}
		}
		if(maxDepth < 0)
		{
			throw new IllegalArgumentException("'maxDepth' is negative");
		}
		
		this.followLinks = fl;
		this.linkOptions = fl ? new LinkOption[0] : new LinkOption[]{LinkOption.NOFOLLOW_LINKS};
		this.maxDepth = maxDepth;
	}
	
	// region Reflect access BasicFileAttributesHolder
	private static boolean initializedBasicFileAttributesHolderClazz;
	private static Class<?> basicFileAttributesHolderClazz;
	private static Method mBasicFileAttributesHolderGet;
	private static boolean wasBasicFileAttributesHolderGetAccessSuccess;
	
	private static void initBasicFileAttributesHolderClazz()
	{
		if(!initializedBasicFileAttributesHolderClazz)
		{
			try
			{
				basicFileAttributesHolderClazz = Class.forName("sun.nio.fs.BasicFileAttributesHolder");
				mBasicFileAttributesHolderGet = basicFileAttributesHolderClazz.getMethod("get");
				mBasicFileAttributesHolderGet.setAccessible(true);
			}
			catch(final Exception ignored)
			{
				// Ignored
			}
			initializedBasicFileAttributesHolderClazz = true;
		}
	}
	
	private static boolean isBasicFileAttributesHolder(final Path file)
	{
		initBasicFileAttributesHolderClazz();
		
		return basicFileAttributesHolderClazz != null
			&& basicFileAttributesHolderClazz.isInstance(file);
	}
	
	private static BasicFileAttributes extractFromBasicFileAttributesHolder(final Path file)
	{
		try
		{
			final BasicFileAttributes attrs = (BasicFileAttributes)mBasicFileAttributesHolderGet.invoke(file);
			wasBasicFileAttributesHolderGetAccessSuccess = true;
			return attrs;
		}
		catch(final IllegalAccessException ex)
		{
			// Did we ever access it successfully?
			if(!wasBasicFileAttributesHolderGetAccessSuccess)
			{
				basicFileAttributesHolderClazz = null;
				mBasicFileAttributesHolderGet = null;
				final Logger logger = LoggerFactory.getLogger(FileTreeWalker.class);
				logger.warn("Failed to access BasicFileAttributesHolder", ex);
				logger.warn("To fix this add '--add-exports java.base/sun.nio.fs=ALL-UNNAMED' as VM options");
			}
			return null;
		}
		catch(final InvocationTargetException ignored)
		{
			return null;
		}
	}
	// endregion
	
	/**
	 * Returns the attributes of the given file, taking into account whether the walk is following sym links is not.
	 * The
	 * {@code canUseCached} argument determines whether this method can use cached attributes.
	 */
	private BasicFileAttributes getAttributes(final Path file, final boolean canUseCached)
		throws IOException
	{
		// if attributes are cached then use them if possible
		if(canUseCached && isBasicFileAttributesHolder(file))
		{
			final BasicFileAttributes cached = extractFromBasicFileAttributesHolder(file);
			if(cached != null && (!this.followLinks || !cached.isSymbolicLink()))
			{
				return cached;
			}
		}
		
		// attempt to get attributes of file. If fails and we are following
		// links then a link target might not exist so get attributes of link
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(file, BasicFileAttributes.class, this.linkOptions);
		}
		catch(final IOException ioe)
		{
			if(!this.followLinks)
			{
				throw ioe;
			}
			
			// attempt to get attributes without following links
			attrs = Files.readAttributes(
				file,
				BasicFileAttributes.class,
				LinkOption.NOFOLLOW_LINKS);
		}
		return attrs;
	}
	
	/**
	 * Returns true if walking into the given directory would result in a file system loop/cycle.
	 */
	private boolean wouldLoop(final Path dir, final Object key)
	{
		// if this directory and ancestor has a file key then we compare
		// them; otherwise we use less efficient isSameFile test.
		for(final DirectoryNode ancestor : this.stack)
		{
			final Object ancestorKey = ancestor.key();
			if(key != null && ancestorKey != null)
			{
				if(key.equals(ancestorKey))
				{
					// cycle detected
					return true;
				}
			}
			else
			{
				try
				{
					if(Files.isSameFile(dir, ancestor.directory()))
					{
						// cycle detected
						return true;
					}
				}
				catch(final IOException e)
				{
					// ignore
				}
			}
		}
		return false;
	}
	
	/**
	 * Visits the given file, returning the {@code Event} corresponding to that visit.
	 * <p>
	 * The {@code canUseCached} parameter determines whether cached attributes for the file can be used or not.
	 */
	private Event visit(final Path entry, final boolean canUseCached)
	{
		// need the file attributes
		final BasicFileAttributes attrs;
		try
		{
			attrs = this.getAttributes(entry, canUseCached);
		}
		catch(final IOException ioe)
		{
			return new Event(EventType.ENTRY, entry, ioe);
		}
		
		// at maximum depth or file is not a directory
		final int depth = this.stack.size();
		// MODIFIED: Patched here -------------------------v
		if(depth >= this.maxDepth || !(attrs.isDirectory() && !attrs.isOther()))
		{
			return new Event(EventType.ENTRY, entry, attrs);
		}
		
		// check for cycles when following links
		if(this.followLinks && this.wouldLoop(entry, attrs.fileKey()))
		{
			return new Event(
				EventType.ENTRY, entry,
				new FileSystemLoopException(entry.toString()));
		}
		
		// file is a directory, attempt to open it
		final DirectoryStream<Path> stream;
		try
		{
			stream = Files.newDirectoryStream(entry);
		}
		catch(final IOException ioe)
		{
			return new Event(EventType.ENTRY, entry, ioe);
		}
		
		// push a directory node to the stack and return an event
		this.stack.push(new DirectoryNode(entry, attrs.fileKey(), stream));
		return new Event(EventType.START_DIRECTORY, entry, attrs);
	}
	
	/**
	 * Start walking from the given file.
	 */
	Event walk(final Path file)
	{
		if(this.closed)
		{
			throw new IllegalStateException("Closed");
		}
		
		return this.visit(
			file,
			false);  // canUseCached
	}
	
	/**
	 * Returns the next Event or {@code null} if there are no more events or the walker is closed.
	 */
	Event next()
	{
		final DirectoryNode top = this.stack.peek();
		if(top == null)
		{
			return null;      // stack is empty, we are done
		}
		
		// continue iteration of the directory at the top of the stack
		Path entry = null;
		IOException ioe = null;
		
		// get next entry in the directory
		if(!top.skipped())
		{
			final Iterator<Path> iterator = top.iterator();
			try
			{
				if(iterator.hasNext())
				{
					entry = iterator.next();
				}
			}
			catch(final DirectoryIteratorException x)
			{
				ioe = x.getCause();
			}
		}
		
		// no next entry so close and pop directory,
		// creating corresponding event
		if(entry == null)
		{
			try
			{
				top.stream().close();
			}
			catch(final IOException e)
			{
				if(ioe == null)
				{
					ioe = e;
				}
				else
				{
					ioe.addSuppressed(e);
				}
			}
			this.stack.pop();
			return new Event(EventType.END_DIRECTORY, top.directory(), ioe);
		}
		
		// visit the entry
		return this.visit(
			entry,
			true);  // canUseCached
	}
	
	/**
	 * Pops the directory node that is the current top of the stack so that there are no more events for the directory
	 * (including no END_DIRECTORY) event. This method is a no-op if the stack is empty or the walker is closed.
	 */
	void pop()
	{
		if(!this.stack.isEmpty())
		{
			final DirectoryNode node = this.stack.pop();
			try
			{
				node.stream().close();
			}
			catch(final IOException ignore)
			{
				// Ignored
			}
		}
	}
	
	/**
	 * Returns {@code true} if the walker is open.
	 */
	boolean isOpen()
	{
		return !this.closed;
	}
	
	/**
	 * Closes/pops all directories on the stack.
	 */
	@Override
	public void close()
	{
		if(!this.closed)
		{
			while(!this.stack.isEmpty())
			{
				this.pop();
			}
			this.closed = true;
		}
	}
}
