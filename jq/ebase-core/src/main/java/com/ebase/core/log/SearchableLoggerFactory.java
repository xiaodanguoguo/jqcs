package com.ebase.core.log;

import org.slf4j.Logger;

/**
 * 可检索日志(通过ELK收集/检索)的LoggerFactory, 应用程序使用getDefaultLogger()获取Logger.
 */
public class SearchableLoggerFactory {
	public static final String KEY_DEFAULT_LOGGER_NAME = "generic";
	public static final String KEY_RESOURCE_TRACE_LOGGER_NAME = "tracing";
	public static final String KEY_EXECUTION_COST_LOGGER_NAME = "costing";

	/**
	 * 获取记录一般可检索日志(区别于service执行轨迹日志和service执行耗时日志)的logger.
	 * 
	 * @return org.slf4j.Logger
	 */
	public static org.slf4j.Logger getDefaultLogger() {
		return org.slf4j.LoggerFactory.getLogger(KEY_DEFAULT_LOGGER_NAME);
	}

	/**
	 * 获取记录service执行轨迹的logger.
	 * 
	 * @return org.slf4j.Logger
	 */
	public static org.slf4j.Logger getResourceTraceLogger() {
		return org.slf4j.LoggerFactory.getLogger(KEY_RESOURCE_TRACE_LOGGER_NAME);
	}

	/**
	 * 获取记录service执行耗时的logger.
	 * 
	 * @return org.slf4j.Logger
	 */
	public static org.slf4j.Logger getExecutionCostLogger() {
		return org.slf4j.LoggerFactory.getLogger(KEY_EXECUTION_COST_LOGGER_NAME);
	}

	/**
	 * 获取记录一般可检索日志(区别于service执行轨迹日志和service执行耗时日志)的logger. <br />
	 * 该方法适用于框架未做拦截处理的场景.
	 * 
	 * @param reqId
	 *            请求ID
	 * @param usrId
	 *            用户ID
	 * @param app
	 *            应用名称
	 * @param res
	 *            资源名称
	 * @return org.slf4j.Logger
	 */
	public static org.slf4j.Logger getLogger(String reqId, String usrId, String app, String res) {
		Logger defaultLogger = SearchableLoggerFactory.getDefaultLogger();
		SearchableLogger logger = new SearchableLogger(defaultLogger);
		logger.setApp(app);
		logger.setRes(res);
		logger.setRid(reqId);
		logger.setUid(usrId);
		return logger;
	}

	/**
	 * 获取记录一般可检索日志(区别于service执行轨迹日志和service执行耗时日志)的logger. <br />
	 * 该方法适用于框架未做拦截处理的场景.
	 * 
	 * @param reqId
	 *            请求ID
	 * @param usrId
	 *            用户ID
	 * @param res
	 *            资源名称
	 * @return org.slf4j.Logger
	 */
	public static org.slf4j.Logger getLogger(String reqId, String usrId, String res) {
		return SearchableLoggerFactory.getLogger(reqId, usrId, SearchableLogHelper.getApplication(), res);
	}

}
