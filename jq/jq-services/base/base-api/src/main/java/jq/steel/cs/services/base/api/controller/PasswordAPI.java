package jq.steel.cs.services.base.api.controller;

import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.base.api.vo.UserInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: chenjie
 */
@FeignClient(value = "${ser.name.base}")
public interface PasswordAPI {
    @RequestMapping(value = "/password/account", method = RequestMethod.POST)
    ServiceResponse<UserInfoVO> account(@RequestBody UserInfoVO userInfoVO);

    @RequestMapping(value = "/password/send", method = RequestMethod.POST)
    ServiceResponse<String> send(@RequestBody UserInfoVO userInfoVO);

    @RequestMapping(value = "/password/validate", method = RequestMethod.POST)
    ServiceResponse<String> validate(@RequestBody UserInfoVO userInfoVO);

    @RequestMapping(value = "/password/restPassword", method = RequestMethod.POST)
    ServiceResponse<String> restPassword(@RequestBody UserInfoVO userInfoVO);

    @RequestMapping(value = "/password/modifyPassword", method = RequestMethod.POST)
    ServiceResponse<String> modifyPassword(@RequestBody UserInfoVO userInfoVO);

    @RequestMapping(value = "/password/validateCode", method = RequestMethod.POST)
    ServiceResponse<String> validateCode(@RequestBody UserInfoVO userInfoVO);

    @RequestMapping(value = "/password/jump", method = RequestMethod.POST)
    ServiceResponse<String> jump(@RequestBody UserInfoVO userInfoVO);

}
