package jq.steel.cs.webapps.cs.controller.announcement;

import com.ebase.core.AssertContext;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.app.AppCrmAnnouncementAPI;
import jq.steel.cs.services.cust.api.vo.CrmAnnouncementVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName:      AppCrmAnnouncementController
 * @Description:
 * @Author:         lujiawei
 * @CreateDate:
 */

@RestController
@RequestMapping("/crmAnnouncement")
public class CrmAnnouncementController {
    private static Logger logger = LoggerFactory.getLogger(CrmAnnouncementController.class);

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

    /**
     * 保存
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/save" , method = RequestMethod.POST)
    public JsonResponse<Integer> save(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setCreateBy(AssertContext.getAcctName());
            jsonRequest.getReqBody().setCreateByid(Long.valueOf(AssertContext.getAcctId()));
            jsonRequest.getReqBody().setCreateDt(new Date());
            ServiceResponse<Integer> retContent = appCrmAnnouncementAPI.save(jsonRequest);
            jsonResponse.setRspBody(retContent.getRetContent());
            jsonResponse.setRetCode(retContent.getRetCode());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 更新
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            jsonRequest.getReqBody().setUpdateDt(new Date());
            ServiceResponse<Integer> retContent = appCrmAnnouncementAPI.update(jsonRequest);
            jsonResponse.setRspBody(retContent.getRetContent());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 删除
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    public JsonResponse<Integer> delete(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<Integer> retContent = appCrmAnnouncementAPI.delete(jsonRequest);
            jsonResponse.setRspBody(retContent.getRetContent());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 查询单条记录
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/querydetails" , method = RequestMethod.POST)
    public JsonResponse<CrmAnnouncementVO> queryDetails(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
        JsonResponse<CrmAnnouncementVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmAnnouncementVO> retContent = appCrmAnnouncementAPI.queryDetails(jsonRequest);
            jsonResponse.setRspBody(retContent.getRetContent());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/querypageresult" , method = RequestMethod.POST)
    public JsonResponse<PageDTO<CrmAnnouncementVO>> queryPageResult(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
        JsonResponse<PageDTO<CrmAnnouncementVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<CrmAnnouncementVO>> retContent = appCrmAnnouncementAPI.queryPageResult(jsonRequest);
            jsonResponse.setRspBody(retContent.getRetContent());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


}
