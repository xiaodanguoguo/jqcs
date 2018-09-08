package com.ebase.core.message.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebase.core.message.connector.XAResourceImpl;

public class SimpleTransactionImpl implements Transaction {
	static Logger logger = LoggerFactory.getLogger(SimpleTransactionImpl.class);

	static final Pattern pattern = Pattern.compile("^(\\w+\\:\\w+\\:\\w*\\/\\/[^\\/]+\\/[^\\/\\?]+).*");
	static final AtomicLong atomic = new AtomicLong();

	private int status = Status.STATUS_ACTIVE;
	private TransactionXid xid;
	private ResourceArchive jdbcResource;
	private ResourceArchive mockResource;
	private final List<SynchronizationImpl> synchronizationList = new ArrayList<SynchronizationImpl>();

	public SimpleTransactionImpl() {
		UUID uuid = UUID.randomUUID();
		byte[] byteArray = uuid.toString().getBytes();
		byte[] globalTransactionId = new byte[byteArray.length];
		System.arraycopy(byteArray, 0, globalTransactionId, 0, byteArray.length);

		TransactionXid current = new TransactionXid();
		current.setGlobalTransactionId(globalTransactionId);
		this.xid = current;
	}

	protected void beforeCompletion() {
		for (int i = 0; i < this.synchronizationList.size(); i++) {
			Synchronization synchronization = this.synchronizationList.get(i);
			synchronization.beforeCompletion();
		}
	}

	protected void afterCompletion() {
		for (int i = 0; i < this.synchronizationList.size(); i++) {
			Synchronization synchronization = this.synchronizationList.get(i);
			synchronization.afterCompletion(this.status);
		}
	}

	public synchronized void suspend() throws SystemException {

		SystemException error = null;
		if (this.jdbcResource != null) {
			try {
				this.suspendResource(this.jdbcResource.getResource());
			} catch (SystemException ex) {
				error = ex;
			}
		}

		if (this.mockResource != null) {
			this.suspendResource(this.mockResource.getResource());
		}

		if (error != null) {
			throw error;
		}

	}

	public synchronized void resume() throws SystemException {

		SystemException error = null;

		if (this.jdbcResource != null) {
			try {
				this.resumeResource(this.jdbcResource.getResource());
			} catch (SystemException ex) {
				error = ex;
			}
		}

		if (this.mockResource != null) {
			this.resumeResource(this.mockResource.getResource());
		}

		if (error != null) {
			throw error;
		}

	}

	public synchronized void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {

		if (this.status == Status.STATUS_ACTIVE) {
			// ignore
		} else if (this.status == Status.STATUS_MARKED_ROLLBACK) {
			this.rollback();
			throw new HeuristicRollbackException("事务被标注回滚, 已回滚当前事务!");
		} else {
			throw new IllegalStateException("非法事务状态!");
		}

		try {
			this.beforeCompletion();
			this.delistForgottenResources();
			this.invokeCommit();
		} finally {
			this.afterCompletion();
		}

	}

	private void invokeCommit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {

		this.status = Status.STATUS_COMMITTING;

		if (this.jdbcResource != null) {
			try {
				XAResource resource = this.jdbcResource.getResource();
				TransactionXid branchXid = this.jdbcResource.getXid();
				resource.commit(branchXid, true);
			} catch (XAException xaex) {
				switch (xaex.errorCode) {
				case XAException.XA_HEURMIX:
					throw new HeuristicMixedException();
				case XAException.XA_HEURCOM:
					break;
				case XAException.XA_HEURRB:
					throw new HeuristicRollbackException();
				default:
					logger.error("Jdbc事务提交失败!", xaex);
					SystemException ex = new SystemException();
					ex.initCause(xaex);
					throw ex;
				}
			}
		}

		this.status = Status.STATUS_COMMITTED;

		if (this.mockResource != null) {

			try {
				XAResource resource = this.mockResource.getResource();
				TransactionXid branchXid = this.mockResource.getXid();
				resource.commit(branchXid, true); // 发送消息
			} catch (XAException xaex) {
				switch (xaex.errorCode) {
				case XAException.XA_HEURMIX:
					logger.error("消息发送失败!", xaex);
					throw new HeuristicMixedException();
				case XAException.XA_HEURCOM:
					break;
				case XAException.XA_HEURRB:
					logger.error("消息发送失败!", xaex);
					throw new HeuristicRollbackException();
				default:
					logger.error("消息发送失败!", xaex);
					SystemException ex = new SystemException();
					ex.initCause(xaex);
					throw ex;
				}
			}
		}

		// this.status = Status.STATUS_COMMITTED;

	}

