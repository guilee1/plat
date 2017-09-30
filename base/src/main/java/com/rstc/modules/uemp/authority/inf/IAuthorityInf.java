package com.rstc.modules.uemp.authority.inf;

public interface IAuthorityInf {

	int login(String userName,String pwdKey);
	
	int logout(String userName);
	
	int kickOut(String userName);
}
