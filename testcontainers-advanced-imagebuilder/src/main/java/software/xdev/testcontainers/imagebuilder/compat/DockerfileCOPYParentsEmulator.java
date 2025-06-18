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
package software.xdev.testcontainers.imagebuilder.compat;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import software.xdev.testcontainers.imagebuilder.glob.GlobMatcher;
import software.xdev.testcontainers.imagebuilder.transfer.DockerFileLineModifier;


/**
 * Emulates <a href="https://docs.docker.com/reference/dockerfile/#copy---parents">COPY --parents</a>
 */
public class DockerfileCOPYParentsEmulator implements DockerFileLineModifier
{
	@Override
	public List<String> modify(final List<String> lines, final Set<String> relativeFiles)
	{
		final List<String> trimmedLines = lines.stream()
			.map(String::trim)
			.toList();
		
		// Support for --parents is only available in 1.7-labs
		if(trimmedLines.stream().noneMatch(s ->
			s.startsWith("# syntax=docker/dockerfile:") && s.endsWith("-labs")))
		{
			return lines;
		}
		
		return trimmedLines.stream()
			.flatMap(line -> this.handleLine(line, relativeFiles))
			.toList();
	}
	
	protected Stream<String> handleLine(final String line, final Set<String> relativeFiles)
	{
		final String parentsArg = " --parents";
		if(!line.startsWith("COPY") || !line.contains(parentsArg))
		{
			return Stream.of(line);
		}
		
		final int parentsArgIndex = line.indexOf(parentsArg);
		final String lineBeforeArgs = line.substring(0, parentsArgIndex);
		String lineAfterArgs = "";
		String targetPath = "";
		
		String argsStr = line.substring(parentsArgIndex + parentsArg.length() + 1);
		final boolean isLastArg = !argsStr.contains(" --");
		if(!isLastArg)
		{
			final int nextArgIndicatorIndex = argsStr.indexOf(" --");
			lineAfterArgs = argsStr.substring(nextArgIndicatorIndex + 1);
			lineAfterArgs = lineAfterArgs.substring(0, lineAfterArgs.lastIndexOf(' '));
			targetPath = argsStr.substring(nextArgIndicatorIndex + lineAfterArgs.length() + 2);
			
			argsStr = argsStr.substring(0, nextArgIndicatorIndex);
		}
		
		final String[] args = argsStr.split(" ");
		if(isLastArg)
		{
			targetPath = args[args.length - 1];
		}
		final String lineAfterArgsFinal = lineAfterArgs;
		final String targetPathFinal = targetPath;
		final String targetPathFinalForRelative = targetPathFinal.endsWith("/")
			? targetPathFinal
			: (targetPathFinal + "/");
		
		return Stream.of(args)
			.limit((long)args.length - (isLastArg ? 1 : 0))
			.flatMap(source -> {
				if(!source.contains("*") && !source.contains("/"))
				{
					return Stream.of(Map.entry(source, targetPathFinal));
				}
				
				final GlobMatcher matcher = new GlobMatcher(source);
				return relativeFiles.stream()
					.filter(matcher::matches)
					.map(s -> Map.entry(s, targetPathFinalForRelative + s));
			})
			.map(e -> lineBeforeArgs + " " + e.getKey()
				+ (!lineAfterArgsFinal.isEmpty() ? " " + lineAfterArgsFinal : "")
				+ " " + e.getValue());
	}
}
