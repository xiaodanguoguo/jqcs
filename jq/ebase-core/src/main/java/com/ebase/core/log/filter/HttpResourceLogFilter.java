package com.ebase.core.log.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ebase.core.log.SearchableLogHelper;
import com.ebase.core.log.SearchableLoggerFactory;

/**
 * 用于为web资源打印执行记录.
 *
 */
public class HttpResourceLogFilter implements Filter {
	static final String CONSTANT_USER_ID = "userId";
	static Logger logger = LoggerFactory.getLogger(HttpResourceLogFilter.class);

	static Logger costingLogger = SearchableLoggerFactory.getExecutionCostLogger();
	static Logger tracingLogger = SearchableLoggerFactory.getResourceTraceLogger();

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		this.invokeFilter(request, response, filterChain);
	}

	public void invokeFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 获取传播过来的请求ID
		String propagatedRid = StringUtils.trimToNull(request.getHeader(SearchableLogHelper.KEY_PROPAGATED_REQUEST_ID));
		// 若传播的请求ID为空, 则新建的一个请求ID
		String requestId = StringUtils.isNotBlank(propagatedRid) ? propagatedRid : SearchableLogHelper.getRandomId();

		// 若传播的请求ID为空, 则将新建的请求ID设置在响应头部中
		if (StringUtils.isBlank(propagatedRid)) {
			response.addHeader(SearchableLogHelper.KEY_PROPAGATED_REQUEST_ID, requestId);
		}

		// 获取传播过来的UserId
		String propagatedUid = StringUtils.trimToNull(request.getHeader(SearchableLogHelper.KEY_PROPAGATED_USER_ID));
		// 若传播的UserId为空, 则获取当前的UserId
		String userId = StringUtils.isNotBlank(propagatedUid) ? propagatedUid : this.getUserId(request);

		// 标识是否设置MDC信息时出错
		boolean filterFailure = true;
		long start = System.currentTimeMillis();
		try {
			// 在MDC中设置userId, 应用名称, ip, 请求ID, 资源名称
			MDC.put(SearchableLogHelper.MDC_KEY_USER_ID, userId);
			MDC.put(SearchableLogHelper.MDC_KEY_APP, SearchableLogHelper.getApplication());
			MDC.put(SearchableLogHelper.MDC_KEY_IP, SearchableLogHelper.getInetAddr());
			MDC.put(SearchableLogHelper.MDC_KEY_REQUEST_ID, requestId);
			MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, request.getRequestURI());
			filterFailure = false;

			// 打印开始跟踪日志
			tracingLogger.info("started, args= {}", request.getQueryString());

			chain.doFilter(request, response);
		} catch (RuntimeException rex) {
			// 若错误发生在设置MDC信息阶段, 则打印错误日志提示后继续处理页面逻辑(当前页面不在MDC中设置值).
			// 若错误发生在doFilter阶段, 则说明错误为web资源自身异常, 该异常直接抛出.
			if (filterFailure) {
				logger.error("Nlog拦截器处理出错!", rex);
				chain.doFilter(request, response);
			} else {
				throw rex;
			}
		} finally {
			// 打印完成跟踪日志
			tracingLogger.info("completed, cost= {}ms", System.currentTimeMillis() - start);

			// 若超时, 则打印超时日志
			long cost = System.currentTimeMillis() - start;
			if (cost > SearchableLogHelper.getInstance().getTimeout()) {
				costingLogger.warn("timeout, type= http, cost= {}ms", cost);
			}

			// 在MDC中清除本次设置的userId, 应用名称, ip, 请求ID, 资源名称
			MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
			MDC.remove(SearchableLogHelper.MDC_KEY_REQUEST_ID);
			MDC.remove(SearchableLogHelper.MDC_KEY_IP);
			MDC.remove(SearchableLogHelper.MDC_KEY_APP);
			MDC.remove(SearchableLogHelper.MDC_KEY_USER_ID);
		}

	}

	/**
	 * 获取当前登录的用户ID, 若没有登录, 则返回anonymous.
	 *
	 * @param request
	 *            请求
	 * @return 用户ID
	 */
	private String getUserId(HttpServletRequest request) {
		try {
			org.apache.shiro.subject.Subject subject = org.apache.shiro.SecurityUtils.getSubject();
			org.apache.shiro.session.Session session = subject.getSession(false);
			Long userId = (Long) session.getAttribute(CONSTANT_USER_ID);
			return userId == null ? "anonymous" : String.valueOf(userId);
		} catch (Exception ex) {
			logger.debug("获取userId失败!", ex); // 若获取userId失败, 则使用anonymous
			return "anonymous";
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

}
