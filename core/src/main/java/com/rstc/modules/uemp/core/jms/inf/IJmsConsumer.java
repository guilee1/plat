package com.rstc.modules.uemp.core.jms.inf;

public interface IJmsConsumer {

	/**
	 *  regist a consumer which want to receive messages.
	 * @param receiver
	 */
	void registConsumer(IMsgReceiver receiver);
	
	/**
	 *  unregist a consumer.
	 * @param receiver
	 */
	void unRegistConsumer(IMsgReceiver receiver);
	
	/**
	 * close consumer
	 */
	void close();
}
