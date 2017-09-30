package com.rstc.modules.uemp.core.cache.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rstc.modules.uemp.core.cache.impl.CacheManagerImpl;
import com.rstc.modules.uemp.core.cache.inf.ICache;
import com.rstc.modules.uemp.core.cache.inf.ICacheManager;

public class CacheManagerTest {

	ICacheManager _manager = new CacheManagerImpl();
	
	@Before
	public void before(){
		_manager.initCacheManager();
	}
	
	@After
	public void after(){
		_manager.shutDownCacheManager();
	}
	
	@Test
	public void testGetNotExistCache(){
		ICache _cache = _manager.getCache("notExist");
		Assert.assertNotNull(_cache);
	}
	
	@Test
	public void testCreCache(){
		ICache _cache = _manager.getCache("newCache");
		Assert.assertNotNull(_cache);
		Assert.assertEquals(0, _cache.getSize());
		List<String> _key = _cache.getKeys();
		Assert.assertEquals(0, _key.size());
		
		Assert.assertNull(_cache.get("notExistKey"));
		
		_cache.put("1", 1387113390);
		int value = (int)_cache.get("1");
		Assert.assertNotNull(value);
		
		_key = _cache.getKeys();
		Assert.assertEquals(1, _key.size());
		Assert.assertEquals("1", _key.get(0));
		
		_cache.remove("notExistKey");
		Assert.assertEquals(1, _cache.getSize());
		
		_cache.remove("1");
		Assert.assertEquals(0, _cache.getSize());
	}
	
	@Test
	public void testCreSearchableCache(){
		ICache _cache = _manager.createSearchableCache("newCache",new String[]{"neId","neName","count"});
		for(int i=0;i<100000;++i){
			_cache.put(String.valueOf(i), new Ne(String.valueOf(i), "name"+i, i));
		}
		Assert.assertEquals(100000, _cache.getSize());
		List obj = _cache.queryEq("neId", "99");
		Assert.assertEquals(1, obj.size());
		 
		List obj2 = _cache.queryLike("neName", "name10000*");
		Assert.assertEquals(1, obj2.size());
		
		List obj3 = _cache.queryEq("count", 99);
		Assert.assertEquals(1, obj3.size());
		
		List obj4 = _cache.queryGt("count", 90000);
		Assert.assertEquals(9999, obj4.size());
		
		for(Object item : obj4){
			Ne n = (Ne)item;
			System.out.println(n.count);
		}
	}
}
