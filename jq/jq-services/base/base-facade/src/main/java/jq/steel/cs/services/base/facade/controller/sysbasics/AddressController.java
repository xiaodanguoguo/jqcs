package jq.steel.cs.services.base.facade.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.AddressVO;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.model.Address;
import jq.steel.cs.services.base.facade.model.OrgInfo;
import jq.steel.cs.services.base.facade.service.sysbasics.AddressService;
import jq.steel.cs.services.base.facade.service.sysbasics.OrgInfoService;
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
@RequestMapping("/address")
public class AddressController {
	
	 private static Logger logger = LoggerFactory.getLogger(AddressController.class);
	 
	 @Autowired
	 private AddressService addressService;

	 
	 /**
	  * 根据当前id，查询出省市区
	  * 物料综合查询用
	  * @return
	  */
	 @RequestMapping(value = "/getList", method = RequestMethod.POST)
	 public ServiceResponse<List<AddressVO>> getList(@RequestBody JsonRequest<AddressVO> jsonRequest){
		 ServiceResponse<List<AddressVO>> response = new ServiceResponse<List<AddressVO>>();
		 AddressVO reqBody = jsonRequest.getReqBody();
		 Address address = new Address();
 		 BeanCopyUtil.copy(reqBody, address);
 		try {
			 List<Address> addresses= addressService.getList(address);
			 List<AddressVO> addressVOS = BeanCopyUtil.copyList(addresses, AddressVO.class);
			 response.setRetContent(addressVOS);
		} catch (BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
 		 return response;
	 }

}
