package jq.steel.cs.webapps.op.controller.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.SysDescriptionCodingAPI;
import jq.steel.cs.services.base.api.vo.SysDescriptionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by MrLi on 2018/7/23.
 */
@RestController
@RequestMapping("/SysDescription")
public class SysDescriptionCodingController {

	private static Logger logger = LoggerFactory.getLogger(SysDescriptionCodingController.class);

	@Autowired
	private SysDescriptionCodingAPI sysDescriptionCodingAPI;

	/**
	 * 系统参数 list 接口
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/sysDescriptionList")
	public JsonResponse<PageDTO<SysDescriptionVO>> listSysDescription(
			@RequestBody JsonRequest<SysDescriptionVO> jsonRequest) {
		JsonResponse<PageDTO<SysDescriptionVO>> jsonResponse = new JsonResponse<>();
		
		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<PageDTO<SysDescriptionVO>> response = sysDescriptionCodingAPI.sysDescriptionList(jsonRequest);
			if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
				jsonResponse.setRspBody(response.getRetContent());
			//如果需要异常信息
			else 
				if (response.isHasError()) 
					//系统异常
					jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
					//如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
				else {
				//根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
					jsonResponse.setRetCode(response.getRetCode());
					jsonResponse.setRetDesc(response.getRetMessage());
				}
		} catch (FeignException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}

		return jsonResponse;
	}

	/**
	 * 系统参数 保存 或者删除 或者修改接口
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/sysDescriptionKeep")
	public JsonResponse<Integer> keepSysDescription(@RequestBody JsonRequest<List<SysDescriptionVO>> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();

		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<Integer> response = sysDescriptionCodingAPI.sysDescriptionKeep(jsonRequest);
			if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
				jsonResponse.setRspBody(response.getRetContent());
			//如果需要异常信息
			else 
				if (response.isHasError()) 
					//系统异常
					jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
					//如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
				else {
				//根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
					jsonResponse.setRetCode(response.getRetCode());
					jsonResponse.setRetDesc(response.getRetMessage());
				}
		} catch (FeignException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}

		return jsonResponse;
	}

}
