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
package software.xdev.testcontainers.imagebuilder.jgit.ignore.internal;

import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.getPathSeparator;


/**
 * Matcher built from patterns for file names (single path segments). This class is immutable and thread safe.
 */
public class NameMatcher extends AbstractMatcher
{
	protected final boolean beginning;
	protected final char slash;
	protected final String subPattern;
	
	@SuppressWarnings("checkstyle:FinalParameters")
	protected NameMatcher(
		String pattern,
		final Character pathSeparator,
		final boolean dirOnly,
		final boolean deleteBackslash)
	{
		super(pattern, dirOnly);
		this.slash = getPathSeparator(pathSeparator);
		if(deleteBackslash)
		{
			pattern = Strings.deleteBackslash(pattern);
		}
		this.beginning = !pattern.isEmpty() && pattern.charAt(0) == this.slash;
		if(!this.beginning)
		{
			this.subPattern = pattern;
		}
		else
		{
			this.subPattern = pattern.substring(1);
		}
	}
	
	@Override
	public boolean matches(
		final String path, final boolean assumeDirectory,
		final boolean pathMatch)
	{
		// A NameMatcher's pattern does not contain a slash.
		int start = 0;
		int stop = path.length();
		if(stop > 0 && path.charAt(0) == this.slash)
		{
			start++;
		}
		if(pathMatch)
		{
			// Can match only after the last slash
			int lastSlash = path.lastIndexOf(this.slash, stop - 1);
			if(lastSlash == stop - 1)
			{
				// Skip trailing slash
				lastSlash = path.lastIndexOf(this.slash, lastSlash - 1);
				stop--;
			}
			boolean match;
			if(lastSlash < start)
			{
				match = this.matches(path, start, stop);
			}
			else
			{
				// Can't match if the path contains a slash if the pattern is
				// anchored at the beginning
				match = !this.beginning
					&& this.matches(path, lastSlash + 1, stop);
			}
			if(match && this.dirOnly)
			{
				match = assumeDirectory;
			}
			return match;
		}
		while(start < stop)
		{
			int end = path.indexOf(this.slash, start);
			if(end < 0)
			{
				end = stop;
			}
			if(end > start && this.matches(path, start, end))
			{
				// make sure the directory matches: either if we are done with
				// segment and there is next one, or if the directory is assumed
				return !this.dirOnly || assumeDirectory || end < stop;
			}
			if(this.beginning)
			{
				break;
			}
			start = end + 1;
		}
		return false;
	}
	
	@Override
	public boolean matches(final String segment, final int startIncl, final int endExcl)
	{
		// faster local access, same as in string.indexOf()
		final String s = this.subPattern;
		final int length = s.length();
		if(length != (endExcl - startIncl))
		{
			return false;
		}
		for(int i = 0; i < length; i++)
		{
			final char c1 = s.charAt(i);
			final char c2 = segment.charAt(i + startIncl);
			if(c1 != c2)
			{
				return false;
			}
		}
		return true;
	}
}
