package com.rstc.modules.uemp.core.db.inf;

import java.io.IOException;


public interface IDbService {

	void startMysql(String path) throws IOException;
	
	void stopMysql(String path)throws IOException,InterruptedException;
	
}
