package jq.steel.cs.webapps.op.controller.objection;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.excel.ExcelUtils;
import com.ebase.utils.excel.ExportExcelUtils;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.ObjectionChuLiAPI;
import jq.steel.cs.services.cust.api.controller.ObjectionDiaoChaAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.webapps.op.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/objectionChuLi")
public class ObjectionChuLiController {

    private final static Logger logger = LoggerFactory.getLogger(ObjectionChuLiController.class);


    @Autowired
    private ObjectionChuLiAPI objectionChuLiAPI;

    @Autowired
    UploadConfig uploadConfig;


    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionChuLiVO>> findByPage(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        JsonResponse<PageDTO<ObjectionChuLiVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<ObjectionChuLiVO>> serviceResponse = objectionChuLiAPI.findByPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     *  公共信息查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public JsonResponse<ObjectionChuLiVO> findAll(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest) {
        JsonResponse<ObjectionChuLiVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            ServiceResponse<ObjectionChuLiVO> response = objectionChuLiAPI.findAll(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else if (ServiceResponse.SUCCESS_CODE.equals("1")){
                jsonResponse.setRetCode("7777777");
                jsonResponse.setRetDesc(response.getRetMessage());
            }
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
     *  协议书保存/提交/审核
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/agreementUpdate",method = RequestMethod.POST)
    public JsonResponse<Integer> agreementUpdate(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            // 根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response = objectionChuLiAPI.agreementUpdate(jsonRequest);
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
     *  异议处理导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public JsonResponse<List<ObjectionChuLiVO>> export(@RequestParam("name") String jsonRequest) {
        JsonResponse<List<ObjectionChuLiVO>> jsonResponse = new JsonResponse<>();
        try {
            List<String> list = JsonUtil.parseObject(jsonRequest,List.class);
            JsonRequest<List<String>> jsonRequest1 = new JsonRequest();
            jsonRequest1.setReqBody(list);
            ServiceResponse<List<ObjectionChuLiVO>> response = objectionChuLiAPI.export(jsonRequest1);
            // 根据service层返回的编码做不同的操作
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                jsonResponse.setRspBody(response.getRetContent());
                //转正报表
                List<String> headers = getParam();
                List<ObjectionChuLiVO> arr =  jsonResponse.getRspBody();
                try {
                    ExportExcelUtils.createExcelDownload("异议处理", "异议处理", "异议处理" +
                            System.currentTimeMillis(), headers.toArray(new String[headers.size()]), arr);

                } catch (Exception e) {
                    logger.error("error = {}",e);
                }

            }   // 如果需要异常信息
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
    private List<String> getParam() {
        List<String> headers = new ArrayList<>();
        headers.add("异议编号@claimNo@4000");
        headers.add("异议状态@claimState@4000");
        headers.add("生产厂家@manufactor@4000");
        headers.add("产品大类@productCategory@4000");
        headers.add("牌号@zph@4000");
        headers.add("规格@specs@4000");
        headers.add("异议量(吨)@objectionNum@8000");
        headers.add("提报日期@presentationDate@4000");
        headers.add("驳回原因@rejectReason@4000");
        headers.add("强制结案原因@reasonsForCompulsoryClosure@4000");
        return headers;
    }


    /**
     *  协议书模板查看/下载
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/agreementLook",method = RequestMethod.POST)
    public ObjectionDiaoChaVO agreementLook(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionChuLiVO>  jsonResponse = new JsonResponse<>();
        try {
            //协议书模板
            String report = uploadConfig.getReportUrl()  +"xieyishu.rpx&claim_no="+jsonRequest.getReqBody().getClaimNo();
            ServiceResponse<ObjectionChuLiVO> serviceResponse = objectionChuLiAPI.agreementLook(jsonRequest);
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
     *  协议书模板下载pdf
     * @param  jsonRequest
     * @return
     *
     * */
    /*@RequestMapping(value = "/agreementModel",method = RequestMethod.POST)
    public ResponseEntity<FileSystemResource> agreementModel(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionChuLiVO>  jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<ObjectionChuLiVO> serviceResponse = objectionChuLiAPI.agreementModel(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
            //String agreementPath = serviceResponse.getRetContent().getAgreementPath();
            //return fileIn(new File(agreementPath));
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return null;
    }*/

    /**
     *  强制结案
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/compulsorySettlement",method = RequestMethod.POST)
    public JsonResponse<Integer> compulsorySettlement(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            // 根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response = objectionChuLiAPI.compulsorySettlement(jsonRequest);
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
}
