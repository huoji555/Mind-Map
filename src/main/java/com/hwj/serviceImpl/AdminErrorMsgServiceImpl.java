package com.hwj.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hwj.entity.AdminErrorMsg;
import com.hwj.repository.AdminErrorMsgRepository;
import com.hwj.service.AdminErrorMsgService;

@Service
public class AdminErrorMsgServiceImpl implements AdminErrorMsgService{
	
	@Autowired
	private AdminErrorMsgRepository adminErrorMsgRepository;

	@Override
	public void save(AdminErrorMsg adminErrorMsg) {adminErrorMsgRepository.save(adminErrorMsg);}

	@Override
	public Page<AdminErrorMsg> pageException(Integer page, Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC,"create_time");
		PageRequest pageRequest = new PageRequest(page, size,sort);
		return adminErrorMsgRepository.findAll(pageRequest);
	}

	
	
}
