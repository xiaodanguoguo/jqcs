package jq.steel.cs.webapps.op.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.file.FileUtil;
import jq.steel.cs.services.cust.api.controller.MillSecurityInfoAPI;
import jq.steel.cs.services.cust.api.vo.MillSecurityInfoVO;
import jq.steel.cs.webapps.op.controller.file.FileInfo;
import jq.steel.cs.webapps.op.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/millsheetcheck")
public class MillSecurityInfoController {
    private final static Logger logger = LoggerFactory.getLogger(MillSheetHostsController.class);

    @Autowired
    private MillSecurityInfoAPI millSecurityInfoAPI;

    @Autowired
    UploadConfig uploadConfig;

    /**
     * 防伪码验真
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/fangWeiMa",method = RequestMethod.POST)
    public JsonResponse<MillSecurityInfoVO> fangWeiMa(@RequestBody JsonRequest<MillSecurityInfoVO> jsonRequest){
        //logger.info("分页",JsonUtil.toJson(jsonRequest));
        JsonResponse<MillSecurityInfoVO> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<MillSecurityInfoVO> serviceResponse = millSecurityInfoAPI.fangWeiMa(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     * 附件验真
     * @param
     * @return
     *
     * */
    @PostMapping("/fuJian")
    public JsonResponse<MillSecurityInfoVO> upload(@RequestBody JsonRequest<MillSecurityInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<MillSecurityInfoVO> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<MillSecurityInfoVO> serviceResponse = millSecurityInfoAPI.fuJian(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
