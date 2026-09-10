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

import software.xdev.testcontainers.imagebuilder.jgit.ignore.IMatcher;


/**
 * Base class for default methods as {@link #toString()} and such.
 * <p>
 * This class is immutable and thread safe.
 */
public abstract class AbstractMatcher implements IMatcher
{
	protected final boolean dirOnly;
	protected final String pattern;
	
	/**
	 * @param pattern string to parse
	 * @param dirOnly true if this matcher should match only directories
	 */
	protected AbstractMatcher(final String pattern, final boolean dirOnly)
	{
		this.pattern = pattern;
		this.dirOnly = dirOnly;
	}
	
	@Override
	public String toString()
	{
		return this.pattern;
	}
	
	@Override
	public int hashCode()
	{
		return this.pattern.hashCode();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(!(obj instanceof final AbstractMatcher other))
		{
			return false;
		}
		return this.dirOnly == other.dirOnly && this.pattern.equals(other.pattern);
	}
}
