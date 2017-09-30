package com.rstc.modules.uemp.webApp.module;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Set;

import org.apache.jasper.runtime.TldScanner;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

import com.rstc.modules.uemp.core.log.Logger;
import com.rstc.modules.uemp.core.module.IModule;

@Component
public class WebAppModule implements IModule {

	static final String JETTY_XML_FILE = "/jetty.xml";
	static final String SPRING_XML_FILE = "classpath:applicationContext.xml";
	
	Server server;

	@Override
	public void startUp() {
		Logger.info("WebAppModule startUp!");
		try {
			startJetty();
		} catch (Exception e) {
			Logger.error("mtosi-ni-impl[run]:error in start Jetty Server", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutDown() {
		Logger.info("WebAppModule shutDown!");
		try {
			if (this.server != null) {
				this.server.stop();
			}
		} catch (Exception exp) {
			Logger.error("mtosi-ni-impl[run]:fail to stop jetty!", exp);
		}
	}

	void startJetty() throws Exception {
		FileInputStream fis = new FileInputStream(this.getClass().getResource(JETTY_XML_FILE).getPath());
		XmlConfiguration config = new XmlConfiguration(fis);
		server = (Server) config.configure();
		WebAppContext webAppCtx = new WebAppContext("src/main/webapp", "/");
        webAppCtx.setDescriptor("src/main/webapp/WEB-INF/web.xml");
        webAppCtx.setResourceBase("src/main/webapp");
        webAppCtx.addEventListener(new ContextLoaderListener());
        webAppCtx.setInitParameter("contextConfigLocation", SPRING_XML_FILE);
        webAppCtx.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webAppCtx);
        
		try {
			Field f = TldScanner.class.getDeclaredField("systemUris");
			f.setAccessible(true);
			((Set) f.get(null)).clear();
		} catch (Exception e) {
			throw new RuntimeException("Could not clear TLD system uris.", e);
		}
        
        server.start();
	}
	
	public static void main(String[] args) {
		new WebAppModule().startUp();
	}
}
