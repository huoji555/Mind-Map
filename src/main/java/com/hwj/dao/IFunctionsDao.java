package com.hwj.dao;

import java.util.List;
import com.hwj.entity.Functions;

public interface IFunctionsDao extends IBaseDao<Functions> {
	public List<Functions> getFunctions(String grandparentId, String limit);

}
