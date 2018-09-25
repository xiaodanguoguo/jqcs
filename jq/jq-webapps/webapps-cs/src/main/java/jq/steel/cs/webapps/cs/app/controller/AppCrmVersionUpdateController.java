package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.app.AppCrmVersionUpdateAPI;
import jq.steel.cs.services.cust.api.vo.CrmVersionUpdateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:      AppCrmVersionUpdateController
 * @Description:
 * @Author:         lujiawei
 * @CreateDate:
 */
@RestController
@RequestMapping("/app/version")
public class AppCrmVersionUpdateController {

    private final static Logger logger = LoggerFactory.getLogger(AppMillSheetHostsDetailController.class);

    @Autowired
    AppCrmVersionUpdateAPI appCrmVersionUpdateAPI;

    /**
     * 返回最新的版本信息
     * @return
     */
    @RequestMapping(value = "/getNewVersion", method = RequestMethod.POST)
    public JsonResponse<CrmVersionUpdateVO> newVersion(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest) {

        JsonResponse<CrmVersionUpdateVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmVersionUpdateVO> crmVersionUpdateVOServiceResponse = appCrmVersionUpdateAPI.newVersion(jsonRequest);
            CrmVersionUpdateVO retContent = crmVersionUpdateVOServiceResponse.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }
}
