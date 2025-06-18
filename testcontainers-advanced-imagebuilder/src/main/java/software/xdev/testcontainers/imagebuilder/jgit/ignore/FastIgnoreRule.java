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
package software.xdev.testcontainers.imagebuilder.jgit.ignore;

import static software.xdev.testcontainers.imagebuilder.jgit.ignore.IMatcher.NO_MATCH;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.isDirectoryPattern;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.stripTrailing;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.stripTrailingWhitespace;

import software.xdev.testcontainers.imagebuilder.jgit.errors.InvalidPatternException;
import software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.PathMatcher;


/**
 * "Fast" (compared with IgnoreRule) git ignore rule implementation supporting also double star {@code **} pattern.
 * <p>
 * This class is immutable and thread safe.
 *
 * @since 3.6
 */
public class FastIgnoreRule
{
	/**
	 * Character used as default path separator for ignore entries
	 */
	public static final char PATH_SEPARATOR = '/';
	
	protected IMatcher matcher;
	protected boolean inverse;
	protected boolean dirOnly;
	
	public FastIgnoreRule()
	{
		this.matcher = IMatcher.NO_MATCH;
	}
	
	@SuppressWarnings("checkstyle:FinalParameters")
	public void parse(String pattern) throws InvalidPatternException
	{
		if(pattern == null)
		{
			throw new IllegalArgumentException("Pattern must not be null!");
		}
		if(pattern.isEmpty())
		{
			this.dirOnly = false;
			this.inverse = false;
			this.matcher = NO_MATCH;
			return;
		}
		this.inverse = pattern.charAt(0) == '!';
		if(this.inverse)
		{
			pattern = pattern.substring(1);
			if(pattern.isEmpty())
			{
				this.dirOnly = false;
				this.matcher = NO_MATCH;
				return;
			}
		}
		if(pattern.charAt(0) == '#')
		{
			this.matcher = NO_MATCH;
			this.dirOnly = false;
			return;
		}
		if(pattern.charAt(0) == '\\' && pattern.length() > 1)
		{
			final char next = pattern.charAt(1);
			if(next == '!' || next == '#')
			{
				// remove backslash escaping first special characters
				pattern = pattern.substring(1);
			}
		}
		this.dirOnly = isDirectoryPattern(pattern);
		if(this.dirOnly)
		{
			pattern = stripTrailingWhitespace(pattern);
			pattern = stripTrailing(pattern, PATH_SEPARATOR);
			if(pattern.isEmpty())
			{
				this.matcher = NO_MATCH;
				return;
			}
		}
		this.matcher = PathMatcher.createPathMatcher(
			pattern,
			PATH_SEPARATOR, this.dirOnly);
	}
	
	/**
	 * Returns true if a match was made. <br> This function does NOT return the actual ignore status of the target!
	 * Please consult {@link #getResult()} for the negation status. The actual ignore status may be true or false
	 * depending on whether this rule is an ignore rule or a negation rule.
	 *
	 * @param path      Name pattern of the file, relative to the base directory of this rule
	 * @param directory Whether the target file is a directory or not
	 * @return True if a match was made. This does not necessarily mean that the target is ignored. Call
	 * {@link #getResult() getResult()} for the result.
	 */
	public boolean isMatch(final String path, final boolean directory)
	{
		return this.isMatch(path, directory, false);
	}
	
	/**
	 * Returns true if a match was made. <br> This function does NOT return the actual ignore status of the target!
	 * Please consult {@link #getResult()} for the negation status. The actual ignore status may be true or false
	 * depending on whether this rule is an ignore rule or a negation rule.
	 *
	 * @param path      Name pattern of the file, relative to the base directory of this rule
	 * @param directory Whether the target file is a directory or not
	 * @param pathMatch {@code true} if the match is for the full path: see {@link IMatcher#matches(String, int, int)}
	 * @return True if a match was made. This does not necessarily mean that the target is ignored. Call
	 * {@link #getResult() getResult()} for the result.
	 * @since 4.11
	 */
	public boolean isMatch(final String path, final boolean directory, final boolean pathMatch)
	{
		if(path == null)
		{
			return false;
		}
		if(path.isEmpty())
		{
			return false;
		}
		return this.matcher.matches(path, directory, pathMatch);
	}
	
	/**
	 * Whether the pattern is just a file name and not a path
	 *
	 * @return {@code true} if the pattern is just a file name and not a path
	 */
	public boolean getNameOnly()
	{
		return !(this.matcher instanceof PathMatcher);
	}
	
	/**
	 * Whether the pattern should match directories only
	 *
	 * @return {@code true} if the pattern should match directories only
	 */
	public boolean dirOnly()
	{
		return this.dirOnly;
	}
	
	/**
	 * Indicates whether the rule is non-negation or negation.
	 *
	 * @return True if the pattern had a "!" in front of it
	 */
	public boolean getNegation()
	{
		return this.inverse;
	}
	
	/**
	 * Indicates whether the rule is non-negation or negation.
	 *
	 * @return True if the target is to be ignored, false otherwise.
	 */
	public boolean getResult()
	{
		return !this.inverse;
	}
	
	/**
	 * Whether the rule never matches
	 *
	 * @return {@code true} if the rule never matches (comment line or broken pattern)
	 * @since 4.1
	 */
	public boolean isEmpty()
	{
		return this.matcher == NO_MATCH;
	}
	
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		if(this.inverse)
		{
			sb.append('!');
		}
		sb.append(this.matcher);
		if(this.dirOnly)
		{
			sb.append(PATH_SEPARATOR);
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.inverse ? 1231 : 1237);
		result = prime * result + (this.dirOnly ? 1231 : 1237);
		result = prime * result + ((this.matcher == null) ? 0 : this.matcher.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(!(obj instanceof final FastIgnoreRule other))
		{
			return false;
		}
		
		if(this.inverse != other.inverse)
		{
			return false;
		}
		if(this.dirOnly != other.dirOnly)
		{
			return false;
		}
		return this.matcher.equals(other.matcher);
	}
}
