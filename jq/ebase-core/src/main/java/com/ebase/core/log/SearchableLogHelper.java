package com.ebase.core.log;

import com.ebase.core.log.filter.bean.MethodInvocationKey;
import com.ebase.core.props.PropertiesConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Stack;
import java.util.UUID;

/**
 * 辅助类.
 *
 */
public class SearchableLogHelper implements InitializingBean, ApplicationContextAware {
	static Logger logger = LoggerFactory.getLogger(SearchableLogHelper.class);

	public static final String KEY_PROPAGATED_USER_ID = "EnnewUid";
	public static final String KEY_PROPAGATED_REQUEST_ID = "EnnewRid";
	public static final String KEY_PROPAGATED_APP = "EnnewApp";

	public static final String MDC_KEY_USER_ID = "uid";
	public static final String MDC_KEY_APP = "app";
	public static final String MDC_KEY_IP = "ip";
	public static final String MDC_KEY_REQUEST_ID = "rid";
	public static final String MDC_KEY_RESOURCE = "res";

	private static SearchableLogHelper instance = new SearchableLogHelper();
	private static String inetAddr;

	private ApplicationContext applicationContext;
	private long timeout = 1000L;

	private ThreadLocal<Stack<MethodInvocationKey>> local = new ThreadLocal<Stack<MethodInvocationKey>>();

	private SearchableLogHelper() {
	}

	/**
	 * 为nlog设置一个超时的阈值, 若service执行时间超过该值则打印超时日志.
	 */
	public void afterPropertiesSet() throws Exception {
		try {
			String timeoutStr = PropertiesConfig.getProperty("logging.timeout", "");
			String timeoutTrim = StringUtils.trimToNull(timeoutStr);
			this.timeout = timeoutTrim == null ? this.timeout : Long.valueOf(timeoutTrim);
		} catch (Exception ex) {
			this.timeout = 1000L;
		}
	}

	/**
	 * 将当前反射调用对应的key绑定到当前线程.
	 *
	 * @param resource
	 *            当前反射调用对应的key
	 */
	public static void interceptionStarted(MethodInvocationKey resource) {
		Stack<MethodInvocationKey> stack = instance.local.get();
		if (stack == null) {
			stack = new Stack<MethodInvocationKey>();
			instance.local.set(stack);
		}
		stack.push(resource);
	}

	/**
	 * 根据反射调用对应的key判断当前调用是否需要打印跟踪日志.
	 *
	 * @param resource
	 *            当前反射调用对应的key
	 * @return 是否需要打印跟踪日志
	 */
	public static boolean isInterceptionRequired(MethodInvocationKey resource) {
		Stack<MethodInvocationKey> stack = instance.local.get();
		if (stack == null || stack.isEmpty()) {
			return true;
		}

		MethodInvocationKey element = stack.peek();
		return element != null ? element.equals(resource) : false;

	}

	/**
	 * 清除当前线程绑定的反射调用key.
	 *
	 * @param resource
	 *            当前反射调用对应的key
	 */
	public static void interceptionCompleted(MethodInvocationKey resource) {
		Stack<MethodInvocationKey> stack = instance.local.get();
		stack.pop();

		if (stack.isEmpty()) {
			instance.local.remove();
		}
	}

	public static String toJSONString(Object value) {
		String content = null;
		try {
			content = JSONObject.toJSONString(value);
		} catch (Exception rex) {
			content = String.valueOf(value);
		}
		return content;
	}

	/**
	 * 获取唯一标识.
	 *
	 * @return 唯一标识
	 */
	public static String getRandomId() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}

	/**
	 * 获取本机IP地址.
	 *
	 * @return 本机IP地址
	 */
	public static String getInetAddr() {
		if (inetAddr == null) {
			doGetInetAddr();
		}
		return StringUtils.isBlank(inetAddr) ? "127.0.0.1" : inetAddr;
	}

	private static synchronized void doGetInetAddr() {
		if (inetAddr == null) {
			try {
				InetAddress addr = InetAddress.getLocalHost();
				inetAddr = addr.getHostAddress();
			} catch (Exception ex) {
				logger.debug("获取IP地址出错!");
			}
		}
	}

	/**
	 * 获取当前应用的名称, 去dubbo:application的name属性.
	 *
	 * @return 应用名称
	 */
	public static String getApplication() {
		try {
			ApplicationConfig appConfig = getInstance().getApplicationContext().getBean(ApplicationConfig.class);
			return appConfig == null || StringUtils.isBlank(appConfig.getName()) ? "undefined" : appConfig.getName();
		} catch (Exception ex) {
			logger.debug("获取应用名称出错!");
			return "unknown";
		}
	}

	/**
	 * 为方法计算资源名称.
	 *
	 * @param method
	 *            调用方法
	 * @return 资源名称
	 */
	public static String getResourceName(Method method) {
		Class<?> clazz = method.getDeclaringClass();
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();

		StringBuilder ber = new StringBuilder();
		ber.append(clazz.getCanonicalName());
		ber.append(".").append(methodName).append("(");
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> ptype = parameterTypes[i];
			String shorthand = getShorthandName(ptype);
			ber.append(shorthand);
		}
		ber.append(")");
		return ber.toString();

	}

	public static String getResourceName(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
		try {
			return getResourceName(clazz.getDeclaredMethod(methodName, parameterTypes));
		} catch (Exception ex) {
			return String.format("%s.%s(...)", clazz.getName(), methodName);
		}
	}

	public static String getShorthandName(Class<?> clazz) {
		if (Boolean.TYPE.equals(clazz)) {
			return "Z";
		} else if (Byte.TYPE.equals(clazz)) {
			return "B";
		} else if (Short.TYPE.equals(clazz)) {
			return "S";
		} else if (Character.TYPE.equals(clazz)) {
			return "C";
		} else if (Integer.TYPE.equals(clazz)) {
			return "I";
		} else if (Float.TYPE.equals(clazz)) {
			return "F";
		} else if (Long.TYPE.equals(clazz)) {
			return "L";
		} else if (Double.TYPE.equals(clazz)) {
			return "D";
		} else if (Void.TYPE.equals(clazz)) {
			return "V";
		} else if (clazz.isArray()) {
			return clazz.getName();
		} else {
			return String.format("L%s;", clazz.getName().replaceAll("\\.", "/"));
		}
	}

	public static SearchableLogHelper getInstance() {
		return instance;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
