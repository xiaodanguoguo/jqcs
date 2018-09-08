package com.ebase.core.message;

import java.io.Serializable;

public interface Message {
	public static final String DEFAULT_CHARSET = "UTF-8";

	/* 消息Tags的键名称 */
	public static final String TAGS_KEY = "__tags__";
	/* 消息的keys. 服务器会为每个消息创建索引(hash)，应用可以通过Topic,key来查询情况. */
	public static final String MSG_KEYS = "__keys__";
	/* 消息类名的键名称 */
	public static final String MSG_CLASSNAME_KEYS = "__className__";
	/* 选填，消息延时级别，0表示不延时，大于0会延时特定的时间才会被消费 */
	public static final String DELAY_TIME_LEVEL = "_delay_time_level_";
	/* 选填，消息业务类型 */
	public static final String MSG_TYPE = "_msg_type_";
	/* 选填，消息业务类型 */
	public static final String PARTITION_ID = "_partition_Id_";
	public static final String APPLICATION_ID = "_application_Id_";
	public static final String IDENTIFIER = "_identifier_";
	public static final String SHORTLIVED = "_short_lived_";
	public static final String REQUEST_ID = "_request_Id_";
	public static final String USER_ID = "_user_Id_";
	public static final String TRANSIENT_MSGID = "_transient_msgid_";

	public String getMessageId();

	public Serializable getBody();

	public Object getProperty(String key);

	public String getBusiId();

	public String getTopics();

	public void setTopics(String topics);

	public String getTags();

	public void setTags(String tags);

	public String getType();

	public int getDelayTimeLevel();

	public String getPartitionId();

	public String getApplication();

	public void setApplication(String application);

	public void setShortLived(boolean shortLived);

	public boolean isShortLived();

	public String getIdentifier();

	public void setIdentifier(String identifier);

	public void setFlag(int flag);

	public int getFlag();

	public void setBusiId(String busiId);

	public void setBody(Serializable body);

}
