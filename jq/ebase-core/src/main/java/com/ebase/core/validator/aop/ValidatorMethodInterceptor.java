package com.ebase.core.validator.aop;

import java.lang.reflect.Method;

import javax.validation.ValidationException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorMethodInterceptor implements MethodInterceptor{
	
	private final static Logger logger =LoggerFactory.getLogger(ValidatorMethodInterceptor.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		// 获取参数
		Object[] arguments = invocation.getArguments();
		
		// 参数类型
		Class<?>[] parameterTypes = method.getParameterTypes();
		
		Class<?> clazz = invocation.getThis().getClass();
		
		logger.debug("validate:{}",method.toString());
		
		String result = ValidatorUtil.doValidate(clazz, method, parameterTypes, arguments);
		
		if(result!=null&&result.length()>0){
			throw new ValidationException(result);
		}
		
		return invocation.proceed();
	}
}
