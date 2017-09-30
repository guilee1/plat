package com.rstc.modules.uemp.core.jms.impl;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.rstc.modules.uemp.core.jms.inf.IJmsConsumer;
import com.rstc.modules.uemp.core.jms.inf.IMsgReceiver;

public class JmsConsumerImpl implements IJmsConsumer {

	DefaultMessageListenerContainer _container;
	JmsMessageReceiver _receiver;
	
	public JmsConsumerImpl(DefaultMessageListenerContainer container, JmsMessageReceiver _msgReceiver) {
		this._container = container;
		this._receiver = _msgReceiver;
	}

	@Override
	public void registConsumer(IMsgReceiver receiver) {
		_receiver.registCallBack(receiver);
	}

	@Override
	public void unRegistConsumer(IMsgReceiver receiver) {
		_receiver.removeCallBack(receiver);
	}

	@Override
	public void close() {
		if(_container!=null)
			_container.shutdown();
	}


}
