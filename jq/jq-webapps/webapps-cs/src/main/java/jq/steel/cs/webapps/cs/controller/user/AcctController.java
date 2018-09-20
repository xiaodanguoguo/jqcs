package jq.steel.cs.webapps.cs.controller.user;

import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.Acct;
import com.ebase.core.session.AcctLogin;
import com.ebase.core.session.AcctSession;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.CookieUtil;
import com.ebase.utils.WebUtil;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台用户
 * @Auther: wangyu
 */
@RestController
@RequestMapping("/acct")
public class AcctController {


    private final static Logger LOG = SearchableLoggerFactory.getDefaultLogger();


    @Autowired
    private AcctAPI backMemberAPI;

    /**
     * 用户注册
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/register")
    private JsonResponse<String> userRegister(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<String> jsonResponse = new JsonResponse<>();

        try{
            AcctInfoVO acctInfoVO = jsonRequest.getReqBody();

            ServiceResponse<String> response =  backMemberAPI.userRegister(acctInfoVO);

            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                jsonResponse.setRetCode(JsonResponse.SUCCESS);
                jsonResponse.setRetDesc(response.getRetContent());
            }else {
                if (response.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(response.getRetCode());
                    jsonResponse.setRetDesc(response.getRetMessage());
                }
            }

        }catch (Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public JsonResponse<AcctSession> userLogin(@RequestBody JsonRequest<AcctLogin> jsonRequest, HttpServletRequest request ,
                                               HttpServletResponse response){
        JsonResponse<AcctSession> jsonResponse = new JsonResponse<>();
        try{
            AcctLogin acctLogin = jsonRequest.getReqBody();
            acctLogin.setSessionId(request.getSession().getId());
            acctLogin.setClientType(WebUtil.getClientType(request));

            ServiceResponse<AcctSession> serviceResponse = backMemberAPI.userLogin(acctLogin);

            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                LOG.error("登陆成功 !!");
                jsonResponse.setRspBody(serviceResponse.getRetContent());

                String sessionId = serviceResponse.getRetContent().getSessionId();
                CookieUtil.setCookie(response,"sessionId",sessionId);
                CookieUtil.setCookie(response,"userName", serviceResponse.getRetContent().getAcct().getName());
                CookieUtil.setCookie(response,"orgId", serviceResponse.getRetContent().getAcct().getOrgId());
                CookieUtil.setCookie(response,"acctId", serviceResponse.getRetContent().getAcct().getAcctId().toString());
            }else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }

        }catch (Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }



//    @RequestMapping(value="/login",method= RequestMethod.GET)
//    public ModelAndView login(HttpServletRequest request, String username, String password, Model model){
//        ModelAndView modelAndView = new ModelAndView();
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token=new UsernamePasswordToken(username,MD5Util.encrypByMd5(password));
//
//        try {
//            subject.login(token);
//            modelAndView.setViewName("test");
//        }catch (LockedAccountException lae) {
//            token.clear();
//            modelAndView.setViewName("error");
//            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
//        } catch (AuthenticationException e) {
//            token.clear();
//            modelAndView.setViewName("error");
//            request.setAttribute("msg", "用户或密码不正确！");
//        }
//        return modelAndView;
//
//    }

    /**
     * 获得用户信息
     * @return
     */
    @RequestMapping("/getAcct")
    public JsonResponse<Acct> getUser(HttpServletRequest request ){
        JsonResponse<Acct> jsonResponse = new JsonResponse<Acct>();

        try{
            String sessionId = CookieUtil.getSessionId(request);

            ServiceResponse<AcctSession> serviceResponse = backMemberAPI.getUser(sessionId);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent().getAcct());
            }else{
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        }catch (Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     * 用户注销
     * @return
     */
    @RequestMapping("/remSession")
    public JsonResponse<Boolean> remSession(HttpServletRequest request , HttpServletResponse response){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<Boolean>();
        try{
            String sessionId = CookieUtil.getSessionId(request);

            ServiceResponse<Boolean> serviceResponse = backMemberAPI.delUser(sessionId);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                LOG.error("注销成功 !!");
                jsonResponse.setRspBody(true);

            }else {

            }
            CookieUtil.removeCookie(response,"sessionId");
            CookieUtil.removeCookie(response,"userName");
            CookieUtil.removeCookie(response,"orgId");
            CookieUtil.removeCookie(response,"acctId");
        }catch (Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


}
