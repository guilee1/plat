package com.rstc.modules.uemp.core.cache.test;

import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rstc.modules.uemp.core.cache.impl.CacheManagerImpl;
import com.rstc.modules.uemp.core.cache.inf.ICacheManager;
import com.rstc.modules.uemp.core.jms.impl.JmsClientManagerImpl;
import com.rstc.modules.uemp.core.jms.impl.JmsServiceManagerImpl;
import com.rstc.modules.uemp.core.jms.inf.IJmsConsumer;
import com.rstc.modules.uemp.core.jms.inf.IJmsProductor;
import com.rstc.modules.uemp.core.jms.inf.IMsgReceiver;

public class JmsClientManagerTest {

	static ICacheManager _cacheManager = new CacheManagerImpl();
	
	static JmsClientManagerImpl _client = new JmsClientManagerImpl();
	
	static JmsServiceManagerImpl _service = new JmsServiceManagerImpl();
	
	@BeforeClass
	public static void beforeClass(){
		_service.set_cacheManager(_cacheManager);
		_client.set_cacheManager(_cacheManager);
		_service.startJmsService("amq", 8002);
	}
	
	@AfterClass
	public static void afterClass(){
		_service.stopJmsService("amq");
	}
	
	
	@Before
	public void producerTest(){
		_client.createTopicProductor("producer1", "nio://127.0.0.1:8002", "alarm");
		_client.createTopicConsumer("consumer1","nio://127.0.0.1:8002", "alarm");
		IJmsConsumer _consumer = _client.getConsumer("consumer1");
		_consumer.registConsumer(new MsgReceiver());
	}
	
	@After
	public void consumerTest(){
		_client.closeProductor("producer1");
		_client.closeConsumer("consumer1");
	}
	
	static class MsgReceiver implements IMsgReceiver {

		@Override
		public void receive(Object obj) {
			AlarmMsg msg = (AlarmMsg)obj;
			System.out.println("receive a msg from jms server :"+msg.getAlarmName());
		}
		
	}
	static class AlarmMsg implements Serializable{
		private static final long serialVersionUID = 1L;
		int alarmId = 1;
		String alarmName = "guilee";
		public int getAlarmId() {
			return alarmId;
		}
		public void setAlarmId(int alarmId) {
			this.alarmId = alarmId;
		}
		public String getAlarmName() {
			return alarmName;
		}
		public void setAlarmName(String alarmName) {
			this.alarmName = alarmName;
		}
	}
	
	@Test
	public void testSendMsg()throws Exception{
		IJmsProductor producer = _client.getProductor("producer1");
		for(int i=0;i<100;++i)
			producer.send(new AlarmMsg());
		System.in.read();
	}
}

