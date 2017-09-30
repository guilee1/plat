package com.rstc.modules.uemp.authority.inf;

import java.util.List;

import com.rstc.modules.uemp.authority.vo.UserVO;

public interface IUserInf {

	int addUser(UserVO vo);
	
	int removeUser(String uId);
	
	int updateUser(UserVO vo);
	
	int addUser(List<UserVO> voList);
	
	UserVO getUser(String uId);
	
	List<UserVO> getAllUser(int from,int to);
}
