package com.hwj.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwj.entity.Admin;
import com.hwj.repository.AdminRepository;
import com.hwj.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;

	//保存
	@Override
	public void save(Admin admin) {adminRepository.save(admin);}

    //查询
	@Override
	public Admin queryAdminByUsernameOrEmail(String username, String email) {
		return adminRepository.queryAdminByUsernameOrEmail(username, email);
	}

	//有无匹配的登录对象
	@Override
	public int hasMatchAdmin(String username, String password) {
		int b = 0;
		Admin admin = new Admin();
		admin = adminRepository.queryAdminByUsernameOrEmail(username,"");

		if (admin == null) {
			return b = 0 ;      //0表示该不存在该用户
		} else if (adminRepository.queryAdminByUsernameAndPassword(username, password) == null ) {
			return b = 2;       //表示密码错误
		}
		
		return 1;               //表通过验证，可以登录
	}

	
}
