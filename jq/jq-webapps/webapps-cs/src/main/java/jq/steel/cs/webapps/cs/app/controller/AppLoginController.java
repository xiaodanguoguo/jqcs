package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.AcctLogin;
import com.ebase.core.session.AcctSession;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.WebUtil;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.webapps.cs.app.vo.AppAcct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: app端接口
 */
@RestController
@RequestMapping("/app")
public class AppLoginController {
    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AcctAPI backMemberAPI;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public JsonResponse<AppAcct> userLogin(@RequestBody JsonRequest<AcctLogin> jsonRequest, HttpServletRequest request ,
                                           HttpServletResponse response){
        JsonResponse<AppAcct> jsonResponse = new JsonResponse<>();
        try{
            AcctLogin acctLogin = jsonRequest.getReqBody();
            acctLogin.setSessionId(request.getSession().getId());
            acctLogin.setClientType(WebUtil.getClientType(request));

            ServiceResponse<AcctSession> serviceResponse = backMemberAPI.userLogin(acctLogin);

            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                AcctSession acctSession = serviceResponse.getRetContent();

                AppAcct appAcct = new AppAcct();
                appAcct.setSessionId(acctSession.getSessionId());
                appAcct.setAcctId(String.valueOf(acctSession.getAcct().getAcctId()));
                appAcct.setName(acctSession.getAcct().getName());
                appAcct.setoInfoName(acctSession.getAcct().getoInfoName());
                appAcct.setAcctType("0");
                // 判断是否有审核权限
                ServiceResponse<Map<String, String>> authResponse = backMemberAPI.getAcctAuth(String.valueOf(acctSession.getAcct().getAcctId()));
                Map<String, String> map = authResponse.getRetContent();
                if(map.get("50") != null){
                    appAcct.setAcctType("1");
                }

                jsonResponse.setRspBody(appAcct);
            }else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        }catch (Exception e){
            logger.error("app login occurred error.", e);

            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    @RequestMapping("/logout")
    public JsonResponse<Boolean> logout(@RequestBody JsonRequest<String> jsonRequest){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<Boolean>();
        try{
            String sessionId = jsonRequest.getReqHeader().getSid();

            ServiceResponse<Boolean> serviceResponse = backMemberAPI.delUser(sessionId);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                logger.error("注销成功 !!");
                jsonResponse.setRspBody(true);

            }else {
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                logger.error("注销成功 !!");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

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
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

}
