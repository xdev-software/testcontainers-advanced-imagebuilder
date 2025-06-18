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

import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.checkWildCards;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.count;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.getPathSeparator;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.isWildCard;
import static software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.split;

import java.util.ArrayList;
import java.util.List;

import software.xdev.testcontainers.imagebuilder.jgit.errors.InvalidPatternException;
import software.xdev.testcontainers.imagebuilder.jgit.ignore.IMatcher;
import software.xdev.testcontainers.imagebuilder.jgit.ignore.internal.Strings.PatternState;


/**
 * Matcher built by patterns consists of multiple path segments.
 * <p>
 * This class is immutable and thread safe.
 */
@SuppressWarnings("java:S2160") // Eclipse
public class PathMatcher extends AbstractMatcher
{
	protected static final WildMatcher WILD_NO_DIRECTORY = new WildMatcher(false);
	protected static final WildMatcher WILD_ONLY_DIRECTORY = new WildMatcher(true);
	
	protected final List<IMatcher> matchers;
	protected final char slash;
	protected final boolean beginning;
	
	protected PathMatcher(
		final String pattern,
		final Character pathSeparator,
		final boolean dirOnly)
		throws InvalidPatternException
	{
		super(pattern, dirOnly);
		this.slash = getPathSeparator(pathSeparator);
		this.beginning = pattern.indexOf(this.slash) == 0;
		if(this.isSimplePathWithSegments(pattern))
		{
			this.matchers = null;
		}
		else
		{
			this.matchers = createMatchers(
				split(pattern, this.slash), pathSeparator,
				dirOnly);
		}
	}
	
	protected boolean isSimplePathWithSegments(final String path)
	{
		return !isWildCard(path) && path.indexOf('\\') < 0
			&& count(path, this.slash, true) > 0;
	}
	
	@SuppressWarnings("java:S5413")
	protected static List<IMatcher> createMatchers(
		final List<String> segments,
		final Character pathSeparator, final boolean dirOnly)
		throws InvalidPatternException
	{
		final List<IMatcher> matchers = new ArrayList<>(segments.size());
		for(int i = 0; i < segments.size(); i++)
		{
			final String segment = segments.get(i);
			final IMatcher matcher = createNameMatcher0(
				segment, pathSeparator,
				dirOnly, i == segments.size() - 1);
			if(i > 0)
			{
				final IMatcher last = matchers.get(matchers.size() - 1);
				if(isWild(matcher) && isWild(last))
				// collapse wildmatchers **/** is same as **, but preserve
				// dirOnly flag (i.e. always use the last wildmatcher)
				{
					matchers.remove(matchers.size() - 1);
				}
			}
			
			matchers.add(matcher);
		}
		return matchers;
	}
	
	/**
	 * Create path matcher
	 *
	 * @param pattern       a pattern
	 * @param pathSeparator if this parameter isn't null then this character will not match at wildcards(* and ? are
	 *                      wildcards).
	 * @param dirOnly       a boolean.
	 * @return never null
	 * @throws InvalidPatternException if pattern is invalid
	 */
	@SuppressWarnings("checkstyle:FinalParameters")
	public static IMatcher createPathMatcher(
		String pattern,
		final Character pathSeparator, final boolean dirOnly)
		throws InvalidPatternException
	{
		pattern = trim(pattern);
		final char slash = Strings.getPathSeparator(pathSeparator);
		// ignore possible leading and trailing slash
		final int slashIdx = pattern.indexOf(slash, 1);
		if(slashIdx > 0 && slashIdx < pattern.length() - 1)
		{
			return new PathMatcher(pattern, pathSeparator, dirOnly);
		}
		return createNameMatcher0(pattern, pathSeparator, dirOnly, true);
	}
	
	/**
	 * Trim trailing spaces, unless they are escaped with backslash, see
	 * https://www.kernel.org/pub/software/scm/git/docs/gitignore.html
	 *
	 * @param pattern non null
	 * @return trimmed pattern
	 */
	protected static String trim(String pattern)
	{
		while(!pattern.isEmpty()
			&& pattern.charAt(pattern.length() - 1) == ' ')
		{
			if(pattern.length() > 1
				&& pattern.charAt(pattern.length() - 2) == '\\')
			{
				// last space was escaped by backslash: remove backslash and
				// keep space
				pattern = pattern.substring(0, pattern.length() - 2) + " ";
				return pattern;
			}
			pattern = pattern.substring(0, pattern.length() - 1);
		}
		return pattern;
	}
	
	protected static IMatcher createNameMatcher0(
		final String segment,
		final Character pathSeparator, final boolean dirOnly, final boolean lastSegment)
		throws InvalidPatternException
	{
		// check if we see /** or ** segments => double star pattern
		if(WildMatcher.WILDMATCH.equals(segment)
			|| WildMatcher.WILDMATCH2.equals(segment))
		{
			return dirOnly && lastSegment
				? WILD_ONLY_DIRECTORY
				: WILD_NO_DIRECTORY;
		}
		
		final PatternState state = checkWildCards(segment);
		return switch(state)
		{
			case LEADING_ASTERISK_ONLY -> new LeadingAsteriskMatcher(segment, pathSeparator, dirOnly);
			case TRAILING_ASTERISK_ONLY -> new TrailingAsteriskMatcher(segment, pathSeparator, dirOnly);
			case COMPLEX -> new WildCardMatcher(segment, pathSeparator, dirOnly);
			default -> new NameMatcher(segment, pathSeparator, dirOnly, true);
		};
	}
	
