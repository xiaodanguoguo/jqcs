package com.ebase.core.logback;

import com.ebase.core.log.SearchableLoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;

public class LogbackDubboConfigPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {
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

		String[] beanNameArray = beanFactory.getBeanDefinitionNames();
		for (int i = 0; i < beanNameArray.length; i++) {
			String beanName = beanNameArray[i];
			BeanDefinition beanDef = beanFactory.getBeanDefinition(beanName);
			String beanClassName = beanDef.getBeanClassName();
			if (ApplicationConfig.class.getName().equals(beanClassName)) {
				String name = (String) beanDef.getPropertyValues().get("name");
				this.serverType = name == null ? null : name.replaceAll("[^0-9a-zA-Z_]", "_");
			} else if (ProtocolConfig.class.getName().equals(beanClassName)) {
				this.serverPort = (String) beanDef.getPropertyValues().get("port");
			}
		}

		// String application = PropertyConfigurer.getProperty("server.TYPE", "");
		// this.serverType = StringUtils.isNotBlank(this.serverType) ? //
		// this.serverType : application == null ? null : application.replaceAll("[^0-9a-zA-Z_]", "_");
		// this.serverPort = StringUtils.isNotBlank(this.serverPort) ? //
		// this.serverPort : PropertyConfigurer.getProperty("server.PORT", "");

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