package com.hwj.dao;

import java.util.List;

import com.hwj.entity.Share;

public interface IShareDao extends IBaseDao<Share>{
	
	List<Share> queryShareMind(Object value, Integer currentPage,
			Integer pageSize);
	
	Long searchShareMindPage(Object value);

}
