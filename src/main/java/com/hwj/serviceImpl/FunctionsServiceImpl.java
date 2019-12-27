package com.hwj.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IFunctionsDao;
import com.hwj.entity.FileStream;
import com.hwj.entity.Functions;
import com.hwj.service.IFunctionsService;

@Service
public class FunctionsServiceImpl extends BaseServiceImpl<Functions> implements
		IFunctionsService {

	@Autowired
	@Qualifier(value="IFunctionsDao")
	private IFunctionsDao iFunctionsDao;

	/**
	* 注入DAO
	*/
	@Resource(name = "IFunctionsDao")
	@Override
	public void setDao(IBaseDao<Functions> dao) {
	super.setDao(dao);
	}
	
	/*@Override
	public void setIBaseDao(IBaseDao<Functions> iFunctionsDao) {
		// TODO Auto-generated method stub
		this.baseDao = iFunctionsDao;
		this.iFunctionsDao = (IFunctionsDao) iFunctionsDao;
	}*/

	@Override
	public List<Functions> getFunctions(String grandparentId, String limit) {
		List<Functions> list;
		System.out.println(1);
		list = iFunctionsDao.getFunctions(grandparentId, limit);
		return list;
	}

}
