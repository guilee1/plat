package com.rstc.modules.uemp.core.jms.impl;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

public class JmsSender {

    private JmsTemplate template;
    private Destination dest;


    public JmsSender(JmsTemplate temp,Destination d) {
		this.template = temp;
		this.dest = d;
	}
    
    
    public void sendMessage(Object message) {
        template.convertAndSend(this.dest, message);
    }

}
