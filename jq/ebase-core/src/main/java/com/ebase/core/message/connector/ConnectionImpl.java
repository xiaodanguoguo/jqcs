package com.ebase.core.message.connector;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.cci.Interaction;
import javax.resource.cci.LocalTransaction;
import javax.resource.cci.ResultSetInfo;
import javax.resource.spi.ManagedConnection;

public class ConnectionImpl implements Connection {
	private ConnectionFactoryImpl connectionFactory;
	private ManagedConnectionImpl managedConnection;

	public void close() throws ResourceException {
		this.managedConnection.fireClose(this);
	}

	public Interaction createInteraction() throws ResourceException {
		InteractionImpl interaction = new InteractionImpl();
		interaction.setConnection(this);
		interaction.setMessageProducer(((ManagedConnectionImpl) this.managedConnection).getMessageProducer());
		interaction.setMessageQueueService(((ManagedConnectionImpl) this.managedConnection).getMessageQueueService());
		return interaction;
	}

	public LocalTransaction getLocalTransaction() throws ResourceException {
		return null;
	}

	public ConnectionMetaData getMetaData() throws ResourceException {
		return null;
	}

	public ResultSetInfo getResultSetInfo() throws ResourceException {
		return null;
	}

	public ConnectionFactoryImpl getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = (ConnectionFactoryImpl) connectionFactory;
	}

	public ManagedConnectionImpl getManagedConnection() {
		return managedConnection;
	}

	public void setManagedConnection(ManagedConnection managedConnection) {
		this.managedConnection = (ManagedConnectionImpl) managedConnection;
	}

}
