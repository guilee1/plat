package com.rstc.modules.uemp.alm.inf;

import java.util.List;

import com.rstc.modules.uemp.alm.vo.AlarmVO;

public interface IAlmInf {

	List<AlarmVO> getAllCurrentAlarm(String uid,int from,int to);
	
	List<AlarmVO> getCurrentAlarm(String uid,long startTime,long endTime,int from,int to);
	
	List<AlarmVO> getAllHistoryAlarm(int from,int to);
	
	List<AlarmVO> getAllHistoryAlarm(long startTime,long endTime,int from,int to);
	
	void addCurrentAlarm(AlarmVO vo);
	
	void addCurrentAlarm(List<AlarmVO> vo);
	
	int confirmAlarm(long id,String remark);
	
	int cancelConfirmAlarm(long id);
	
	int clearAlarm(long id);
	
	int cancelClearAlarm(long id);
}
