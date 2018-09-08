package com.ebase.core.message.connector;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManagerImpl implements ConnectionManager {
	private static final long serialVersionUID = 1L;
	static Logger logger = LoggerFactory.getLogger(ConnectionManagerImpl.class);

	private TransactionManager transactionManager;

	public Object allocateConnection(ManagedConnectionFactory mcf, ConnectionRequestInfo cxRequestInfo)
			throws ResourceException {
		ManagedConnection mc = mcf.createManagedConnection(null, cxRequestInfo);
		XAResource xares = mc.getXAResource();
		try {
			Transaction transaction = transactionManager.getTransaction();
			mc.addConnectionEventListener(new ConnectionEventListenerImpl(this.transactionManager));
			if (transaction != null) {
				transaction.enlistResource(xares);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new ResourceException(ex.getMessage());
		}
		return (Connection) mc.getConnection(null, cxRequestInfo);
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	static class ConnectionEventListenerImpl implements ConnectionEventListener {
		private TransactionManager transactionManager;

		public ConnectionEventListenerImpl(TransactionManager transactionManager) {
			this.transactionManager = transactionManager;
		}

		public void connectionClosed(ConnectionEvent event) {
			ManagedConnection mc = (ManagedConnection) event.getSource();

			try {
				XAResource xares = mc.getXAResource();
				Transaction transaction = transactionManager.getTransaction();
				if (transaction != null) {
					transaction.delistResource(xares, XAResource.TMSUCCESS);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}

		}

		public void connectionErrorOccurred(ConnectionEvent event) {

			try {
				Transaction transaction = transactionManager.getTransaction();
				transaction.setRollbackOnly();
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}

		}

		public void localTransactionStarted(ConnectionEvent event) {
		}

		public void localTransactionCommitted(ConnectionEvent event) {
		}

		public void localTransactionRolledback(ConnectionEvent event) {
		}
	}

}
