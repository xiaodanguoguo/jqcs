package com.ebase.core.lock.curator;

import java.util.concurrent.TimeUnit;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.lock.Lock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * zookeeper 锁代理实现
 * </p>
 *
 * @project core
 * @class CuratorLock
 */
public class CuratorLock implements Lock {
	
	private final static Logger logger = LoggerFactory.getLogger(CuratorLock.class);
	
	private final InterProcessMutex lock;
	
	public CuratorLock(InterProcessMutex lock) {
		super();
		this.lock = lock;
	}
	
	@Override
	public boolean lock() {
		try {
			lock.acquire();
			return true;
		} catch (Exception e) {
			logger.error("",e);
			throw new BusinessException("获取锁失败");
		}
	}

	
	@Override
	public boolean lock(long time) {
		try {
			return lock.acquire(time, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("",e);
			throw new BusinessException("获取锁失败");
		}
	}
	
	@Override
	public boolean isLocked() {
		return lock.isAcquiredInThisProcess();
	}


	/**
	 * 释放锁失败，不报错
	 */
	@Override
	public void release() {
		try {
			if(lock.isAcquiredInThisProcess()){
				lock.release();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}


	
}
