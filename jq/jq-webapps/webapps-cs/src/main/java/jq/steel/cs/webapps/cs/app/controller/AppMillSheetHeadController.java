package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.MillCoilInfoAPI;
import jq.steel.cs.services.cust.api.controller.app.MillSheetHeadAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:      AppMillSheetHeadController
 * @Description:
 * @Author:         lujiawei 2018/10/23
 * @CreateDate:
 */
@RestController
@RequestMapping("/app/millSheetHead/")
public class AppMillSheetHeadController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private MillSheetHeadAPI millSheetHeadAPI;

    @RequestMapping(value = {"/findMillSheetHeadByMillSheetNo"}, method = RequestMethod.POST)
    public JsonResponse<MillSheetHeadVO> getSheetHeadByMillSheetNo(@RequestBody JsonRequest<MillSheetHeadVO> jsonRequest) {
        JsonResponse<MillSheetHeadVO> jsonResponse = new JsonResponse<>();
        logger.info("查询质证书信息 = {}", JsonUtil.toJson(jsonRequest));

        try {
            ServiceResponse<MillSheetHeadVO> srVO = millSheetHeadAPI.getMillSheetHeadByMillSheetNo(jsonRequest);
            MillSheetHeadVO retContent = srVO.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("查询质证书信息 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    @RequestMapping(value = {"/findCoilByZcharg"}, method = RequestMethod.POST)
    public JsonResponse<MillCoilInfoVO> getCoilsByZcharg(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest) {
        JsonResponse<MillCoilInfoVO> jsonResponse = new JsonResponse<>();
        logger.info("获取钢卷相关信息 = {}", JsonUtil.toJson(jsonRequest));

        try {
            ServiceResponse<MillCoilInfoVO> srVO = millSheetHeadAPI.getCoilByZcharg(jsonRequest);
            MillCoilInfoVO retContent = srVO.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("获取钢卷相关信息 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
