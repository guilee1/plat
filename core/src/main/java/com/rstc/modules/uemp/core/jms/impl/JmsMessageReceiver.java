package com.rstc.modules.uemp.core.jms.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rstc.modules.uemp.core.jms.inf.IMsgReceiver;

public final class JmsMessageReceiver {

	List<IMsgReceiver> _listener = new CopyOnWriteArrayList<>();
	
	public void receive(Object message){
		for(IMsgReceiver _receiver : _listener){
			_receiver.receive(message);
		}
	}
	
	void registCallBack(IMsgReceiver msgReceiver){
		this._listener.add(msgReceiver);
	}
	
	void removeCallBack(IMsgReceiver msgReceiver){
		this._listener.remove(msgReceiver);
	}
}
