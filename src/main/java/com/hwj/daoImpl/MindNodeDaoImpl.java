package com.hwj.daoImpl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.4.16
	 * @param  模糊匹配一条两个表的字段，实现根据真实名字和知识图谱名字显示想要搜索的结果
	 */
	@Override
	public List<MindNode> queryMindMap(Object value1, Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = "select * from  MindNode as model where (model.nodename like '%" + value1
				+ "%'";
		hql += " or model.userid = (select name from User as model2 where model2.real_name like '%"+value1+"%' limit 1)";
		hql += " or model.userid like '%"+value1+"%'";
		hql += " )and model.parentid = '00100'";
		Query query = ((SQLQuery) getCurrentSession().createSQLQuery(hql).setFirstResult((currentPage-1)*pageSize).setMaxResults(pageSize)).addEntity(MindNode.class);
				/*.addEntity(MindNode.class);*/
		@SuppressWarnings("unchecked")
		List<MindNode> list = query.list();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
	
	
	/**
	 * @author Ragty
	 * @param  根据查询的信息获取到的总页数
	 * @serialData 2018.4.17
	 */
	@Override
	public Long searchMindPage(Object value1){
		// TODO Auto-generated method stub
		String hql = "select count(*) from  MindNode as model where (model.nodename like '%" + value1
				+ "%'";
		hql += " or model.userid = (select name from User as model2 where model2.real_name like '%"+value1+"%' limit 1)";
		hql += " or model.userid like '%"+value1+"%'";
		hql += " )and model.parentid = '00100'";
		
		Query query = getCurrentSession().createSQLQuery(hql);
		BigInteger big = (BigInteger) query.uniqueResult();
		Long total = big.longValue();
		return total;
	}
	
   
	
}
