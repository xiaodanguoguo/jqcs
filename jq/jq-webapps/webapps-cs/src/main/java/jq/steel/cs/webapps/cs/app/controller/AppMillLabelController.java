package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.app.AppMillLabelAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @Author: lujiawei
 * @CreateDate: 2018/09/19 20:00
 */
@RestController
@RequestMapping("/app/millLabel")
public class AppMillLabelController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AppMillLabelAPI appMillLabelAPI;

    @RequestMapping(value = {"/getmillLabelByQrcode"}, method = RequestMethod.POST)
    public JsonResponse<List<CrmMillCoilInfoVO>> getCoilsByCurrentUser(@RequestBody JsonRequest<String> jsonRequest) {

        JsonResponse<List<CrmMillCoilInfoVO>> jsonResponse = new JsonResponse<>();
        logger.info("标签扫描验真,返回质证书结构化数据", JsonUtil.toJson(jsonRequest));

        try {

            ServiceResponse<List<CrmMillCoilInfoVO>> listServiceResponse = appMillLabelAPI.queryByQrCode(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(listServiceResponse.getRetCode()))
                jsonResponse.setRspBody(listServiceResponse.getRetContent());
            else if (listServiceResponse.isHasError())
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            else {
                jsonResponse.setRetCode(listServiceResponse.getRetCode());
                jsonResponse.setRetDesc(listServiceResponse.getRetMessage());
            }

        } catch (BusinessException e) {

            logger.error("标签扫描验真,返回质证书结构化数据", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
