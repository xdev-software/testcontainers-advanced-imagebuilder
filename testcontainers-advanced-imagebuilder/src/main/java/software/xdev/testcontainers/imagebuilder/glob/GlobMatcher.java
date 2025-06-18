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
package software.xdev.testcontainers.imagebuilder.glob;

import java.util.regex.Pattern;

import software.xdev.testcontainers.imagebuilder.jgit.errors.InvalidPatternException;
import software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings;


/**
 * A simple implementation of GlobMatcher that utilizes JGit's {@link Strings#convertGlob(String)}
 */
public class GlobMatcher
{
	protected final Pattern pattern;
	
	public GlobMatcher(final String pattern)
	{
		try
		{
			this.pattern = Pattern.compile("\\/?" + Strings.convertGlob(pattern));
		}
		catch(final InvalidPatternException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
	
	protected String correctPathForMatching(final String path)
	{
		return path.startsWith("/") ? path : ("/" + path);
	}
	
	public boolean matches(final String path)
	{
		return this.pattern.matcher(this.correctPathForMatching(path)).matches();
	}
}
