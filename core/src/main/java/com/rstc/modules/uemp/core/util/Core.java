package com.rstc.modules.uemp.core.util;

import java.io.File;

import javax.swing.JOptionPane;

public final class Core {

	public static String rootDir;
	public static String usersDir;
	public static String confDir;
	public static String dbDir;
	public static String MODULE_XML_FILE;
	public static String SYSTEM_PRO_FILE;

	static {
		rootDir = null;
		rootDir = System.getProperty("user.dir");
		rootDir = rootDir.replace(File.separatorChar, '/');
		rootDir = rootDir + "/";
		usersDir = null;
		usersDir = System.getProperty("user.dir");
		usersDir = usersDir.replace(File.separatorChar, '/');
		usersDir = usersDir + "/";
		confDir = rootDir + "conf/";
		dbDir = rootDir+"MySQL";
		MODULE_XML_FILE = confDir+"modules.xml";
		SYSTEM_PRO_FILE = "system.properties";
	}

	public static void showError(String errorInfo){
		JOptionPane.showMessageDialog(null, errorInfo, "Error",JOptionPane.ERROR_MESSAGE );
	}
}
