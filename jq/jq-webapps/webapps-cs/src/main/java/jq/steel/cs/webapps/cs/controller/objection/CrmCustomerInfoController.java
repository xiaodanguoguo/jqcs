package jq.steel.cs.webapps.cs.controller.objection;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.CrmCustomerInfoAPI;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderUnit")
public class CrmCustomerInfoController {

    private final static Logger logger = LoggerFactory.getLogger(CrmCustomerInfoController.class);
    @Autowired
    private CrmCustomerInfoAPI crmCustomerInfoAPI;


    /**
     * 修改/增加
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/orderUnitInsert",method = RequestMethod.POST)
    public JsonResponse<CrmCustomerInfoVO> orderUnitInsert(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest) {
        JsonResponse<CrmCustomerInfoVO> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            // 根据service层返回的编码做不同的操作
            ServiceResponse<CrmCustomerInfoVO> response = crmCustomerInfoAPI.orderUnitInsert(jsonRequest);
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
    public JsonResponse<PageDTO<CrmCustomerInfoVO>>  findByPage(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<CrmCustomerInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<CrmCustomerInfoVO>> serviceResponse = crmCustomerInfoAPI.findByPage(jsonRequest);
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
    @RequestMapping(value = "/orderUnitDelete",method = RequestMethod.POST)
    public JsonResponse<Integer> orderUnitDelete(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response = crmCustomerInfoAPI.orderUnitDelete(jsonRequest);
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
     * 诉赔页面查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findDefault",method = RequestMethod.POST)
    public JsonResponse<CrmCustomerInfoVO>  findDefault(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmCustomerInfoVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmCustomerInfoVO> serviceResponse = crmCustomerInfoAPI.findDefault(jsonRequest);
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
     * 订货单位获取当前登录人的客户联系编号和客户单位名称
     * @param
     * @return
     */
    @RequestMapping(value = "/customerInfo",method = RequestMethod.POST)
    public JsonResponse<CrmCustomerInfoVO> findDefault() {
        JsonResponse<CrmCustomerInfoVO> serviceResponse = new JsonResponse<>();
        try {
            CrmCustomerInfoVO crmLastuserInfoVO1 = new CrmCustomerInfoVO();
            crmLastuserInfoVO1.setCustomerId(AssertContext.getOrgCode());
            crmLastuserInfoVO1.setCustomerName(AssertContext.getOrgName());
            serviceResponse.setRspBody(crmLastuserInfoVO1);
        } catch (BusinessException e) {
            logger.error("获取分页出错", e);
            serviceResponse.setRetCode("500");
        }
        return serviceResponse;
    }
}
