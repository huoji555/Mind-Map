package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IMindNodeDao;
import com.hwj.entity.MindNode;

@Repository("IMindNodeDao")
public class MindNodeDaoImpl extends BaseDaoImpl<MindNode> implements
		IMindNodeDao {
	@Resource
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();
		} catch (Exception e) {
			System.out.println(e);
		}
		return session;
	}
}
