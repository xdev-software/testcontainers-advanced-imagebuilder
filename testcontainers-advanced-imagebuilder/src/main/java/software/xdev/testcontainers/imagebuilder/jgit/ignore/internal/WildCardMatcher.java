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

import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.convertGlob;

import java.util.regex.Pattern;

import software.xdev.testcontainers.imagebuilder.jgit.errors.InvalidPatternException;


/**
 * Matcher built from path segments containing wildcards. This matcher converts glob wildcards to Java
 * {@link Pattern}'s.
 * <p>
 * This class is immutable and thread safe.
 */
@SuppressWarnings("java:S2160") // Eclipse
public class WildCardMatcher extends NameMatcher
{
	protected final Pattern p;
	
	protected WildCardMatcher(final String pattern, final Character pathSeparator, final boolean dirOnly)
		throws InvalidPatternException
	{
		super(pattern, pathSeparator, dirOnly, false);
		this.p = convertGlob(this.subPattern);
	}
	
	@Override
	public boolean matches(final String segment, final int startIncl, final int endExcl)
	{
		return this.p.matcher(segment.substring(startIncl, endExcl)).matches();
	}
}
