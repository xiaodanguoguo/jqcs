package jq.steel.cs.webapps.op.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.CrmMillSheetRebackApplyAPI;
import jq.steel.cs.services.cust.api.controller.MillCoilInfoAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetRebackApplyVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rebackApply")
public class CrmMillSheetRebackApplyController {
    private final static Logger logger = LoggerFactory.getLogger(CrmMillSheetRebackApplyController.class);

    @Autowired
    private CrmMillSheetRebackApplyAPI crmMillSheetRebackApplyAPI;
    /**
     *  申请回退
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/applyForRetreat",method = RequestMethod.POST)
    public JsonResponse<Integer> applyForRetreat(@RequestBody JsonRequest<CrmMillSheetRebackApplyVO> jsonRequest){
//        logger.info("分页",JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<Integer> serviceResponse = crmMillSheetRebackApplyAPI.applyForRetreat(jsonRequest);
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
            logger.error("回退错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
