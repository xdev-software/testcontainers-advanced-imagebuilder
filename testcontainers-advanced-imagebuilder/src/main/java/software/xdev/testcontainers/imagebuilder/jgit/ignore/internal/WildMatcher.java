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
 * Wildmatch matcher for "double star" (<code>**</code>) pattern only. This matcher matches any path.
 * <p>
 * This class is immutable and thread safe.
 */
public class WildMatcher extends AbstractMatcher
{
	protected static final String WILDMATCH = "**";
	
	// double star for the beginning of pattern
	protected static final String WILDMATCH2 = "/**";
	
	protected WildMatcher(final boolean dirOnly)
	{
		super(WILDMATCH, dirOnly);
	}
	
	@Override
	public final boolean matches(
		final String path, final boolean assumeDirectory,
		final boolean pathMatch)
	{
		return !this.dirOnly || assumeDirectory
			|| !pathMatch && isSubdirectory(path);
	}
	
	@Override
	public final boolean matches(final String segment, final int startIncl, final int endExcl)
	{
		return true;
	}
	
	protected static boolean isSubdirectory(final String path)
	{
		final int slashIndex = path.indexOf('/');
		return slashIndex >= 0 && slashIndex < path.length() - 1;
	}
}
