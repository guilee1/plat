package com.rstc.modules.uemp.core.jms.impl;

import java.io.Serializable;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.support.converter.MessageConverter;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class JmsMessageConverter implements MessageConverter {

    @Override
    public Message toMessage(Object obj, Session session) throws JMSException {
    	ActiveMQObjectMessage txtMsg = (ActiveMQObjectMessage) session.createObjectMessage();
    	txtMsg.setObject((Serializable)obj);
        return txtMsg;
    }

    @Override
    public Object fromMessage(Message msg) throws JMSException {
        if (msg instanceof ActiveMQObjectMessage) {
        	ActiveMQObjectMessage txtMsg = (ActiveMQObjectMessage) msg;
            return txtMsg.getObject();
        }
        return null;
    }
}
