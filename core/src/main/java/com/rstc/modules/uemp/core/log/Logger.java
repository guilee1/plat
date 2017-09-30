package com.rstc.modules.uemp.core.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class Logger {

	private static final Log log = LogFactory.getLog(Logger.class);
	
	public static void error(Throwable e){
		log.error(e);
	}
	
	public static void error(String msg,Throwable e){
		log.error(msg, e);
	}
	
	public static void info(String e){
		log.info(e);
	}
	
	public static void info(String msg,Throwable e){
		log.info(msg, e);
	}
	public static void debug(String e){
		log.debug(e);
	}
	public static void debug(String msg,Throwable e){
		log.debug(msg, e);
	}
}
