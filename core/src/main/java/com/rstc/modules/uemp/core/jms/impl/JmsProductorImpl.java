package com.rstc.modules.uemp.core.jms.impl;

import java.io.Serializable;

import com.rstc.modules.uemp.core.jms.inf.IJmsProductor;

public class JmsProductorImpl implements IJmsProductor {

	JmsSender _sender;
	
	public JmsProductorImpl(JmsSender sender) {
		this._sender = sender;
	}
	
	@Override
	public void send(Serializable obj) {
		_sender.sendMessage(obj);
	}


}
