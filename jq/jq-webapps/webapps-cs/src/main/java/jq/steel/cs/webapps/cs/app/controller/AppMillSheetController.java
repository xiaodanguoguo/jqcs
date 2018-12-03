package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.MillSheetHostsAPI;
import jq.steel.cs.services.cust.api.controller.app.AppMillSheetHostsDetailAPI;
import jq.steel.cs.services.cust.api.vo.MillSheetDownloadVO;
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
    @Autowired
    private AppMillSheetHostsDetailAPI appMillSheetHostsDetailAPI;
    /**
     * App端质证书下载
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/downFile", method = RequestMethod.POST)
    public JsonResponse<MillSheetDownloadVO> downFile(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest) {

        JsonResponse<MillSheetDownloadVO> jsrp = new JsonResponse<>();
        if(jsonRequest == null
                || jsonRequest.getReqBody().getMillSheetNo() == null
                || "".equals(jsonRequest.getReqBody().getMillSheetNo())){
            jsrp.setRetCode("非法操作");
            return jsrp;
        }
        try {
            //修改质证书状态为已下载,同时减少可打印的次数
            appMillSheetHostsDetailAPI.updateMillSheetHostsState(jsonRequest);

            ServiceResponse<MillSheetHostsVO> srpVO = millSheetHostsAPI.downloadForApp(jsonRequest);
            MillSheetHostsVO vo = srpVO.getRetContent();
            String millSheetUrl = vo.getMillSheetUrl();
            String millSheetName = vo.getMillSheetName();
            Integer downableNum = vo.getDownableNum();
            MillSheetDownloadVO downloadVO = new MillSheetDownloadVO();
            if(millSheetUrl == null){
                jsrp.setRetDesc("URL或者文件名为null");
            }else {
                downloadVO.setMillSheetPath(uploadConfig.getDomain()+millSheetUrl+"/"+millSheetName);
                downloadVO.setDownableNum(downableNum);
            }
            jsrp.setRspBody(downloadVO);
            jsrp.setRetCode(JsonResponse.SUCCESS);

            return jsrp;
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            jsrp.setRetCode(JsonResponse.SYS_EXCEPTION);
            e.printStackTrace();
            return jsrp;
        }
    }
}
