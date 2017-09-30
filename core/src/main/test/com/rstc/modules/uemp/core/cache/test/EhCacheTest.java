package com.rstc.modules.uemp.core.cache.test;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

public class EhCacheTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CacheManager _manager = CacheManager.create();
		String name[] = _manager.getCacheNames();
		for(String item : name)
			System.out.println(item);
		
//		Cache _cache = _manager.getCache("myCache");
//		System.out.println(_cache.getSize());
//		for(int i=0;i<100;i++)
//			_cache.put(new Element(i, 1));
//		System.out.println(_cache.getSize());
		
//		Cache _newCache = new Cache("newCache", 1, true, false, 0, 0);
//		_manager.addCache(_newCache);
//		System.out.println(_newCache.getSize());
//		for(int i=0;i<1000000;i++)
//			_newCache.put(new Element(i, 1));
//		System.out.println(_newCache.getSize());
		
		CacheConfiguration _config = new CacheConfiguration();
		_config.name("newCache").maxEntriesInCache(0).eternal(false).overflowToDisk(false)
				.maxEntriesLocalHeap(0).timeToIdleSeconds(0).timeToLiveSeconds(0);
		Searchable searchable = new Searchable();
		_config.searchable(searchable);
		SearchAttribute searchAttribute = new SearchAttribute();
		searchAttribute.name("neName");
		searchable.addSearchAttribute(searchAttribute);
		
		SearchAttribute searchAttribute2 = new SearchAttribute();
		searchAttribute2.name("count");
		searchable.addSearchAttribute(searchAttribute2);
		
		
		
		Cache _newCache = new Cache(_config);
		_manager.addCache(_newCache);
		for (int i = 0; i < 10000; i++) {
			_newCache.put(new Element(i, new Ne(String.valueOf(i), "name" + i,i)));
		}
		System.out.println(_newCache.getSize());
		 Query query = _newCache.createQuery();  
		 Attribute<String> nameAtt = _newCache.getSearchAttribute("neName");  
		 Attribute<Integer> ncountAtt = _newCache.getSearchAttribute("count");
		 query.includeValues().addCriteria(nameAtt.ilike("name*").and(ncountAtt.le(1000))); 
		 Results results = query.execute();  
		List<Result> resultList = results.all();  
		if (resultList != null && !resultList.isEmpty()) {  
		   for (Result result : resultList) {  
		      if (results.hasValues()) {  
		    	  System.out.println(result.getValue());  
		      }  
		   }  
		}  
//		_manager.shutdown();
	}

	
}
