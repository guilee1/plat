package com.rstc.modules.uemp.core.conf;


public class Module {
	
	/**
	 * the name of module
	 */
	String name;
	
	/**
	 * if is a implement of a module,must have a startUp class
	 */
	String className;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
