package com.rstc.modules.uemp.core.cache.test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.sync.LockMode;

import com.rstc.modules.uemp.core.util.Core;

public class ConfifurationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Configurations configs = new Configurations();
//		try
//		{
//		    Configuration config = configs.properties(new File(Core.confDir+"config.properties"));
//		    System.out.println(config.getString("database.host"));
//		    System.out.println(config.getInt("database.port"));
//		    config.addProperty("hello", "world");
//		}
//		catch (ConfigurationException cex)
//		{
//		    // Something went wrong
//		}
//
//		try
//		{
//		    XMLConfiguration config = configs.xml(Core.confDir+"modules.xml");
//		    System.out.println(config.getString("modules.module(1).name"));
//		}
//		catch (ConfigurationException cex)
//		{
//		    cex.printStackTrace();
//		}
		
		
//		//----------------------------properties
//		Parameters params = new Parameters();
//		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
//		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
//		    .configure(params.properties()
//		        .setFile(new File(Core.confDir+"config.properties")));
//		try
//		{
//		    Configuration config = builder.getConfiguration();
//		    System.out.println(config.getString("database.host"));
//		    System.out.println(config.getInt("database.port"));
////		    config.addProperty("hello", "world");
//		    System.out.println(config.getString("database.o"));
//		    List objs = config.getList("database.o");
//		    System.out.println(objs.size());
////		    builder.save();
//		    
//		    Iterator<String> iter = config.getKeys();
//			while(iter.hasNext()) { 
//				  String s = iter.next(); 
//				  System.out.println(s);
//				} 
//			config.lock(LockMode.WRITE);
//		}
//		catch(ConfigurationException cex)
//		{
//		    // loading of the configuration file failed
//		}
		
		
		
		
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<XMLConfiguration> builder =
		    new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
		    .configure(params.xml()
		        .setFile(new File(Core.confDir+"1.xml"))
		        .setValidating(false));

		// This will throw a ConfigurationException if the XML document does not
		// conform to its DTD.
		try {
			XMLConfiguration config = builder.getConfiguration();
			List objs = config.getList("modules.module");
			System.out.println(objs.size());
			System.out.println(config.get(String.class, "modules.module(0)"));
			
//			config.addProperty("modules.module(0)", "world");
			
			
//			config.setProperty("modules.module(0)", "lusinni");
//			builder.save();
			
			Iterator<String> iter = config.getKeys();
			while(iter.hasNext()) { 
				  String s = iter.next(); 
				  System.out.println(s);
				} 
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
