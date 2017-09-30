package com.rstc.modules.uemp.core.jms.inf;

public interface IJmsClientManager {

	
	IJmsProductor createTopicProductor(String productorName,String brokerUrl,String topicName);
	
	
	IJmsProductor getProductor(String productorName);
	
	
	void closeProductor(String productorName);
	
	
	IJmsConsumer createTopicConsumer(String consumerName,String brokerUrl,String topicName);
	
	
	IJmsConsumer getConsumer(String consumerName);			
			
			
	void closeConsumer(String consumerName);
	
}
