package com.rstc.modules.uemp.core.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.cache.inf.ICache;
import com.rstc.modules.uemp.core.cache.inf.ICacheManager;
import com.rstc.modules.uemp.core.job.inf.IScheduler;
import com.rstc.modules.uemp.core.job.inf.ISchedulerManager;

@Component
public class SchedulerManagerImpl implements ISchedulerManager {

	public static final String JOB_CACHE = "ISchedulerManager_JOB_CACHE";
	
	@Autowired
	ICacheManager _cacheManager;
	
	@Override
	public IScheduler createSchedulerPool(String name,int threadNum) {
		ICache _cache = _cacheManager.getCache(JOB_CACHE);
		IScheduler _scheduler = new SchedulerImpl(threadNum);
		_cache.put(name, _scheduler);
		return _scheduler;
	}

	@Override
	public IScheduler getSchedulerPool(String name) {
		ICache _cache = _cacheManager.getCache(JOB_CACHE);
		return (IScheduler)_cache.get(name);
	}

	@Override
	public void closeSchedulerPool(String name) {
		IScheduler _scheduler = this.getSchedulerPool(name);
		if(_scheduler!=null)
			_scheduler.close();
	}

}
