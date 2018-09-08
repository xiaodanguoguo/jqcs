package com.ebase.core.message;

/**
 * 消息监听
 */
public interface MessageListener {

	/* 响应一条消息 */
	public Boolean onMessage(Message message);

}