package com.ebase.core.message;

/**
 * 消费者处理消息回调接口
 */
public interface MessageConsumer {

	/* 消费者处理消息 */
	public Boolean onConsume(Message message);

}
