package com.ebase.core.message.service.impl;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.nio.charset.Charset;

import com.ebase.core.message.MessageException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;

import com.ebase.core.message.MessageImpl;
import com.ebase.core.message.service.MessageProduceService;

public class MessageProduceServiceImpl implements MessageProduceService, EnvironmentAware {
	static Logger logger = LoggerFactory.getLogger(MessageProduceServiceImpl.class);

	private DefaultMQProducer producer;
	private Environment environment;

	public void sendMessage(com.ebase.core.message.Message msg) throws MessageException {

		Message message = new Message();
		if (msg.getBusiId() == null) {
			throw new MessageException("Message's keys can not be null.");
		} else if (msg.getType() == null) {
			throw new MessageException("Message's type can not be null.");
		} /*
			 * else if (msg.getPartitionId() == null) { throw new MessageException("Message's partitionId can not be null."); }
			 */ else if (msg.getBody() == null) {
			throw new MessageException("Message's content can not be null.");
		} else if (msg.getApplication() == null) {
			throw new MessageException("Message's application can not be null.");
		}

		message.setKeys(msg.getBusiId());
		message.setTopic(msg.getTopics());
		message.setTags(msg.getTags());
		message.setFlag(msg.getFlag());

		int delayTimeLevel = msg.getDelayTimeLevel();
		if (delayTimeLevel != 0) {
			message.setDelayTimeLevel(delayTimeLevel);
		}

		MessageImpl mesg = (MessageImpl) msg;

		message.putUserProperty(com.ebase.core.message.Message.APPLICATION_ID,
				StringUtils.trimToEmpty(msg.getApplication()));
		message.putUserProperty(com.ebase.core.message.Message.MSG_TYPE, StringUtils.trimToEmpty(msg.getType()));
		message.putUserProperty(com.ebase.core.message.Message.PARTITION_ID, StringUtils.trimToEmpty(msg.getPartitionId()));
		message.putUserProperty(com.ebase.core.message.Message.IDENTIFIER, StringUtils.trimToEmpty(msg.getIdentifier()));
		message.putUserProperty(com.ebase.core.message.Message.SHORTLIVED, String.valueOf(msg.isShortLived()));
		message.putUserProperty(com.ebase.core.message.Message.REQUEST_ID, StringUtils.trimToEmpty(mesg.getRecordName()));
		message.putUserProperty(com.ebase.core.message.Message.USER_ID,
				StringUtils.trimToEmpty(mesg.getRecordShortDescription()));
		message.putUserProperty(com.ebase.core.message.Message.TRANSIENT_MSGID,
				StringUtils.trimToEmpty(mesg.getTransientMsgId()));

		if (msg instanceof MessageImpl) {
			Serializable value = msg.getBody();
			message.putUserProperty(com.ebase.core.message.Message.MSG_CLASSNAME_KEYS //
					, value.getClass().getName());
			String stringContent = JSON.toJSONString(value);
			message.setBody(stringContent.getBytes(Charset.forName(com.ebase.core.message.Message.DEFAULT_CHARSET)));
		} else {
			throw new MessageException("Unsupported message " + msg.getClass().getName());
		}

		SendResult result = null;
		try {
			result = this.producer.send(message);
			String messageId = result.getMsgId();
			mesg.setMessageId(messageId);
			logger.info("发送消息成功, message= {}.", mesg);
		} catch (Exception ex) {
			logger.error(String.format("发送消息失败(%s)!", message), ex);
			throw new MessageException("发送消息失败!");
		}

		SendStatus status = result.getSendStatus();
		if (status == SendStatus.FLUSH_DISK_TIMEOUT) {
			logger.warn(String.format("消息发送成功, 但服务器刷盘超时(status= %s, message= %s)!", status, message));
		} else if (status == SendStatus.FLUSH_SLAVE_TIMEOUT) {
			logger.warn(String.format("消息发送成功, 但服务器同步到slave时超时(status= %s, message= %s)!", status, message));
		} else if (status == SendStatus.SLAVE_NOT_AVAILABLE) {
			logger.warn(String.format("消息发送成功, 但此时slave不可用, 消息已进入服务器队列(status= %s, message= %s)!", status, message));
		}

	}

	private String getInstanceName() {
		String dubboApp = this.environment.getProperty("server.TYPE");
		String cloudApp = this.environment.getProperty("spring.application.name");

		String name = StringUtils.isBlank(dubboApp) ? cloudApp : dubboApp;

		// String instance = PropertyConfigurer.getProperty("mq.producer.instance.name", "Producer");
		String appId = name == null ? null : name.replaceAll("[^0-9a-zA-Z_]", "_");
		// PropertyConfigurer.getProperty("mq.producer.appId", "app1");
		String mxname = ManagementFactory.getRuntimeMXBean().getName();
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			return String.format("%s-%s-%s", appId, mxname, address);
		} catch (Exception ex) {
			return String.format("%s-%s", appId, mxname);
		}
	}

	public void start() {

		this.producer.setInstanceName(this.getInstanceName());

		try {
			this.producer.start();
		} catch (MQClientException ex) {
			logger.warn("DefaultMQProducer启动失败!", ex);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				shutdown();
			}
		}));
	}

	public void shutdown() {
		try {
			this.producer.shutdown();
		} catch (RuntimeException ex) {
			logger.warn("DefaultMQProducer关闭失败!", ex);
		}
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public DefaultMQProducer getProducer() {
		return producer;
	}

	public void setProducer(DefaultMQProducer producer) {
		this.producer = producer;
	}

}
