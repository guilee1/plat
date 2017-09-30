package com.rstc.modules.uemp.core.jms.inf;

public interface IMsgReceiver {

	/**
	 * receive a message come from message provider.
	 * caution: must be registed this receiverImpl first.
	 * @param obj
	 */
	void receive(final Object obj);
}
