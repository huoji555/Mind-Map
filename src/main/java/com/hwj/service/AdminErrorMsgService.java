package com.hwj.service;

import org.springframework.data.domain.Page;

import com.hwj.entity.AdminErrorMsg;

public interface AdminErrorMsgService {
	
	void save(AdminErrorMsg adminErrorMsg);
	
	Page<AdminErrorMsg> pageException(Integer page,Integer size);

}
