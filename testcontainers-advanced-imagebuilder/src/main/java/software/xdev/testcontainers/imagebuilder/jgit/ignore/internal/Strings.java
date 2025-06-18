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

import static java.lang.Character.isLetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import software.xdev.testcontainers.imagebuilder.jgit.errors.InvalidPatternException;
import software.xdev.testcontainers.imagebuilder.jgit.ignore.FastIgnoreRule;


/**
 * Various {@link String} related utility methods, written mostly to avoid generation of new String objects (e.g. via
 * splitting Strings etc).
 */
public final class Strings
{
	static char getPathSeparator(final Character pathSeparator)
	{
		return pathSeparator == null
			? FastIgnoreRule.PATH_SEPARATOR
			: pathSeparator;
	}
	
	/**
	 * Strip trailing characters
	 *
	 * @param pattern non null
	 * @param c       character to remove
	 * @return new string with all trailing characters removed
	 */
	public static String stripTrailing(final String pattern, final char c)
	{
		for(int i = pattern.length() - 1; i >= 0; i--)
		{
			final char charAt = pattern.charAt(i);
			if(charAt != c)
			{
				if(i == pattern.length() - 1)
				{
					return pattern;
				}
				return pattern.substring(0, i + 1);
			}
		}
		return "";
	}
	
	/**
	 * Strip trailing whitespace characters
	 *
	 * @param pattern non null
	 * @return new string with all trailing whitespace removed
	 */
	public static String stripTrailingWhitespace(final String pattern)
	{
		for(int i = pattern.length() - 1; i >= 0; i--)
		{
			final char charAt = pattern.charAt(i);
			if(!Character.isWhitespace(charAt))
			{
				if(i == pattern.length() - 1)
				{
					return pattern;
				}
				return pattern.substring(0, i + 1);
			}
		}
		return "";
	}
	
	/**
	 * Check if pattern is a directory pattern ending with a path separator
	 *
	 * @param pattern non null
	 * @return {@code true} if the last character, which is not whitespace, is a path separator
	 */
	public static boolean isDirectoryPattern(final String pattern)
	{
		for(int i = pattern.length() - 1; i >= 0; i--)
		{
			final char charAt = pattern.charAt(i);
			if(!Character.isWhitespace(charAt))
			{
				return charAt == FastIgnoreRule.PATH_SEPARATOR;
			}
		}
		return false;
	}
	
	static int count(final String s, final char c, final boolean ignoreFirstLast)
	{
		int start = 0;
		int count = 0;
		final int length = s.length();
		while(start < length)
		{
			start = s.indexOf(c, start);
			if(start == -1)
			{
				break;
			}
			if(!ignoreFirstLast || start != 0 && start != length - 1)
			{
				count++;
			}
			start++;
		}
		return count;
	}
	
	/**
	 * Splits given string to substrings by given separator
	 *
	 * @param pattern non null
	 * @param slash   separator char
	 * @return list of substrings
	 */
	public static List<String> split(final String pattern, final char slash)
	{
		final int count = count(pattern, slash, true);
		if(count < 1)
		{
			throw new IllegalStateException(
				"Pattern must have at least two segments: " + pattern);
		}
		final List<String> segments = new ArrayList<>(count);
		int right = 0;
		while(true)
		{
			final int left = right;
			right = pattern.indexOf(slash, right);
			if(right == -1)
			{
				if(left < pattern.length())
				{
					segments.add(pattern.substring(left));
				}
				break;
			}
			if(right - left > 0)
			{
				if(left == 1)
				// leading slash should remain by the first pattern
				{
					segments.add(pattern.substring(0, right));
				}
				else if(right == pattern.length() - 1)
				// trailing slash should remain too
				{
					segments.add(pattern.substring(left, right + 1));
				}
				else
				{
					segments.add(pattern.substring(left, right));
				}
			}
			right++;
		}
		return segments;
	}
	
	static boolean isWildCard(final String pattern)
	{
		return pattern.indexOf('*') != -1 || isComplexWildcard(pattern);
	}
	
	private static boolean isComplexWildcard(final String pattern)
	{
		final int idx1 = pattern.indexOf('[');
		if(idx1 != -1)
		{
			return true;
		}
		if(pattern.indexOf('?') != -1)
		{
			return true;
		}
		// check if the backslash escapes one of the glob special characters
		// if not, backslash is not part of a regex and treated literally
		final int backSlash = pattern.indexOf('\\');
		if(backSlash >= 0)
		{
			final int nextIdx = backSlash + 1;
			if(pattern.length() == nextIdx)
			{
				return false;
			}
			final char nextChar = pattern.charAt(nextIdx);
			return escapedByBackslash(nextChar);
		}
		return false;
	}
	
	private static boolean escapedByBackslash(final char nextChar)
	{
		return nextChar == '?' || nextChar == '*' || nextChar == '[';
	}
	
