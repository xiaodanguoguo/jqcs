package jq.steel.cs.webapps.cs.controller.objection;


import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.ObjectionJieAnAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionJieAnVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/objectionJieAn")
public class ObjectionJieAnController {

    private final static Logger logger = LoggerFactory.getLogger(ObjectionJieAnController.class);

    @Autowired
    private ObjectionJieAnAPI objectionJieAnAPI;

    @Autowired
    UploadConfig uploadConfig;

    /**
     *  上传协议书文件
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public JsonResponse<Integer> upload(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response = objectionJieAnAPI.upload(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;

    }

    /**
     *  异议结案撤销
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/revoke",method = RequestMethod.POST)
    public JsonResponse<Integer> revoke(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<Integer> response = objectionJieAnAPI.revoke(jsonRequest);
            // 根据service层返回的编码做不同的操作
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;

    }

    /**
     *  打印通知单
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/print",method = RequestMethod.POST)
    public ObjectionJieAnVO print(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionJieAnVO>  jsonResponse = new JsonResponse<>();
        try {
            String report = uploadConfig.getReportUrl() +"/"+ uploadConfig.getPathPattern() +"tongzhidan.rpx&claim_no="+jsonRequest.getReqBody().getClaimNo();
            ServiceResponse<ObjectionJieAnVO> serviceResponse = objectionJieAnAPI.print(jsonRequest);
            serviceResponse.getRetContent().setReport(report);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return null;
    }

    /**
     * 查看文件
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/look",method = RequestMethod.POST)
    public ObjectionJieAnVO look(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionJieAnVO>  jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<ObjectionJieAnVO> serviceResponse = objectionJieAnAPI.look(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
            // String agreementPath = serviceResponse.getRetContent().getAgreementPath();
            // return fileIn(new File(agreementPath));
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return null;
    }
}
