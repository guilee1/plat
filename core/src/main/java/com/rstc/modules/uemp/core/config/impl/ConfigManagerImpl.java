package com.rstc.modules.uemp.core.config.impl;

import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.config.inf.IConfig;
import com.rstc.modules.uemp.core.config.inf.IConfigManager;

@Component
public class ConfigManagerImpl implements IConfigManager {

	@Override
	public IConfig getConfig(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		if("properties".equalsIgnoreCase(suffix)){
			return new ConfigPropertyImpl(fileName);
		}else if("xml".equalsIgnoreCase(suffix)){
			return new ConfigXmlImpl(fileName);
		}
		throw new RuntimeException("ConfigManager not support file type:"+suffix);
	}

}
