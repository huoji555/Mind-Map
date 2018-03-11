package com.hwj.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.IRoleNameDao;
import com.hwj.entity.RoleName;
import com.hwj.entityUtil.RoleNameUtil;
import com.hwj.service.IRoleNameService;

@Service
public class RoleNameServiceImpl extends BaseServiceImpl<RoleName> implements IRoleNameService{
	
	@Autowired
	@Qualifier(value="IRoleNameDao")
	private IRoleNameDao iRoleNameDao;
	
	//注入Dao层
	@Resource(name="IRoleNameDao")
	@Override
	public void setDao(IBaseDao<RoleName> dao) {
		// TODO Auto-generated method stub
		super.setDao(dao);
	}
	

	@Override
	public void saveRoleNameByBatch(List<RoleName> list) {
		// TODO Auto-generated method stub
		iRoleNameDao.saveRoleNameByBatch(list);
	}

	@Override
	public void updateRoleNameByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		iRoleNameDao.updateRoleNameByBatch(list);
	}

	@Override
	public void deleteRoleNameByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		iRoleNameDao.deleteRoleNameByBatch(list);
	}

	@Override
	public List<RoleNameUtil> getRoleNameUtils() {
		// TODO Auto-generated method stub
		return iRoleNameDao.getRoleNameUtils();
	}

}
