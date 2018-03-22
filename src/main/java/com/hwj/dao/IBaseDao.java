package com.hwj.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
	
	public void save(T entity);

	public void saveOrUpdate(T entity);

	// id是有唯一标识的(也是唯一一个有标识的)
	public T get(Serializable id);

	public void delete(T entity);

	public void update(T entity);

	public T get(String propertyName, Object value);

	public boolean findById(String propertyName, Object value);

	public T get(String propertyName1, String propertyName2, Object value1,
			Object value2);

	public T get(String propertyName1, String propertyName2,
			String propertyName3, Object value1, Object value2, Object value3);

	public List<T> getAll();

	public void flush();

	public void clear();

	public void saveByMerge(T entity);

	public Long totalCount();

	// public List<T> getByAll(String propertyName1);
	// public boolean findByAll(String propertyName1);
	public List<T> getByPage(Integer currentPage, Integer pageSize);

	public List<T> select(String propertyName, Object value);

	public List<T> select(String propertyName1, String propertyName2,
			Object value1, Object value2);

	public List<T> getAll(String propertyName, Object value);

	public List<T> getAll(String propertyName1, Object value1,
			String propertyName2, Object value);

	Long countByTwo(String propertyName1, Object value1, String propertyName2,
			Object value2);

	Long countByOne(String propertyName, Object value);

	List<T> getAllByPage(Integer currentPage, Integer pageSize,
			String propertyName1, Object value1, String propertyName2,
			Object value2);

	List<T> getAllByPage(Integer currentPage, Integer pageSize,
			String propertyName, Object value);
	
	

}