	static PatternState checkWildCards(final String pattern)
	{
		if(isComplexWildcard(pattern))
		{
			return PatternState.COMPLEX;
		}
		final int startIdx = pattern.indexOf('*');
		if(startIdx < 0)
		{
			return PatternState.NONE;
		}
		
		if(startIdx == pattern.length() - 1)
		{
			return PatternState.TRAILING_ASTERISK_ONLY;
		}
		if(pattern.lastIndexOf('*') == 0)
		{
			return PatternState.LEADING_ASTERISK_ONLY;
		}
		
		return PatternState.COMPLEX;
	}
	
	enum PatternState
	{
		LEADING_ASTERISK_ONLY, TRAILING_ASTERISK_ONLY, COMPLEX, NONE
	}
	
	
	static final List<String> POSIX_CHAR_CLASSES = Arrays.asList(
		"alnum", "alpha", "blank", "cntrl",
		// [:alnum:] [:alpha:] [:blank:] [:cntrl:]
		"digit", "graph", "lower", "print",
		// [:digit:] [:graph:] [:lower:] [:print:]
		"punct", "space", "upper", "xdigit",
		// [:punct:] [:space:] [:upper:] [:xdigit:]
		"word"
		// [:word:] XXX I don't see it in
		// http://man7.org/linux/man-pages/man7/glob.7.html
		// but this was in org.eclipse.jgit.fnmatch.GroupHead.java ???
	);
	
	private static final String DL = "\\p{javaDigit}\\p{javaLetter}";
	
	static final List<String> JAVA_CHAR_CLASSES = Arrays
		.asList(
			"\\p{Alnum}",
			"\\p{javaLetter}",
			"\\p{Blank}",
			"\\p{Cntrl}",
			// [:alnum:] [:alpha:] [:blank:] [:cntrl:]
			"\\p{javaDigit}",
			"[\\p{Graph}" + DL + "]",
			"\\p{Ll}",
			"[\\p{Print}" + DL + "]",
			// [:digit:] [:graph:] [:lower:] [:print:]
			"\\p{Punct}",
			"\\p{Space}",
			"\\p{Lu}",
			"\\p{XDigit}",
			// [:punct:] [:space:] [:upper:] [:xdigit:]
			"[" + DL + "_]"
			// [:word:]
		);
	
	// Collating symbols [[.a.]] or equivalence class expressions [[=a=]] are
	// not supported by CLI git (at least not by 1.9.1)
	static final Pattern UNSUPPORTED = Pattern
		.compile("\\[\\[[.=]\\w+[.=]\\]\\]");
	
