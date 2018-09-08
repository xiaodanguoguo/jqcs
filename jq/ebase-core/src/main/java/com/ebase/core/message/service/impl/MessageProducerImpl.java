package com.ebase.core.message.service.impl;

import java.util.UUID;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;

import com.ebase.core.log.SearchableLogHelper;
import com.ebase.core.message.Message;
import com.ebase.core.message.MessageException;
import com.ebase.core.message.MessageImpl;
import com.ebase.core.message.MessageProducer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.rocketmq.client.exception.MQClientException;

public class MessageProducerImpl implements MessageProducer, ApplicationContextAware {
	static Logger logger = LoggerFactory.getLogger(MessageProducerImpl.class);

	private ConnectionFactory connectionFactory;
	private ApplicationContext applicationContext;

	public void sendMessage(Message message) throws MessageException {
		Connection connection = null;
		Interaction interaction = null;
		try {
			connection = this.connectionFactory.getConnection();
			interaction = connection.createInteraction();

			ApplicationConfig config = this.applicationContext.getBean(ApplicationConfig.class);
			String application = config == null ? null : config.getName();
			message.setApplication(application);
			((MessageImpl) message)
					.setRecordName((String) MDC.get(SearchableLogHelper.MDC_KEY_REQUEST_ID));
			((MessageImpl) message)
					.setRecordShortDescription((String) MDC.get(SearchableLogHelper.MDC_KEY_USER_ID));

			if (StringUtils.isBlank(message.getIdentifier())) {
				String identifier = UUID.randomUUID().toString().replace("-", "");
				message.setIdentifier(identifier);
			}
			MessageImpl msg = (MessageImpl) message;
			msg.setTransientMsgId(message.getIdentifier());

			interaction.execute(null, msg);
		} catch (Exception ex) {
			throw new MessageException(ex);
		} finally {
			this.closeIfNecessary(interaction);
			this.closeIfNecessary(connection);
		}
	}

	private void closeIfNecessary(Interaction interaction) {
		if (interaction != null) {
			try {
				interaction.close();
			} catch (ResourceException ex) {
				logger.debug(ex.getMessage(), ex);
			}
		}
	}

	private void closeIfNecessary(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (ResourceException ex) {
				logger.debug(ex.getMessage(), ex);
			}
		}
	}

	public void shutdown() throws MQClientException, InterruptedException {
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
}
