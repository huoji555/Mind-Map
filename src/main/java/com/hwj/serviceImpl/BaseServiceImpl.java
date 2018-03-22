package com.hwj.serviceImpl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

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
	
	//用于操作数据库
    private  EntityManager em;

	
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
	@Transactional
	@Modifying
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

	@Override
	public List<T> getAllByPage(Integer currentPage, Integer pageSize,
			String propertyName, Object value) {
		// TODO Auto-generated method stub
		return this.dao.getAllByPage(currentPage, pageSize, propertyName, value);
	}

	@Override
	public List<T> getAllByPage(Integer currentPage, Integer pageSize,
			String propertyName1, Object value1, String propertyName2,
			Object value2) {
		// TODO Auto-generated method stub
		return this.dao.getAllByPage(currentPage, pageSize, propertyName1, value1, propertyName2, value2);
	}

	@Override
	public Long countByOne(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return this.dao.countByOne(propertyName, value);
	}

	@Override
	public Long countByTwo(String propertyName1, Object value1,
			String propertyName2, Object value2) {
		// TODO Auto-generated method stub
		return this.dao.countByTwo(propertyName1, value1, propertyName2, value2);
	}

}
