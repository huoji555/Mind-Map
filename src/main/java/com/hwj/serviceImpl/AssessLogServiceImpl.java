package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.hwj.dao.IAssessLogDao;
import com.hwj.dao.IBaseDao;
import com.hwj.entity.AssessLog;
import com.hwj.service.IAssessLogService;

@Service
public class AssessLogServiceImpl extends BaseServiceImpl<AssessLog> implements
		IAssessLogService {

	@Autowired
	@Qualifier(value="IAssessLogDao")
	private IAssessLogDao iAssessLogDao;

	/**
	* 注入DAO
	*/
	@Resource(name = "IAssessLogDao")
	@Override
	public void setDao(IBaseDao<AssessLog> dao) {
	super.setDao(dao);
	}
	
	/*@Override
	public void setIBaseDao(IBaseDao<AssessLog> iAssessLogDao) {
		// TODO Auto-generated method stub
		this.baseDao = iAssessLogDao;
		this.iAssessLogDao = (IAssessLogDao) iAssessLogDao;
	}*/

	
	public void setAssessLog(AssessLog assessLog) {
		System.out.println(assessLog);

		iAssessLogDao.save(assessLog);
	}

}
