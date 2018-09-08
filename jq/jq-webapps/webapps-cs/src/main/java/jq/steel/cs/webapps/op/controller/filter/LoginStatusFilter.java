package jq.steel.cs.webapps.op.controller.filter;

import com.ebase.core.AssertContext;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.AcctSession;
import com.ebase.core.session.User;
import com.ebase.core.session.UserSession;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.CookieUtil;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.WebUtil;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: wangyu
 */
@Component
@ServletComponentScan("jq.steel.cs.webapps.op.controller.filter")
@WebFilter(value = {"/*"},filterName = "loginStatusFilter")
public class LoginStatusFilter implements Filter {

    private final static Logger LOG =LoggerFactory.getLogger(LoginStatusFilter.class);

    //不拦截的url
    private List<String> ALLOWED_PATHS = new ArrayList<>();

    //不拦截文件
    private List<String> ALLOWED_SUFFIX = new ArrayList<>();

    @Autowired
    private AcctAPI acctAPI;

    @Value("${acct.register.url}")
    private String returnUrl;

    @Value("${allowed.paths.op}" )
    private String allowedUrl;

    @Value("${allowed.suffix}")
    private String allowedSuffix;

    @Value("${pass.url}")
    private String passUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("初始化信息 --");
        ALLOWED_PATHS = Arrays.asList(allowedUrl.split(","));
        ALLOWED_SUFFIX = Arrays.asList(allowedSuffix.split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;

        //不拦截
        String requestURL = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length()).replaceAll("[/]+$", "");

        if(allowedUrl(requestURL) || allowedSuffix(requestURL)){
            chain.doFilter(request, response);
            return ;
        }

        //验证用户是否登录
        String sessionId = CookieUtil.getSessionId(servletRequest);

        ServiceResponse<AcctSession> serviceResponse =  acctAPI.getUser(sessionId);

        if (!ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode()) || serviceResponse.getRetContent() == null) {
            //获得缓存失败或登录状态无效 调到登录页面
            LOG.info("登录状态无效!");
//            response.setContentType("text/html; charset=UTF-8");
//            servletResponse.sendRedirect(passUrl + "/project/login/html-gulp-www/login.html");
            //
            if (WebUtil.isJsonRequest(servletRequest)) {
                servletRequest.setCharacterEncoding("UTF-8");
                JsonResponse jsonResponse = new JsonResponse();
                servletResponse.setContentType("application/json; charset=UTF-8"); // 转码

                jsonResponse.setRetCode("0200000");
                servletResponse.getWriter().write(JsonUtil.toJson(jsonResponse));
                return;
            } else {
                servletRequest.setCharacterEncoding("UTF-8");
                servletResponse.setContentType("text/html; charset=UTF-8"); // 转码
                servletResponse.getWriter()
                        .println("<script language=\"javascript\">if(window.opener==null){window.top.location.href=\""
                                + returnUrl + "\";}else{window.opener.top.location.href=\"" + returnUrl
                                + "\";window.close();}</script>");
                return;
            }

        }

        LOG.info("用户登录成功!");
        AcctSession acctSession = serviceResponse.getRetContent();

        UserSession userSession = convert(acctSession);
        AssertContext.init(userSession);
//        AdminContext.init(acctSession);


        CookieUtil.setCookie(servletResponse,"sessionId",sessionId);

        chain.doFilter(request, response);
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


    @Override
    public void destroy() {
        AssertContext.destroy();
    }


    /**
     * 不拦截请求
     * @param requestURL
     * @return
     */
    private boolean allowedSuffix(String requestURL) {

        if(requestURL.contains(".")){
            String suffix = requestURL.substring(requestURL.lastIndexOf("."));

            return ALLOWED_SUFFIX.stream().anyMatch(suf -> suf.equals(suffix));
        }

        return false;
    }

    /**
     * 不拦截 url
     * @param requestURL
     * @return
     */
    private boolean allowedUrl(String requestURL) {

        boolean allowed = ALLOWED_PATHS.contains(requestURL);

        if(!allowed){
            allowed = ALLOWED_PATHS.stream().filter(x -> x.contains("*"))
                    .map(x -> x.substring(0,x.lastIndexOf("*"))).anyMatch(x -> requestURL.contains(x));
        }

        return allowed;
    }
}
