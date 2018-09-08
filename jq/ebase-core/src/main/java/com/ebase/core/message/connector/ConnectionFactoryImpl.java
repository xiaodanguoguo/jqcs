package com.ebase.core.message.connector;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ConnectionManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
	private static final long serialVersionUID = 1L;

	private ManagedConnectionFactoryImpl managedConnectionFactory;
	private ConnectionManager connectionManager;

	public javax.resource.cci.Connection getConnection() throws javax.resource.ResourceException {
		Connection allocateConnection = (Connection) this.connectionManager
				.allocateConnection(this.managedConnectionFactory, null);
		ConnectionImpl connection = (ConnectionImpl) allocateConnection;
		connection.setConnectionFactory(this);

		return connection;
	}

	public javax.resource.cci.Connection getConnection(javax.resource.cci.ConnectionSpec arg0)
			throws javax.resource.ResourceException {
		return this.getConnection();
	}

	public javax.resource.cci.RecordFactory getRecordFactory() throws javax.resource.ResourceException {
		return null;
	}

	public javax.resource.cci.ResourceAdapterMetaData getMetaData() throws javax.resource.ResourceException {
		return null;
	}

	public void setReference(Reference reference) {
	}

	public Reference getReference() throws NamingException {
		return null;
	}

	public ManagedConnectionFactoryImpl getManagedConnectionFactory() {
		return managedConnectionFactory;
	}

	public void setManagedConnectionFactory(ManagedConnectionFactoryImpl managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

}
