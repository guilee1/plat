package com.rstc.modules.uemp.core.config.inf;

import java.util.Iterator;

public interface IConfig {
	
	String getString(String key);
	
	String getString(String key,String defaultValue);
	
	int getInt(String key);
	
	int getInt(String key,int defaultValue);
	
	long getLong(String key);
	
	long getLong(String key,long defaultValue);
	
	Iterator<String> getkeys();
	
	void setProperty(String key,String value);
	
	void addProperty(String key,String value);
	
	void clearProperty(String key);
	
}
