package com.ebase.core.message;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebase.core.log.SearchableLogHelper;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * RocketMQ随机消费监听实现
 */
public class RocketMQListener implements MessageListenerConcurrently {
	private final Logger logger = LoggerFactory.getLogger(RocketMQListener.class);
	private final Map<String, MessageListener> listeners = new HashMap<String, MessageListener>();
	// DataProducer dataProducer = new DataProducer();

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (int i = 0; i < msgs.size(); i++) {
			MessageExt msg = msgs.get(i);
			try {
				this.onMessage(msg.getTopic(), msg);
				logger.info("consume message: identifiers={}, msgId={}.", msg.getKeys(), msg.getMsgId());
			} catch (Exception ex) {
				context.setAckIndex(i);
				logger.warn("消息(message= {})消费失败, 待后续重新消费!", msg);
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	private void onMessage(String topic, MessageExt msg)
			throws MessageException, UnsupportedEncodingException, ClassNotFoundException {

		String className = msg.getUserProperty(Message.MSG_CLASSNAME_KEYS);
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (Exception ex) {
			logger.warn("类(name= {})加载失败, 消息(message= {})忽略!", className, msg);
			return;
		}

		if (Serializable.class.isAssignableFrom(clazz) == false) {
			throw new MessageException("Class " + clazz.getName() + " must implements Serializable");
		}

		String stringContent = new String(msg.getBody(), Message.DEFAULT_CHARSET);
		Object obj = JSON.parseObject(stringContent, clazz);

		MessageImpl message = new MessageImpl();
		message.setBody((Serializable) obj);
		message.setTopics(msg.getTopic());
		message.setTags(msg.getTags());
		message.setType(msg.getUserProperty(Message.MSG_TYPE));
		message.setPartitionId(msg.getUserProperty(Message.PARTITION_ID));
		message.setBusiId(msg.getKeys());
		message.setFlag(msg.getFlag());

		message.setMessageId(msg.getMsgId());

		message.setApplication(msg.getUserProperty(Message.APPLICATION_ID));
		message.setIdentifier(msg.getUserProperty(Message.IDENTIFIER));
		message.setShortLived(Boolean.valueOf(msg.getUserProperty(Message.SHORTLIVED)));
		message.setRecordName(msg.getUserProperty(Message.REQUEST_ID));
		message.setRecordShortDescription(msg.getUserProperty(Message.USER_ID));
		message.setTransientMsgId(msg.getUserProperty(Message.TRANSIENT_MSGID));

		MessageListener listener = this.listeners.get(topic);
		if (listener == null) {
			logger.info("No topic listener find for " + topic + " RECONSUME_LATER!");
			throw new MessageException("No topic listener find for " + topic);
		}

		Boolean status = null;
		try {
			String requestId = message.getRecordName() == null ? SearchableLogHelper.getRandomId() : message.getRecordName();
			String userId = message.getRecordShortDescription() == null ? "anonymous" : message.getRecordShortDescription();
			MDC.put(SearchableLogHelper.MDC_KEY_USER_ID, userId);
			MDC.put(SearchableLogHelper.MDC_KEY_APP, SearchableLogHelper.getApplication());
			MDC.put(SearchableLogHelper.MDC_KEY_IP, SearchableLogHelper.getInetAddr());
			MDC.put(SearchableLogHelper.MDC_KEY_REQUEST_ID, requestId);
			MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, String.valueOf(listener));

			// new SendMsgThread(listener.toString(), message, msg, "C", " ").start();
			status = listener.onMessage(message);
			// new SendMsgThread(listener.toString(), message, msg, "CE", status.toString() + " ").start();

			logger.debug("msgkey: {}, msgid: {}", msg.getKeys(), msg.getMsgId());
		} catch (Exception ex) {
			logger.error(String.format("消息(message= %s)消费出错!", message), ex);
			// new SendMsgThread(listener.toString(), message, msg, "E", ex.toString() + " ").start();
			throw ex;
		} finally {
			MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
			MDC.remove(SearchableLogHelper.MDC_KEY_REQUEST_ID);
			MDC.remove(SearchableLogHelper.MDC_KEY_IP);
			MDC.remove(SearchableLogHelper.MDC_KEY_APP);
			MDC.remove(SearchableLogHelper.MDC_KEY_USER_ID);
		}

		if (status == null || status == false) {
			throw new MessageException("Reconsume required!");
		}

	}

	/**
	 * 注册对应主题的监听器
	 * 
	 * @param topic
	 * @param listener
	 */
	public void registerMessageLisener(String topic, MessageListener listener) {
		MessageListener old = this.listeners.put(topic, listener);
		if (old != null) {
			logger.warn("Listener with topic {} is override", topic);
		}
	}

}
