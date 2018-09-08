package com.ebase.core.log;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

/**
 * 用于打印可检索日志(通过ELK收集/检索)的Logger.<br />
 * 适用于未做拦截处理的情况. 该情况下框架未设置相应的app, reqId, usrId, resource, 需要应用传入这些参数 .<br />
 * 这种情况下需要应用程序通过SearchableLoggerFactory.getLogger(...)来获取Logger.
 */
public class SearchableLogger implements Logger {

	private final Logger logger;

	private String app;
	private String res;
	private String rid;
	private String uid;

	public SearchableLogger(Logger logger) {
		this.logger = logger;
	}

	private void registerVariables() {
		// 在MDC中设置userId, application, ip, requestId, resource等信息
		MDC.put(SearchableLogHelper.MDC_KEY_REQUEST_ID, this.rid);
		MDC.put(SearchableLogHelper.MDC_KEY_USER_ID, this.uid);
		MDC.put(SearchableLogHelper.MDC_KEY_APP, this.app);
		MDC.put(SearchableLogHelper.MDC_KEY_IP,
				SearchableLogHelper.getInetAddr());
		MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, this.res);
	}

	private void unRegisterVariables() {
		// 清空MDC中设置的userId, application, ip, requestId, resource等信息
		MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
		MDC.remove(SearchableLogHelper.MDC_KEY_IP);
		MDC.remove(SearchableLogHelper.MDC_KEY_APP);
		MDC.remove(SearchableLogHelper.MDC_KEY_USER_ID);
		MDC.remove(SearchableLogHelper.MDC_KEY_REQUEST_ID);
	}

	public String getName() {
		return logger.getName();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(String msg) {
		try {
			this.registerVariables();
			logger.trace(msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(String format, Object arg) {
		try {
			this.registerVariables();
			logger.trace(format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.trace(format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.trace(format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.trace(msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isTraceEnabled(Marker marker) {
		return logger.isTraceEnabled(marker);
	}

	public void trace(Marker marker, String msg) {
		try {
			this.registerVariables();
			logger.trace(marker, msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(Marker marker, String format, Object arg) {
		try {
			this.registerVariables();
			logger.trace(marker, format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.trace(marker, format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(Marker marker, String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.trace(marker, format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void trace(Marker marker, String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.trace(marker, msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String msg) {
		try {
			this.registerVariables();
			logger.debug(msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(String format, Object arg) {
		try {
			this.registerVariables();
			logger.debug(format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.debug(format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.debug(format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.debug(msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isDebugEnabled(Marker marker) {
		return logger.isDebugEnabled(marker);
	}

	public void debug(Marker marker, String msg) {
		try {
			this.registerVariables();
			logger.debug(marker, msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(Marker marker, String format, Object arg) {
		try {
			this.registerVariables();
			logger.debug(marker, format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.debug(marker, format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(Marker marker, String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.debug(marker, format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void debug(Marker marker, String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.debug(marker, msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(String msg) {
		try {
			this.registerVariables();
			logger.info(msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(String format, Object arg) {
		try {
			this.registerVariables();
			logger.info(format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.info(format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.info(format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.info(msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isInfoEnabled(Marker marker) {
		return logger.isInfoEnabled(marker);
	}

	public void info(Marker marker, String msg) {
		try {
			this.registerVariables();
			logger.info(marker, msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(Marker marker, String format, Object arg) {
		try {
			this.registerVariables();
			logger.info(marker, format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(Marker marker, String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.info(marker, format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(Marker marker, String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.info(marker, format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void info(Marker marker, String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.info(marker, msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void warn(String msg) {
		try {
			this.registerVariables();
			logger.warn(msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(String format, Object arg) {
		try {
			this.registerVariables();
			logger.warn(format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.warn(format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.warn(format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.warn(msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isWarnEnabled(Marker marker) {
		return logger.isWarnEnabled(marker);
	}

	public void warn(Marker marker, String msg) {
		try {
			this.registerVariables();
			logger.warn(marker, msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(Marker marker, String format, Object arg) {
		try {
			this.registerVariables();
			logger.warn(marker, format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.warn(marker, format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(Marker marker, String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.warn(marker, format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void warn(Marker marker, String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.warn(marker, msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public void error(String msg) {
		try {
			this.registerVariables();
			logger.error(msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(String format, Object arg) {
		try {
			this.registerVariables();
			logger.error(format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.error(format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.error(format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.error(msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public boolean isErrorEnabled(Marker marker) {
		return logger.isErrorEnabled(marker);
	}

	public void error(Marker marker, String msg) {
		try {
			this.registerVariables();
			logger.error(marker, msg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(Marker marker, String format, Object arg) {
		try {
			this.registerVariables();
			logger.error(marker, format, arg);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(Marker marker, String format, Object arg1, Object arg2) {
		try {
			this.registerVariables();
			logger.error(marker, format, arg1, arg2);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(Marker marker, String format, Object... argArray) {
		try {
			this.registerVariables();
			logger.error(marker, format, argArray);
		} finally {
			this.unRegisterVariables();
		}
	}

	public void error(Marker marker, String msg, Throwable t) {
		try {
			this.registerVariables();
			logger.error(marker, msg, t);
		} finally {
			this.unRegisterVariables();
		}
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
