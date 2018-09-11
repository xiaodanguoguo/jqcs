package jq.steel.cs.services.base.facade.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import io.swagger.models.auth.In;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.model.OrgInfo;
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
 * @author zhangx
 *
 */
@RestController
public class OrgInfoController {
	
	 private static Logger logger = LoggerFactory.getLogger(OrgInfoController.class);
	 
	 @Autowired
	 private OrgInfoService orgInfoService;
	 
	 /**
	  * 添加   组织机构信息
	  * @param record
	  * @return
	  */
	 @RequestMapping("/addOrgInfo")
	 public ServiceResponse<String> addOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 ServiceResponse<String> response = new ServiceResponse<String>();
		 
		 OrgInfoVO reqBody = jsonRequest.getReqBody();
		 OrgInfo orgInfo = new OrgInfo();
		 BeanCopyUtil.copy(reqBody, orgInfo);
		try {
			Long i = orgInfoService.addOrgInfo(orgInfo);
			
			response.setRetContent(orgInfo.getId());
			
		} catch(BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
		return response;
	 }
	 
	 /**
	  * 刪除 组织机构信息
	  * @param id
	  * @return
	  */
	 @RequestMapping("/removeOrgInfo")
	 public ServiceResponse<Integer> removeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		ServiceResponse<Integer> response = new ServiceResponse<Integer>();
		 
		OrgInfoVO reqBody = jsonRequest.getReqBody();
		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(reqBody, orgInfo);
		try {
			Integer i = orgInfoService.removeOrgInfo(orgInfo);
			response.setRetContent(i);
		}catch(BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
		return response;
	 }
	 
	 /**
	  * 组织机构信息父查子
	  * @param id
	  * @return
	  */
	 @RequestMapping("/getListTreeOrgInfo")
	 public ServiceResponse<List<OrgInfoVO>> getListTreeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
         ServiceResponse<List<OrgInfoVO>> response = new ServiceResponse<List<OrgInfoVO>>();
         
