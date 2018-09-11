package jq.steel.cs.services.base.api.controller;


import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.UserLogin;
import com.ebase.core.session.UserSession;
import jq.steel.cs.services.base.api.vo.RegisterUserVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${ser.name.base}")
public interface MemberAPI {

    /**
     * 用户注册
     * @param registerUserVO
     * @return
     */
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    ServiceResponse<Integer> userRegister(@RequestBody RegisterUserVO registerUserVO);

    /**
     * 登录 并生成key 存到 redis
     * @param UserLogin
     * @return
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    ServiceResponse<UserSession> userLogin(@RequestBody UserLogin UserLogin);

    /**
     * 从缓存中拿到session信息
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/user/getCacheUser",method = RequestMethod.GET)
    ServiceResponse<UserSession> getUser(@RequestParam(value = "sessionId", required = false) String sessionId);

    /**
     * 去掉会话信息
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/user/delCacheUser",method = RequestMethod.GET)
    ServiceResponse<Boolean> delUser(@RequestParam(value = "sessionId", required = false) String sessionId);
}
