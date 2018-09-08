package com.ebase.core.message.connector;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

import com.ebase.core.message.service.MessageProduceService;
import com.ebase.core.message.service.MessageQueueService;

public class ManagedConnectionFactoryImpl implements ManagedConnectionFactory {
	private static final long serialVersionUID = 1L;

	private ConnectionFactory connectionFactory;
	private MessageQueueService messageQueueService;
	private MessageProduceService messageProducer;

	public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
		((ConnectionFactoryImpl) this.connectionFactory).setConnectionManager(cxManager);
		return this.connectionFactory;
	}

	public Object createConnectionFactory() throws ResourceException {
		return this.connectionFactory;
	}

	public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo)
			throws ResourceException {

		ManagedConnectionImpl mc = new ManagedConnectionImpl();
		mc.setMcf(this);
		mc.setInformation(cxRequestInfo);
		mc.setMessageQueueService(this.messageQueueService);
		mc.setMessageProducer(this.messageProducer);
		return mc;

	}

	@SuppressWarnings("rawtypes")
	public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo)
			throws ResourceException {

		Iterator<?> itr = connectionSet.iterator();
		while (itr.hasNext()) {
			Object element = itr.next();
			if (ManagedConnectionImpl.class.isInstance(element)) {
				ManagedConnectionImpl mc = (ManagedConnectionImpl) element;
				if (cxRequestInfo == null && mc.getInformation() == null) {
					return mc;
				} else if (cxRequestInfo != null && cxRequestInfo.equals(mc.getInformation())) {
					return mc;
				}
			}
		}

		return null;
	}

	public void setLogWriter(PrintWriter out) throws ResourceException {
	}

	public PrintWriter getLogWriter() throws ResourceException {
		return null;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public MessageQueueService getMessageQueueService() {
		return messageQueueService;
	}

	public void setMessageQueueService(MessageQueueService messageQueueService) {
		this.messageQueueService = messageQueueService;
	}

	public MessageProduceService getMessageProducer() {
		return messageProducer;
	}

	public void setMessageProducer(MessageProduceService messageProducer) {
		this.messageProducer = messageProducer;
	}

}
