package com.ebase.core.message;

public interface MessageProducer {

	public void sendMessage(Message message) throws MessageException;

}
