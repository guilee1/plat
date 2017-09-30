package com.rstc.modules.uemp.core.cache.inf;

public interface ICacheManager {

	void initCacheManager();
	
	void shutDownCacheManager();
	
	ICache createSearchableCache(String name,String[] attrs);
	
	ICache getCache(String key);
	
}
