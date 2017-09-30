package com.rstc.modules.uemp.core.jms.inf;

public interface IJmsServiceManager {

	int startJmsService(String serviceName,int port);
	
	int stopJmsService(String serviceName);
}
