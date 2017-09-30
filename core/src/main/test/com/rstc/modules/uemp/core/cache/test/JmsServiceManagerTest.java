package com.rstc.modules.uemp.core.cache.test;

import junit.framework.Assert;

import org.junit.Test;

import com.rstc.modules.uemp.core.jms.impl.JmsServiceManagerImpl;
import com.rstc.modules.uemp.core.jms.inf.IJmsServiceManager;

public class JmsServiceManagerTest {

	
	IJmsServiceManager  _manager = new JmsServiceManagerImpl();
	
	@Test
	public void testServiceStartup()throws Exception{
		int result = _manager.startJmsService("testServer", 61616);
		Assert.assertEquals(0, result);
		
		testServiceStop();
		
		System.in.read();
	}
	
	public void testServiceStop(){
		int result = _manager.stopJmsService("testServer");
		Assert.assertEquals(0, result);
	}
}
