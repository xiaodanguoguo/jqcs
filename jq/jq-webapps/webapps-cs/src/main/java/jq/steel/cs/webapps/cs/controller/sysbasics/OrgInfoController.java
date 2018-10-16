package jq.steel.cs.webapps.cs.controller.sysbasics;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.OrgInfoServiceAPI;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author zhangx
 *
 */
@RestController
@RequestMapping("/orgInfo")
public class OrgInfoController {
	
	 private static Logger logger = LoggerFactory.getLogger(OrgInfoController.class);
 
	 @Autowired
	 private OrgInfoServiceAPI orgInfoServiceAPI;
	 
	 /**
	  * 组织机构信息添加
	  * @param record
	  * @return
	  */
	 @RequestMapping(value="/addOrgInfo", method = RequestMethod.POST)
	 public JsonResponse<String> addOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<String> jsonResponse = new JsonResponse<String>();
		try {
			jsonRequest.getReqBody().setCreatedBy(AssertContext.getAcctName());
			jsonRequest.getReqBody().setCreatedTime(new Date());
			ServiceResponse<String> addOrgInfo = orgInfoServiceAPI.addOrgInfo(jsonRequest);
			String retContent = addOrgInfo.getRetContent();
			jsonResponse.setRspBody(retContent);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return jsonResponse;
	 }
	 
	 /**
	  * 组织机构信息刪除
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/removeOrgInfo", method = RequestMethod.POST)
	 public JsonResponse<Integer> removeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<Integer> jsonResponse = new JsonResponse<Integer>();;
		try {
			jsonRequest.getReqBody().setUpdatedBy(AssertContext.getAcctName());
			ServiceResponse<Integer> removeOrgInfo = orgInfoServiceAPI.removeOrgInfo(jsonRequest);
			Integer retContent = removeOrgInfo.getRetContent();
			if(retContent != 0) {
				jsonResponse.setRspBody(retContent);
			}else {
				jsonResponse.setRetCode("0301001");
				jsonResponse.setRspBody(retContent);
			}		
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return jsonResponse;
	 }
	 
	 /**
	  * 组织机构信息修改
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/saveOrgInfo", method = RequestMethod.POST)
	 public JsonResponse<Integer> saveOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<Integer> jsonResponse = new JsonResponse<Integer>();
		 try {
			 jsonRequest.getReqBody().setUpdatedBy(AssertContext.getAcctName());
			ServiceResponse<Integer> saveOrgInfo = orgInfoServiceAPI.saveOrgInfo(jsonRequest);
			Integer retContent = saveOrgInfo.getRetContent();
			jsonResponse.setRspBody(retContent);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }
	 
	 /**
	  * 组织机构信息查詢
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getOrgInfo", method = RequestMethod.POST)
	 public JsonResponse<OrgInfoVO> getOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<OrgInfoVO> jsonResponse = new JsonResponse<OrgInfoVO>();
		 try {
			ServiceResponse<OrgInfoVO> orgInfo = orgInfoServiceAPI.getOrgInfo(jsonRequest);
			OrgInfoVO retContent = orgInfo.getRetContent();
			jsonResponse.setRspBody(retContent);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /**
	  * 组织机构信息查詢
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getListOrgInfo", method = RequestMethod.POST)
	 public JsonResponse<PageDTO<OrgInfoVO>> getListOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<PageDTO<OrgInfoVO>> jsonResponse = new JsonResponse<PageDTO<OrgInfoVO>>();
		 try {
			 jsonResponse = orgInfoServiceAPI.getListOrgInfo(jsonRequest);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }
	 
	 /**
	  * 组织机构信息树查詢
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getListTreeOrgInfo")
	 public JsonResponse<Map<String, List<OrgInfoVO>>> getListTreeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<Map<String, List<OrgInfoVO>>> jsonResponse = new JsonResponse<Map<String, List<OrgInfoVO>>>();
		 try {
		 	jsonRequest.getReqBody().setId2(AssertContext.getOrgId());
			ServiceResponse<List<OrgInfoVO>> listTreeOrgInfo2 = orgInfoServiceAPI.getListTreeOrgInfo(jsonRequest);
			List<OrgInfoVO> retContent = listTreeOrgInfo2.getRetContent();
			Map<String, List<OrgInfoVO>> map=new HashMap<String, List<OrgInfoVO>>();
			map.put("resultData",retContent);
			jsonResponse.setRspBody(map);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }
	 
	 /**
	  * 组织机构信息树查詢
	  * @param id
	  * @return
	  */
	 @RequestMapping(value = "/getChildTreeOrgInfo")
	 public JsonResponse<Map<String, List<OrgInfoVO>>> getChildTreeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<Map<String, List<OrgInfoVO>>> jsonResponse = new JsonResponse<Map<String, List<OrgInfoVO>>>();
		 try {
			ServiceResponse<OrgInfoVO> childTreeOrgInfo = orgInfoServiceAPI.getChildTreeOrgInfo(jsonRequest);
			OrgInfoVO retContent = childTreeOrgInfo.getRetContent();
			Map<String, List<OrgInfoVO>> map=new HashMap<String, List<OrgInfoVO>>();
			map.put("children", Arrays.asList(retContent));
			jsonResponse.setRspBody(map);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }
	 
