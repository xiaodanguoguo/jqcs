package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.app.AppCrmCustGrumbleApi;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName: AppCrmCustGrumbleController
 * @Description: 客户抱怨
 * @Author: lirunze
 * @CreateDate: 2018/9/7 13:23
 */
@RestController
@RequestMapping("/app/grumble")
public class AppCrmCustGrumbleController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AppCrmCustGrumbleApi appCrmCustGrumbleApi;

    /**
     * @param:
     * @return:
     * @description:  添加客户抱怨
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/add")
    public JsonResponse<Integer> addCrmCustGrumble(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("添加客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();

        try {
            CrmCustGrumbleVO grumbleVO = jsonRequest.getReqBody();
            grumbleVO.setCreateByid(Long.parseLong(AssertContext.getAcctId()));
            grumbleVO.setCreateDt(new Date());
            ServiceResponse<Integer> serviceResponse = appCrmCustGrumbleApi.addCrmCustGrumble(jsonRequest);
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
            logger.error("添加客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("添加客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }
}
