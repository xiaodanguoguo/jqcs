package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.app.AppMillSheetHostsDetailAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.webapps.cs.app.vo.AppAcct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AppMillSheetHostsDetailController {

    private final static Logger logger = LoggerFactory.getLogger(AppMillSheetHostsDetailController.class);

    @Autowired
    private AppMillSheetHostsDetailAPI appMillSheetHostsDetailAPI;

    @RequestMapping(value = "/findMillSheetDeatilBySheet", method = RequestMethod.POST)
    public JsonResponse<List<CrmMillCoilInfoVO>> getCoilDetailByMillSheet(@RequestBody JsonRequest<CrmMillSheetDetailVO> jsonRequest) {
        JsonResponse<List<CrmMillCoilInfoVO>> jsonResponse = new JsonResponse<>();
        if (jsonRequest == null && jsonRequest.getReqBody().getMillSheetNo() == null && jsonRequest.getReqBody().getShowFlag() == null) {
            jsonResponse.setRetCode("非法操作");
            return jsonResponse;
        }

        try {
            ServiceResponse<List<CrmMillCoilInfoVO>> coilDetailByMillSheet = appMillSheetHostsDetailAPI.getCoilDetailByMillSheet(jsonRequest);
            List<CrmMillCoilInfoVO> listAll = coilDetailByMillSheet.getRetContent();
            jsonResponse.setRspBody(listAll);
        } catch (Exception e) {
            logger.error("查询错误:", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
