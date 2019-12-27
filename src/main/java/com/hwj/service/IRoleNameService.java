package com.hwj.service;

import java.util.List;
import java.util.Map;


import com.hwj.entity.RoleName;
import com.hwj.entityUtil.RoleNameUtil;

public interface IRoleNameService extends IBaseService<RoleName> {
	public void saveRoleNameByBatch(List<RoleName> list);

	public void updateRoleNameByBatch(List<Map<String, Object>> list);

	public void deleteRoleNameByBatch(List<Integer> list);

	public List<RoleNameUtil> getRoleNameUtils();

}
