package com.rstc.modules.uemp.core.jms.impl;

import java.io.File;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.cache.inf.ICache;
import com.rstc.modules.uemp.core.cache.inf.ICacheManager;
import com.rstc.modules.uemp.core.jms.inf.IJmsServiceManager;
import com.rstc.modules.uemp.core.log.Logger;

@Component
public class JmsServiceManagerImpl implements IJmsServiceManager {

	public static final String JMS_CACHE = "IJmsServiceManager_JMS_CACHE";
	
	@Autowired
	ICacheManager _cacheManager;
	
	public ICacheManager get_cacheManager() {
		return _cacheManager;
	}

	public void set_cacheManager(ICacheManager _cacheManager) {
		this._cacheManager = _cacheManager;
	}

	@Override
	public int startJmsService(String serviceName,int port) {
		ICache _cache = _cacheManager.getCache(JMS_CACHE);
		if(null != _cache.get(serviceName)){
			Logger.info(String.format("jms service broker %s has exist!", serviceName));
			return -1;
		}else{
			BrokerService broker = new BrokerService();
			broker.setBrokerName(serviceName);
			broker.setPersistent(false);
			broker.setUseShutdownHook(false);
			PersistenceAdapter persistenceAdapter = new KahaDBPersistenceAdapter();
			persistenceAdapter.setDirectory(new File("activemq-data"));
			persistenceAdapter.setBrokerName(serviceName);
			try {
				broker.setPersistenceAdapter(persistenceAdapter);
				broker.addConnector("nio://0.0.0.0:"+port);
				broker.start();
				_cache.put(serviceName, broker);
			} catch (Exception e) {
				Logger.error(String.format("jms service broker %s start error!", serviceName),e);
				return -1;
			}
			return 0;
		}
	}

	@Override
	public int stopJmsService(String serviceName){
		ICache _cache = _cacheManager.getCache(JMS_CACHE);
		BrokerService _broker = (BrokerService)_cache.get(serviceName);
		if(null != _broker){
			try {
				_broker.stop();
			} catch (Exception e) {
				Logger.error(String.format("jms service broker %s stop error!", serviceName),e);
				return -1;
			}
		}else{
			Logger.info(String.format("jms service broker %s has not exist!", serviceName));
			return -1;
		}
		return 0;
	}

}
