package com.ebase.core.log.filter.bean;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 由于@ServiceTraceable注解的调用可能与自定义拦截的执行存在交集, 为避免重复打印执行记录, 因此设置一个key用于标识一次反射调用.
 */
public class MethodInvocationKey {

	/**
	 * 根据Method生成的资源名称.
	 */
	private String resource;
	/**
	 * 反射调用使用的参数.
	 */
	private Object[] args;

	public int hashCode() {
		int hash = 13;
		hash += 17 * (this.resource == null ? 0 : this.resource.hashCode());
		hash += 19 * (this.args == null ? 0 : Arrays.hashCode(this.args));
		return hash;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (MethodInvocationKey.class.isInstance(obj) == false) {
			return false;
		}
		MethodInvocationKey that = (MethodInvocationKey) obj;
		boolean resourceEquals = StringUtils.equals(this.resource, that.resource);
		boolean argsEquals = Arrays.equals(this.args, that.args);
		return resourceEquals && argsEquals;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
