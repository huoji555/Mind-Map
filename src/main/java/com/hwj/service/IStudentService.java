package com.hwj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwj.dao.IStudentDao;
import com.hwj.entity.student;

@Service
public class IStudentService {
	
	@Autowired
	private IStudentDao iStudentDao;
	
	
	//保存信息
	public void saveStudent(student student){
		
			this.iStudentDao.save(student);
		
	} 
	
	
	//查找信息
	public student findByName(String propertyName,Object value){
		
		return this.iStudentDao.get(propertyName, value);
			
	}
	
	
	
	

}
