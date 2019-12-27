package com.hwj.service;

import java.util.List;

import com.hwj.entity.Share;

public interface IShareService extends IBaseService<Share>{

	List<Share> queryShareMind(Object value, Integer currentPage,
			Integer pageSize);
	
	Long searchShareMindPage(Object value);
	
}
