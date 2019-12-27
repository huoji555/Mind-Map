package com.hwj.daoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IFunctionsDao;
import com.hwj.entity.Functions;

@Repository(value = "IFunctionsDao")
public class FunctionsDaoImpl extends BaseDaoImpl<Functions> implements
		IFunctionsDao {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Session getCurrentSession() {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();

		} catch (Exception e) {
			System.out.println(e);
		}

		return session;
	}

	@Override
	public List<Functions> getFunctions(String grandparentId, String limit) {
		System.out.println(grandparentId);
		String hql = "from Functions f where f.grandparentId=:grandparentId";
		hql += " and f.limits like :limits";

		Query query = getCurrentSession().createQuery(hql);
		query.setString("grandparentId", grandparentId);
		query.setString("limits", "%" + limit + "%");
		@SuppressWarnings("unchecked")
		List<Functions> list = query.list();
		System.out.println(list);
		if (list.size() > 0) {
			return list;

		} else {
			return null;
		}
	}

}