	public synchronized void rollback() throws IllegalStateException, SystemException {

		if (this.status != Status.STATUS_ACTIVE && this.status != Status.STATUS_MARKED_ROLLBACK) {
			throw new IllegalStateException("非法事务状态!");
		}

		try {
			this.beforeCompletion();
			this.delistForgottenResources();
			this.invokeRollback();
		} finally {
			this.afterCompletion();
		}

	}

	private void invokeRollback() throws IllegalStateException, SystemException {

		this.status = Status.STATUS_ROLLING_BACK;

		if (this.jdbcResource != null) {
			try {
				XAResource resource = this.jdbcResource.getResource();
				TransactionXid branchXid = this.jdbcResource.getXid();
				resource.rollback(branchXid);
			} catch (XAException xaex) {
				logger.error("Jdbc事务回滚失败!", xaex);
				throw new SystemException(xaex.getMessage());
			}
		}

		if (this.mockResource != null) {
			try {
				XAResource resource = this.mockResource.getResource();
				TransactionXid branchXid = this.mockResource.getXid();
				resource.rollback(branchXid);
			} catch (XAException xaex) {
				logger.error("消息丢弃失败!", xaex);
				throw new SystemException(xaex.getMessage());
			}
		}

		this.status = Status.STATUS_ROLLEDBACK;

	}

	protected boolean resourceMatched(XAResource target, XAResource resource) {
		try {
			return target.isSameRM(resource);
		} catch (Exception ex) {
			return false;
		}
	}

	public void suspendResource(XAResource xares) throws SystemException {

		boolean jdbcExists = this.jdbcResource != null;
		boolean mockExists = this.mockResource != null;
		boolean jdbcMatched = this.resourceMatched(jdbcExists ? this.jdbcResource.getResource() : null, xares);
		boolean mockMatched = this.resourceMatched(mockExists ? this.mockResource.getResource() : null, xares);

		ResourceArchive archive = jdbcMatched ? this.jdbcResource : (mockMatched ? this.mockResource : null);

		if (archive == null) {
			this.setRollbackOnly();
		} else {
			try {
				XAResource resource = archive.getResource();
				TransactionXid branchXid = archive.getXid();
				resource.end(branchXid, XAResource.TMSUSPEND);
			} catch (XAException ex) {
				this.setRollbackOnly();
				throw new SystemException(ex.getMessage());
			}
		}

	}

	public void resumeResource(XAResource xares) throws SystemException {

		boolean jdbcExists = this.jdbcResource != null;
		boolean mockExists = this.mockResource != null;
		boolean jdbcMatched = this.resourceMatched(jdbcExists ? this.jdbcResource.getResource() : null, xares);
		boolean mockMatched = this.resourceMatched(mockExists ? this.mockResource.getResource() : null, xares);

		ResourceArchive archive = jdbcMatched ? this.jdbcResource : (mockMatched ? this.mockResource : null);

		if (archive == null) {
			this.setRollbackOnly();
		} else {
			try {
				XAResource resource = archive.getResource();
				TransactionXid branchXid = archive.getXid();
				resource.start(branchXid, XAResource.TMRESUME);
			} catch (XAException ex) {
				this.setRollbackOnly();
				throw new SystemException(ex.getMessage());
			}
		}

	}

