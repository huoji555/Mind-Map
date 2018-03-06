package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IFileCollectionDao;
import com.hwj.entity.AssessLog;
import com.hwj.entity.FileCollection;
import com.hwj.service.IFileCollectionService;

@Service
public class FileCollectionServiceImpl extends BaseServiceImpl<FileCollection>
		implements IFileCollectionService {

	@Autowired
	@Qualifier(value="IFileCollectionDao")
	private IFileCollectionDao iFileCollectionDao;
	
	/**
	* 注入DAO
	*/
	@Resource(name = "IFileCollectionDao")
	@Override
	public void setDao(IBaseDao<FileCollection> dao) {
	super.setDao(dao);
	}

	/*@Override
	public void setIBaseDao(IBaseDao<FileCollection> iFileCollectionDao) {

		this.baseDao = iFileCollectionDao;
		this.iFileCollectionDao = (IFileCollectionDao) iFileCollectionDao;
	}*/

}
