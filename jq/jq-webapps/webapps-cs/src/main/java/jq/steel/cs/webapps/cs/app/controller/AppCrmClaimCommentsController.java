package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.CrmClaimCommentsAPI;
import jq.steel.cs.services.cust.api.vo.CrmClaimCommentsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param:
 * @return:
 * @description:  客户评价 x
 * @author: lirunze
 * @Date: 2018/9/7
 */
@RestController
@RequestMapping("/app/claimComments")
public class AppCrmClaimCommentsController {

    private final static Logger logger = LoggerFactory.getLogger(AppCrmClaimCommentsController.class);
    @Autowired
    private CrmClaimCommentsAPI crmClaimCommentsAPI;


    /**
     * 修改/增加
     * @param  jsonRequest
     * @return
     * */
    @RequestMapping(value = "/evaluate",method = RequestMethod.POST)
    public JsonResponse<Integer> evaluate(@RequestBody JsonRequest<CrmClaimCommentsVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            // 根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response = crmClaimCommentsAPI.evaluate(jsonRequest);
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
}
