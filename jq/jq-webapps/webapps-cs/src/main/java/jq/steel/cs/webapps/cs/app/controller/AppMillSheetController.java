package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.MillSheetHostsAPI;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/app/millsheet")
public class AppMillSheetController {
    private static Logger logger = LoggerFactory.getLogger(AppCrmAnnouncementController.class);

    @Autowired
    UploadConfig uploadConfig;
    @Autowired
    private MillSheetHostsAPI millSheetHostsAPI;

    /**
     * App端质证书下载
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/downFile", method = RequestMethod.POST)
    public JsonResponse<List<MillSheetHostsVO>> downFile(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest) {

        JsonResponse<List<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
        JsonRequest<List<String>> jsonRequestListStr = new JsonRequest<>();
       List<String> listStr= new ArrayList<>();
        jsonRequestListStr.setReqBody(listStr);
        try {
            String orgName = AssertContext.getOrgName();
            String orgCode = AssertContext.getOrgCode();

            String millSheetNo = jsonRequest.getReqBody().getMillSheetNo();
            List<String> strs = new ArrayList<>();
            strs.add(millSheetNo);
            jsonRequestListStr.setReqBody(strs);
            ServiceResponse<List<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.downFile(jsonRequestListStr);

            List<MillSheetHostsVO> list2 = new ArrayList<>();
            jsonResponse.setRspBody(list2);

            if (serviceResponse.getRetContent().size() > 1) {

                for (MillSheetHostsVO millSheetHostsVO : serviceResponse.getRetContent()) {
                    String createPdfPath = uploadConfig.getDomain();
                    String millSheetPathB = millSheetHostsVO.getMillSheetPath();
                    String url = createPdfPath + millSheetPathB;
                    millSheetHostsVO.setMillSheetUrl(url);
                    list2.add(millSheetHostsVO);
                    /*String millSheetName =  millSheetHostsVO.getMillSheetName();
                String millSheetUrl = serviceResponse.getRetContent().get(0).getMillSheetUrl();*/
            }
                return jsonResponse;
            } else {
                //单个文件下载
                List<MillSheetHostsVO> list1 = serviceResponse.getRetContent();
                String createPdfPath = uploadConfig.getDomain();
                MillSheetHostsVO vo = list1.get(0);
                String millSheetPathA = vo.getMillSheetPath();
                String url = createPdfPath + millSheetPathA;
                vo.setMillSheetUrl(url);
                jsonResponse.setRspBody(list1);

                String millSheetUrl = serviceResponse.getRetContent().get(0).getMillSheetUrl();

                return jsonResponse;
            }
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            e.printStackTrace();
        }
        return jsonResponse;
    }

}
