package com.rstc.modules.uemp.core.module;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.cache.inf.ICacheManager;
import com.rstc.modules.uemp.core.db.inf.IDbService;
import com.rstc.modules.uemp.core.log.Logger;
import com.rstc.modules.uemp.core.util.Core;

@Component
public class CoreModule implements IModule {

	@Autowired
	IDbService _dbService;
	
	@Autowired
	ICacheManager _cacheManager;
	
	@Override
	public void startUp() {
		_cacheManager.initCacheManager();
		Logger.info("Core Module startUp.DB service starting...");
		try {
			_dbService.startMysql(Core.rootDir+"MySql");
		} catch (IOException e) {
			Logger.error("DB service start error", e);
			Core.showError(String.format("DB service start error,errcode is %s", e.getMessage()));
		} 
		Logger.info("Core Module startUp.DB service started.");
	}

	@Override
	public void shutDown() {
		_cacheManager.shutDownCacheManager();
		Logger.info("Core Module DB service shutDowning...");
		try {
			_dbService.stopMysql(Core.rootDir+"MySql");
		} catch (IOException | InterruptedException e) {
			Logger.error("DB service stop error", e);
			Core.showError(String.format("DB service stop error,errcode is %s", e.getMessage()));
		} 
		Logger.info("Core Module startUp.DB service shutDown.");
	}

}
