package com.hwj.dao;

import java.util.List;

import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;

public interface IMindMapDao extends IBaseDao<MindMap> {
	
	List<MindMap> queryMindNap(Object value1, Integer currentPage,
			Integer pageSize);
	
	Long searchMapPage(Object value1);
	
	Long getAllMapPage();
	
}
