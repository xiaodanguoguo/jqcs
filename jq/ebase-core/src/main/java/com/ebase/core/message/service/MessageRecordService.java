package com.ebase.core.message.service;

public interface MessageRecordService {

	public void receiveMessage(long partitionId, String identifier, String msgid, String tmpid) throws RuntimeException;

	public void consumeMessage(long partitionId, String identifier) throws RuntimeException;

	public void consumeMessageError(long partitionId, String identifier) throws RuntimeException;

	public void consumeMessageLater(long partitionId, String identifier) throws RuntimeException;

}
