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

import java.util.Collections;
import java.util.List;


/**
 * Represents a bundle of ignore rules inherited from a base directory.
 */
public class IgnoreNode
{
	/**
	 * Result from {@link IgnoreNode#isIgnored(String, boolean)}.
	 */
	public enum MatchResult
	{
		/**
		 * The file is not ignored, due to a rule saying its not ignored.
		 */
		NOT_IGNORED,
		
		/**
		 * The file is ignored due to a rule in this node.
		 */
		IGNORED,
		
		/**
		 * The ignore status is unknown, check inherited rules.
		 */
		CHECK_PARENT
	}
	
	
	/**
	 * The rules that have been parsed into this node.
	 */
	protected final List<FastIgnoreRule> rules;
	
	/**
	 * Create an ignore node with given rules.
	 *
	 * @param rules list of rules.
	 */
	public IgnoreNode(final List<FastIgnoreRule> rules)
	{
		this.rules = rules;
	}
	
	/**
	 * Get list of all ignore rules held by this node
	 *
	 * @return list of all ignore rules held by this node
	 */
	public List<FastIgnoreRule> getRules()
	{
		return Collections.unmodifiableList(this.rules);
	}
	
	/**
	 * Determine if an entry path matches an ignore rule.
	 *
	 * @param entryPath   the path to test. The path must be relative to this ignore node's own repository path, and in
	 *                    repository path format (uses '/' and not '\').
	 * @param isDirectory true if the target item is a directory.
	 * @return status of the path.
	 */
	public MatchResult isIgnored(final String entryPath, final boolean isDirectory)
	{
		final Boolean result = this.checkIgnored(entryPath, isDirectory);
		if(result == null)
		{
			return MatchResult.CHECK_PARENT;
		}
		
		return result
			? MatchResult.IGNORED
			: MatchResult.NOT_IGNORED;
	}
	
	/**
	 * Determine if an entry path matches an ignore rule.
	 *
	 * @param entryPath   the path to test. The path must be relative to this ignore node's own repository path, and in
	 *                    repository path format (uses '/' and not '\').
	 * @param isDirectory true if the target item is a directory.
	 * @return Boolean.TRUE, if the entry is ignored; Boolean.FALSE, if the entry is forced to be not ignored (negated
	 * match); or null, if undetermined
	 * @since 4.11
	 */
	public Boolean checkIgnored(
		final String entryPath,
		final boolean isDirectory)
	{
		// Parse rules in the reverse order that they were read because later
		// rules have higher priority
		for(int i = this.rules.size() - 1; i > -1; i--)
		{
			final FastIgnoreRule rule = this.rules.get(i);
			if(rule.isMatch(entryPath, isDirectory, true))
			{
				return rule.getResult();
			}
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		return this.rules.toString();
	}
}
