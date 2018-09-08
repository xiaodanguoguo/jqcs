package com.ebase.core.message;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.resource.cci.Record;

public class MessageImpl implements Message, Record, Cloneable {
	private static final long serialVersionUID = 1L;

	// 分库id
	protected String partitionId;
	// 业务id 唯一
	protected String busiId;
	// 主题
	protected String topics;
	// 标签
	protected String tags;
	// 业务类型
	protected String type;
	// 延迟级别 默认0
	protected int delayTimeLevel;
	// 发送时间
	protected Date sendtime;
	// 消费时间
	protected Date consumeTime;
	// 状态标识
	// 1生产者消息发送完成
	// 2消费者接收到消息，并锁定
	// 3消费者转发消息到error队列
	// 4消费者处理消息完成
	// 5消息生产者作废
	protected int flag;
	// 失败次数
	protected int failtimes;
	// 消息id
	protected String messageId;
	// 属性值映射
	private Map<String, Object> props = new HashMap<String, Object>();

	private String name; // 临时用于存放requestId
	private String description; // 临时用于存放userId
	private String application;
	private boolean shortLived;
	private String identifier;
	private String transientMsgId;
	private Serializable body;

	public String toString() {
		return String.format("[message| busi= %s, type= %s, flag= %s, body= %s]" //
				, this.busiId, this.type, this.flag, this.body);
	}

	public Object clone() throws CloneNotSupportedException {
		return null;
	}

	public String getRecordName() {
		return this.name;
	}

	public void setRecordName(String name) {
		this.name = name;
	}

	public void setRecordShortDescription(String description) {
		this.description = description;
	}

	public String getRecordShortDescription() {
		return this.description;
	}

	public boolean setProperty(String key, Object value) {
		return this.props.put(key, value) != null;
	}

	public Object getProperty(String key) {
		return this.props.get(key);
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDelayTimeLevel() {
		return delayTimeLevel;
	}

	public void setDelayTimeLevel(int delayTimeLevel) {
		this.delayTimeLevel = delayTimeLevel;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getFailtimes() {
		return failtimes;
	}

	public void setFailtimes(int failtimes) {
		this.failtimes = failtimes;
	}

	public Map<String, Object> getProps() {
		return props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public boolean isShortLived() {
		return shortLived;
	}

	public void setShortLived(boolean shortLived) {
		this.shortLived = shortLived;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTransientMsgId() {
		return transientMsgId;
	}

	public void setTransientMsgId(String transientMsgId) {
		this.transientMsgId = transientMsgId;
	}

	public Serializable getBody() {
		return body;
	}

	public void setBody(Serializable body) {
		this.body = body;
	}

}
