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
package software.xdev.testcontainers.imagebuilder.log;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;


public class LoggingBuildImageResultCallback extends BuildImageResultCallback
{
	protected final Logger logger;
	protected final List<String> notFlushedString;
	
	public LoggingBuildImageResultCallback(final Logger logger)
	{
		this(logger, new ArrayList<>(16));
	}
	
	protected LoggingBuildImageResultCallback(final Logger logger, final List<String> notFlushedString)
	{
		this.logger = logger;
		this.notFlushedString = notFlushedString;
	}
	
	@Override
	public void onNext(final BuildResponseItem item)
	{
		super.onNext(item);
		
		if(item.isErrorIndicated())
		{
			if(this.logger.isInfoEnabled())
			{
				this.logger.info(removeEnd(String.join("", this.notFlushedString), "\n"));
			}
			this.notFlushedString.clear();
			
			this.logger.error(item.getErrorDetail() != null ? item.getErrorDetail().getMessage() : "<null>");
		}
		else if(item.getStream() != null)
		{
			final String details = item.getStream();
			
			this.notFlushedString.add(details);
			
			if(details.endsWith("\n") || this.notFlushedString.size() > 1000)
			{
				if(this.logger.isInfoEnabled())
				{
					this.logger.info(removeEnd(String.join("", this.notFlushedString), "\n"));
				}
				this.notFlushedString.clear();
			}
		}
	}
	
	// From Apache Commons Lang3 - StringUtils
	protected static String removeEnd(final String str, final String remove)
	{
		if(str == null || str.isEmpty() || remove == null || remove.isEmpty())
		{
			return str;
		}
		if(str.endsWith(remove))
		{
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}
}
