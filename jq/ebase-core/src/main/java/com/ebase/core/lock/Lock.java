package com.ebase.core.lock;

/**
 * <p>
 * 分布式锁对象
 * </p>
 *
 * @project core
 * @class Lock
 */
public interface Lock {
	
	/**
	 * 锁定，必须主动释放
	 * @return
	 */
	boolean lock();

	/**
	 * 锁定资源
	 * @param time,单位为毫秒
	 * @return
	 */
	boolean lock(long time);
	
	
	/**
	 * 是否已经进入获取锁成功的状态
	 * @return
	 */
	boolean isLocked();
	
	/**
	 * 释放资源
	 */
	void release();
	
	
	
	
}
