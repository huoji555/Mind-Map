package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IAssessLogDao;
import com.hwj.entity.AssessLog;

@Repository(value = "IAssessLogDao")
public class AssessLogDaoImpl extends BaseDaoImpl<AssessLog> implements
		IAssessLogDao {

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
		System.out.println(session);
		return session;
	}

	@Override
	public void setAssessLog(AssessLog assessLog) {
		System.out.println(1);
		getCurrentSession().save(assessLog);
		System.out.println(123);
	}
}
