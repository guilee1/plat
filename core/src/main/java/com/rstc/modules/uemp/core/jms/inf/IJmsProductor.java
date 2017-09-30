package com.rstc.modules.uemp.core.jms.inf;

import java.io.Serializable;

public interface IJmsProductor {

	/**
	 * send a message to JMS server
	 * @param obj
	 */
	void send(Serializable obj);
	
	
}
