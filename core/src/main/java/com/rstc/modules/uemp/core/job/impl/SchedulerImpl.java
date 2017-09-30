package com.rstc.modules.uemp.core.job.impl;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.rstc.modules.uemp.core.job.inf.IScheduler;

public class SchedulerImpl implements IScheduler {

	EventExecutorGroup defaultScheduler;
	
	public SchedulerImpl(int threadNum) {
		this.defaultScheduler = new DefaultEventExecutorGroup(threadNum);
	}
	
	
	@Override
	public void schedule(Runnable paramRunnable, long delay,
			TimeUnit paramTimeUnit) {
		defaultScheduler.schedule(paramRunnable, delay, paramTimeUnit);
	}

	@Override
	public void scheduleAtFixedRate(Runnable paramRunnable, long initialDelay,
			long period, TimeUnit paramTimeUnit) {
		defaultScheduler.scheduleAtFixedRate(paramRunnable, initialDelay, period, paramTimeUnit);
	}

	@Override
	public void scheduleAtTime(Runnable paramRunnable, Date d) {
		this.schedule(paramRunnable, computeDelayMill(d), TimeUnit.MILLISECONDS);

	}

	@Override
	public <T> Future<T> submit(Callable<T> syncTask) {
		return defaultScheduler.submit(syncTask);
	}


	@Override
	public void close() {
		defaultScheduler.shutdownGracefully();
	}

	
	public static long computeDelayMill(Date _boomTime){
		long now = System.currentTimeMillis();
		long after = _boomTime.getTime();
		return after-now;
	}
}
