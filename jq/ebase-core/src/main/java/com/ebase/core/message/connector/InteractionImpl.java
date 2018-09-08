package com.ebase.core.message.connector;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;
import javax.resource.cci.ResourceWarning;
import javax.transaction.xa.XAException;

import com.ebase.core.message.Message;
import com.ebase.core.message.service.MessageQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebase.core.message.service.MessageProduceService;

public class InteractionImpl implements Interaction {
	static Logger logger = LoggerFactory.getLogger(InteractionImpl.class);

	private ConnectionImpl connection;
	private MessageQueueService messageQueueService;
	private MessageProduceService messageProducer;

	public boolean execute(InteractionSpec ispec, Record input, Record output) throws ResourceException {
		throw new ResourceException("Not supported yet!");
	}

	public Record execute(InteractionSpec ispec, Record input) throws ResourceException {
		Message message = (Message) input;
		try {
			if (message.isShortLived() == false) {
				this.messageQueueService.saveMessage(message);
			}
			ManagedConnectionImpl managedConnection = this.connection.getManagedConnection();
			XAResourceImpl xaResource = managedConnection.getXAResource();
			xaResource.addMessage(message);
		} catch (XAException rex) {
			switch (rex.errorCode) {
			case XAException.XAER_NOTA:
				this.sendMessageImmediately(message); // TODO application
				break;
			default:
				logger.error(String.format("保存消息失败(%s)!", message), rex);
				throw new ResourceException("保存消息失败!");
			}
		} catch (Exception rex) {
			logger.error(String.format("保存消息失败(%s)!", message), rex);
			throw new ResourceException("保存消息失败!");
		}

		return null;
	}

	protected void sendMessageImmediately(Message message) throws ResourceException {
		try {
			this.messageProducer.sendMessage(message);
		} catch (Exception rex) {
			logger.error(String.format("保存消息失败(%s)!", message), rex);
			throw new ResourceException("保存消息失败!");
		}
	}

	public ResourceWarning getWarnings() throws ResourceException {
		return null;
	}

	public void clearWarnings() throws ResourceException {
	}

	public void close() throws ResourceException {
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = (ConnectionImpl) connection;
	}

	public MessageProduceService getMessageProducer() {
		return messageProducer;
	}

	public void setMessageProducer(MessageProduceService messageProducer) {
		this.messageProducer = messageProducer;
	}

	public MessageQueueService getMessageQueueService() {
		return messageQueueService;
	}

	public void setMessageQueueService(MessageQueueService messageQueueService) {
		this.messageQueueService = messageQueueService;
	}

}
