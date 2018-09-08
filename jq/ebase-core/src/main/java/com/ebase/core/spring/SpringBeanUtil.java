package com.ebase.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @project 	core
 * @class 		SpringBeanUtil
 * @description <p>通过common.spring.xml初始化, 在业务代码中尽量不要用这个类获取SpringBean</p> 
 *
 */
public class SpringBeanUtil implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}
	
	/**
	 * getBean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}
	
	/**
	 * getBean
	 * @param beanId
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String beanId,Class<T> clazz){
		return context.getBean(beanId,clazz);
	}
	
	/**
	 * containsBean
	 * @param beanId
	 * @return
	 */
	public static boolean containsBean(String beanId){
		return context.containsBean(beanId);
	}
	
	
	
}