	@Override
	public boolean matches(
		final String path,
		final boolean assumeDirectory,
		final boolean pathMatch)
	{
		if(this.matchers == null)
		{
			return this.simpleMatch(path, assumeDirectory, pathMatch);
		}
		return this.iterate(path, 0, path.length(), assumeDirectory, pathMatch);
	}
	
	/*
	 * Stupid but fast string comparison: the case where we don't have to match
	 * wildcards or single segments (mean: this is multi-segment path which must
	 * be at the beginning of the another string)
	 */
	protected boolean simpleMatch(
		String path, final boolean assumeDirectory,
		final boolean pathMatch)
	{
		final boolean hasSlash = path.indexOf(this.slash) == 0;
		if(this.beginning && !hasSlash)
		{
			path = this.slash + path;
		}
		if(!this.beginning && hasSlash)
		{
			path = path.substring(1);
		}
		if(path.equals(this.pattern))
		{
			// Exact match: must meet directory expectations
			return !this.dirOnly || assumeDirectory;
		}
		/*
		 * Add slashes for startsWith check. This avoids matching e.g.
		 * "/src/new" to /src/newfile" but allows "/src/new" to match
		 * "/src/new/newfile", as is the git standard
		 */
		final String prefix = this.pattern + this.slash;
		if(pathMatch)
		{
			return path.equals(prefix) && (!this.dirOnly || assumeDirectory);
		}
		return path.startsWith(prefix);
	}
	
	@Override
	public boolean matches(final String segment, final int startIncl, final int endExcl)
	{
		throw new UnsupportedOperationException(
			"Path matcher works only on entire paths");
	}
	
	@SuppressWarnings({
		"java:S3776",
		"PMD.CognitiveComplexity",
		"java:S6541"}) // Eclipse code = Big brain required
	protected boolean iterate(
		final String path,
		final int startIncl,
		final int endExcl,
		final boolean assumeDirectory,
		final boolean pathMatch)
	{
		int matcher = 0;
		int right = startIncl;
		boolean match = false;
		int lastWildmatch = -1;
		// ** matches may get extended if a later match fails. When that
		// happens, we must extend the ** by exactly one segment.
		// wildmatchBacktrackPos records the end of the segment after a **
		// match, so that we can reset correctly.
		int wildmatchBacktrackPos = -1;
		while(true)
		{
			final int left = right;
			right = path.indexOf(this.slash, right);
			if(right == -1)
			{
				if(left < endExcl)
				{
					match = this.matches(
						matcher, path, left, endExcl,
						assumeDirectory, pathMatch);
				}
				else
				{
					// a/** should not match a/ or a
					match = match && !isWild(this.matchers.get(matcher));
				}
				if(match)
				{
					if(matcher < this.matchers.size() - 1
						&& isWild(this.matchers.get(matcher)))
					{
						// ** can match *nothing*: a/**/b match also a/b
						matcher++;
						match = this.matches(
							matcher, path, left, endExcl,
							assumeDirectory, pathMatch);
					}
					else if(this.dirOnly && !assumeDirectory)
					{
						// Directory expectations not met
						return false;
					}
				}
				return match && matcher + 1 == this.matchers.size();
			}
			if(wildmatchBacktrackPos < 0)
			{
				wildmatchBacktrackPos = right;
			}
			if(right - left > 0)
			{
				match = this.matches(
					matcher, path, left, right, assumeDirectory,
					pathMatch);
			}
			else
			{
				// path starts with slash???
				right++;
				continue;
			}
			if(match)
			{
				final boolean wasWild = isWild(this.matchers.get(matcher));
				if(wasWild)
				{
					lastWildmatch = matcher;
					wildmatchBacktrackPos = -1;
					// ** can match *nothing*: a/**/b match also a/b
					right = left - 1;
				}
				matcher++;
				if(matcher == this.matchers.size())
				{
					// We had a prefix match here.
					if(!pathMatch)
					{
						return true;
					}
					if(right == endExcl - 1)
					{
						// Extra slash at the end: actually a full match.
						// Must meet directory expectations
						return !this.dirOnly || assumeDirectory;
					}
					// Prefix matches only if pattern ended with /**
					if(wasWild)
					{
						return true;
					}
					if(lastWildmatch >= 0)
					{
						// Consider pattern **/x and input x/x.
						// We've matched the prefix x/ so far: we
						// must try to extend the **!
						matcher = lastWildmatch + 1;
						right = wildmatchBacktrackPos;
						wildmatchBacktrackPos = -1;
					}
					else
					{
						return false;
					}
				}
			}
			else if(lastWildmatch != -1)
			{
				matcher = lastWildmatch + 1;
				right = wildmatchBacktrackPos;
				wildmatchBacktrackPos = -1;
			}
			else
			{
				return false;
			}
			right++;
		}
	}
	
	protected boolean matches(
		final int matcherIdx,
		final String path,
		final int startIncl,
		final int endExcl,
		final boolean assumeDirectory,
		final boolean pathMatch)
	{
		final IMatcher matcher = this.matchers.get(matcherIdx);
		
		final boolean matches = matcher.matches(path, startIncl, endExcl);
		if(!matches || !pathMatch || matcherIdx < this.matchers.size() - 1
			|| !(matcher instanceof AbstractMatcher))
		{
			return matches;
		}
		
		return assumeDirectory || !((AbstractMatcher)matcher).dirOnly;
	}
	
	protected static boolean isWild(final IMatcher matcher)
	{
		return matcher == WILD_NO_DIRECTORY || matcher == WILD_ONLY_DIRECTORY;
	}
}
