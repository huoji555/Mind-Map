package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IUploadFileDao;
import com.hwj.entity.UploadFile;
import com.hwj.service.IUploadFileService;

@Service
public class UploadFileServiceImpl extends BaseServiceImpl<UploadFile> implements IUploadFileService{

	@Autowired
	@Qualifier(value = "IUploadFileDao")
	private IUploadFileDao iUploadFileDao;
	
	//注入Dao
	@Resource( name = "IUploadFileDao")
	@Override
	public void setDao(IBaseDao<UploadFile> dao){
		super.setDao(dao);
	}
	
}
