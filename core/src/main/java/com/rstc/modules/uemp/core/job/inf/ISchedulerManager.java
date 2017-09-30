package com.rstc.modules.uemp.core.job.inf;

public interface ISchedulerManager {

	IScheduler createSchedulerPool(String name,int threadNum);
	
	IScheduler getSchedulerPool(String name);
	
	void closeSchedulerPool(String name);
	
}
