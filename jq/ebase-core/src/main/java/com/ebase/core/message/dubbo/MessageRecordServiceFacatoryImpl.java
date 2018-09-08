package com.ebase.core.message.dubbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebase.core.message.service.MessageRecordService;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import com.ebase.core.message.service.MessageRecordServiceFacatory;

public class MessageRecordServiceFacatoryImpl
		implements ApplicationContextAware, BeanFactoryPostProcessor, MessageRecordServiceFacatory {

	private ApplicationContext applicationContext;
	private Map<String, ReferenceConfig<MessageRecordService>> facadeMap = new HashMap<String, ReferenceConfig<MessageRecordService>>();

	private transient String nativeApplication;
	private transient MessageRecordService nativeService;

	public void init() {
		String[] beanNameArray = this.applicationContext.getBeanNamesForType(ApplicationConfig.class);
		if (beanNameArray != null && beanNameArray.length == 1) {
			String beanName = beanNameArray[0];
			ApplicationConfig appConfig = (ApplicationConfig) this.applicationContext.getBean(beanName);
			this.nativeApplication = appConfig.getName();

			String[] servNameArray = this.applicationContext.getBeanNamesForType(MessageRecordService.class);
			if (servNameArray != null && servNameArray.length == 1) {
				String servName = servNameArray[0];
				this.nativeService = (MessageRecordService) this.applicationContext.getBean(servName);
			}
		}
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] appBeanNameArray = beanFactory.getBeanNamesForType(ApplicationConfig.class);
		if (appBeanNameArray == null || appBeanNameArray.length != 1) {
			throw new FatalBeanException("没有指定dubbo应用名称, 或存在多于一个的应用名称!");
		}
		
		String[] registryBeanNameArray = beanFactory.getBeanNamesForType(RegistryConfig.class);
		if (registryBeanNameArray == null || appBeanNameArray.length < 1) {
			throw new FatalBeanException("没有指定dubbo的注册中心!");
		}
	}

	public MessageRecordService getMessageRecordService(String application) {

		if (this.nativeApplication != null && this.nativeApplication.equals(application)
				&& this.nativeService != null) {
			return this.nativeService;
		}

		ReferenceConfig<MessageRecordService> referenceConfig = this.facadeMap.get(application);
		if (referenceConfig == null) {
			synchronized (this) {
				referenceConfig = this.initMessageQueueFacade(application);
				this.facadeMap.put(application, referenceConfig);
			}
		}
		return referenceConfig == null ? null : referenceConfig.get();
	}

	protected synchronized ReferenceConfig<MessageRecordService> initMessageQueueFacade(String application) {
		ReferenceConfig<MessageRecordService> referenceConfig = this.facadeMap.get(application);
		if (referenceConfig == null) {
			ApplicationConfig appConfig = this.applicationContext.getBean(ApplicationConfig.class);

			referenceConfig = new ReferenceConfig<MessageRecordService>();
			referenceConfig.setApplication(appConfig);
			referenceConfig.setCheck(false);
			referenceConfig.setInterface(MessageRecordService.class);
			referenceConfig.setGroup(application);
			referenceConfig.setRetries(0);
			referenceConfig.setTimeout(60000);

			String[] beanNameArray = this.applicationContext.getBeanNamesForType(RegistryConfig.class);
			if (beanNameArray.length == 1) {
				RegistryConfig registryConfig = this.applicationContext.getBean(RegistryConfig.class);
				referenceConfig.setRegistry(registryConfig);
			} else {
				List<RegistryConfig> registries = new ArrayList<RegistryConfig>();
				for (int i = 0; i < beanNameArray.length; i++) {
					String beanName = beanNameArray[i];
					RegistryConfig registryConfig = (RegistryConfig) this.applicationContext.getBean(beanName);
					registries.add(registryConfig);
				}
				referenceConfig.setRegistries(registries);
			}

		}
		return referenceConfig;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
