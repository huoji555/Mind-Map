package com.hwj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hwj.entity.Admin;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AdminRepository extends PagingAndSortingRepository<Admin, String>{
	
	Admin queryAdminByUsernameOrEmail(String username,String email);

	Admin queryAdminByUsernameAndPassword(String username, String password);

	@Query( value = "select id, username, null as password, null as salt, email, ip, create_date, role_id from admin " +
			        "where 1=1 " +
			        "and (case when :firstDate != '' and :lastDate != '' then create_date between :firstDate and :lastDate else 1=1 end ) " +
			        "order by ?#{#pageable}",
			countQuery = "select count(*) from admin " +
					"where 1=1 " +
					"and (case when :firstDate != '' and :lastDate != '' then create_date between :firstDate and :lastDate else 1=1 end ) ",
			nativeQuery = true)
	Page<Admin> queryAdminsByDate(@Param("firstDate") Date firstDate,@Param("lastDate") Date lastDate, Pageable pageable);

	
}
