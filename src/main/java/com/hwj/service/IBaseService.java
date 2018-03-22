package com.hwj.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T> {

	public T get(Serializable id);

	public void save(T entity);

	public void delete(T entity);

	public void update(T entity);

	public boolean findById(String propertyName, Object value);

	public T get(String propertyName1, String propertyName2, Object value1,
			Object value2);

	public List<T> getAll();

	public void flush();

	public void clear();

	public T get(String propertyName1, Object value1);

	public void saveByMerge(T entity);

	public Long totalCount();

	public List<T> getByPage(Integer currentPage, Integer pageSize);

	public void saveOrUpdate(T entity);

	public List<T> select(String propertyName, Object value);

	public List<T> select(String propertyName1, String propertyName2,
			Object value1, Object value2);

	public List<T> getAll(String propertyName, Object value);

	public List<T> getAll(String propertyName1, Object value1,
			String propertyName2, Object value2);

	public T get(String propertyName1, String propertyName2,
			String propertyName3, Object value1, Object value2, Object value3);
	
	public List<T> getAllByPage(Integer currentPage, Integer pageSize,
			String propertyName, Object value);
	
	public List<T> getAllByPage(Integer currentPage, Integer pageSize,
			String propertyName1, Object value1,String propertyName2, Object value2);
	
    public Long countByOne(String propertyName, Object value);
	
	public Long countByTwo(String propertyName1, Object value1,
			String propertyName2, Object value2);
	
}
