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

/**
 * Generic path matcher.
 *
 * @since 5.7
 */
public interface IMatcher
{
	/**
	 * Matcher that does not match any pattern.
	 */
	IMatcher NO_MATCH = new IMatcher()
	{
		
		@Override
		public boolean matches(
			final String path, final boolean assumeDirectory,
			final boolean pathMatch)
		{
			return false;
		}
		
		@Override
		public boolean matches(final String segment, final int startIncl, final int endExcl)
		{
			return false;
		}
	};
	
	/**
	 * Matches entire given string
	 *
	 * @param path            string which is not null, but might be empty
	 * @param assumeDirectory true to assume this path as directory (even if it doesn't end with a slash)
	 * @param pathMatch       {@code true} if the match is for the full path: prefix-only matches are not allowed
	 * @return true if this matcher pattern matches given string
	 */
	boolean matches(String path, boolean assumeDirectory, boolean pathMatch);
	
	/**
	 * Matches only part of given string
	 *
	 * @param segment   string which is not null, but might be empty
	 * @param startIncl start index, inclusive
	 * @param endExcl   end index, exclusive
	 * @return true if this matcher pattern matches given string
	 */
	boolean matches(String segment, int startIncl, int endExcl);
}
