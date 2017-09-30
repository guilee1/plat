package com.rstc.modules.uemp.core.conf.test;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rstc.modules.uemp.core.aware.SelfBeanFactoryAware;
import com.rstc.modules.uemp.core.conf.Feature;
import com.rstc.modules.uemp.core.conf.ModuleLoader;

public class ModuleLoaderTest {

	@Test
	public void testLoader(){
		new ClassPathXmlApplicationContext("applicationContext.xml");
		ModuleLoader loader = SelfBeanFactoryAware.getBean("moduleLoader");
		try {
			Feature f = loader.load();
			Assert.assertFalse(false);
			Assert.assertEquals(f.getModules().size(), 3);
		} catch (Exception e) {
			Assert.assertFalse(true);
		}
		
	}
}
