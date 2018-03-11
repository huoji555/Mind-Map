package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.hwj.dao.IUploadFileDao;
import com.hwj.entity.UploadFile;

@Repository(value = "IUploadFileDao")
public class UploadFileDaoImpl extends BaseDaoImpl<UploadFile> implements IUploadFileDao{
	
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
