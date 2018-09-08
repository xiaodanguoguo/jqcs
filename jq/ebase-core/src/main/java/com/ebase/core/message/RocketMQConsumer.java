package com.ebase.core.message;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.HashMap;

import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

public class RocketMQConsumer implements InitializingBean, EnvironmentAware {
	final Logger logger = LoggerFactory.getLogger(getClass());

	private RocketMQListener messageListener;

	private String[] topicAndTag;

	private DefaultMQPushConsumer consumer;

	private Environment environment;

	/**
	 * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从EnnewMQ服务器推到了应用客户端。<br>
	 * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
	 */
	public void afterPropertiesSet() throws Exception {
		/**
		 * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
		 * 注意：ConsumerGroupName需要由应用来保证唯一
		 */

		String groupName = this.environment.getProperty("mq.consumer.group.name", "ConsumerGroupName1");
		logger.info("MqConsumerImpl ## groupName:" + groupName);

		String namesrvaddr = this.environment.getProperty("mq.namesrv.address", "10.37.149.35:9876;10.37.149.36:9876");
		logger.info("MqConsumerImpl ## namesrvaddr ## mq.namesrv.address:" + namesrvaddr);

		consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(namesrvaddr);

		String messageModel = this.environment.getProperty("mq.consumer.messageModel", "CLUSTERING");

		String consumeFromWhere = this.environment.getProperty("mq.consumer.consumeFromWhere", "CONSUME_FROM_LAST_OFFSET");
		String consumeThreadMin = this.environment.getProperty("mq.consumer.consumeThreadMin", "3");
		String consumeThreadMax = this.environment.getProperty("mq.consumer.consumeThreadMax", "10");

		consumer.setInstanceName(this.getInstanceName());

		if (messageModel != null) {
			if (MessageModel.BROADCASTING.toString().equals(messageModel)) {
				consumer.setMessageModel(MessageModel.BROADCASTING);
			} else {
				consumer.setMessageModel(MessageModel.CLUSTERING);
			}
		}

		if (consumeFromWhere != null) {
			if (ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET.toString().equals(consumeFromWhere)) {
				consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			} else if (ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET.toString().equals(consumeFromWhere)) {
				consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			} else if (ConsumeFromWhere.CONSUME_FROM_TIMESTAMP.toString().equals(consumeFromWhere)) {
				consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
			} else {
				consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			}
		}
		if (!"0".equals(consumeThreadMin) && !"".equals(consumeThreadMin)) {
			consumer.setConsumeThreadMin(new Integer(consumeThreadMin).intValue());
		}
		if (!"0".equals(consumeThreadMax) && !"".equals(consumeThreadMax)) {
			consumer.setConsumeThreadMax(new Integer(consumeThreadMax).intValue());
		}

		String topictags = this.environment.getProperty("mq.consumer.topictags", "TopicTest1,*;orderTopic,*");//
		logger.info(" topictags:" + topictags);
		String[] dimension2 = topictags.split(";");
		topicAndTag = dimension2;
		for (int i = 0; i < dimension2.length; i++) {
			String[] dimension1 = dimension2[i].split(",");
			String topic = dimension1[0];
			String tags = dimension1[1];
			/**
			 * 订阅指定topic下tags
			 */
			logger.info(" tags：" + tags);
			logger.info("topic:" + topic + " tags:" + tags);

			try {
				consumer.subscribe(topic, tags);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
		/**
		 * 订阅指定topic下所有消息<br>
		 * 注意：一个consumer对象可以订阅多个topic
		 */
		// consumer.subscribe("TopicTest2", "*");

		this.messageListener = new RocketMQListener();
		consumer.registerMessageListener(this.messageListener);
	}

	private String getInstanceName() {
		String dubboApp = this.environment.getProperty("server.TYPE");
		String cloudApp = this.environment.getProperty("spring.application.name");

		String name = StringUtils.isBlank(dubboApp) ? cloudApp : dubboApp;

		// String instance = PropertyConfigurer.getProperty("mq.producer.instance.name", "Producer");
		String appId = name == null ? null : name.replaceAll("[^0-9a-zA-Z_]", "_");
		// PropertyConfigurer.getProperty("mq.producer.appId", "app1");
		String mxname = ManagementFactory.getRuntimeMXBean().getName();
		try {
			String address = InetAddress.getLocalHost().getHostAddress();
			return String.format("%s-%s-%s", appId, mxname, address);
		} catch (Exception ex) {
			return String.format("%s-%s", appId, mxname);
		}
	}

	/**
	 * 订阅一个主题
	 * 
	 * @param topic
	 * @param listener
	 * @throws MQClientException
	 */
	public void subscribe(String topic, final MessageListener listener) throws MQClientException {
		this.messageListener.registerMessageLisener(topic, listener);
	}

	public String[] getTopicAndTag() {
		return topicAndTag;
	}

	// Map的注入
	private HashMap<String, MessageListener> scribeList;

	public void setScribeList(HashMap<String, MessageListener> scribeList) {
		this.scribeList = scribeList;
	}

	public void initMethod() throws Exception {
		for (String key : scribeList.keySet()) {
			logger.info(key + " : ");
			MessageListener mqMessageListener = scribeList.get(key);
			subscribe(key, mqMessageListener);
		}

		/**
		 * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		 */
		consumer.start();

		logger.info("ConsumerStarted.");

		/**
		 * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
		 */
		// producer.shutdown();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				consumer.shutdown();
			}
		}));

		logger.info("执行: mqConsumer init-method");
	}

	@PreDestroy
	public void preDestroy() {
		logger.info("执行: mqConsumer preDestroy");
	}

	public void destroyMethod() {
		logger.info("执行: mqConsumer destroy-method");
		consumer.shutdown();
	}

	public void destroy() throws Exception {
		logger.info("执行: mqConsumerdestroy");
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
