package com.ebase.core.lock;

import com.ebase.core.lock.Lock;
import com.ebase.core.lock.LockFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:lock/lock.spring.xml")
public class LockTest {

	@Autowired
	private LockFactory lockFactory;
	
	/**
	 * 预期有错误
	 */
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * 获取lock，并锁定，finally release
	 */
	@Test
	public void testLock() {
		String key = "1";
		Lock lock = lockFactory.getLock(key);
		try {
			boolean locked = lock.lock();
			assertThat("获取锁成功", locked, is(Boolean.valueOf(true)));
		} finally {
			lock.release();
		}
	}
	
	/**
	 * 获取lock，按照时间进行获取锁操作，finally release
	 */
	@Test
	public void testLock1() {
		String key = "2";
		Lock lock = lockFactory.getLock(key);
		try {
			boolean locked = lock.lock(1000);
			assertThat("获取锁成功", locked, is(Boolean.valueOf(true)));
		} finally {
			lock.release();
		}
	}

	/**
	 * 获取不同的多个锁
	 */
	@Test
	public void testLocks() {
		String key = "3";
		String key1 = "4";
		Lock lock = lockFactory.getLock(key);
		Lock lock1 = lockFactory.getLock(key1);
		try {
			boolean locked = lock.lock(1000);
			assertThat("获取锁1成功", locked, is(Boolean.valueOf(true)));
			boolean locked1 = lock1.lock(1000);
			assertThat("获取锁2成功", locked1, is(Boolean.valueOf(true)));
		} finally {
			lock.release();
			lock1.release();
		}
	}
	
	/**
	 * 释放锁成功
	 */
	@Test
	public void testRelaseLock() {
		String key = "6";
		Lock lock = lockFactory.getLock(key);
		try {
			if(lock.lock(1000)){
				//执行业务逻辑
			}
			assertThat("获取锁3成功", lock.isLocked(), is(Boolean.valueOf(true)));
		}finally{
			lock.release();
		}
		assertThat("释放锁3成功", lock.isLocked(), is(Boolean.valueOf(false)));
	}


}
