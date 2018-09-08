package com.ebase.core.lock;

/**
 * <p>
 * 分布式锁工厂
 * </p>
 * <p>
 * 使用方法<br>
 * <pre>
 * LockFactory f = ....; //通过spring 初始化
 * Lock lock = f.getLock("key");
 * try{
 *    if(lock.lock()){
 *    	 doSomething();
 *    }else{
 *    	//获取锁失败
 *    }
 * }finally{
 *    lock.relase();
 * }
 * </pre>
 *
 * @project core
 * @class LockFactory
 */
public interface LockFactory {
	
	Lock getLock(String key); 
	
}
