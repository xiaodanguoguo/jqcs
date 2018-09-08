package com.ebase.core.message.dubbo;

import com.ebase.core.message.service.MessageRecordService;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.alibaba.dubbo.config.ApplicationConfig;

public class MessageBeanFactoryPostProcessorImpl implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

		String appName = null;
		String[] appBeanNameArray = beanFactory.getBeanNamesForType(ApplicationConfig.class);
		if (appBeanNameArray != null && appBeanNameArray.length == 1) {
			String beanName = appBeanNameArray[0];
			BeanDefinition beanDef = registry.getBeanDefinition(beanName);
			MutablePropertyValues values = beanDef.getPropertyValues();
			String propertyName = "name";
			PropertyValue pv = values.getPropertyValue(propertyName);
			appName = pv == null ? null : (String) pv.getValue();
		} else {
			throw new FatalBeanException("没有指定dubbo应用名称, 或存在多于一个的应用名称!");
		}

		String[] svcBeanNameArray = beanFactory.getBeanNamesForType(MessageRecordService.class);
		if (svcBeanNameArray != null && svcBeanNameArray.length == 1) {
			String beanName = svcBeanNameArray[0];

			GenericBeanDefinition beanDef = new GenericBeanDefinition();
			beanDef.setBeanClass(com.alibaba.dubbo.config.spring.ServiceBean.class);
			MutablePropertyValues values = beanDef.getPropertyValues();
			values.addPropertyValue("group", appName);
			values.addPropertyValue("interface", MessageRecordService.class.getName());
			values.addPropertyValue("ref", new RuntimeBeanReference(beanName));
			values.addPropertyValue("retries", "0");
			values.addPropertyValue("timeout", "60000");
			registry.registerBeanDefinition(String.format("_%s_%s_", appName, beanName), beanDef);
		} else {
			throw new FatalBeanException("没有可用的(或存在多余一个的)MessageRecordService!");
		}

	}

}
