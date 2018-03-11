package com.hwj.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.student;
import com.hwj.service.IStudentService;

@Component
public class TryCatchStudentService {
	
	@Autowired
	private IStudentService iStudentService;

	public boolean saveStudent(student student){
		
		try {
			this.iStudentService.save(student);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
	
	public student findByName(String propertyName,Object value){
		
		student student=new student();
		try {
			student=this.iStudentService.get(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return student;
	}
	
	
	public boolean delete(student student){
		
		try {
			this.iStudentService.delete(student);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public boolean update(student student){
		
		try {
			this.iStudentService.update(student);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		return true;
	}
	
	
	
}
