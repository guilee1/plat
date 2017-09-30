package com.rstc.modules.uemp.core.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;

import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.cache.inf.ICache;
import com.rstc.modules.uemp.core.cache.inf.ICacheManager;

@Component
public class CacheManagerImpl implements ICacheManager {

	Map<String, ICache> _cacheMap = new ConcurrentHashMap<String, ICache>();
	
	CacheManager _cacheManagerInstance;
	
	public CacheManagerImpl() {
		_cacheManagerInstance = CacheManager.create();
	}
	
	
	@Override
	public ICache getCache(String key) {
		ICache _cache = _cacheMap.get(key);
		if(_cache==null)
			_cache = this.createCache(key);
		return _cache;
	}

	public ICache createCache(String name) {
		CacheConfiguration _config = new CacheConfiguration();
		_config.name(name).maxEntriesInCache(0).eternal(false).overflowToDisk(false)
				.maxEntriesLocalHeap(0).timeToIdleSeconds(0).timeToLiveSeconds(0);
		Cache _newCache = new Cache(_config);
		_cacheManagerInstance.addCache(_newCache);
		ICache _cache = new CacheImpl(_newCache);
		_cacheMap.put(name, _cache);
		return _cache;
	}

	
	@Override
	public ICache createSearchableCache(String name, String[] attrs) {
		CacheConfiguration _config = new CacheConfiguration();
		_config.name(name).maxEntriesInCache(0).eternal(false).overflowToDisk(false)
				.maxEntriesLocalHeap(0).timeToIdleSeconds(0).timeToLiveSeconds(0);
		Searchable searchable = new Searchable();
		for(String attr : attrs){
			SearchAttribute searchAttribute = new SearchAttribute();
			searchAttribute.name(attr);
			searchable.addSearchAttribute(searchAttribute);
		}
		_config.searchable(searchable);
		Cache _newCache = new Cache(_config);
		_cacheManagerInstance.addCache(_newCache);
		ICache _cache = new CacheImpl(_newCache);
		_cacheMap.put(name, _cache);
		return _cache;
	}
	

	@Override
	public void initCacheManager() {
		_cacheManagerInstance = CacheManager.create();
	}

	@Override
	public void shutDownCacheManager() {
		if(_cacheManagerInstance!=null){
			_cacheManagerInstance.clearAll();
			_cacheMap.clear();
			_cacheManagerInstance.shutdown();
		}
	}


}
