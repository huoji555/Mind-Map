package com.hwj.dao;

import java.util.List;

import com.hwj.entity.MindNode;

public interface IMindNodeDao extends IBaseDao<MindNode> {

	List<MindNode> queryMindMap(Object value1, Integer currentPage,
			Integer pageSize);

}
