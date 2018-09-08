package com.ebase.core.message.transaction;

import javax.transaction.Synchronization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizationImpl implements Synchronization {
	static Logger logger = LoggerFactory.getLogger(SynchronizationImpl.class);

	private Synchronization delegate;
	private boolean beforeCompleted;
	private boolean afterCompleted;

	public void beforeCompletion() {
		if (this.beforeCompleted == false) {
			try {
				this.delegate.beforeCompletion();
			} catch (RuntimeException rex) {
				logger.error("事务提交时, 执行beforeCompletion出错!", rex);
			}
			this.beforeCompleted = true;
		}
	}

	public void afterCompletion(int status) {
		if (this.afterCompleted == false) {
			try {
				this.delegate.afterCompletion(status);
			} catch (RuntimeException rex) {
				logger.error("事务提交时, 执行afterCompletion出错!", rex);
			}
			this.afterCompleted = true;
		}
	}

	public Synchronization getDelegate() {
		return delegate;
	}

	public void setDelegate(Synchronization delegate) {
		this.delegate = delegate;
	}

}
