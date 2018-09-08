package com.ebase.core.message.service;

import com.ebase.core.message.Message;
import com.ebase.core.message.MessageException;

public interface MessageProduceService {

	public void sendMessage(Message message) throws MessageException;

}
