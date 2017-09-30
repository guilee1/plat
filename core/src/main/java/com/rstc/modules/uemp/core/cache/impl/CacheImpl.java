package com.rstc.modules.uemp.core.cache.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;


import com.rstc.modules.uemp.core.cache.inf.ICache;

public class CacheImpl implements ICache {

	Cache _cache;
	
	public CacheImpl(Cache _cache) {
		this._cache = _cache;
	}

	@Override
	public void put(String key, Object value) {
		_cache.put(new Element(key, value));
	}

	@Override
	public Object get(String key) {
		Element _ele = _cache.get(key);
		if(_ele!=null)
			return _ele.getObjectValue();
		return null;
	}

	@Override
	public void remove(String key) {
		_cache.remove(key);
	}

	@Override
	public int getSize() {
		return _cache.getSize();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getKeys() {
		return _cache.getKeys();
	}

	@Override
	public void clear() {
		_cache.removeAll();
	}

	@Override
	public List queryLike(String attrName, String pattern) {
		Query query = _cache.createQuery();
		Attribute<String> nameAtt = _cache.getSearchAttribute(attrName);
		query.includeValues().addCriteria(nameAtt.ilike(pattern));
		return fillQueryResult(query);
	}

	private List fillQueryResult(Query query) {
		List resultValue = new ArrayList<>();
		Results results = query.execute();
		List<Result> resultList = results.all();
		if (resultList != null && !resultList.isEmpty()) {
			for (Result result : resultList) {
				if (results.hasValues()) {
					resultValue.add(result.getValue());
				}
			}
		}
		return resultValue;
	}

	@Override
	public List queryEq(String attrName, String value) {
		Query query = _cache.createQuery();
		Attribute<String> nameAtt = _cache.getSearchAttribute(attrName);
		query.includeValues().addCriteria(nameAtt.eq(value));
		return fillQueryResult(query);
	}

	@Override
	public List queryEq(String attrName, int value) {
		Query query = _cache.createQuery();
		Attribute<Integer> nameAtt = _cache.getSearchAttribute(attrName);
		query.includeValues().addCriteria(nameAtt.eq(value));
		return fillQueryResult(query);
	}

	@Override
	public List queryLt(String attrName, int value) {
		Query query = _cache.createQuery();
		Attribute<Integer> nameAtt = _cache.getSearchAttribute(attrName);
		query.includeValues().addCriteria(nameAtt.lt(value));
		return fillQueryResult(query);
	}

	@Override
	public List queryGt(String attrName, int value) {
		Query query = _cache.createQuery();
		Attribute<Integer> nameAtt = _cache.getSearchAttribute(attrName);
		query.includeValues().addCriteria(nameAtt.gt(value));
		return fillQueryResult(query);
	}

}
