package com.hwj.service;

import org.springframework.data.domain.Page;

import com.hwj.entity.AdminErrorMsg;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AdminErrorMsgService {
	
	void save(AdminErrorMsg adminErrorMsg);
	
	Page<AdminErrorMsg> pageException(Date firstDate, Date lastDate, Pageable pageable);

}
