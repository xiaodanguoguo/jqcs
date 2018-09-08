package com.ebase.core.bootstrap;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bootstrap implements Runnable {
	private static Bootstrap instance = new Bootstrap();

	private Bootstrap() {
		if (instance != null) {
			throw new IllegalStateException();
		}
	}

	public static Bootstrap getInstance() {
		return instance;
	}

	private Lock lock = new ReentrantLock();
	private Condition condition = this.lock.newCondition();
	private Boolean status;

	public void run() {
		this.destroy();
	}

	public void startup() {
		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(this));

		try {
			this.lock.lock();
			if (this.status == null) {
				this.status = true; // 标记已经启动
				this.condition.awaitUninterruptibly();
			} else if (this.status) {
				// 已经启动, 忽略
			} else {
				// 已经退出, 不予启动
			}
		} finally {
			this.lock.unlock();
		}

		System.out.println("application exit...");
	}

	public void destroy() {
		try {
			this.lock.lock();
			if (this.status == null) {
				this.status = false; // 标记已经退出
			} else if (this.status) {
				this.status = false;
				this.condition.signalAll();
			} else {
				// 已经退出, 忽略
			}
		} finally {
			this.lock.unlock();
		}
	}

}
