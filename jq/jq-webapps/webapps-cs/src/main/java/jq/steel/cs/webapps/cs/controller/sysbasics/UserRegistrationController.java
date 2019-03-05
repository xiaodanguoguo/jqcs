package jq.steel.cs.webapps.cs.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.AddressAPI;
import jq.steel.cs.services.base.api.controller.UserRegistrationAPI;
import jq.steel.cs.services.base.api.vo.AddressVO;
import jq.steel.cs.services.base.api.vo.UserRegistrationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 
 * @author wushibin
 *
 */
@RestController
@RequestMapping("/userRegistration")
public class UserRegistrationController {
	
	 private static Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
 
	 @Autowired
	 private UserRegistrationAPI userRegistrationAPI;
	 
	 /**
	  * 查询省市县
	  * @return
	  */
	 @RequestMapping(value = "/getSale")
	 public JsonResponse<UserRegistrationVO> getSale(@RequestBody JsonRequest<UserRegistrationVO> jsonRequest) {
		 logger.info(" list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<UserRegistrationVO> jsonResponse = new JsonResponse<UserRegistrationVO>();
		 try {
			ServiceResponse<UserRegistrationVO> apiList = userRegistrationAPI.getSale(jsonRequest);
			UserRegistrationVO retContent = apiList.getRetContent();
			jsonResponse.setRspBody(retContent);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }


}