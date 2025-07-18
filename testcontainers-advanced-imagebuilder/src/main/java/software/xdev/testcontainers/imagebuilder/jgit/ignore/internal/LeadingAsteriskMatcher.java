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

/**
 * Matcher for simple regex patterns starting with an asterisk, e.g. "*.tmp"
 */
public class LeadingAsteriskMatcher extends NameMatcher
{
	protected LeadingAsteriskMatcher(final String pattern, final Character pathSeparator, final boolean dirOnly)
	{
		super(pattern, pathSeparator, dirOnly, true);
		
		if(this.subPattern.charAt(0) != '*')
		{
			throw new IllegalArgumentException(
				"Pattern must have leading asterisk: " + pattern);
		}
	}
	
	@Override
	public boolean matches(final String segment, final int startIncl, final int endExcl)
	{
		// faster local access, same as in string.indexOf()
		final String s = this.subPattern;
		
		// we don't need to count '*' character itself
		final int subLength = s.length() - 1;
		// simple /*/ pattern
		if(subLength == 0)
		{
			return true;
		}
		
		if(subLength > (endExcl - startIncl))
		{
			return false;
		}
		
		for(int i = subLength, j = endExcl - 1; i > 0; i--, j--)
		{
			final char c1 = s.charAt(i);
			final char c2 = segment.charAt(j);
			if(c1 != c2)
			{
				return false;
			}
		}
		return true;
	}
}
