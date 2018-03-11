package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IMindNodeDao;
import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;
import com.hwj.service.IMindNodeService;

@Service
public class MindNodeServiceImpl extends BaseServiceImpl<MindNode> implements
		IMindNodeService {
	@Autowired
	@Qualifier(value="IMindNodeDao")
	private IMindNodeDao iMindNodeDao;
	
	/**
	* 注入DAO
	*/
	@Resource(name = "IMindNodeDao")
	@Override
	public void setDao(IBaseDao<MindNode> dao) {
	super.setDao(dao);
	}
	
	
}
