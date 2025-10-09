package software.xdev.testcontainers.imagebuilder.concurrent;

import java.util.concurrent.ExecutorService;


/**
 * Holds the {@link ExecutorService} that will be used for ImageBuilder related work.
 * <p>
 * This {@link ExecutorService} should be used in favor of {@link java.util.concurrent.ForkJoinPool#commonPool()} as it
 * guarantees better scalability.
 * </p>
 */
public final class ImageBuilderExecutorServiceHolder
{
	private static ExecutorService instance;
	
	public static ExecutorService instance()
	{
		if(instance == null)
		{
			init();
		}
		return instance;
	}
	
	private static synchronized void init()
	{
		if(instance != null)
		{
			return;
		}
		instance = ExecutorServiceCreatorHolder.instance().createUnlimited("ImageBuilder");
	}
	
	public static void setInstance(final ExecutorService instance)
	{
		ImageBuilderExecutorServiceHolder.instance = instance;
	}
	
	private ImageBuilderExecutorServiceHolder()
	{
	}
}
