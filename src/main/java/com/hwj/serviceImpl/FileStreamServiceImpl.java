package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IFileStreamDao;
import com.hwj.entity.FileShare;
import com.hwj.entity.FileStream;
import com.hwj.service.IFileStreamService;

@Service
public class FileStreamServiceImpl extends BaseServiceImpl<FileStream>
		implements IFileStreamService {

	@Autowired
	@Qualifier(value="IFileStreamDao")
	private IFileStreamDao iFileStreamDao;

	/**
	* 注入DAO
	*/
	@Resource(name = "IFileStreamDao")
	@Override
	public void setDao(IBaseDao<FileStream> dao) {
	super.setDao(dao);
	}
	
	
	/*@Override
	public void setIBaseDao(IBaseDao<FileStream> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao = iFileStreamDao;
		this.iFileStreamDao = (IFileStreamDao) iFileStreamDao;
	}*/

}
