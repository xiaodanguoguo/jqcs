package com.ebase.core.log.filter;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.ebase.core.log.SearchableLogHelper;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.log.annotation.ServiceTraceable;
import com.ebase.core.log.filter.bean.MethodInvocationKey;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * 用于为标注了@ServiceTraceable注解的service打印其执行记录.
 *
 */
public class ServiceTraceableFilter implements MethodInterceptor {
	static Logger costingLogger = SearchableLoggerFactory.getExecutionCostLogger();
	static Logger tracingLogger = SearchableLoggerFactory.getResourceTraceLogger();

	public Object invoke(MethodInvocation invocation) throws Throwable {

		String resource = SearchableLogHelper.getResourceName(invocation.getMethod());

		// 根据当前执行方法和参数, 构建本次调用的key
		MethodInvocationKey key = new MethodInvocationKey();
		key.setResource(resource);
		key.setArgs(invocation.getArguments());

		// 根据当前调用key, 判断本次是否需要打印执行记录
		boolean flag = SearchableLogHelper.isInterceptionRequired(key);
		if (flag == false) {
			return invocation.proceed();
		}

		try {
			// 执行之前设置当前的key
			SearchableLogHelper.interceptionStarted(key);
			return this.execute(invocation, resource);
		} finally {
			// 执行完毕清除当前的key
			SearchableLogHelper.interceptionCompleted(key);
		}

	}

	/**
	 * 拦截@ServiceTraceable注解的跟踪日志处理逻辑.
	 * 
	 * @param invocation
	 *            MethodInvocation
	 * @param resource
	 *            根据当前执行方法生成的资源名称
	 * @return 执行结果
	 * @throws Throwable
	 *             执行异常
	 */
	public Object execute(MethodInvocation invocation, String resource) throws Throwable {
		Method method = invocation.getMethod();
		Class<?> clazz = method.getDeclaringClass();

		ServiceTraceable clsAnnotation = clazz.getAnnotation(ServiceTraceable.class);
		ServiceTraceable mthAnnotation = method.getAnnotation(ServiceTraceable.class);

		// 根据类注解和方法注解的定义, 计算最终生效的注解
		ServiceTraceable annotation = mthAnnotation == null ? clsAnnotation : mthAnnotation;

		// 若注解被禁用, 则直接执行即可
		if (annotation == null || annotation.enable() == false) {
			return invocation.proceed();
		}

		// 获取先前在MDC中设置过的app, ip, rid
		String app = MDC.get(SearchableLogHelper.MDC_KEY_APP);
		String ip = MDC.get(SearchableLogHelper.MDC_KEY_IP);
		String rid = MDC.get(SearchableLogHelper.MDC_KEY_REQUEST_ID);
		String res = MDC.get(SearchableLogHelper.MDC_KEY_RESOURCE);

		long start = System.currentTimeMillis();
		try {
			// 若之前没有设置过应用名称, 则设置应用名称
			if (app == null) {
				MDC.put(SearchableLogHelper.MDC_KEY_APP, SearchableLogHelper.getApplication());
			}
			// 若之前没有设置过ip, 则设置ip
			if (ip == null) {
				MDC.put(SearchableLogHelper.MDC_KEY_IP, SearchableLogHelper.getInetAddr());
			}
			// 若之前没有设置过请求ID, 则设置请求ID
			if (rid == null) {
				MDC.put(SearchableLogHelper.MDC_KEY_REQUEST_ID, SearchableLogHelper.getRandomId());
			}
			// 在MDC中设置当前调用的资源名称
			MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, resource);

			// 打印service执行开始跟踪日志
			tracingLogger.info("started, args= {}", Arrays.toString(invocation.getArguments()));

			// 反射调用service
			Object value = invocation.proceed();

			// 打印service执行完成跟踪日志
			tracingLogger.info("completed, cost= {}ms, result= {}", System.currentTimeMillis() - start,
					SearchableLogHelper.toJSONString(value));

			return value;
		} catch (Throwable rex) {
			// 打印service执行完成跟踪日志
			tracingLogger.error("completed, cost= {}ms", System.currentTimeMillis() - start, rex);
			throw rex;
		} finally {
			// 若执行超时, 则打印超时日志
			long cost = System.currentTimeMillis() - start;
			if (cost > SearchableLogHelper.getInstance().getTimeout()) {
				costingLogger.warn("timeout, type= service, cost= {}ms", cost);
			}

			// 若之前没有设置过资源名称, 则删除当前资源名称; 若之前设置过, 则恢复之前的
			if (res == null) {
				MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
			} else {
				MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, res);
			}
			// 若之前没有设置过请求ID, 则删除当前设置的请求ID
			if (rid == null) {
				MDC.remove(SearchableLogHelper.MDC_KEY_REQUEST_ID);
			}
			// 若之前没有设置过ip, 则删除当前设置的ip
			if (ip == null) {
				MDC.remove(SearchableLogHelper.MDC_KEY_IP);
			}
			// 若之前没有设置过应用名称, 则删除当前设置的应用名称
			if (app == null) {
				MDC.remove(SearchableLogHelper.MDC_KEY_APP);
			}
		}

	}

}
