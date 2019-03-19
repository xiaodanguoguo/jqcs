package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.UserRegistrationAPI;
import jq.steel.cs.services.base.api.vo.UserRegistrationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author wushibin
 *
 */
@RestController
@RequestMapping("/app/userRegistration")
public class AppUserRegistrationController {
	
	 private static Logger logger = LoggerFactory.getLogger(AppUserRegistrationController.class);
 
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
			 if(!apiList.getRetContent().getSalesCompany().equals("111")){
				 UserRegistrationVO retContent = apiList.getRetContent();
				 jsonResponse.setRspBody(retContent);
			 }else {
				 //查询无数据
				 jsonResponse.setRetCode("0000007");
				 jsonResponse.setRetDesc("客户注册过程中所选择的产品类别和公司所在区域，如果没有匹配出对应销售公司的，系统给予提示：“该类产品在该区域没有销售网点，详情请咨询0937-6713969”");
			 }
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }


}
