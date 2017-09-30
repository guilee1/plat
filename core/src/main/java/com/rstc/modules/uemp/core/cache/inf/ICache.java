package com.rstc.modules.uemp.core.cache.inf;

import java.util.List;

public interface ICache {

	void put(String key,Object value);
	
	
	Object get(String key);
	
	
	void remove(String key);
	
	
	int getSize();
	
	
	void clear();
	
	
	List<String> getKeys();
	
	List queryLike(String attrName,String pattern);
	
	List queryEq(String attrName,String value);
	
	List queryEq(String attrName,int value);
	
	List queryLt(String attrName,int value);
	
	List queryGt(String attrName,int value);
}
