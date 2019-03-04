package jq.steel.cs.services.base.facade.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.base.api.vo.AddressVO;
import jq.steel.cs.services.base.api.vo.UserRegistrationVO;
import jq.steel.cs.services.base.facade.model.Address;
import jq.steel.cs.services.base.facade.model.UserRegistration;
import jq.steel.cs.services.base.facade.service.sysbasics.AddressService;
import jq.steel.cs.services.base.facade.service.sysbasics.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 private UserRegistrationService userRegistrationService;

	 
	 /**
	  * 根据当前id，查询出省市区
	  * 物料综合查询用
	  * @return
	  */
	 @RequestMapping(value = "/getSale", method = RequestMethod.POST)
	 public ServiceResponse<UserRegistrationVO> getSale(@RequestBody JsonRequest<UserRegistrationVO> jsonRequest){
		 ServiceResponse<UserRegistrationVO> response = new ServiceResponse<UserRegistrationVO>();
		 UserRegistrationVO reqBody = jsonRequest.getReqBody();
		 UserRegistration userRegistration = new UserRegistration();
 		 BeanCopyUtil.copy(reqBody, userRegistration);
 		try {
			 UserRegistration userRegistrations= userRegistrationService.getSale(userRegistration);
			 UserRegistrationVO userRegistrationVOS = new UserRegistrationVO();
			 BeanCopyUtil.copy(userRegistrations, userRegistrationVOS);
			 response.setRetContent(userRegistrationVOS);
		} catch (BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
 		 return response;
	 }

}
