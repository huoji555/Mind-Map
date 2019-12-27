package com.hwj.daoImpl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.hwj.dao.IShareDao;
import com.hwj.entity.MindNode;
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

    
    /**
     * @author Ragty
     * @param  学习界面搜索分页功能
     * @serialData 2018.4.19
     */
	@Override
	public List<Share> queryShareMind(Object value, Integer currentPage,Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = "select * from  share_mind as model where model.mind_name like '%" + value+ 
				"%'";
		hql += " or model.userid = (select name from User as model2 where model2.real_name like '%"+value+"%' limit 1)";
		hql += " or model.userid like '%"+value+"%'";
		Query query = ((SQLQuery) getCurrentSession().createSQLQuery(hql).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize)).addEntity(Share.class);
		
		@SuppressWarnings("unchecked")
		List<Share> list = query.list();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取学习页面搜索后结果的总页数
	 * @serialData 2018.4.19
	 */
	@Override
	public Long searchShareMindPage(Object value) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from  share_mind as model where model.mind_name like '%" + value+ 
				"%'";
		hql += " or model.userid = (select name from User as model2 where model2.real_name like '%"+value+"%' limit 1)";
		hql += " or model.userid like '%"+value+"%'";
		
		Query query = getCurrentSession().createSQLQuery(hql);
		BigInteger big = (BigInteger) query.uniqueResult();
		Long total = big.longValue();
		return total;
	}
	
	
}
