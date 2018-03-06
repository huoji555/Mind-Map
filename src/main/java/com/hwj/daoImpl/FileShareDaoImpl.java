package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IFileShareDao;
import com.hwj.entity.FileShare;

@Repository(value = "IFileShareDao")
public class FileShareDaoImpl extends BaseDaoImpl<FileShare> implements
		IFileShareDao {

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

}
