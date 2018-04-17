package com.hwj.daoImpl;



import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.hwj.dao.IMindMapDao;
import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;

@Repository("IMindMapDao")
public class MindMapDaoImpl extends BaseDaoImpl<MindMap> implements IMindMapDao {
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
