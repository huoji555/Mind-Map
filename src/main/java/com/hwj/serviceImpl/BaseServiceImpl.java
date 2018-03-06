package com.hwj.serviceImpl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.hwj.dao.IBaseDao;
import com.hwj.service.IBaseService;

public class BaseServiceImpl<T> implements IBaseService<T> {

	/**
	* 注入BaseDao
	*/
	private IBaseDao<T> dao;
	@Resource
	public void setDao(IBaseDao<T> dao) {
	    this.dao = dao;
	}
	
	@Override
	public T get(Serializable id) {
		// TODO Auto-generated method stub
		return this.dao.get(id);
	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
        this.dao.save(entity); 		
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		this.dao.delete(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		this.dao.update(entity);
	}

	@Override
	public boolean findById(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return this.dao.findById(propertyName, value);
	}

	@Override
	public T get(String propertyName1, String propertyName2, Object value1,
			Object value2) {
		// TODO Auto-generated method stub
		return this.dao.get(propertyName1, propertyName2, value1, value2);
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return this.getAll();
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		this.dao.flush();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
        this.dao.clear();		
	}

	@Override
	public T get(String propertyName1, Object value1) {
		// TODO Auto-generated method stub
		return this.dao.get(propertyName1, value1);
	}

	@Override
	public void saveByMerge(T entity) {
		// TODO Auto-generated method stub
        this.dao.saveByMerge(entity);		
	}

	@Override
	public Long totalCount() {
		// TODO Auto-generated method stub
		return this.dao.totalCount();
	}

	@Override
	public List<T> getByPage(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		return this.dao.getByPage(currentPage, pageSize);
	}

	@Override
	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub
		this.dao.saveOrUpdate(entity);
	}

	@Override
	public List<T> select(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return this.dao.select(propertyName, value);
	}

	@Override
	public List<T> select(String propertyName1, String propertyName2,
			Object value1, Object value2) {
		// TODO Auto-generated method stub
		return this.dao.select(propertyName1, propertyName2, value1, value2);
	}

	@Override
	public List<T> getAll(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return this.dao.getAll(propertyName, value);
	}

	@Override
	public List<T> getAll(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		// TODO Auto-generated method stub
		return this.dao.getAll(propertyName1, value1, propertyName2, value2);
	}

	@Override
	public T get(String propertyName1, String propertyName2,
			String propertyName3, Object value1, Object value2, Object value3) {
		// TODO Auto-generated method stub
		return this.dao.get(propertyName1, propertyName2, propertyName3, value1, value2, value3);
	}

}
