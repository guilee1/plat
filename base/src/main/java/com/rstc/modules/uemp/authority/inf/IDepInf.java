package com.rstc.modules.uemp.authority.inf;

import java.util.List;

import com.rstc.modules.uemp.authority.vo.DepVO;

public interface IDepInf {

	int addDep(DepVO vo);
	
	int removeDep(String depId);
	
	int updateDepInfo(DepVO vo);
	
	int addUserInDep(String depId,String uId);
	
	int addUserInDep(String depId,List<Integer> uidList);
	
	int updateUserDep(String uid,String oldDepId,String newDepId);
	
	int updateUserDep(List<Integer> uidList,String oldDepId,String newDepId);
	
	DepVO getDepInfo(String depId);
	
	List<DepVO> getAllDep(int from,int to);
	
}
