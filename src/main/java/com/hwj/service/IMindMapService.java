package com.hwj.service;

import java.util.List;

import com.hwj.entity.MindMap;

public interface IMindMapService extends IBaseService<MindMap> {

	List<MindMap> queryMindNap(Object value1, Integer currentPage,
			Integer pageSize);
	
	Long searchMapPage(Object value1);
	
	Long getAllMapPage();
	
	List<MindMap> getAllMap();
	
}
