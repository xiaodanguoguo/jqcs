package com.ebase.core.message.service;

import com.ebase.core.message.Message;

public interface MessageQueueFacade {

	/**
	 * 消费者接收消息后，更新消息，如果更新成功，则继续处理，否则，丢弃消息 将消息状态从1更新为2 成功返回1，失败返回0.
	 */
	public int receiverUpdateMessageRunning(Message message);

	/**
	 * 业务处理出现异常时，更新消息为异常 将消息状态从2更新为3，重试次数+1 成功返回1，失败返回0.
	 */
	public int receiverUpdateMessageException(Message message);

	/**
	 * 消息处理完成 将消息状态从2更新为4 成功返回1，失败返回0.
	 */
	public int receiverUpdateMessageSuccess(Message message);

	/**
	 * 消息处理完成 将消息状态从2更新为1,并且重试次数增加1 成功返回1，失败返回0.
	 */
	public int receiverUpdateMessageToBegin(Message message);

}
