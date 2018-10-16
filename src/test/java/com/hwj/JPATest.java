package com.hwj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hwj.entity.Admin;
import com.hwj.repository.AdminRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATest {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Test
	public void testJpa() {
		Admin admin = adminRepository.queryAdminByUsernameOrEmail("hwj", "23");
		
		System.out.println(admin.getPassword());
	}
}
