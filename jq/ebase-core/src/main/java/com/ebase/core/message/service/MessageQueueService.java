package com.ebase.core.message.service;

import com.ebase.core.message.Message;

public interface MessageQueueService {

	public int saveMessage(Message message);

}
