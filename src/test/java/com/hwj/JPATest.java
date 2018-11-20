package com.hwj;

import com.google.common.collect.Maps;
import com.hwj.util.JsonAnalyze;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.hwj.entity.Admin;
import com.hwj.repository.AdminRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATest {
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private JsonAnalyze jsonAnalyze;

	@Test
	public void testJpa() throws Exception{
		Admin admin = adminRepository.queryAdminByUsernameOrEmail("hwj23", "23");
		System.out.println(admin.getPassword());

		Sort sort = new Sort(Sort.Direction.DESC,"create_date");
		Pageable pageable = new PageRequest(1,3,sort);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = sdf.parse("2018-9-18");
		Date lastDate =sdf.parse("2018-11-27");

		Page<Admin> list = adminRepository.queryAdminsByDate(null,null,pageable);

		System.out.println(list.toString());
		System.out.println(list.getSize());


	}
}
