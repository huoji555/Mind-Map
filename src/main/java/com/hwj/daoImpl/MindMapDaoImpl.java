package com.hwj.daoImpl;



import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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

	
	/**
	 * @author Ragty
	 * @param  查询知识图谱（教师端）
	 * @serialData 2018.6.14
	 */
	@Override
	public List<MindMap> queryMindNap(Object value1, Integer currentPage,
			Integer pageSize) {
		String hql = "select * from  Mind_map as model where model.nodename like '%" + value1
				     + "%'";
		hql += " or model.userid like '%"+value1+"%'";
		hql += " or model.realname like '%"+value1+"%'";
		
		Query query = ((SQLQuery) getCurrentSession().createSQLQuery(hql).
				      setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize)).addEntity(MindMap.class);
		
		@SuppressWarnings("unchecked")
		List<MindMap> list=query.list();
		
		if(list.size()>0){
			return list;
		}
		
		return null;
	}

	
	
	/**
	 * @author Ragty
	 * @param  查询获取知识图谱总条数
	 * @serialData 2018.6.14
	 */
	@Override
	public Long searchMapPage(Object value1) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from  Mind_map as model where model.nodename like '%" + value1
				     + "%'";
		hql += " or model.userid like '%"+value1+"%'";
		hql += " or model.realname like '%"+value1+"%'";
			
		Query query = getCurrentSession().createSQLQuery(hql);
		BigInteger big = (BigInteger) query.uniqueResult();
		Long total = big.longValue();
		return total;
	}

	
	/**
	 * @author Ragty
	 * @param  获取当前知识图谱总条数
	 * @serialData 2018.6.14
	 */
	@Override
	public Long getAllMapPage() {
		// TODO Auto-generated method stub
		String hql = "select count(*) from  Mind_map";
		
		Query query = getCurrentSession().createSQLQuery(hql);
		BigInteger big = (BigInteger) query.uniqueResult();
		Long total = big.longValue();
		return total;
	}


	/**
	 * @author Ragty
	 * @param  获取所有的图谱
	 * @serialData 2018.10.12
	 */
	@Override
	public List<MindMap> getAllMap() {
		String sql = "select * from mind_map";
		Query query = getCurrentSession().createSQLQuery(sql).addEntity(MindMap.class);
		
		@SuppressWarnings("unchecked")
		List<MindMap> list=query.list();
		
		if(list.size()>0){
			return list;
		}
		return null;
	}
	
	

	
}
