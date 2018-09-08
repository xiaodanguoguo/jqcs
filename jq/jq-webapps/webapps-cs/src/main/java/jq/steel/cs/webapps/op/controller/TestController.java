package jq.steel.cs.webapps.op.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.BaseApi;
import jq.steel.cs.services.base.api.vo.EcmPtProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 * @author Kim
 *
 */
@RestController
public class TestController {

	private final static Logger logger =LoggerFactory.getLogger(TestController.class);

	@Autowired
	private BaseApi baseApi;

	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public JsonResponse<PageDTO<EcmPtProjectVO>> test(@RequestBody JsonRequest<EcmPtProjectVO> jsonRequest){
		JsonResponse<PageDTO<EcmPtProjectVO>> result = new JsonResponse<>();
		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<PageDTO<EcmPtProjectVO>> response = baseApi.test(jsonRequest);
			if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
					result.setRspBody(response.getRetContent());
			//如果需要异常信息
			else 
				if (response.isHasError()) 
					//系统异常
					result.setRetCode(JsonResponse.SYS_EXCEPTION);
					//如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
				else {
				//根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
					result.setRetCode(response.getRetCode());
					result.setRetDesc(response.getRetMessage());
				}
			
		} catch (FeignException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return result;
	}
}