	 /**
	  * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	  * 物料综合查询用
	  * @return
	  */
	 @RequestMapping(value = "/getMaterielOrginfo")
	 public JsonResponse<List<OrgInfoVO>> getMaterielOrginfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest) {
		 logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		 JsonResponse<List<OrgInfoVO>> jsonResponse = new JsonResponse<List<OrgInfoVO>>();
		 try {
			ServiceResponse<List<OrgInfoVO>> materielOrginfo = orgInfoServiceAPI.getMaterielOrginfo(jsonRequest);
			List<OrgInfoVO> retContent = materielOrginfo.getRetContent();
			jsonResponse.setRspBody(retContent);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }
	 
	 /**
	  * @param:
	  * @return:
	  * @description: 审核列表
	  * @author: lirunze
	  * @Date: 2018/8/31
	  */
	@RequestMapping(value = "/audit/list", method = RequestMethod.POST)
	public JsonResponse<PageDTO<OrgInfoVO>> getAuditOrgList(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<PageDTO<OrgInfoVO>> jsonResponse = new JsonResponse<PageDTO<OrgInfoVO>>();
		try {
			jsonResponse = orgInfoServiceAPI.getAuditOrgList(jsonRequest);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	}

	/**
	 * @param:
	 * @return:
	 * @description: 审核
	 * @author: lirunze
	 * @Date: 2018/8/31
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public JsonResponse<Integer> getAuditOrg(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		logger.info(" www 审核 参数 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();

		try {
		    ServiceResponse<Integer> serviceResponse = orgInfoServiceAPI.getAuditOrg(jsonRequest);
		    if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
		        jsonResponse.setRspBody(serviceResponse.getRetContent());
		    } else {
		        if (serviceResponse.isHasError()) {
		            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		        } else {
		            jsonResponse.setRetCode(serviceResponse.getRetCode());
		            jsonResponse.setRetDesc(serviceResponse.getRetMessage());
		        }
		    }
		} catch (BusinessException e) {
		    logger.error("错误 = {}", e);
		    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		} catch (Exception e) {
		    logger.error("错误 = {}", e);
		    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}

		return jsonResponse;
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /**
	  * 组织机构信息递归查詢
	  * @param id
	  * @return
	  */
	/* @RequestMapping(value="/getListRecursionOrgInfo", method = RequestMethod.POST)
	 public JsonResponse<Map> getListRecursionOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 LOG.info(" www 系统编码list 参数 = {}",JsonUtil.toJson(jsonRequest));
		 JsonResponse<Map> jsonResponse = new JsonResponse<>();
		 try {
			 JsonResponse jsonResponse2 = orgInfoServiceAPI.getListRecursionOrgInfo(jsonRequest);
			Map map=new HashMap();
			map.put("children",jsonResponse2.getRspBody());
			jsonResponse.setRspBody(map);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }*/

	/**
	 * @param:
	 * @return:
	 * @description: 验证是否有客户名称
	 * @author: wushibin
	 * @Date: 2018/9/3
	 */
	@RequestMapping(value = "/selectOrgName", method = RequestMethod.POST)
	public JsonResponse<OrgInfoVO> selectOrgName(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<OrgInfoVO> jsonResponse = new JsonResponse<>();

		try {
			ServiceResponse<OrgInfoVO> serviceResponse = orgInfoServiceAPI.selectOrgName(jsonRequest);
			if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
				jsonResponse.setRspBody(serviceResponse.getRetContent());
			} else {
				if (serviceResponse.isHasError()) {
					jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
				} else {
					jsonResponse.setRetCode(serviceResponse.getRetCode());
					jsonResponse.setRetDesc(serviceResponse.getRetMessage());
				}
			}
		} catch (BusinessException e) {
			logger.error("错误 = {}", e);
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		} catch (Exception e) {
			logger.error("错误 = {}", e);
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}

		return jsonResponse;
	}

	/**
	 * @param:
	 * @return:
	 * @description: 通过sap编码获取客户名称
	 * @author: lirunze
	 * @Date: 2018/9/3
	 */
	@RequestMapping(value = "/getOrgName", method = RequestMethod.POST)
	public JsonResponse<OrgInfoVO> getOrgName(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		logger.info("通过sap编码获取客户名称 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<OrgInfoVO> jsonResponse = new JsonResponse<>();

		try {
			ServiceResponse<OrgInfoVO> serviceResponse = orgInfoServiceAPI.getOrgName(jsonRequest);
			if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
				jsonResponse.setRspBody(serviceResponse.getRetContent());
			} else {
				if (serviceResponse.isHasError()) {
					jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
				} else {
					jsonResponse.setRetCode(serviceResponse.getRetCode());
					jsonResponse.setRetDesc(serviceResponse.getRetMessage());
				}
			}
		} catch (BusinessException e) {
			logger.error("通过sap编码获取客户名称错误 = {}", e);
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		} catch (Exception e) {
			logger.error("通过sap编码获取客户名称错误 = {}", e);
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}

		return jsonResponse;
	}

}
