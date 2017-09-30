package com.rstc.modules.uemp.core.config.impl;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.rstc.modules.uemp.core.config.inf.IConfig;
import com.rstc.modules.uemp.core.log.Logger;
import com.rstc.modules.uemp.core.util.Core;

public class ConfigXmlImpl implements IConfig {

	FileBasedConfigurationBuilder<XMLConfiguration> builder;
	XMLConfiguration config;
	
	public ConfigXmlImpl(String fileName) {
		Parameters params = new Parameters();
		builder =
		    new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
		    .configure(params.xml()
		        .setFile(new File(Core.confDir+fileName))
		        .setValidating(false));
		try {
			config = builder.getConfiguration();
		} catch (ConfigurationException e) {
			Logger.error("build ConfigPropertyImpl error!", e);
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}
	
	
	@Override
	public String getString(String key) {
		return config.getString(key);
	}

	@Override
	public String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	@Override
	public int getInt(String key) {
		return config.getInt(key);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	@Override
	public long getLong(String key) {
		return config.getLong(key);
	}

	@Override
	public long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	@Override
	public Iterator<String> getkeys() {
		return config.getKeys();
	}

	@Override
	public void setProperty(String key, String value) {
		config.setProperty(key, value);
		try {
			builder.save();
		} catch (ConfigurationException e) {
			Logger.error("setProperty ConfigPropertyImpl save error!", e);
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	@Override
	public void addProperty(String key, String value) {
		config.addProperty(key, value);
		try {
			builder.save();
		} catch (ConfigurationException e) {
			Logger.error("addProperty ConfigPropertyImpl save error!", e);
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	@Override
	public void clearProperty(String key) {
		config.clearProperty(key);
		try {
			builder.save();
		} catch (ConfigurationException e) {
			Logger.error("clearProperty ConfigPropertyImpl save error!", e);
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}
}
