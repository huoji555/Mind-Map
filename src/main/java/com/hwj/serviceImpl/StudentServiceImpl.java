package com.hwj.serviceImpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IStudentDao;
import com.hwj.entity.MindNode;
import com.hwj.entity.student;
import com.hwj.service.IStudentService;
@Service
public class StudentServiceImpl extends BaseServiceImpl<student> implements IStudentService{
	
	
	@Autowired
	@Qualifier(value="IStudentDao")
	private IStudentDao iStudentDao;

	

	/**
	* 注入DAO
	*/
	@Resource(name = "IStudentDao")
	@Override
	public void setDao(IBaseDao<student> dao) {
	super.setDao(dao);
	}
	//保存信息
		public void saveStudent(student student){
			
				this.iStudentDao.save(student);
			
		}


}
