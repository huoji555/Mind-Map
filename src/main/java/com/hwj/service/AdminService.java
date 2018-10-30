package com.hwj.service;

import com.hwj.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AdminService {
	
	void save(Admin admin);
	
	Admin queryAdminByUsernameOrEmail(String username, String email);
	
	int hasMatchAdmin(String username, String password);

	Page<Admin> findAllAdmin(Date firstDate, Date lastDate, Pageable pageable);
	
}
