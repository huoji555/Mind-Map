package com.hwj.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.IBaseDao;

@Transactional
public class BaseDaoImpl<T> implements IBaseDao<T> {

	private Class<T> entityClass;
	@Resource
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	// 告诉编译器忽略指定的警告，不用在编译完成后出现警告信息
	// 公共类
	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass()
				.getGenericSuperclass();
		entityClass = (Class<T>) type.getActualTypeArguments()[0];

	}
	
	//Session
	public Session getCurrentSession() {
		Session session = null;
		System.out.println("这是baseDao的session");
		try {
			session = sessionFactory.getCurrentSession();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return session;
	}
	
	
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		System.out.println(entity);
		getCurrentSession().save(entity);
	}

	@Override
	@Transactional
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		System.out.println(entityClass);
		return (T) getCurrentSession().get(entityClass, id);
	}

	@Override
	public void delete(T entity) {
		getCurrentSession().update(entity);
		getCurrentSession().delete(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		getCurrentSession().update(entity);
	}

	@Override
	public boolean findById(String propertyName, Object value) {
		T object = get(propertyName, value);
		return (object != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName, Object value) {
		System.out.println(entityClass.getName());
		String hql = "from " + entityClass.getName() + "  model where model."
				+ propertyName + " = :name";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("name", value);
		System.out.println(123);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		System.out.println(list);
		if (list.size() > 0) {
			System.out.println(list);
			return list.get(0);

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName1, String propertyName2, Object value1,
			Object value2) {
		String hql = " from " + entityClass.getName()
				+ " as model where model." + propertyName1 + " = :name"
				+ " and " + " model." + propertyName2 + " = :pd";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("name", value1);
		query.setParameter("pd", value2);
		if (query.list().size() > 0) {
			return (T) query.list().get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<T> getAll() {
		System.out.println("Dao层");
		String hql = "from " + entityClass.getName();
		Query query = getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		System.out.println(list);
		if (list.size() > 0) {

			return list;
		} else {
			return null;
		}

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		getCurrentSession().flush();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		getCurrentSession().clear();

	}

	@Override
	public void saveByMerge(T entity) {
		// TODO Auto-generated method stub
		getCurrentSession().merge(entity);
	}

	@Override
	public Long totalCount() {
		// TODO Auto-generated method stub
		String hql = "select Count (model) from " + entityClass.getName()
				+ " as model";
		Long total = (Long) getCurrentSession().createQuery(hql).uniqueResult();
		return total;
	}

	@Override
	public List<T> getByPage(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		String hql = "select model from " + entityClass.getName() + " as model";
		@SuppressWarnings("unchecked")
		List<T> list = getCurrentSession().createQuery(hql)
				.setFirstResult((currentPage - 1) * pageSize)
				.setMaxResults(pageSize).list();
		return list;
	}

	@Override
	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate(entity);

	}

	@Override
	public List<T> select(String propertyName, Object value) {
		System.out.println(entityClass.getName());
		String hql = "from " + entityClass.getName() + "  model where model."
				+ propertyName + " like '%" + value + "%'";
		Query query = getCurrentSession().createQuery(hql);
		// query.setParameter("name", "%"+value+"%");
		System.out.println(123);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		System.out.println(list);
		if (list.size() > 0) {
			System.out.println(list);
			return list;

		}
		return null;
	}

	@Override
	public List<T> select(String propertyName1, String propertyName2,
			Object value1, Object value2) {
		System.out.println(propertyName1 + "" + propertyName2 + ""
				+ value1.toString() + "   " + value1 + "" + value2);
		String hql = " from " + entityClass.getName()
				+ " as model where model." + propertyName1 + "like '%" + value1
				+ "%'";
		hql += " and " + " model." + propertyName2 + " like '%" + value2
				+ "%'   ";
		Query query = getCurrentSession().createQuery(hql);
		// query.setParameter("name1","%"+ value1+"%");
		// query.setParameter("name2","%"+ value2+"%");
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<T> getAll(String propertyName, Object value) {

		String hql = "from " + entityClass.getName() + " model where model."
				+ propertyName + " like '%" + value + "%'";

		Query query = getCurrentSession().createQuery(hql);
		// query.setParameter("value", value);
		@SuppressWarnings("unchecked")
		List<T> list = query.list();
		if (list.size() > 0) {
			return list;
		} else {

			return null;
		}
	}

	@Override
	public List<T> getAll(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		String hql = "from " + entityClass.getName() + " model where model."
				+ propertyName1 + " = :name1 ";
		hql += "and model." + propertyName2 + " = :name2";

		// Query query=
		@SuppressWarnings("unchecked")
		List<T> list = getCurrentSession().createQuery(hql)
				.setParameter("name1", value1).setParameter("name2", value2)
				.list();
		// query.setParameter("value", value);

		// List<T> list=query.list();
		if (list.size() > 0) {
			return list;
		} else {

			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName1, String propertyName2,
			String propertyName3, Object value1, Object value2, Object value3) {
		// TODO Auto-generated method stub

		String hql = " from " + entityClass.getName()
				+ " as model where model." + propertyName1 + " = :name"
				+ " and " + " model." + propertyName2 + " = :pd" + " and "
				+ " model." + propertyName3 + " = :name1";

		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("name", value1);
		query.setParameter("pd", value2);
		query.setParameter("name1", value3);
		if (query.list().size() > 0) {
			return (T) query.list().get(0);
		} else {
			return null;
		}

	}


}
