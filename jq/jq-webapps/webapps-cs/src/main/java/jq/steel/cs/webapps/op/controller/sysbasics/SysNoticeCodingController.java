package jq.steel.cs.webapps.op.controller.sysbasics;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.SysNoticeCodingAPI;
import jq.steel.cs.services.base.api.vo.SysNoticeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by MrLi on 2018/7/19.
 */
@RestController
@RequestMapping("/SysNotice")
public class SysNoticeCodingController {

	private static Logger logger = LoggerFactory.getLogger(SysNoticeCodingController.class);

	@Autowired
	private SysNoticeCodingAPI sysNoticeCodingAPI;

	/**
	 *
	 * @Description: @param: [jsonRequest] @return:
	 * com.ebase.core.web.json.JsonResponse @throws
	 */
	@RequestMapping("/getSysNoticeAll")
	public JsonResponse<Map<String, List<SysNoticeVO>>> getSysNoticeAll(@RequestBody JsonRequest<SysNoticeVO> jsonRequest) {
		JsonResponse<Map<String, List<SysNoticeVO>>> jsonResponse = new JsonResponse<Map<String, List<SysNoticeVO>>>();
		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<Map<String, List<SysNoticeVO>>> response = sysNoticeCodingAPI.getSysNoticeAll(jsonRequest);
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
	 * 获取数据详细信息
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/getSysNotice")
	public JsonResponse<SysNoticeVO> getSysNoticeByKey(@RequestBody JsonRequest<SysNoticeVO> jsonRequest) {
		JsonResponse<SysNoticeVO> jsonResponse = new JsonResponse<SysNoticeVO>();
		
		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<SysNoticeVO> response = sysNoticeCodingAPI.getSysNoticeByKey(jsonRequest);
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
	@RequestMapping(value = "/sysNoticeKeep")
	public JsonResponse<Integer> keepSysNotice(@RequestBody JsonRequest<List<SysNoticeVO>> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<Integer>();
		
		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<Integer> response = sysNoticeCodingAPI.sysNoticeKeep(jsonRequest);
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
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/sysNoticeAdd")
	public JsonResponse<SysNoticeVO> sysNoticeAdd(@RequestBody JsonRequest<SysNoticeVO> jsonRequest) {
		JsonResponse<SysNoticeVO> jsonResponse = new JsonResponse<SysNoticeVO>();
		
		try {
			//根据service层返回的编码做不同的操作
			ServiceResponse<SysNoticeVO> response = sysNoticeCodingAPI.sysNoticeAdd(jsonRequest);
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
