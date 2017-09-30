package com.rstc.modules.uemp.core.job.inf;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface IScheduler {

	void schedule(Runnable paramRunnable,long delay, TimeUnit paramTimeUnit);
	
	void scheduleAtFixedRate(Runnable paramRunnable, long initialDelay, long period,TimeUnit paramTimeUnit);

	void scheduleAtTime(Runnable paramRunnable,Date d);

	<T> Future<T> submit(Callable<T> syncTask);

	void close();
}
