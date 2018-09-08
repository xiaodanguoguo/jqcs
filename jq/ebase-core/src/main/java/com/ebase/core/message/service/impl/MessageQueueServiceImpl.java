package com.ebase.core.message.service.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.ebase.core.message.Message;
import com.ebase.core.message.service.MessageQueueService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import com.ebase.core.message.MessageImpl;

public class MessageQueueServiceImpl implements MessageQueueService {
	static Logger logger = LoggerFactory.getLogger(MessageQueueServiceImpl.class);

	static final String INSERT_SQL_PRODUCE = "insert into mq_queue_product(partitionid, id, busiid, appname, topics, tags, type, sendtime, flag, failtimes, reqid, userid, msgid) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static final String INSERT_SQL_PRODUCE_BODY = "insert into mq_queue_product_body(partitionid, id, busiid, body, classname) values(?, ?, ?, ?, ?)";
	// static final String MODIFY_SQL_PRODUCE = "update mq_queue_product set msgid = ? where id = ? and type = ?";

	private DataSource dataSource;

	public int saveMessage(Message message) {
		Connection conn = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		try {
			long partitionId = Long.parseLong(message.getPartitionId());

			String identifier = message.getIdentifier();

			Serializable body = message.getBody();
			String className = body.getClass().getName();
			String content = JSONObject.toJSON(body).toString();

			String userKey = ((MessageImpl) message).getRecordShortDescription();
			Long userId = null;
			try {
				userId = Long.valueOf(StringUtils.trimToEmpty(userKey));
			} catch (Exception ex) {
				userId = null;
			}

			conn = this.dataSource.getConnection();
			stmt1 = conn.prepareStatement(INSERT_SQL_PRODUCE);
			stmt1.setLong(1, partitionId);
			stmt1.setString(2, identifier);
			stmt1.setString(3, message.getBusiId());
			stmt1.setString(4, message.getApplication());
			stmt1.setString(5, message.getTopics());
			stmt1.setString(6, message.getTags());
			stmt1.setString(7, message.getType());
			stmt1.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			stmt1.setInt(9, 1);
			stmt1.setInt(10, 0);
			stmt1.setString(11, ((MessageImpl) message).getRecordName());
			stmt1.setObject(12, userId);
			stmt1.setString(13, identifier);

			int value1 = stmt1.executeUpdate();

			stmt2 = conn.prepareStatement(INSERT_SQL_PRODUCE_BODY);
			stmt2.setLong(1, partitionId);
			stmt2.setString(2, identifier);
			stmt2.setString(3, message.getBusiId());
			stmt2.setString(4, content);
			stmt2.setString(5, className);
			int value2 = stmt2.executeUpdate();

			return value1 > 0 && value2 > 0 ? 1 : 0;
		} catch (Exception ex) {
			logger.error("写入消息记录失败!", ex);
			return 0;
		} finally {
			this.closeQuietly(stmt1);
			this.closeQuietly(stmt2);
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
