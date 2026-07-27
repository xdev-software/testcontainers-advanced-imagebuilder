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
package software.xdev.testcontainers.imagebuilder.jgit.errors;

/**
 * Thrown when a pattern passed in an argument was wrong.
 */
public class InvalidPatternException extends Exception
{
	protected final String pattern;
	
	/**
	 * Constructor for InvalidPatternException
	 *
	 * @param message explains what was wrong with the pattern.
	 * @param pattern the invalid pattern.
	 */
	public InvalidPatternException(final String message, final String pattern)
	{
		super(message);
		this.pattern = pattern;
	}
	
	/**
	 * Constructor for InvalidPatternException
	 *
	 * @param message explains what was wrong with the pattern.
	 * @param pattern the invalid pattern.
	 * @param cause   the cause.
	 * @since 4.10
	 */
	public InvalidPatternException(
		final String message, final String pattern,
		final Throwable cause)
	{
		this(message, pattern);
		this.initCause(cause);
	}
	
	/**
	 * Get the invalid pattern
	 *
	 * @return the invalid pattern.
	 */
	public String getPattern()
	{
		return this.pattern;
	}
}
