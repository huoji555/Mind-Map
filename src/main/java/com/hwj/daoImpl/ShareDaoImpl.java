package com.hwj.daoImpl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.hwj.dao.IShareDao;
import com.hwj.entity.Share;

@Repository( value = "IShareDao")
public class ShareDaoImpl extends BaseDaoImpl<Share> implements IShareDao {
  
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
