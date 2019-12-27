package com.hwj.serviceImpl;

import java.util.List;

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

	@Override
	public List<MindMap> queryMindNap(Object value1, Integer currentPage,
			Integer pageSize) {
		// TODO Auto-generated method stub
		List<MindMap> list = iMindMapDao.queryMindNap(value1, currentPage, pageSize);
		return list;
	}

	@Override
	public Long searchMapPage(Object value1) {
		// TODO Auto-generated method stub
		return iMindMapDao.searchMapPage(value1);
	}

	@Override
	public Long getAllMapPage() {
		// TODO Auto-generated method stub
		return iMindMapDao.getAllMapPage();
	}

	@Override
	public List<MindMap> getAllMap() {
		return iMindMapDao.getAllMap();
	}
	
	
}
