package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IFileStreamDao;
import com.hwj.entity.FileStream;

@Repository(value = "IFileStreamDao")
public class FileStreamDaoImpl extends BaseDaoImpl<FileStream> implements
		IFileStreamDao {

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
