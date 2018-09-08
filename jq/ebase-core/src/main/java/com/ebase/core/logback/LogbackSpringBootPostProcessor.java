package com.ebase.core.logback;

import com.ebase.core.log.SearchableLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.ebase.utils.StringUtil;

public class LogbackSpringBootPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {
	static Logger logger = SearchableLoggerFactory.getDefaultLogger();

	private Environment environment;

	private String location;
	private String serverType;
	private String serverPort;

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (StringUtils.isBlank(location)) {
			String profile = this.environment.getProperty("spring.profiles.active");
			this.location = String.format("classpath:props/log/%s/logback.xml", profile);
		}
		
		if(StringUtil.isEmpty(serverType)){
			serverType = environment.getProperty("server.TYPE");
		}
		if(StringUtil.isNotEmpty(serverType)){
			serverType = serverType.replaceAll("[^0-9a-zA-Z_]", "_");
		}
		this.serverPort = this.environment.getProperty("server.port");
		// String application = this.environment.getProperty("spring.application.name");
		// this.serverType = StringUtils.isNotBlank(this.serverType) ? //
		// this.serverType : application == null ? null : application.replaceAll("[^0-9a-zA-Z_]", "_");
		// this.serverPort = StringUtils.isNotBlank(this.serverPort) ? //
		// this.serverPort : this.environment.getProperty("server.port");

		this.initLogbackConfig(this.location);
	}

	private synchronized void initLogbackConfig(String location) {
		try {
			System.setProperty("server.TYPE", this.serverType);
			System.setProperty("server.PORT", this.serverPort);

//			ch.qos.logback.ext.spring.LogbackConfigurer.initLogging(location);
			logger.info("initLogging serverType: {} path: {}", this.serverType, location);
		} catch (Exception ex) {
			logger.error("initLogging failed!", ex);
		} finally {
			System.getProperties().remove("server.TYPE");
			System.getProperties().remove("server.PORT");
		}
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

}