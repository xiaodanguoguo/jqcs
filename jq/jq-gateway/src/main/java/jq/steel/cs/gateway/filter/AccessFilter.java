package jq.steel.cs.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 6;
	}

	public boolean shouldFilter() {
		return true;
	}

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
//		for (Iterator<String> it = ctx.keySet().iterator(); it.hasNext();) {
//			String key = it.next();
//			logger.info(key + "------------------" + ctx.get(key));
//		}

//		String httpMethod = "GET";
		try {
//			HttpServletRequest requestWrapper = null;
//			if (request instanceof HttpServletRequest) {
//				httpMethod = ((HttpServletRequest) request).getMethod();
//				requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
//			}

			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			String parameter = request.getParameter("param");
			paramMap.put("testParam", Arrays.asList(parameter));
//			if ("POST".equalsIgnoreCase(httpMethod)) {
				

//				requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request, "requestBody");
				ctx.setRequestQueryParams(paramMap);
//				String requestURI = "/" + methodName;
				ctx.set("serviceId", "gateway");
//				ctx.setRequest(requestWrapper);
				ctx.set("requestURI", "/test");
				System.out.println("zuul=============execute!!!");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("webapps 转发出错！", e);
		}

		return null;

	}

	// 获取request请求body中参数
	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			logger.error("IOException: " + e);
		}
		return str;
	}
	/**
	 * 转换成InterfaceInvokeHis对象
	 * @param interfaceCode
	 * @param interfaceVersion
	 * @param systemId
	 * @param requestId
	 * @param beginTime
	 * @return
	 */
//	private InterfaceInvokeHis getMsgBody(String interfaceCode,String interfaceVersion,
//            String systemId,String requestId,Long beginTime) {
//			InterfaceInvokeHis his = new InterfaceInvokeHis();
//			his.setRequest_id(requestId);
//			his.setExecutime_begin(beginTime);
//			his.setRequest_dt(new Date());
//			his.setInterface_code(interfaceCode);
//			his.setInterface_version(interfaceVersion);
//			his.setSystem_code(systemId);
//			return his;
//    }
}