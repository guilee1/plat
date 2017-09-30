package com.rstc.modules.uemp.core.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.conf.Feature;
import com.rstc.modules.uemp.core.conf.ModuleLoader;
import com.rstc.modules.uemp.core.context.AppContext;
import com.rstc.modules.uemp.core.log.Logger;

@Component
public class CoreRunner implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	ModuleLoader moduleLoader;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent paramE) {
		if (paramE.getApplicationContext().getParent() == null) {
			Feature feature = null;
			try {
				feature = moduleLoader.load();
			} catch (Exception e) {
				Logger.error("load modules error:", e);
				System.exit(1);
			}
			AppContext.putContext(AppContext.APPCONTEXT, feature);
//			for (Module module : feature.getModules()) {
//				Logger.info(String.format("module name is:%s,class name is %s",
//						module.getName(), module.getClassName()));
//				IModule service = (IModule) SelfBeanFactoryAware.getBean(module
//						.getClassName());
//				service.startUp();
//			}
		}
	}

}
