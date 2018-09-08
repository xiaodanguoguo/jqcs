package com.ebase.core.lock.curator;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.lock.Lock;
import com.ebase.core.lock.LockFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * <p>
 * 锁工厂的实现,
 * 必须通过spring 配合文件初始化
 * </p>
 *
 * @project core
 * @class CuratorLockFactory
 */
public class CuratorLockFactory implements LockFactory, InitializingBean {

	private final static Logger logger =LoggerFactory.getLogger(CuratorLockFactory.class);
	
	private final static String BASE_LOCK_PATH = "/lock";

	private CuratorFramework curatorClient;

	@Override
	public Lock getLock(String key) {
		try {
			Assert.notNull(key, "");
			String lockPath = "";
			if (key.startsWith("/")) {
				lockPath = BASE_LOCK_PATH + key;
			} else {
				lockPath = BASE_LOCK_PATH + "/" + key;
			}
			InterProcessMutex interProcessMutex = new InterProcessMutex(curatorClient, lockPath);
			CuratorLock lock = new CuratorLock(interProcessMutex);
			return lock;
		} catch (Exception e) {
			logger.error("获取锁对象失败",e);
			throw new BusinessException("获取锁对象失败");
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(curatorClient, "client 不能为空");
	}

	public void setCuratorClient(CuratorFramework curatorClient) {
		this.curatorClient = curatorClient;
	}

}
