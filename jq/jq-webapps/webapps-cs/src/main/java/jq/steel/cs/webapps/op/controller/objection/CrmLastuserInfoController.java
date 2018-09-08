package jq.steel.cs.webapps.op.controller.objection;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.CrmLastuserInfoAPI;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.webapps.op.controller.millsheet.MillSheetHostsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unitOfUse")
public class CrmLastuserInfoController {
    private final static Logger logger = LoggerFactory.getLogger(CrmLastuserInfoController.class);
    @Autowired
    private CrmLastuserInfoAPI crmLastuserInfoAPI;
    /**
     * 修改/增加
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/unitOfUseInsert",method = RequestMethod.POST)
    public JsonResponse<Integer> unitOfUseInsert(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest) {
        logger.info("参数={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            // 根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response = crmLastuserInfoAPI.unitOfUseInsert(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;

    }


    /**
     *条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<CrmLastuserInfoVO>>  findByPage(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
           logger.info("分页={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<CrmLastuserInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<CrmLastuserInfoVO>> serviceResponse = crmLastuserInfoAPI.findByPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

     /**
     * 删除
     * @param  jsonRequest
     * @return
     *
     * */
     @RequestMapping(value = "/unitOfUseDelete",method = RequestMethod.POST)
     public JsonResponse<Integer> unitOfUseDelete(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest) {
         JsonResponse<Integer> jsonResponse = new JsonResponse<>();
         try {
             jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
             jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
             // 根据service层返回的编码做不同的操作
             ServiceResponse<Integer> response = crmLastuserInfoAPI.unitOfUseDelete(jsonRequest);
             if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                 jsonResponse.setRspBody(response.getRetContent());
                 // 如果需要异常信息
             else if (response.isHasError())
                 // 系统异常
                 jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                 // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
             else {
                 // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                 jsonResponse.setRetCode(response.getRetCode());
                 jsonResponse.setRetDesc(response.getRetMessage());
             }
         } catch (FeignException e) {
             logger.error(e.getMessage());
             e.printStackTrace();
             jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
             return jsonResponse;
         }
         return jsonResponse;

     }
     //诉赔页面使用单位回显
    @RequestMapping(value = "/findDefault",method = RequestMethod.POST)
    public JsonResponse<CrmLastuserInfoVO>  findDefault(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmLastuserInfoVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmLastuserInfoVO> serviceResponse = crmLastuserInfoAPI.findDefault(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
