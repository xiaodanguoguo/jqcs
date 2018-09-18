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
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:      AppMillCoilInfoController
 * @Description:
 * @Author:         lujiawei 2018/9/15 17:30
 * @CreateDate:
 */
@RestController
@RequestMapping("/app/millCoilInfo")
public class AppMillCoilInfoController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private MillCoilInfoAPI millCoilInfoAPI;

    @RequestMapping(value = {"/getCoilsByCurrentUser"}, method = RequestMethod.POST)
    public JsonResponse<PageDTO<MillCoilInfoVO>> getCoilsByCurrentUser(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest) {
        //获取当前用户的orgCode
        String orgCode = AssertContext.getOrgCode();

        JsonResponse<PageDTO<MillCoilInfoVO>> jsonResponse = new JsonResponse<>();
        logger.info("用户对应钢卷分页钢卷分页 = {}", JsonUtil.toJson(jsonRequest));

        try {

            jsonRequest.getReqBody().setOrgCode(orgCode);
            ServiceResponse<PageDTO<MillCoilInfoVO>> pageDTOServiceResponse = millCoilInfoAPI.CoilsByCurrentUser(jsonRequest);
            PageDTO<MillCoilInfoVO> retContent = pageDTOServiceResponse.getRetContent();
            jsonResponse.setRspBody(retContent);

        } catch (BusinessException e) {

            logger.error("用户对应钢卷分页钢卷分页 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    @RequestMapping(value = {"/getCoilDetailByCoil"}, method = RequestMethod.POST)
    public JsonResponse<List<CrmMillCoilInfoVO>> getCoilDetailByCoil(@RequestBody JsonRequest<CrmMillCoilInfoVO> jsonRequest) {
        logger.info("钢卷对应物理化学,数据 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmMillCoilInfoVO>> jsonResponse = new JsonResponse<>();

        try {
            jsonRequest.getReqBody().setShowFlag(1);
            ServiceResponse<List<CrmMillCoilInfoVO>> coilDetail = millCoilInfoAPI.getCoilDetail(jsonRequest);
            List<CrmMillCoilInfoVO> retContent = coilDetail.getRetContent();
            jsonResponse.setRspBody(retContent);

        } catch (BusinessException e) {

            logger.error("钢卷对应物理化学,数据 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


}
