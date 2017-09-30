package com.rstc.modules.uemp.core.cache.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rstc.modules.uemp.core.job.impl.SchedulerManagerImpl;
import com.rstc.modules.uemp.core.job.inf.IScheduler;
import com.rstc.modules.uemp.core.job.inf.ISchedulerManager;

public class JobManagerTest {

	
	ISchedulerManager _manager;
	IScheduler _job;
	
	@Before
	public void before(){
		_manager = new SchedulerManagerImpl();	
		_job = _manager.createSchedulerPool("test", 10);
	}
	
	@After
	public void after(){
		_manager.closeSchedulerPool("test");
	}
	
	@Test
	public void JobTest()throws IOException{
		_job.schedule(new JobTask(), 5, TimeUnit.SECONDS);
		System.in.read();
	}
	
	static class JobTask implements Runnable{

		@Override
		public void run() {
			System.out.println("running!");
		}
		
	}
}
