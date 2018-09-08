package com.ebase.core.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:zk/zk.spring.xml")
public class ZkTest {
	
	@Autowired
	private CuratorFramework curatorClient;
	
	@Test
	public void testCreate() throws Exception{
		String path = "/create";
		String value = "create";
		String str = curatorClient.create().forPath(path, value.getBytes());
		assertThat("", str, is(path));
		byte[] bytes  =  curatorClient.getData().forPath(path);
		assertThat("", value, is(new String(bytes)));
		Stat stat=  curatorClient.checkExists().forPath(path);
		assertThat("", stat, notNullValue());
		System.out.println(stat);
		curatorClient.delete().forPath(path);
	}
	
	
	@Test
	public void testCheckExist() throws Exception{
		String path = "/check";
		String str = curatorClient.create().forPath(path);
		assertThat("", str, is(path));
		Stat stat=  curatorClient.checkExists().forPath(path);
		assertThat("", stat, notNullValue());
		System.out.println(stat);
		curatorClient.delete().forPath(path);
		
		String noExist = "/noExist";
		Stat stat1=  curatorClient.checkExists().forPath(noExist);
		System.out.println(stat);
		assertThat("", stat1, nullValue());
	}
	
	@Test
	public void testCheckDelete() throws Exception{
		String path = "/delete";
		String str = curatorClient.create().forPath(path);
		assertThat("", str, is(path));
		Stat stat=  curatorClient.checkExists().forPath(path);
		assertThat("", stat, notNullValue());
		curatorClient.delete().forPath(path);
		
		Stat stat1=  curatorClient.checkExists().forPath(path);
		System.out.println(stat);
		assertThat("", stat1, nullValue());
	}
	
	
	@Test
	public void testChild() throws Exception{
		String path = "/zk";
		curatorClient.create().forPath(path);
		curatorClient.create().forPath("/zk/child1");
		curatorClient.create().forPath("/zk/child2");
		List<String> list =curatorClient.getChildren().forPath(path);
		assertThat("", list.size(), is(2));
		curatorClient.delete().deletingChildrenIfNeeded().forPath(path);
	}
	
}
