package com.hwj.service;

import java.util.List;

import com.hwj.entity.MindNode;

public interface IMindNodeService extends IBaseService<MindNode> {
	
	List<MindNode> selectMindNode(Object value1, Integer currentPage,
			Integer pageSize);

}
