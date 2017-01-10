package com.leafson.fuzhou.db;

import java.io.Serializable;
import java.util.List;

/**
 * 抽取数据库的通用操作
 * @author Administrator
 *
 */
public interface DAO<T> {
	/**
	 * 添加
	 * @param news
	 * @return
	 */
	public long insert(T t);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(Serializable id);
	/**
	 * 修改
	 * @param news
	 * @return
	 */
	public int update(T t);
	public void exeC(String sql);
	/**
	 * 获取�?��信息
	 * @return
	 */
	public List<T> findAll();
	
	public T getInstance();//获取到实际运行时的类
	public boolean tabbleIsExist(String string);
	public void createTable(String string);
	

}
