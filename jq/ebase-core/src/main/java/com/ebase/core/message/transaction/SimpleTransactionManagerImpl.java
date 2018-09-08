package com.ebase.core.message.transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

public class SimpleTransactionManagerImpl implements TransactionManager {
	private Map<Thread, SimpleTransactionImpl> transactionMap = new ConcurrentHashMap<Thread, SimpleTransactionImpl>();

	public void begin() throws NotSupportedException, SystemException {
		SimpleTransactionImpl transaction = new SimpleTransactionImpl();
		this.transactionMap.put(Thread.currentThread(), transaction);
	}

	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {
		SimpleTransactionImpl transaction = this.transactionMap.remove(Thread.currentThread());
		transaction.commit();
	}

	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		SimpleTransactionImpl transaction = this.transactionMap.remove(Thread.currentThread());
		transaction.rollback();
	}

	public Transaction suspend() throws SystemException {
		SimpleTransactionImpl transaction = this.transactionMap.remove(Thread.currentThread());
		transaction.suspend();
		return transaction;
	}

	public void resume(Transaction tx) throws InvalidTransactionException, IllegalStateException, SystemException {
		if (SimpleTransactionImpl.class.isInstance(tx) == false) {
			throw new IllegalStateException();
		}
		SimpleTransactionImpl transaction = (SimpleTransactionImpl) tx;
		transaction.resume();
		this.transactionMap.put(Thread.currentThread(), transaction);
	}

	public int getStatus() throws SystemException {
		Transaction transaction = this.getTransaction();
		return transaction == null ? Status.STATUS_NO_TRANSACTION : transaction.getStatus();
	}

	public Transaction getTransaction() throws SystemException {
		return this.transactionMap.get(Thread.currentThread());
	}

	public void setRollbackOnly() throws IllegalStateException, SystemException {
		Transaction transaction = this.getTransaction();
		if (transaction == null) {
			throw new IllegalStateException();
		}
		transaction.setRollbackOnly();
	}

	public void setTransactionTimeout(int arg0) throws SystemException {
	}
}
