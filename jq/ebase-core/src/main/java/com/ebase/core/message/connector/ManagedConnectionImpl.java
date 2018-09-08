package com.ebase.core.message.connector;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;

import com.ebase.core.message.service.MessageProduceService;
import com.ebase.core.message.service.MessageQueueService;

public class ManagedConnectionImpl implements ManagedConnection {
	private Set<ConnectionEventListener> listeners = new HashSet<ConnectionEventListener>();
	private MessageQueueService messageQueueService;
	static final XAResourceImpl xares = new XAResourceImpl();;
	private ManagedConnectionFactoryImpl mcf;
	private MessageProduceService messageProducer;

	private ConnectionRequestInfo information;

	public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		ConnectionImpl connection = new ConnectionImpl();
		connection.setManagedConnection(this);
		return connection;
	}

	public void destroy() throws ResourceException {
	}

	public void cleanup() throws ResourceException {
	}

	public void fireClose(Object connection) throws ResourceException {
		for (Iterator<ConnectionEventListener> itr = this.listeners.iterator(); itr.hasNext();) {
			ConnectionEventListener listener = itr.next();
			ConnectionEvent event = new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
			event.setConnectionHandle(connection);
			listener.connectionClosed(event);
		}
	}

	public void associateConnection(Object conn) throws ResourceException {
		if (ConnectionImpl.class.isInstance(conn)) {
			ConnectionImpl connection = (ConnectionImpl) conn;
			connection.setManagedConnection(this);
		}
	}

	public void addConnectionEventListener(ConnectionEventListener listener) {
		this.listeners.add(listener);
	}

	public void removeConnectionEventListener(ConnectionEventListener listener) {
		this.listeners.remove(listener);
	}

	public XAResourceImpl getXAResource() throws ResourceException {
		xares.setMcf(this.mcf);
		xares.setMessageProducer(this.messageProducer);
		return xares;
	}

	public LocalTransaction getLocalTransaction() throws ResourceException {
		return null;
	}

	public ManagedConnectionMetaData getMetaData() throws ResourceException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws ResourceException {
	}

	public PrintWriter getLogWriter() throws ResourceException {
		return null;
	}

	public MessageQueueService getMessageQueueService() {
		return messageQueueService;
	}

	public void setMessageQueueService(MessageQueueService messageQueueService) {
		this.messageQueueService = messageQueueService;
	}

	public ManagedConnectionFactoryImpl getMcf() {
		return mcf;
	}

	public void setMcf(ManagedConnectionFactoryImpl mcf) {
		this.mcf = mcf;
	}

	public MessageProduceService getMessageProducer() {
		return messageProducer;
	}

	public void setMessageProducer(MessageProduceService messageProducer) {
		this.messageProducer = messageProducer;
	}

	public ConnectionRequestInfo getInformation() {
		return information;
	}

	public void setInformation(ConnectionRequestInfo information) {
		this.information = information;
	}

}
