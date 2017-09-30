package com.rstc.modules.uemp.mo.inf;

import java.util.List;

import com.rstc.modules.uemp.mo.vo.DomainVO;
import com.rstc.modules.uemp.mo.vo.EmsVO;
import com.rstc.modules.uemp.mo.vo.LinkVO;
import com.rstc.modules.uemp.mo.vo.NeVO;

public interface IMoInf {

	int addDomain(DomainVO vo);
	
	int addNe(NeVO vo);
	
	int addLink(LinkVO vo);
	
	int addEms(EmsVO vo);
	
	int removeDomain(String domainId);
	
	int removeNe(String neId);
	
	int removeLink(String linkId);
	
	int removeEms(String emsId);
	
	int updateDomainInfo(DomainVO vo);
	
	int updateNeInfo(NeVO vo);
	
	int updateLinkInfo(LinkVO vo);
	
	int updateEmsInfo(EmsVO vo);
	
	List<DomainVO> getAllDomainByUser(String uid,int from,int to);
	
	List<NeVO> getAllNeByDomain(String domainId,int from,int to);
	
	List<LinkVO> getAllLinkByDomain(String domainId,int from,int to);
	
	List<EmsVO> getAllEmsInfo(int from,int to);
	
	
}
