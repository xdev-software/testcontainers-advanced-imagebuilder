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
package software.xdev.testcontainers.imagebuilder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Fork of {@link AdvancedParsedDockerfile} that makes it possible to parse ARGS<br> Fixes:
 * <ul>
 * 	<li><a href="https://github.com/testcontainers/testcontainers-java/issues/3238">testcontainers-java#3238</a></li>
 * </ul>
 *
 * @author AB
 * @see org.testcontainers.images.ParsedDockerfile
 */
public class AdvancedParsedDockerfile
{
	private static final Logger LOG = LoggerFactory.getLogger(AdvancedParsedDockerfile.class);
	
	protected static final Pattern FROM_LINE_PATTERN =
		Pattern.compile("FROM (?<arg>--[^\\s]+\\s)*(?<image>[^\\s]+).*", Pattern.CASE_INSENSITIVE);
	
	protected final Path dockerFilePath;
	
	protected Set<String> dependencyImageNames = Collections.emptySet();
	protected final Map<String, Optional<String>> arguments = new HashMap<>();
	
	public AdvancedParsedDockerfile(final Path dockerFilePath)
	{
		this.dockerFilePath = dockerFilePath;
		this.parse(this.read());
	}
	
	protected List<String> read()
	{
		if(!Files.exists(this.dockerFilePath))
		{
			LOG.warn("Tried to parse Dockerfile at path {} but none was found", this.dockerFilePath);
			return Collections.emptyList();
		}
		
		try
		{
			return Files.readAllLines(this.dockerFilePath);
		}
		catch(final IOException e)
		{
			LOG.warn("Unable to read Dockerfile at path {}", this.dockerFilePath, e);
			return Collections.emptyList();
		}
	}
	
	protected void parse(final List<String> lines)
	{
		// FROM
		this.dependencyImageNames = lines.stream()
			.map(FROM_LINE_PATTERN::matcher)
			.filter(Matcher::matches)
			.map(matcher -> matcher.group("image"))
			.collect(Collectors.toSet());
		
		if(!this.dependencyImageNames.isEmpty())
		{
			LOG.debug("Found dependency images in Dockerfile {}: {}", this.dockerFilePath, this.dependencyImageNames);
		}
		
		// ARG
		final Properties properties = new Properties();
		try
		{
			properties.load(new StringReader(
				lines.stream()
					.filter(line -> line.length() > 4 && line.startsWith("ARG "))
					.map(line -> line.substring(4))
					.collect(Collectors.joining("\n"))
			));
		}
		catch(final IOException e)
		{
			LOG.error("Unable to load Args from Dockerfile {}", this.dockerFilePath);
		}
		
		for(final Entry<Object, Object> entry : properties.entrySet())
		{
			final String value = entry.getValue().toString();
			
			this.arguments.put(entry.getKey().toString(), value.isEmpty() ? Optional.empty() : Optional.of(value));
		}
		
		if(!this.arguments.isEmpty())
		{
			LOG.debug("Found args in Dockerfile {}: {}", this.dockerFilePath, this.arguments);
		}
	}
	
	public Set<String> getDependencyImageNames()
	{
		return this.dependencyImageNames;
	}
	
	public Map<String, Optional<String>> getArguments()
	{
		return this.arguments;
	}
}
