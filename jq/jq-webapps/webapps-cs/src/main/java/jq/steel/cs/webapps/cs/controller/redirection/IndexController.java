package jq.steel.cs.webapps.cs.controller.redirection;

import com.ebase.core.log.SearchableLoggerFactory;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: RedirectionController
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/27 18:32
 */
@Controller
public class IndexController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AcctAPI acctAPI;

    @Value("${acct.register.url}")
    private String url;

    @RequestMapping({"/", "/index", ""})
    public String redirection(HttpServletRequest request, HttpServletResponse response) {

        //验证用户是否登录..
//        String sessionId = CookieUtil.getSessionId(request);
//
//        ServiceResponse<AcctSession> serviceResponse =  acctAPI.getUser(sessionId);
//
//        if (!ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode()) || serviceResponse.getRetContent() == null) {
//            //获得缓存失败或登录状态无效 调到登录页面
//            logger.info("登录状态无效!");
            return "login";


//        } else {
//            return url + "/project/homePage/html-gulp-www/homePage";
//        }
    }
}
