package com.rstc.modules.uemp.core.cache.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.rstc.modules.uemp.core.config.impl.ConfigManagerImpl;
import com.rstc.modules.uemp.core.config.inf.IConfig;
import com.rstc.modules.uemp.core.config.inf.IConfigManager;

public class ConfigManagerTest {

	IConfigManager _manager;
	
	@Before
	public void before(){
		_manager = new ConfigManagerImpl();
	}
	
	@Test
	public void propertyFileTest(){
		IConfig _config = _manager.getConfig("config.properties");
		String value = _config.getString("notExist");
		Assert.assertNull(value);
		
		value = _config.getString("notExist","defaultValue");
		Assert.assertEquals("defaultValue", value);
		
		value = _config.getString("database.host");
		Assert.assertEquals("db.acme.com", value);
		
		_config.addProperty("phoneNum", "13871133907");
		value = _config.getString("phoneNum");
		Assert.assertEquals("13871133907", value);
		
		long lValue = _config.getLong("phoneNum");
		Assert.assertEquals(13871133907L, lValue);
		
		_config.clearProperty("phoneNum");
		value = _config.getString("phoneNum");
		Assert.assertNull(value);
	}
	
	
	@Test
	public void xmlFileTest(){
		IConfig _config = _manager.getConfig("1.xml");
		String value = _config.getString("notExist");
		Assert.assertNull(value);
		
		value = _config.getString("notExist","defaultValue");
		Assert.assertEquals("defaultValue", value);
		
		value = _config.getString("modules.module(0)");
		Assert.assertEquals("lusinni", value);
		
		_config.addProperty("phoneNum", "13871133907");
		value = _config.getString("phoneNum");
		Assert.assertEquals("13871133907", value);
		
		long lValue = _config.getLong("phoneNum");
		Assert.assertEquals(13871133907L, lValue);
		
		_config.clearProperty("phoneNum");
		value = _config.getString("phoneNum");
		Assert.assertNull(value);
	}
}
