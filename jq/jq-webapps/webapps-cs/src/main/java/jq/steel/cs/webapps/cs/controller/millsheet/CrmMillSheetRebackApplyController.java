package jq.steel.cs.webapps.cs.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.CrmMillSheetRebackApplyAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetRebackApplyVO;
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
    public JsonResponse<CrmMillSheetRebackApplyVO> applyForRetreat(@RequestBody JsonRequest<CrmMillSheetRebackApplyVO> jsonRequest){
//        logger.info("分页",JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmMillSheetRebackApplyVO> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<CrmMillSheetRebackApplyVO> serviceResponse = crmMillSheetRebackApplyAPI.applyForRetreat(jsonRequest);
            if (serviceResponse.getRetContent().getIsReback().equals("Y")){
                jsonResponse.setRetCode("1111111");
                jsonResponse.setRetDesc("此质证书已回退过，不可再次回退");
            }else {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            }
        } catch (BusinessException e) {
            logger.error("回退错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
