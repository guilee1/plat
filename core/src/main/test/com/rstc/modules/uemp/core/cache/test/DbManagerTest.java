package com.rstc.modules.uemp.core.cache.test;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.rstc.modules.uemp.core.db.impl.DbServiceImpl;
import com.rstc.modules.uemp.core.db.inf.IDbService;
import com.rstc.modules.uemp.core.util.Core;

public class DbManagerTest {

	IDbService _service = new DbServiceImpl();
	
	@Test
	public void testStartDbService(){
		try {
			_service.startMysql(Core.dbDir);
			Assert.assertTrue(true);
		} catch (IOException e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStopDbService(){
		try {
			_service.stopMysql(Core.dbDir);
			Assert.assertTrue(true);
		} catch (IOException | InterruptedException e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
}
