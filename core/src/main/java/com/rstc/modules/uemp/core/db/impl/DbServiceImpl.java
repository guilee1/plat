package com.rstc.modules.uemp.core.db.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.config.inf.IConfig;
import com.rstc.modules.uemp.core.config.inf.IConfigManager;
import com.rstc.modules.uemp.core.db.inf.IDbService;
import com.rstc.modules.uemp.core.log.Logger;
import com.rstc.modules.uemp.core.util.Core;

@Component
public class DbServiceImpl implements IDbService{

	@Autowired
	IConfigManager _configManager;
	
	public DbServiceImpl() {
		IConfig _config = _configManager.getConfig(Core.SYSTEM_PRO_FILE);
		this.mysqlUserName = _config.getString("mysql.userName", "root");
		this.mysqlPwd = _config.getString("mysql.pwd", "root");
	}
	
	
	String mysqlUserName;
	String mysqlPwd;
	
	public  void startMysql(String path) throws IOException {
		String s1 = System.getProperty("mysql.home");
		if (s1 == null) {
			s1 = path;
		}
		if (s1.indexOf(".") >= 0) {
			s1 = (new File(s1)).getAbsolutePath();
		}
		String osName = System.getProperty("os.name");
		String s4 = s1.replace('/', '\\');
		String s6 = "";
		if ((osName.equalsIgnoreCase("windows nt"))
				|| (osName.equalsIgnoreCase("windows 2000"))
				|| (osName.equalsIgnoreCase("windows xp"))
				|| (osName.equalsIgnoreCase("windows 2003"))
				|| (osName.equalsIgnoreCase("windows vista"))
				|| (osName.equalsIgnoreCase("windows 7"))
				|| (osName.contains("Windows Server 2008"))) {
			s6 = "cmd /c \"\"" + s1 + "\\bin\\StartMySQL\" \"" + s4 + "\"\"";
		} else if ((osName.indexOf("95") >= 0) || (osName.indexOf("98") >= 0)) {
			s6 = "command.com /c \"" + s1 + "\\bin\\StartMySQL\" " + "\"" + s4
					+ "\"";
		} else if (osName.equals("SunOS")) {
			s6 = "sh " + path + "/bin/startMySQL.sh " + s1;
		} else {
			Logger.info("Unknown windows version???");
			return;
		}
		Runtime.getRuntime().exec(s6);
	}

	public  void stopMysql(String path)throws IOException,InterruptedException {
		String command = "";
		String dBServerName = "localhost";
		String mysql = System.getProperty("mysql.home");
		if (mysql == null) {
			mysql = path;
		}
		String osName = System.getProperty("os.name");
		String s2 = (new File(mysql)).getAbsolutePath();
		s2 = s2.replace('/', '\\');
		if (osName.toUpperCase().indexOf("WINDOW") >= 0) {
			command = "\"" + s2 + "\\bin\\mysqladmin\" ";
			command = addUserAndPassword(command);
			command = command + " -h " + dBServerName + " shutdown";
		} else if (osName.toUpperCase().indexOf("SUNOS") >= 0) {
			command = s2 + "\\bin\\mysqladmin ";
			command = addUserAndPassword(command);
			command = command + " -h " + dBServerName + " shutdown";
		} else {
			return;
		}
		if ((osName.equalsIgnoreCase("windows nt"))
				|| (osName.equalsIgnoreCase("windows 2000"))
				|| (osName.equalsIgnoreCase("windows xp"))
				|| (osName.equalsIgnoreCase("windows 2003"))
				|| (osName.equalsIgnoreCase("windows vista"))
				|| (osName.equalsIgnoreCase("windows 7"))
				|| (osName.contains("Windows Server 2008"))) {
			command = "cmd /c \"" + command + "\"";
		} else if ((osName.indexOf("95") >= 0)
				|| (osName.indexOf("98") >= 0)) {
			command = "command.com /c " + command;
		} else if (osName.equals("SunOS")) {
			command = command.replace('\\', '/');
		} else {
			Logger.info("Unknown windows version???");
			return;
		}
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
	}

	private  String addUserAndPassword(String s) {
		String s1 = mysqlUserName;
		if (s1 != null) {
			s = s + " -u " + s1;
		}
		String s2 = "";
		if (s2 != null) {
			s = s + " --password="+ mysqlPwd + s2;
		}
		return s;
	}

	public String getMysqlUserName() {
		return mysqlUserName;
	}

	public void setMysqlUserName(String mysqlUserName) {
		this.mysqlUserName = mysqlUserName;
	}

	public String getMysqlPwd() {
		return mysqlPwd;
	}

	public void setMysqlPwd(String mysqlPwd) {
		this.mysqlPwd = mysqlPwd;
	}


}
