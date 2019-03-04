package jq.steel.cs.services.base.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.AddressVO;
import jq.steel.cs.services.base.api.vo.UserRegistrationVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * 
 * @author wushibin
 *
 */
@FeignClient(value = "${ser.name.base}")
public interface UserRegistrationAPI {
	
	 /**
	  * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	  * 物料综合查询用
	  * @return
	  */
	@RequestMapping(value = "/userRegistration/getSale",method = RequestMethod.POST)
	public ServiceResponse<UserRegistrationVO> getSale(@RequestBody JsonRequest<UserRegistrationVO> jsonRequest);


}