         OrgInfoVO reqBody = jsonRequest.getReqBody();
 		 OrgInfo orgInfo = new OrgInfo();
 		 BeanCopyUtil.copy(reqBody, orgInfo);
		 try {
		    List<OrgInfo> listTreeOrgInfo = orgInfoService.getListTreeOrgInfo(orgInfo);
		    List<OrgInfoVO> retContentVO  = BeanCopyUtil.copyList(listTreeOrgInfo, OrgInfoVO.class);
		    response.setRetContent(retContentVO);
		 } catch (BusinessException e) {
            response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		 }
		return response;
	 }
	 
	 /**
	  * 组织机构信息树查詢
	  * @param id
	  * @return
	  */
	 @RequestMapping("/getChildTreeOrgInfo")
	 public ServiceResponse<OrgInfoVO> getChildTreeOrgInfo(@RequestBody JsonRequest<OrgInfo> jsonRequest) {
		 ServiceResponse<OrgInfoVO> response = new ServiceResponse<OrgInfoVO>();
		 
		 OrgInfo reqBody = jsonRequest.getReqBody();
 		 OrgInfo orgInfo = new OrgInfo();
 		 BeanCopyUtil.copy(reqBody, orgInfo);
		 try {
			OrgInfo childTreeOrgInfo = orgInfoService.getChildTreeOrgInfo(orgInfo);
			OrgInfoVO copy = BeanCopyUtil.copy(childTreeOrgInfo, OrgInfoVO.class);
			response.setRetContent(copy);
		} catch (BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
		return response;
	 }
	 
	 /**
	  * 修改  组织机构信息
	  * @param id
	  * @return
	  */
	 @RequestMapping("/saveOrgInfo")
	 public ServiceResponse<Integer> saveOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 ServiceResponse<Integer> response = new ServiceResponse<Integer>();
		 
		 OrgInfoVO reqBody = jsonRequest.getReqBody();
 		 OrgInfo orgInfo = new OrgInfo();
 		 BeanCopyUtil.copy(reqBody, orgInfo);
		 try {
			 Integer i = orgInfoService.saveOrgInfo(orgInfo);
			 response.setRetContent(i);
		} catch (BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
		return response;
	 }
	 
	 /**
	  * 组织机构信息查詢
	  * @param id
	  * @return
	  */
	 @RequestMapping("/getOrgInfo")
	 public ServiceResponse<OrgInfoVO> getOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 ServiceResponse<OrgInfoVO> response = new ServiceResponse<OrgInfoVO>();
		 
		 OrgInfoVO reqBody = jsonRequest.getReqBody();
 		 OrgInfo orgInfo = new OrgInfo();
 		 BeanCopyUtil.copy(reqBody, orgInfo);
		 try {
			 OrgInfo orgInfo2 = orgInfoService.getOrgInfo(orgInfo);
			 OrgInfoVO copy = BeanCopyUtil.copy(orgInfo2, OrgInfoVO.class);
			 response.setRetContent(copy);
		} catch (BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
		return response;
	 }
	 
	 /**
	  * 组织机构信息查詢分页
	  * @param id
	  * @return
	  */
	 @RequestMapping("/getListOrgInfo")
	 public JsonResponse<PageDTO<OrgInfoVO>> getListOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 JsonResponse<PageDTO<OrgInfoVO>> jsonResponse = new JsonResponse<PageDTO<OrgInfoVO>>();
		 try {
			PageDTO<OrgInfoVO> page = orgInfoService.getListOrgInfo(jsonRequest);
			jsonResponse.setRspBody(page);
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
	 @RequestMapping("/getMaterielOrginfo")
	 public ServiceResponse<List<OrgInfoVO>> getMaterielOrginfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
		 ServiceResponse<List<OrgInfoVO>> response = new ServiceResponse<List<OrgInfoVO>>();
		 
		 AcctInfoVO reqBody = jsonRequest.getReqBody();
		 AcctInfo acctInfo = new AcctInfo();
 		 BeanCopyUtil.copy(reqBody, acctInfo);
 		 
 		try {
			 List<OrgInfo> listOrgInfo= orgInfoService.getMaterielOrginfo(acctInfo);
			 List<OrgInfoVO> listOrgInfoVO = BeanCopyUtil.copyList(listOrgInfo, OrgInfoVO.class);
			 response.setRetContent(listOrgInfoVO);
		} catch (BusinessException e) {
			response.setException(new BusinessException("异常编码", new Object[]{"参数"}));
			logger.error(e.getMessage());
		}
 		 
 		 return response;
	 }

	/**
	 * @param:
	 * @return:
	 * @description: 审核列表
	 * @author: lirunze
	 * @Date: 2018/8/31
	 */
	@RequestMapping(value = "/audit/list", method = RequestMethod.POST)
	JsonResponse<PageDTO<OrgInfoVO>> getAuditOrgList(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		JsonResponse<PageDTO<OrgInfoVO>> jsonResponse = new JsonResponse<PageDTO<OrgInfoVO>>();
		try {
			PageDTO<OrgInfoVO> page = orgInfoService.getAuditOrgList(jsonRequest);
			jsonResponse.setRspBody(page);
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
	ServiceResponse<Integer> getAuditOrg(@RequestBody JsonRequest<OrgInfoVO> jsonRequest)  {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();

		try {
			OrgInfoVO orgInfoVO = jsonRequest.getReqBody();
			Integer i = orgInfoService.getAuditOrg(orgInfoVO);
		} catch (Exception e) {
		    logger.error("错误 = {}", e);
		    serviceResponse.setException(new BusinessException("500"));
		}

		return serviceResponse;
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /**
	  * 根据前台传入的用户id查询出该用户下绑定的所有的组织机构
	  * @param jsonRequest
	  * @return
	  */
	 /*@RequestMapping("/getAcctOrgInfoTree")
	 public JsonResponse<OrgInfoVO> getAcctOrgInfoTree(@RequestBody JsonRequest<AcctInfoVO> jsonRequest) {
		 JsonResponse<OrgInfoVO> jsonResponse = new JsonResponse<OrgInfoVO>();
		 try {
			jsonResponse = orgInfoService.getAcctOrgInfoTree(jsonRequest);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	 }*/
	 
	 /**
	  * 组织机构信息递归查詢
	  * @param id
	  * @return
	  *//*
	 @RequestMapping("/getListRecursionOrgInfo")
	 public JsonResponse getListRecursionOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		 JsonResponse jsonResponse = new JsonResponse();
		 try {
			jsonResponse = orgInfoService.getListRecursionOrgInfo(jsonRequest);
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
	public  ServiceResponse<OrgInfoVO> selectOrgName(@RequestBody JsonRequest<OrgInfoVO> jsonRequest)  {
		ServiceResponse<OrgInfoVO> serviceResponse = new ServiceResponse<>();
		try {
			OrgInfoVO orgInfoVO = new OrgInfoVO();
			orgInfoVO.setOrgName(jsonRequest.getReqBody().getOrgName());
			OrgInfo list= orgInfoService.selectOrgName(orgInfoVO);
			if (list.getTrue()){
				orgInfoVO.setOrgCode(list.getOrgCode());
				serviceResponse.setRetContent(orgInfoVO);
			}else {
				serviceResponse.setRetCode("00");
				serviceResponse.setRetMessage("请验证客户名称");
				return serviceResponse;
			}

		} catch (Exception e) {
			logger.error("错误 = {}", e);
			serviceResponse.setException(new BusinessException("500"));
		}

		return serviceResponse;
	}
	 

}
