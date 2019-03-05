package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.AddressAPI;
import jq.steel.cs.services.base.api.vo.AddressVO;
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
@RequestMapping("/app/address")
public class AppAddressController {
	
	 private static Logger logger = LoggerFactory.getLogger(AppAddressController.class);
 
	 @Autowired
	 private AddressAPI addressAPI;
	 
	 /**
	  * 查询省市县
	  * @return
	  */
	 @RequestMapping(value = "/getList")
	 public JsonResponse<List<AddressVO>> getList(@RequestBody JsonRequest<AddressVO> jsonRequest) {
		 logger.info(" list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<List<AddressVO>> jsonResponse = new JsonResponse<List<AddressVO>>();
		 try {
			ServiceResponse<List<AddressVO>> apiList = addressAPI.getList(jsonRequest);
			List<AddressVO> retContent = apiList.getRetContent();
			jsonResponse.setRspBody(retContent);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }


}
