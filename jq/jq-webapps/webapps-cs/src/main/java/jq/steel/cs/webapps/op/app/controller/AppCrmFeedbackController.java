package jq.steel.cs.webapps.op.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.app.AppCrmFeedbackApi;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import jq.steel.cs.services.cust.api.vo.CrmFeedbackVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName: AppCrmFeedbackController
 * @Description:  意见反馈
 * @Author: lirunze
 * @CreateDate: 2018/9/7 14:01
 */
@RestController
@RequestMapping("/app/feedback")
public class AppCrmFeedbackController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AppCrmFeedbackApi appCrmFeedbackApi;

    /**
     * @param:
     * @return:
     * @description:  添加客户意见反馈
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/add")
    public JsonResponse<Integer> addCrmFeedback(@RequestBody JsonRequest<CrmFeedbackVO> jsonRequest) {
        logger.info("添加客户意见反馈 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        CrmFeedbackVO feedbackVO = jsonRequest.getReqBody();
        feedbackVO.setCreateByid(Long.parseLong(AssertContext.getAcctId()));
        feedbackVO.setCreateDt(new Date());
        try {
            ServiceResponse<Integer> serviceResponse = appCrmFeedbackApi
                    .addCrmFeedback(jsonRequest);
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
            logger.error("添加客户意见反馈错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("添加客户意见反馈错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }
}
