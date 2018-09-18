package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.app.AppCrmAnnouncementAPI;
import jq.steel.cs.services.cust.api.vo.CrmAnnouncementVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName:      AppCrmAnnouncementController
 * @Description:
 * @Author:         lujiawei
 * @CreateDate:
 */

@RestController
@RequestMapping("/app/Announcement")
public class AppCrmAnnouncementController {
    private static Logger logger = LoggerFactory.getLogger(AppCrmAnnouncementController.class);

    @Autowired
    private AppCrmAnnouncementAPI appCrmAnnouncementAPI;


    /**
     * 或取最新的公告
     * 参数：opt insert、update、delete
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/getNewAnnouncement", method = RequestMethod.POST)
    public JsonResponse<CrmAnnouncementVO> getNewAnnouncement() {
        JsonResponse<CrmAnnouncementVO> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<CrmAnnouncementVO> vo = appCrmAnnouncementAPI.getNewAnnouncement();
            CrmAnnouncementVO retContent = vo.getRetContent();
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
