package com.hwj.service;

import java.util.List;
import com.hwj.entity.Functions;

public interface IFunctionsService extends IBaseService<Functions> {
	public List<Functions> getFunctions(String grandparentId, String limit);

}
