package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IZsdDao;
import com.hwj.entity.FileShare;
import com.hwj.entity.Zsd;
import com.hwj.service.IZsdService;

@Service
public class ZsdServiceImpl extends BaseServiceImpl<Zsd> implements IZsdService{

	@Autowired
	@Qualifier("IZsdDao")
	private  IZsdDao iZsdDao;
	
	@Resource(name = "IZsdDao")
	@Override
	public void setDao(IBaseDao<Zsd> dao){
		super.setDao(dao);
	}
	
	
	
}
