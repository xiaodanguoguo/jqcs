package com.ebase.core.message;

import javax.annotation.PreDestroy;

import com.ebase.core.message.service.MessageQueueFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class MessageListenerImpl implements MessageListener {
	static final Logger logger = (Logger) LoggerFactory.getLogger(MessageListenerImpl.class);

	private MessageQueueFacade messageQueueFacade;

	private MessageConsumer messageConsumer;

	public Boolean onMessage(Message message) {
		if (message.isShortLived()) {
			return this.messageConsumer.onConsume(message);
		} else {
			return this.invokeConsumeMessage(message);
		}
	}

	public Boolean invokeConsumeMessage(Message message) {
		logger.info("------------------onMessage..." + message);

		if (this.messageQueueFacade.receiverUpdateMessageRunning(message) != 1) {
			// 无效的消息, 当消费成功处理. 后续不再消费.
			return Boolean.TRUE;
		}

		Boolean status = null;
		try {
			status = this.messageConsumer.onConsume(message);
		} catch (RuntimeException rex) {
			logger.warn(String.format(
					"消息(topic= %s, tag= %s, type= %s, partitionId= %s, busiId= %s, body= %s)消费失败, 待Producer重新发送 resend!",
					message.getTopics(), message.getTags(), message.getType(), message.getPartitionId(), message.getBusiId(),
					JSON.toJSONString(message.getBody())), rex);
			status = Boolean.FALSE;
		}

		if (status) {
			this.messageQueueFacade.receiverUpdateMessageSuccess(message);
			logger.warn("消息(topic= {}, tag= {}, type= {}, partitionId= {}, busiId= {}, body= {})消费成功 success!",
					new Object[] { message.getTopics(), message.getTags(), message.getType(), message.getPartitionId(),
							message.getBusiId(), JSON.toJSONString(message.getBody()) });
			return Boolean.TRUE;
		} else {
			this.messageQueueFacade.receiverUpdateMessageToBegin(message);
			logger.warn("消息(topic= {}, tag= {}, type= {}, partitionId= {}, busiId= {}, body= {})消费失败 fail!",
					new Object[] { message.getTopics(), message.getTags(), message.getType(), message.getPartitionId(),
							message.getBusiId(), JSON.toJSONString(message.getBody()) });
			return Boolean.FALSE;
		}

	}

	public void initMethod() {
		logger.info("执行: MqMessageListener init-method");
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("执行: MqMessageListener preDestroy");
	}

	public void destroyMethod() {
		logger.info("执行: MqMessageListener destroy-method");
	}

	public void destroy() throws Exception {
		logger.info("执行: MqMessageListener destroy");
	}

	public String toString() {
		if (this.messageConsumer == null) {
			return String.format("%s.onConsume(MqMessage)", MessageConsumer.class.getCanonicalName());
		} else {
			return String.format("%s.onConsume(MqMessage)", this.messageConsumer.getClass().getCanonicalName());
		}
	}

	public MessageQueueFacade getMessageQueueFacade() {
		return messageQueueFacade;
	}

	public void setMessageQueueFacade(MessageQueueFacade messageQueueFacade) {
		this.messageQueueFacade = messageQueueFacade;
	}

	public MessageConsumer getMessageConsumer() {
		return messageConsumer;
	}

	public void setMessageConsumer(MessageConsumer messageConsumer) {
		this.messageConsumer = messageConsumer;
	}

}
