package jq.steel.cs.webapps.cs.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.esa2000.PfxSignShell;
import jq.steel.cs.services.cust.api.controller.MillSecurityInfoAPI;
import jq.steel.cs.services.cust.api.vo.MillSecurityInfoVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

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
            String localUrl = uploadConfig.getUploadPath()+jsonRequest.getReqBody().getFileUrl();
            jsonRequest.getReqBody().setFileUrl(localUrl);
            ServiceResponse<MillSecurityInfoVO> serviceResponse = millSecurityInfoAPI.fuJian(jsonRequest);
            PfxSignShell signShell = new PfxSignShell(); // 验证PDF文件内的签名是否有效
            if(signShell.verifySign(localUrl)){
                signShell.close();
                boolean success = deleteDir(new File(localUrl));
                if (success) {
                    System.out.println("Successfully deleted populated directory: " + localUrl);
                } else {
                    System.out.println("Failed to delete populated directory: " + localUrl);
                }
                serviceResponse.getRetContent().setExplain("文档内签名有效，验真成功！");
            }else {
                serviceResponse.getRetContent().setExplain("文档内签名被篡改，验真失败！");
            }

            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir
     *            将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     *         deletion fails, the method stops attempting to delete and returns
     *         "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
