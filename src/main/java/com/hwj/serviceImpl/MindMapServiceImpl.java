package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IMindMapDao;
import com.hwj.entity.LoginUser;
import com.hwj.entity.MindMap;
import com.hwj.service.IMindMapService;

@Service
public class MindMapServiceImpl extends BaseServiceImpl<MindMap> implements
		IMindMapService {
	@Autowired
	@Qualifier(value="IMindMapDao")
	private IMindMapDao iMindMapDao;
	
	/**
	* 注入DAO
	*/
	@Resource(name = "IMindMapDao")
	@Override
	public void setDao(IBaseDao<MindMap> dao) {
	super.setDao(dao);
	}
	
	/*@Override
	public void setIBaseDao(IBaseDao<MindMap> iMindMapDao) {
		this.baseDao = iMindMapDao;
		this.iMindMapDao = ((IMindMapDao) iMindMapDao);
	}*/
}
