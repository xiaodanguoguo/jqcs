package com.ebase.core.message.transaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class UserTransactionImpl implements UserTransaction {

	private TransactionManager transactionManager;

	public void begin() throws NotSupportedException, SystemException {
		transactionManager.begin();
	}

	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {
		transactionManager.commit();
	}

	public int getStatus() throws SystemException {
		return transactionManager.getStatus();
	}

	public Transaction getTransaction() throws SystemException {
		return transactionManager.getTransaction();
	}

	public void resume(Transaction arg0) throws InvalidTransactionException, IllegalStateException, SystemException {
		transactionManager.resume(arg0);
	}

	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		transactionManager.rollback();
	}

	public void setRollbackOnly() throws IllegalStateException, SystemException {
		transactionManager.setRollbackOnly();
	}

	public void setTransactionTimeout(int arg0) throws SystemException {
		transactionManager.setTransactionTimeout(arg0);
	}

	public Transaction suspend() throws SystemException {
		return transactionManager.suspend();
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
