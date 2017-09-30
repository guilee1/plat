package com.rstc.modules.uemp.core.module;


/**
 * Module Interface.every subSystem all components with some modules.
 * every module must implement IModule interface.
 * @author Administrator
 *
 */
public interface IModule {

	void startUp();

	void shutDown();

}
