package com.hwj.service;

import com.hwj.entity.Admin;

public interface AdminService {
	
	void save(Admin admin);
	
	Admin queryAdminByUsernameOrEmail(String username, String email);
	
	int hasMatchAdmin(String username, String password);
	
}
