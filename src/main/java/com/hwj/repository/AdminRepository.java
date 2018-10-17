package com.hwj.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.hwj.entity.Admin;

public interface AdminRepository extends PagingAndSortingRepository<Admin, String>{
	
	Admin queryAdminByUsernameOrEmail(String username,String email);
	
	Admin queryAdminByUsernameAndPassword(String username, String password);
	
}
