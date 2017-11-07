package com.hwj.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hwj.dao.IBaseDao;

public abstract class BaseServiceImpl<T> implements IBaseDao<T> {
	@Autowired
	protected IBaseDao<T> baseDao;

	public abstract void setIBaseDao(IBaseDao<T> baseDao);

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		System.out.println("到这里了！");
		baseDao.save(entity);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		System.out.println("这是service层：");
		baseDao.delete(entity);

	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		System.out.println(entity);
		baseDao.update(entity);
	}

	@Override
	public boolean findById(String propertyName, Object value) {
		return baseDao.findById(propertyName, value);
	}

	@Override
	public T get(String propertyName1, String propertyName2, Object value1,
			Object value2) {
		return baseDao.get(propertyName1, propertyName2, value1, value2);
	}

	@Override
	public T get(String propertyName1, String propertyName2,
			String propertyName3, Object value1, Object value2, Object value3) {
		return baseDao.get(propertyName1, propertyName2, propertyName3, value1,
				value2, value3);
	}

	@Override
	public List<T> getAll() {

		System.out.println(2);

		return baseDao.getAll();
	}

	@Override
	public void flush() {
		baseDao.flush();
	}

	@Override
	public void clear() {
		baseDao.clear();
	}

	@Override
	public T get(String propertyName, Object value1) {
		if (baseDao.get(propertyName, value1) != null) {
			return baseDao.get(propertyName, value1);
		} else {
			return null;
		}
	}

	@Override
	public void saveByMerge(T entity) {
		baseDao.saveByMerge(entity);
	}

	@Override
	public Long totalCount() {
		return baseDao.totalCount();
	}

	@Override
	public List<T> getByPage(Integer currentPage, Integer pageSize) {
		return baseDao.getByPage(currentPage, pageSize);
	}

	@Override
	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.get(id);
	}

	public List<T> select(String propertyName, Object value) {
		return baseDao.select(propertyName, value);
	}

	public List<T> select(String propertyName1, String propertyName2,
			Object value1, Object value2) {
		return baseDao.select(propertyName1, propertyName2, value1, value2);
	}

	public List<T> getAll(String propertyName, Object value) {
		return baseDao.getAll(propertyName, value);
	}

	public List<T> getAll(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		return baseDao.getAll(propertyName1, value1, propertyName2, value2);
	}
}
