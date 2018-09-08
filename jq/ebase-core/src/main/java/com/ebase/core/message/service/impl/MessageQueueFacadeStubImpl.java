package com.ebase.core.message.service.impl;

import com.ebase.core.message.Message;
import com.ebase.core.message.MessageImpl;
import com.ebase.core.message.service.MessageQueueFacade;
import com.ebase.core.message.service.MessageRecordService;
import com.ebase.core.message.service.MessageRecordServiceFacatory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageQueueFacadeStubImpl implements MessageQueueFacade {
	static Logger logger = LoggerFactory.getLogger(MessageQueueFacadeStubImpl.class);

	private MessageRecordServiceFacatory messageRecordServiceFacatory;

	public int receiverUpdateMessageRunning(Message message) {
		String application = message.getApplication();
		MessageRecordService service = this.messageRecordServiceFacatory.getMessageRecordService(application);
		try {
			long partitionId = Long.parseLong(message.getPartitionId());
			String identifier = message.getIdentifier();
			String msgid = message.getMessageId();
			String tmpid = ((MessageImpl) message).getTransientMsgId();
			service.receiveMessage(partitionId, identifier, msgid, tmpid);
			return 1;
		} catch (RuntimeException rex) {
			logger.debug("receiverUpdateMessageRunning处理失败!");
			return 0;
		}
	}

	public int receiverUpdateMessageException(Message message) {
		String application = message.getApplication();
		MessageRecordService service = this.messageRecordServiceFacatory.getMessageRecordService(application);
		try {
			long partitionId = Long.parseLong(message.getPartitionId());
			service.consumeMessageError(partitionId, message.getIdentifier());
			return 1;
		} catch (RuntimeException rex) {
			logger.debug("receiverUpdateMessageException处理失败!");
			return 0;
		}
	}

	public int receiverUpdateMessageSuccess(Message message) {
		String application = message.getApplication();
		MessageRecordService service = this.messageRecordServiceFacatory.getMessageRecordService(application);
		try {
			long partitionId = Long.parseLong(message.getPartitionId());
			service.consumeMessage(partitionId, message.getIdentifier());
			return 1;
		} catch (RuntimeException rex) {
			logger.debug("receiverUpdateMessageSuccess处理失败!");
			return 0;
		}
	}

	public int receiverUpdateMessageToBegin(Message message) {
		String application = message.getApplication();
		MessageRecordService service = this.messageRecordServiceFacatory.getMessageRecordService(application);
		try {
			long partitionId = Long.parseLong(message.getPartitionId());
			service.consumeMessageLater(partitionId, message.getIdentifier());
			return 1;
		} catch (RuntimeException rex) {
			logger.debug("receiverUpdateMessageToBegin处理失败!");
			return 0;
		}
	}

	public MessageRecordServiceFacatory getMessageRecordServiceFacatory() {
		return messageRecordServiceFacatory;
	}

	public void setMessageRecordServiceFacatory(MessageRecordServiceFacatory messageRecordServiceFacatory) {
		this.messageRecordServiceFacatory = messageRecordServiceFacatory;
	}

}
