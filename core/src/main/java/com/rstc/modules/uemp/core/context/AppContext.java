package com.rstc.modules.uemp.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AppContext {

	static Map<String, Object> _context = new ConcurrentHashMap<>();
	
	public static final String APPCONTEXT = "APPCONTEXT";
	
	public static void putContext(String key,Object value){
		_context.put(key, value);
	}
	
	public static Object getContext(String key){
		return _context.get(key);
	}
}
