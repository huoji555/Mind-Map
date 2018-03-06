package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IFileShareDao;
import com.hwj.entity.FileCollection;
import com.hwj.entity.FileShare;
import com.hwj.service.IShareFileService;

@Service
public class FileShareServiceImpl extends BaseServiceImpl<FileShare> implements
		IShareFileService {

	@Autowired
	@Qualifier(value="IFileShareDao")
	private IFileShareDao iFileShareDao;

	/**
	* 注入DAO
	*/
	@Resource(name = "IFileShareDao")
	@Override
	public void setDao(IBaseDao<FileShare> dao) {
	super.setDao(dao);
	}
	
	/*@Override
	public void setIBaseDao(IBaseDao<FileShare> iFileShareDao) {
		// TODO Auto-generated method stub
		this.baseDao = iFileShareDao;
		this.iFileShareDao = (IFileShareDao) iFileShareDao;
	}
*/
}
