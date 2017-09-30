package com.rstc.modules.uemp.authority.inf;

import java.util.List;

import com.rstc.modules.uemp.authority.vo.RoleVO;

public interface IRoleInf {

	int addRole(RoleVO vo);
	
	int removeRole(String rId);
	
	int updateRoleInfo(RoleVO vo);
	
	int addUserInRole(String rid,String uid);
	
	int addUserInRole(String rid,List<Integer> uidList);
	
	int updateUserRole(String uid,String oldRoleId,String newRoleId);
	
	int updateUserRole(List<Integer> uidList,String oldRoleId,String newRoleId);
	
	List<RoleVO> getAllRole(int from,int to);
}
