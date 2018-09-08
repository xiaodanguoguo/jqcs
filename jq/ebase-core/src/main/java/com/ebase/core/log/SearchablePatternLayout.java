package com.ebase.core.log;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * log4j的PatternLayout的扩展, 用于为日志新增application, ip, requestId, userId, resource等自定义信息.
 *
 */
public class SearchablePatternLayout extends ch.qos.logback.classic.PatternLayout /* org.apache.log4j.PatternLayout */ {
	static {
		defaultConverterMap.put("app", AppConverter.class.getName());
		defaultConverterMap.put("res", ResConverter.class.getName());
		defaultConverterMap.put("ip", IPConverter.class.getName());
		defaultConverterMap.put("uid", UidConverter.class.getName());
		defaultConverterMap.put("z", ZConverter.class.getName());
		defaultConverterMap.put("rid", RidConverter.class.getName());
	}

	public static class AppConverter extends ClassicConverter {

		public String convert(ILoggingEvent event) {
			String value = MDC.get("app");
			return StringUtils.isBlank(value) ? "undefined" : value;
		}

	}

	public static class IPConverter extends ClassicConverter {

		public String convert(ILoggingEvent event) {
			String value = MDC.get("ip");
			return StringUtils.isBlank(value) ? "127.0.0.1" : value;
		}

	}

	public static class UidConverter extends ClassicConverter {

		public String convert(ILoggingEvent event) {
			String value = MDC.get("uid");
			return StringUtils.isBlank(value) ? "anonymous" : value;
		}

	}

	public static class RidConverter extends ClassicConverter {

		public String convert(ILoggingEvent event) {
			String value = MDC.get("rid");
			return StringUtils.isBlank(value) ? "0" : value;
		}

	}

	public static class ResConverter extends ClassicConverter {

		public String convert(ILoggingEvent event) {
			String value = MDC.get("res");
			return StringUtils.isBlank(value) ? "undefined" : value;
		}

	}

	public static class ZConverter extends ClassicConverter {

		public String convert(ILoggingEvent event) {
			String loggerName = event.getLoggerName();
			if (SearchableLoggerFactory.KEY_DEFAULT_LOGGER_NAME.equals(loggerName)) {
				return loggerName;
			} else if (SearchableLoggerFactory.KEY_RESOURCE_TRACE_LOGGER_NAME.equals(loggerName)) {
				return loggerName;
			} else if (SearchableLoggerFactory.KEY_EXECUTION_COST_LOGGER_NAME.equals(loggerName)) {
				return loggerName;
			} else {
				return "customize";
			}
		}

	}


}
