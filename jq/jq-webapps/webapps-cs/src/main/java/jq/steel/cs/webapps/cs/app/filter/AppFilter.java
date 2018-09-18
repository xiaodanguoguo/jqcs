package jq.steel.cs.webapps.cs.app.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebase.core.AssertContext;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.AcctSession;
import com.ebase.core.session.User;
import com.ebase.core.session.UserSession;
import com.ebase.core.web.json.JsonHeader;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.WebUtil;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@Component
@ServletComponentScan("jq.steel.cs.webapps.cs.app.filter")
@WebFilter(value = {"/app/*"}, filterName = "appFilter")
public class AppFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(AppFilter.class);

    @Autowired
    private AcctAPI acctAPI;

    private static JsonResponse<String> jsonResponse = new JsonResponse<>("0401000", "登录超时", "");

    // 排除的路径
    private String[] EXCLUDE_URL;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("--------------------------参数初始化" + filterConfig);
        String[] str = new String[]{"/app/login","/app/logout","/app/register","/app/version/getNewVersion"};
        EXCLUDE_URL = str;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        HttpServletRequest request = (HttpServletRequest) req;
        String urlPath = WebUtil.getPath(httpRequest);

        if (EXCLUDE_URL != null && EXCLUDE_URL.length > 0) {
            for (String str : EXCLUDE_URL) {
                if (WebUtil.matchPath(str, urlPath)) {
                    filterChain.doFilter(req, res);
                    return;
                }
            }
        }

        boolean valid = false;
        BodyReaderHttpServletRequestWrapper wrapper = new BodyReaderHttpServletRequestWrapper(request);
        if(request.getHeader("Content-type") != null && request.getHeader("Content-type").startsWith("multipart/form-data")){
            valid = true;
        }else {
            wrapper = new BodyReaderHttpServletRequestWrapper(request, (HttpServletResponse) res);

            if (StringUtils.isNotBlank(wrapper.getReqBody())) {
                JsonRequest jsonRequest = null;
                try {
                    jsonRequest = JSON.parseObject(wrapper.getReqBody(), JsonRequest.class);
                } catch (Exception e) {
                    logger.error("parseObject Exception", e);
                }
                if (null != jsonRequest) {
                    JsonHeader header = jsonRequest.getReqHeader();

                    // 取得token
                    String sId = header.getSid();

                    // 非空sid
                    if (!StringUtils.isBlank(sId)) {
                        //
                        ServiceResponse<AcctSession> serviceResponse = acctAPI.getUser(sId);

                        if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode()) && serviceResponse.getRetContent() != null) {
                            valid = true;

                            AcctSession acctSession = serviceResponse.getRetContent();

                            UserSession userSession = convert(acctSession);
                            AssertContext.init(userSession);
                        }
                    }
                }
            }
        }

        String method = ((HttpServletRequest) req).getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            valid = true;
        }

        if (!valid) {
            String resultMsg = JSONObject.toJSONString(jsonResponse);
            HttpServletResponse response = (HttpServletResponse) res;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(resultMsg);
            return;
        } else {
            filterChain.doFilter(wrapper, res);
        }
    }

    @Override
    public void destroy() {
        System.err.println("------------ appFilter AssertContext 开始销毁-------------");
        AssertContext.destroy();
    }

    /**
     * 获取请求Body
     *
     * @param request ServletRequest
     * @return String
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private UserSession convert(AcctSession acctSession) {
        UserSession userSession = new UserSession();
        userSession.setSessionId(acctSession.getSessionId());
        User user = new User();
        user.setAcctId(acctSession.getAcct().getAcctId().toString());
        user.setAcctName(acctSession.getAcct().getAcctTitle());
        user.setOrgId(acctSession.getAcct().getoInfoId());
        user.setOrgName(acctSession.getAcct().getoInfoName());
        user.setCompanyId(acctSession.getAcct().getCompanyId());
        user.setOrgCode(acctSession.getAcct().getOrgCode());
        user.setOrgType(acctSession.getAcct().getOrgType());
        userSession.setUser(user);
        return userSession;
    }
}
