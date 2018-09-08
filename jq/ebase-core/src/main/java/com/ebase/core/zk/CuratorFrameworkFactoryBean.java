package com.ebase.core.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>,InitializingBean {
	
	private CuratorFramework curatorFramework;
	
	private String zookeeperAddr;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(curatorFramework==null){
			Assert.notNull(zookeeperAddr, "zookeeperAddr地址不能为空");
			curatorFramework = CuratorFrameworkFactory.newClient(zookeeperAddr, new ExponentialBackoffRetry(3000, 5));
			curatorFramework.start();
		}
	}

	@Override
	public CuratorFramework getObject() throws Exception {
		return curatorFramework;
	}

	@Override
	public Class<?> getObjectType() {
		return CuratorFramework.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setZookeeperAddr(String zookeeperAddr) {
		this.zookeeperAddr = zookeeperAddr;
	}
	
}
