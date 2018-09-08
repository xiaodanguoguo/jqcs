package com.ebase.core.elasticsearch;

import java.util.List;

/**
 * <p>
 * 对数据的处理
 * 数据库id是指用 @JestId
 * 注解的字段
 * </p>
 *
 * @project core
 * @class EsDao
 */
public interface EsRepository<T> {
	
	/**
	 * 新增或者修改
	 * @param t
	 * @return
	 */
	boolean meger(T t);
	
	/**
	 * 批量新增或者修改
	 * @param list
	 * @return
	 */
	boolean megerList(List<T> list);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	boolean delete(List<String> ids);

	
	T get(String id);
	
	/**
	 * count
	 * @param query
	 * @return
	 */
	int count(String query);
	
	/**
	 * 查询
	 * @param query
	 * @return
	 */
	List<T> queryList(String query);
	
	
}
