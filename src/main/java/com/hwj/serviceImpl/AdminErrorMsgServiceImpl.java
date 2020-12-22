package com.hwj.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hwj.entity.AdminErrorMsg;
import com.hwj.repository.AdminErrorMsgRepository;
import com.hwj.service.AdminErrorMsgService;

import java.util.Date;

@Service
public class AdminErrorMsgServiceImpl implements AdminErrorMsgService{
	
	@Autowired
	private AdminErrorMsgRepository adminErrorMsgRepository;

	@Override
	public void save(AdminErrorMsg adminErrorMsg) {adminErrorMsgRepository.save(adminErrorMsg);}

	@Override
	public Page<AdminErrorMsg> pageException(Date firstDate, Date lastDate, Pageable pageable) {
		return adminErrorMsgRepository.queryErrorMsg(firstDate, lastDate, pageable);
	}

}
