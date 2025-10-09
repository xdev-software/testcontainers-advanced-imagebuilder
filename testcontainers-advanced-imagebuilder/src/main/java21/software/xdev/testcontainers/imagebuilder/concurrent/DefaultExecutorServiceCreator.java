package software.xdev.testcontainers.imagebuilder.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class DefaultExecutorServiceCreator implements ExecutorServiceCreator
{
	@Override
	public ExecutorService createUnlimited(final String threadNamePrefix)
	{
		return Executors.newCachedThreadPool(factory(threadNamePrefix));
	}
	
	private static ThreadFactory factory(final String threadNamePrefix)
	{
		return Thread.ofVirtual()
			.name(threadNamePrefix + "-", 0)
			.factory();
	}
}
