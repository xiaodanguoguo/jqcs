package com.ebase.core.message.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import com.ebase.core.message.Message;
import com.ebase.core.message.MessageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.message.service.MessageQueueFacade;
import com.ebase.core.message.service.MessageRecordService;

public class MessageQueueFacadeSkeletonImpl implements MessageQueueFacade, MessageRecordService {
	static Logger logger = LoggerFactory.getLogger("framework-service");

	static final String UPDATE_SQL_FLAG_AND_CONSMTIME_BY_MSG = "update mq_queue_product set FLAG = ?, CONSUMETIME = ? where id = ? and FLAG = ? and partitionid= ? and (msgid = ? or msgid = ?)";
	static final String UPDATE_SQL_FLAG_AND_CONSMTIME = "update mq_queue_product set FLAG = ?, CONSUMETIME = ? where id = ? and FLAG = ? and partitionid= ?";
	static final String UPDATE_SQL_FLAG_AND_FAILTIMES = "update mq_queue_product set FLAG = ?, CONSUMETIME = ?, FAILTIMES = FAILTIMES + 1 where id = ? and FLAG = ? and partitionid= ?";

	private DataSource dataSource;

	public int receiverUpdateMessageRunning(Message message) {
		long partitionId = Long.parseLong(message.getPartitionId());
		String identifier = message.getIdentifier();
		MessageImpl msg = (MessageImpl) message;
		String msgid = message.getMessageId();
		String tmpid = msg.getTransientMsgId();
		int value = this.updateFlagAndConsumeTimeByMessageId(partitionId, identifier, msgid, tmpid, 1, 2);
		return value > 0 ? 1 : 0;
	}

	public void receiveMessage(long partitionId, String identifier, String msgid, String tmpid) throws BusinessException {
		int value = this.updateFlagAndConsumeTimeByMessageId(partitionId, identifier, msgid, tmpid, 1, 2);
		if (value == 0) {
			throw new RuntimeException(String.format("receiveMessage(%s, %s)操作失败!", partitionId, identifier));
		}
	}

	public int receiverUpdateMessageException(Message message) {
		long partitionId = Long.parseLong(message.getPartitionId());
		int value = this.updateFlagAndFailtimes(partitionId, message.getIdentifier(), 2, 3);
		return value > 0 ? 1 : 0;
	}

	public void consumeMessageError(long partitionId, String identifier) throws BusinessException {
		int value = this.updateFlagAndFailtimes(partitionId, identifier, 2, 3);
		if (value == 0) {
			throw new RuntimeException(String.format("consumeMessageError(%s, %s)操作失败!", partitionId, identifier));
		}
	}

	public int receiverUpdateMessageSuccess(Message message) {
		long partitionId = Long.parseLong(message.getPartitionId());
		int value = this.updateFlagAndConsumeTime(partitionId, message.getIdentifier(), 2, 4);
		return value > 0 ? 1 : 0;
	}

	public void consumeMessage(long partitionId, String identifier) throws BusinessException {
		int value = this.updateFlagAndConsumeTime(partitionId, identifier, 2, 4);
		if (value == 0) {
			throw new RuntimeException(String.format("consumeMessage(%s, %s)操作失败!", partitionId, identifier));
		}
	}

	public int receiverUpdateMessageToBegin(Message message) {
		long partitionId = Long.parseLong(message.getPartitionId());
		int value = this.updateFlagAndFailtimes(partitionId, message.getIdentifier(), 2, 1);
		return value > 0 ? 1 : 0;
	}

	public void consumeMessageLater(long partitionId, String identifier) throws RuntimeException {
		int value = this.updateFlagAndFailtimes(partitionId, identifier, 2, 1);
		if (value == 0) {
			throw new RuntimeException(String.format("consumeMessageLater(%s, %s)操作失败!", partitionId, identifier));
		}
	}

	protected int updateFlagAndConsumeTimeByMessageId(long partitionId, String identifier, String msgid, String tmpid,
			int oldval, int newval) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dataSource.getConnection();
			stmt = conn.prepareStatement(UPDATE_SQL_FLAG_AND_CONSMTIME_BY_MSG);
			stmt.setInt(1, newval);
			stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			stmt.setString(3, identifier);
			stmt.setInt(4, oldval);
			stmt.setLong(5, partitionId);
			stmt.setString(6, msgid);
			stmt.setString(7, tmpid);
			return stmt.executeUpdate();
		} catch (Exception ex) {
			logger.error("更新flag, consumetime失败!", ex);
			return 0;
		} finally {
			this.closeQuietly(stmt);
			this.closeQuietly(conn);
		}
	}

	protected int updateFlagAndConsumeTime(long partitionId, String identifier, int oldval, int newval) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dataSource.getConnection();
			stmt = conn.prepareStatement(UPDATE_SQL_FLAG_AND_CONSMTIME);
			stmt.setInt(1, newval);
			stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			stmt.setString(3, identifier);
			stmt.setInt(4, oldval);
			stmt.setLong(5, partitionId);
			return stmt.executeUpdate();
		} catch (Exception ex) {
			logger.error("更新flag, consumetime失败!", ex);
			return 0;
		} finally {
			this.closeQuietly(stmt);
			this.closeQuietly(conn);
		}
	}

	protected int updateFlagAndFailtimes(long partitionId, String identifier, int oldval, int newval) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dataSource.getConnection();
			stmt = conn.prepareStatement(UPDATE_SQL_FLAG_AND_FAILTIMES);
			stmt.setInt(1, newval);
			stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			stmt.setString(3, identifier);
			stmt.setInt(4, oldval);
			stmt.setLong(5, partitionId);
			return stmt.executeUpdate();
		} catch (Exception ex) {
			logger.error("更新flag, failtimes失败!", ex);
			return 0;
		} finally {
			this.closeQuietly(stmt);
			this.closeQuietly(conn);
		}
	}

	protected void closeQuietly(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception ex) {
				logger.debug(ex.getMessage(), ex);
			}
		}
	}

	protected void closeQuietly(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ex) {
				logger.debug(ex.getMessage(), ex);
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
