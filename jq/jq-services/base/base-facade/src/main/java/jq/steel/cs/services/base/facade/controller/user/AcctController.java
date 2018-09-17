package jq.steel.cs.services.base.facade.controller.user;

import com.ebase.core.cache.CacheService;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.AcctLogin;
import com.ebase.core.session.AcctSession;
import com.ebase.core.session.CacheKeyConstant;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.secret.base64.Base64Util;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.MessageVO;
import jq.steel.cs.services.base.facade.service.message.MessageService;
import jq.steel.cs.services.base.facade.service.user.AcctService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wangyu
 */
@RestController
@RequestMapping("/acct")
public class AcctController {

    private static Logger LOG = LoggerFactory.getLogger(AcctController.class);

    @Autowired
    private AcctService acctService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private MessageService messageService;

    /**
     * 注册
     * @return
     */

    @RequestMapping("/register")
    public ServiceResponse<String> userRegister(@RequestBody AcctInfoVO acctInfoVO){
        LOG.info(" = {}", JsonUtil.toJson(acctInfoVO));
        ServiceResponse<String> serviceResponse = new ServiceResponse<>();

        try {
            serviceResponse = acctService.userRegister(acctInfoVO);

            // 从邮件内容中获取提示
            MessageVO messageVO = new MessageVO();
            messageVO.setDestination(acctInfoVO.getEmail());
            Map<String, Object> map = new HashMap<>();
            map.put("username", acctInfoVO.getAcctTitle());
            messageVO.setVariables(map);

            String content = messageService.getRegisterContent("jgzc", "酒钢客服系统注册成功通知", messageVO);

            serviceResponse.setRetContent(content);

        } catch (Exception e) {
            LOG.error("注册 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
            serviceResponse.setResponseCode("500");
        }

        return serviceResponse;
    }

    /**
     * 用户登录接口
     * @return
     */
    @RequestMapping("/login")
    public ServiceResponse<AcctSession> userLogin(@RequestBody AcctLogin acctLogin){
        ServiceResponse<AcctSession> serviceResponse = new ServiceResponse();
        try{

            //用户注册，查出用户 并 生成key 放到 redis 中
            ServiceResponse<AcctSession> response = acctService.userLogin(acctLogin);

            //如果正常查出对象
            if(ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())){
                serviceResponse.setRetContent(response.getRetContent());
            }else{
                serviceResponse.setResponseCode(response.getRetCode());
            }
        }catch (Exception e){
            LOG.error("用户登录 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
            serviceResponse.setResponseCode("500");
        }

        return serviceResponse;
    }

    /**
     * 从缓存中 拿 用户登录对象
     * @return
     */
    @RequestMapping("/getCacheUser")
    public ServiceResponse<AcctSession> getUser(@RequestParam(value = "sessionId",required = false) String sessionId){
        ServiceResponse<AcctSession> serviceResponse = new ServiceResponse<>();

        LOG.info("sessionId = {}",sessionId);
        try{
            if(sessionId == null){
                serviceResponse.setResponseCode("0702005");
            }else{

                String key = CacheKeyConstant.ACCT_SESSION + Base64Util.decode(sessionId);

                AcctSession acctSession = cacheService.getObject(key, AcctSession.class);

                serviceResponse.setRetContent(acctSession);
            }

        }catch (Exception e){
            LOG.error("查询缓存 中用户信息 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }


    /**
     * 从缓存中 删除用户会话信息
     * @return
     */
    @RequestMapping("/delCacheUser")
    public ServiceResponse<Boolean> delUser(@RequestParam(value = "sessionId",required = false) String sessionId){
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try{
            if(sessionId == null){
                serviceResponse.setResponseCode("0702005");
            }else{
                String key = CacheKeyConstant.ACCT_SESSION + Base64Util.decode(sessionId);
                Boolean boo = cacheService.delete(key);
                if(boo){
                    serviceResponse.setRetContent(boo);
                }else{
                    serviceResponse.setRetContent(boo);
                    serviceResponse.setResponseCode("0702006");
                }
            }

        }catch (Exception e){
            LOG.error("去除会话信息 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }


    /**
     * 获得用户信息
     * @return
     */
    @RequestMapping("/getAcct")
    public ServiceResponse<AcctInfoVO> getAcct(@RequestParam(value = "acctName",required = false) String acctName){
        ServiceResponse<AcctInfoVO> serviceResponse = new ServiceResponse<>();

        try{
            if(acctName == null){
                serviceResponse.setResponseCode("0702005");
            }else {

                AcctInfoVO response = acctService.getAcct(acctName);

                //如果正常查出对象
                serviceResponse.setRetContent(response);
            }

        }catch (Exception e){
            LOG.error("获取用户信息 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    @RequestMapping("/getAcctAuth")
    public ServiceResponse<Map<String, String>> getAcctAuth(String acctId){
        ServiceResponse<Map<String, String>> serviceResponse = new ServiceResponse<>();

        try{
            Map<String, String> map = acctService.getAcctAuth(acctId);
            serviceResponse.setRetContent(map);

        } catch (Exception e){
            LOG.error("获取用户信息 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    @RequestMapping("/getAcctAuthPath")
    public ServiceResponse<Map<String, String>> getAcctAuthPath(String acctId){
        ServiceResponse<Map<String, String>> serviceResponse = new ServiceResponse<>();

        try{
            Map<String, String> map = acctService.getAcctAuth(acctId);
            serviceResponse.setRetContent(map);

        } catch (Exception e){
            LOG.error("获取用户信息 error  = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }
}
