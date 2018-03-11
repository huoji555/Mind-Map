package com.hwj.daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IRoleNameDao;
import com.hwj.entity.RoleName;
import com.hwj.entityUtil.RoleNameUtil;

@Repository(value = "IRoleNameDao")
public class RoleNameDaoImpl extends BaseDaoImpl<RoleName> implements IRoleNameDao {

	/**
	 * @author b505
	 * @param  批量保存用户权限 
	 * @param list
	 * @serialData 2017.11.9
	 */
	@Override
	public void saveRoleNameByBatch(List<RoleName> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for (int i = 0; i < list.size(); i++) {
			RoleName roleName = list.get(i);
			session.save(roleName);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	
    //批量修改用户权限
	@Override
	public void updateRoleNameByBatch(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for (int i = 0; i < list.size(); i++) {
			RoleName roleName = this.get((Integer) list.get(i).get("id"));
			roleName.setRoleName((String) list.get(i).get("roleName"));
			roleName.setRemark((String) list.get(i).get("remark"));
			session.update(roleName);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	
	//批量删除用户权限
	@Override
	public void deleteRoleNameByBatch(List<Integer> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for (int i = 0; i < list.size(); i++) {
			RoleName roleName = new RoleName();
			roleName.setId(list.get(i));
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	
	//批量获得用户权限
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleNameUtil> getRoleNameUtils() {
		// TODO Auto-generated method stub
		String hql = "select new com.b505.bean.util.RoleNameUtil(r.id,r.roleName,r.remark)from RoleName r";
		Query query = getCurrentSession().createQuery(hql);
		List<RoleNameUtil> list = new ArrayList<RoleNameUtil>();
		list = query.list();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

}