	public synchronized boolean enlistResource(XAResource xares)
			throws RollbackException, IllegalStateException, SystemException {

		boolean jdbcExists = this.jdbcResource != null;
		boolean mockExists = this.mockResource != null;
		boolean jdbcMatched = this.resourceMatched(jdbcExists ? this.jdbcResource.getResource() : null, xares);
		boolean mockMatched = this.resourceMatched(mockExists ? this.mockResource.getResource() : null, xares);

		ResourceArchive archive = jdbcMatched ? this.jdbcResource : (mockMatched ? this.mockResource : null);

		if (archive == null) {
			TransactionXid branch = new TransactionXid();
			branch.setGlobalTransactionId(this.xid.getGlobalTransactionId());
			byte[] byteArray = String.valueOf(atomic.incrementAndGet()).getBytes();
			byte[] branchQualifier = new byte[byteArray.length];
			System.arraycopy(byteArray, 0, branchQualifier, 0, byteArray.length);
			branch.setBranchQualifier(branchQualifier);

			archive = new ResourceArchive();
			archive.setResource(xares);
			archive.setXid(branch);

			if (XAResourceImpl.class.isInstance(xares)) {
				if (this.mockResource != null) {
					this.setRollbackOnly();
					throw new IllegalStateException();
				} else {
					this.mockResource = archive;
				}
			} else {
				if (this.jdbcResource != null) {
					this.setRollbackOnly();
					throw new IllegalStateException();
				} else {
					this.jdbcResource = archive;
				}
			}
		}

		try {
			XAResource resource = archive.getResource();
			TransactionXid branchXid = archive.getXid();
			resource.start(branchXid, jdbcMatched || mockMatched ? XAResource.TMJOIN : XAResource.TMNOFLAGS);
		} catch (XAException ex) {
			this.setRollbackOnly();
			throw new SystemException(ex.getMessage());
		}

		return true;
	}

	public synchronized boolean delistResource(XAResource xares, int flags) throws IllegalStateException, SystemException {

		boolean jdbcExists = this.jdbcResource != null;
		boolean mockExists = this.mockResource != null;
		boolean jdbcMatched = this.resourceMatched(jdbcExists ? this.jdbcResource.getResource() : null, xares);
		boolean mockMatched = this.resourceMatched(mockExists ? this.mockResource.getResource() : null, xares);

		ResourceArchive archive = jdbcMatched ? this.jdbcResource : (mockMatched ? this.mockResource : null);

		if (archive == null) {
			this.setRollbackOnly();
			return false;
		}

		try {
			if (XAResource.TMFAIL == flags) {
				archive.setDelisted(true);
				this.setRollbackOnly();
			} else if (XAResource.TMSUCCESS == flags) {
				archive.setDelisted(true);
			}

			XAResource resource = archive.getResource();
			TransactionXid branchXid = archive.getXid();
			resource.end(branchXid, flags);
		} catch (XAException ex) {
			logger.error("Delist transaction failed!", ex);
			this.setRollbackOnly();
			throw new SystemException(ex.getMessage());
		}

		return true;
	}

	public void delistForgottenResources() {
		if (this.jdbcResource != null && this.jdbcResource.isDelisted() == false) {
			try {
				this.delistResource(this.jdbcResource.getResource(), XAResource.TMSUCCESS);
			} catch (Exception ex) {
				logger.error("delistForgottenResources操作出错!", ex);
				this.setRollbackOnlyQuietly();
			}

			this.jdbcResource.setDelisted(true);
		}

		if (this.mockResource != null && this.mockResource.isDelisted() == false) {
			try {
				this.delistResource(this.mockResource.getResource(), XAResource.TMSUCCESS);
			} catch (Exception ex) {
				logger.error("delistForgottenResources操作出错!", ex);
				this.setRollbackOnlyQuietly();
			}

			this.mockResource.setDelisted(true);
		}
	}

	public int getStatus() throws SystemException {
		return status;
	}

	public void registerSynchronization(Synchronization synchronization)
			throws RollbackException, IllegalStateException, SystemException {
		SynchronizationImpl wrapped = new SynchronizationImpl();
		wrapped.setDelegate(synchronization);
		this.synchronizationList.add(wrapped);
	}

	public synchronized void setRollbackOnly() throws IllegalStateException, SystemException {
		if (this.status == Status.STATUS_ACTIVE) {
			this.status = Status.STATUS_MARKED_ROLLBACK;
			logger.error("事务状态被标记为MARDED_ROLLBACK, 相关业务代码请参考异常堆栈(该异常并非实质错误, 可忽略)!", new Exception());
		} else if (this.status == Status.STATUS_MARKED_ROLLBACK) {
			// 忽略
		} else {
			throw new IllegalStateException();
		}
	}

	public void setRollbackOnlyQuietly() {
		try {
			this.setRollbackOnly();
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		}
	}

}
