package com.hwj.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IShareDao;
import com.hwj.entity.Share;
import com.hwj.service.IShareService;

@Service
public class ShareServiceImpl extends BaseServiceImpl<Share> implements IShareService{
	
	@Autowired
	@Qualifier(value="IShareDao")
	private IShareDao iShareDao;

	@Resource(name="IShareDao")
	@Override
	public void setDao(IBaseDao<Share> dao){
		super.setDao(dao);
	}

	@Override
	public List<Share> queryShareMind(Object value, Integer currentPage,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return iShareDao.queryShareMind(value, currentPage, pageSize);
	}

	@Override
	public Long searchShareMindPage(Object value) {
		// TODO Auto-generated method stub
		return iShareDao.searchShareMindPage(value);
	}
	

}
