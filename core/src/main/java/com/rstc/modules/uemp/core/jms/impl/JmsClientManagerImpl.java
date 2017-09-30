package com.rstc.modules.uemp.core.jms.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.cache.inf.ICacheManager;
import com.rstc.modules.uemp.core.jms.inf.IJmsClientManager;
import com.rstc.modules.uemp.core.jms.inf.IJmsConsumer;
import com.rstc.modules.uemp.core.jms.inf.IJmsProductor;

@Component
public class JmsClientManagerImpl implements IJmsClientManager {

	public static final String JMS_CACHE = "IJmsClientManager_JMS_CACHE";
	
	@Qualifier("taskExecutor")
	ThreadPoolTaskExecutor _executor;
	
	@Autowired
	ICacheManager _manager;

	public ICacheManager get_cacheManager() {
		return _manager;
	}

	public void set_cacheManager(ICacheManager _cacheManager) {
		this._manager = _cacheManager;
	}
	
	@Override
	public IJmsProductor createTopicProductor(String productorName,String brokerUrl, String topicName) {
		ActiveMQConnectionFactory _connFactory = new ActiveMQConnectionFactory(brokerUrl);
		_connFactory.setAlwaysSyncSend(true);
		_connFactory.setTrustAllPackages(true);
		CachingConnectionFactory cacheConnFactory = new CachingConnectionFactory(_connFactory);
		cacheConnFactory.setSessionCacheSize(10);
		ActiveMQTopic topic = new ActiveMQTopic(topicName);
		JmsTemplate template = new JmsTemplate(cacheConnFactory);
		template.setMessageConverter(new JmsMessageConverter());
		JmsSender sender = new JmsSender(template,topic);
		IJmsProductor _productor = new JmsProductorImpl(sender);
		_manager.getCache(JMS_CACHE).put(productorName, _productor);
		return _productor;
	}

	@Override
	public IJmsProductor getProductor(String productorName) {
		return (IJmsProductor)_manager.getCache(JMS_CACHE).get(productorName);
	}

	@Override
	public void closeProductor(String productorName) {
		_manager.getCache(JMS_CACHE).remove(productorName);
	}
	
	
	@Override
	public IJmsConsumer createTopicConsumer(String consumerName,String brokerUrl,String topicName) {
		JmsMessageReceiver _msgReceiver = new JmsMessageReceiver();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
		connectionFactory.setTrustAllPackages(true);
		CachingConnectionFactory cacheConnFactory = new CachingConnectionFactory(connectionFactory);
		cacheConnFactory.setSessionCacheSize(10);
		ActiveMQTopic topic = new ActiveMQTopic(topicName);
		MessageListenerAdapter adapter = new MessageListenerAdapter(_msgReceiver);
		adapter.setDefaultListenerMethod("receive");
		adapter.setMessageConverter(new JmsMessageConverter());
		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setMessageListener(adapter);
		container.setDestination(topic);
		container.setConnectionFactory(cacheConnFactory);
		container.setAutoStartup(false);
		
//		ThreadPoolTaskExecutor _executor = new ThreadPoolTaskExecutor();
//		_executor.setCorePoolSize(5);
//		container.setTaskExecutor(_executor);
		container.afterPropertiesSet();
		IJmsConsumer _consumer = new JmsConsumerImpl(container,_msgReceiver);
		_manager.getCache(JMS_CACHE).put(consumerName, _consumer);
		container.start();
		return _consumer;
	}

	@Override
	public IJmsConsumer getConsumer(String consumerName) {
		return (IJmsConsumer)_manager.getCache(JMS_CACHE).get(consumerName);
	}

	@Override
	public void closeConsumer(String consumerName) {
		IJmsConsumer consumer = this.getConsumer(consumerName);
		if(consumer != null){
			consumer.close();
			_manager.getCache(JMS_CACHE).remove(consumerName);
		}
	}


	
	

}
