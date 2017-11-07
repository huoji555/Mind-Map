package com.hwj.dao;

import com.hwj.entity.student;

public interface IStudentDao  extends IBaseDao<student>{

	/*//查询名字
	@Query(value = "select a from student a where a.name= :#{#student.name}", nativeQuery = true)  
	@Modifying 
	public student findByName(@Param("student") String name);*/
	
	
	

}
