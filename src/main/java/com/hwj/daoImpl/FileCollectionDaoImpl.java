package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IFileCollectionDao;
import com.hwj.entity.FileCollection;

@Repository(value = "IFileCollectionDao")
public class FileCollectionDaoImpl extends BaseDaoImpl<FileCollection>
		implements IFileCollectionDao {
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public Session getCurrentSession() {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return session;
	}

}