	/**
	 * Conversion from glob to Java regex following two sources:
	 * <ul>
	 * <li>http://man7.org/linux/man-pages/man7/glob.7.html
	 * <li>org.eclipse.jgit.fnmatch.FileNameMatcher.java Seems that there are
	 * various ways to define what "glob" can be.
	 * </ul>
	 *
	 * @param pattern non null pattern
	 * @return Java regex pattern corresponding to given glob pattern
	 * @throws InvalidPatternException if pattern is invalid
	 */
	@SuppressWarnings({"checkstyle:MethodLength", "checkstyle:MagicNumber"})
	public static Pattern convertGlob(final String pattern) throws InvalidPatternException
	{
		if(UNSUPPORTED.matcher(pattern).find())
		{
			throw new InvalidPatternException(
				"Collating symbols [[.a.]] or equivalence class expressions [[=a=]] are not supported",
				pattern);
		}
		
		final StringBuilder sb = new StringBuilder(pattern.length());
		
		int inBrackets = 0;
		boolean seenEscape = false;
		boolean ignoreLastBracket = false;
		boolean inCharClass = false;
		// 6 is the length of the longest posix char class "xdigit"
		final char[] charClass = new char[6];
		
		for(int i = 0; i < pattern.length(); i++)
		{
			final char c = pattern.charAt(i);
			switch(c)
			{
				
				case '*':
					if(seenEscape || inBrackets > 0)
					{
						sb.append(c);
					}
					else
					{
						sb.append('.').append(c);
					}
					break;
				
				case '(': // fall-through
				case ')': // fall-through
				case '{': // fall-through
				case '}': // fall-through
				case '+': // fall-through
				case '$': // fall-through
				case '^': // fall-through
				case '|':
					if(seenEscape || inBrackets > 0)
					{
						sb.append(c);
					}
					else
					{
						sb.append('\\').append(c);
					}
					break;
				
				case '.':
					if(seenEscape)
					{
						sb.append(c);
					}
					else
					{
						sb.append('\\').append('.');
					}
					break;
				
				case '?':
					if(seenEscape || inBrackets > 0)
					{
						sb.append(c);
					}
					else
					{
						sb.append('.');
					}
					break;
				
				case ':':
					if(inBrackets > 0
						&& lookBehind(sb) == '['
						&& isLetter(lookAhead(pattern, i)))
					{
						inCharClass = true;
					}
					
					sb.append(':');
					break;
				
				case '-':
					if(inBrackets > 0)
					{
						if(lookAhead(pattern, i) == ']')
						{
							sb.append('\\').append(c);
						}
						else
						{
							sb.append(c);
						}
					}
					else
					{
						sb.append('-');
					}
					break;
				
				case '\\':
					if(inBrackets > 0)
					{
						final char lookAhead = lookAhead(pattern, i);
						if(lookAhead == ']' || lookAhead == '[')
						{
							ignoreLastBracket = true;
						}
					}
					else
					{
						//
						final char lookAhead = lookAhead(pattern, i);
						if(lookAhead != '\\' && lookAhead != '['
							&& lookAhead != '?' && lookAhead != '*'
							&& lookAhead != ' ' && lookBehind(sb) != '\\')
						{
							break;
						}
					}
					sb.append(c);
					break;
				
				case '[':
					if(inBrackets > 0)
					{
						if(!seenEscape)
						{
							sb.append('\\');
						}
						sb.append('[');
						ignoreLastBracket = true;
					}
					else
					{
						if(!seenEscape)
						{
							inBrackets++;
							ignoreLastBracket = false;
						}
						sb.append('[');
					}
					break;
				
				case ']':
					if(seenEscape)
					{
						sb.append(']');
						ignoreLastBracket = true;
						break;
					}
					if(inBrackets <= 0)
					{
						sb.append('\\').append(']');
						ignoreLastBracket = true;
						break;
					}
					final char lookBehind = lookBehind(sb);
					if(lookBehind == '[' && !ignoreLastBracket
						|| lookBehind == '^')
					{
						sb.append('\\');
						sb.append(']');
						ignoreLastBracket = true;
					}
					else
					{
						ignoreLastBracket = false;
						if(!inCharClass)
						{
							inBrackets--;
							sb.append(']');
						}
						else
						{
							inCharClass = false;
							final String charCl = checkPosixCharClass(charClass);
							// delete last \[:: chars and set the pattern
							if(charCl != null)
							{
								sb.setLength(sb.length() - 4);
								sb.append(charCl);
							}
							reset(charClass);
						}
					}
					break;
				
				case '!':
					if(inBrackets > 0)
					{
						if(lookBehind(sb) == '[')
						{
							sb.append('^');
						}
						else
						{
							sb.append(c);
						}
					}
					else
					{
						sb.append(c);
					}
					break;
				
				default:
					if(inCharClass)
					{
						setNext(charClass, c);
					}
					else
					{
						sb.append(c);
					}
					break;
			} // end switch
			
			seenEscape = c == '\\';
		} // end for
		
		if(inBrackets > 0)
		{
			throw new InvalidPatternException("Not closed bracket?", pattern);
		}
		try
		{
			return Pattern.compile(sb.toString(), Pattern.DOTALL);
		}
		catch(final PatternSyntaxException e)
		{
			throw new InvalidPatternException(
				"Exception caught while parsing ignore rule \"" + pattern + "\"",
				pattern, e);
		}
	}
	
	/**
	 * @param buffer buffer
	 * @return zero of the buffer is empty, otherwise the last character from buffer
	 */
	private static char lookBehind(final StringBuilder buffer)
	{
		return !buffer.isEmpty() ? buffer.charAt(buffer.length() - 1) : 0;
	}
	
	/**
	 * Lookahead next character after given index in pattern
	 *
	 * @param pattern the pattern
	 * @param i       current pointer in the pattern
	 * @return zero if the index is out of range, otherwise the next character from given position
	 */
	private static char lookAhead(final String pattern, final int i)
	{
		final int idx = i + 1;
		return idx >= pattern.length() ? 0 : pattern.charAt(idx);
	}
	
	private static void setNext(final char[] buffer, final char c)
	{
		for(int i = 0; i < buffer.length; i++)
		{
			if(buffer[i] == 0)
			{
				buffer[i] = c;
				break;
			}
		}
	}
	
	private static void reset(final char[] buffer)
	{
		Arrays.fill(buffer, (char)0);
	}
	
	private static String checkPosixCharClass(final char[] buffer)
	{
		for(int i = 0; i < POSIX_CHAR_CLASSES.size(); i++)
		{
			final String clazz = POSIX_CHAR_CLASSES.get(i);
			boolean match = true;
			for(int j = 0; j < clazz.length(); j++)
			{
				if(buffer[j] != clazz.charAt(j))
				{
					match = false;
					break;
				}
			}
			if(match)
			{
				return JAVA_CHAR_CLASSES.get(i);
			}
		}
		return null;
	}
	
	static String deleteBackslash(final String s)
	{
		if(s.indexOf('\\') < 0)
		{
			return s;
		}
		final StringBuilder sb = new StringBuilder(s.length());
		for(int i = 0; i < s.length(); i++)
		{
			final char ch = s.charAt(i);
			if(ch == '\\')
			{
				if(i + 1 == s.length())
				{
					continue;
				}
				final char next = s.charAt(i + 1);
				if(next == '\\')
				{
					sb.append(ch);
					i++;
					continue;
				}
				if(!escapedByBackslash(next))
				{
					continue;
				}
			}
			sb.append(ch);
		}
		return sb.toString();
	}
	
	private Strings()
	{
	}
}
