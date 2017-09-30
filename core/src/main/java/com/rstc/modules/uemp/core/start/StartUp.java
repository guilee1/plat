package com.rstc.modules.uemp.core.start;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {


	public static void main(String[] args) throws IOException {
		new ClassPathXmlApplicationContext("applicationContext.xml");
	}

}
