package jq.steel.cs.webapps.cs.controller.objection;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.excel.ExportExcelUtils;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.ObjectionDiaoChaAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/objectionDiaoCha")
public class ObjectionDiaoChaController {
    private final static Logger logger = LoggerFactory.getLogger(ObjectionDiaoChaController.class);


    @Autowired
    private ObjectionDiaoChaAPI objectionDiaoChaAPI;

    @Autowired
    private RoleInfoAPI roleInfoAPI;

    @Autowired
    UploadConfig uploadConfig;



    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionDiaoChaVO>> findByPage(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
//        logger.info("分页",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionDiaoChaVO>> jsonResponse = new JsonResponse<>();

        String acctId = AssertContext.getAcctId();
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        jsonRequest.getReqBody().setDeptCodes(list);
        try {
            ServiceResponse<PageDTO<ObjectionDiaoChaVO>> serviceResponse = objectionDiaoChaAPI.findByPage(jsonRequest);
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
     *  异议调查外部调查内部调查回显数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findDetails",method = RequestMethod.POST)
    public JsonResponse<ObjectionDiaoChaVO> findDetails(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) {
        JsonResponse<ObjectionDiaoChaVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            ServiceResponse<ObjectionDiaoChaVO> response = objectionDiaoChaAPI.findDetails(jsonRequest);
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
     *  内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setAcctName(AssertContext.getAcctName());
            ServiceResponse<Integer> response = objectionDiaoChaAPI.update(jsonRequest);
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
     *  内部调查报告（保存，提交）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateInside",method = RequestMethod.POST)
    public JsonResponse<Integer> updateInside(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setAcctName(AssertContext.getAcctName());
            ServiceResponse<Integer> response = objectionDiaoChaAPI.updateInside(jsonRequest);
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
     *  异议调查导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public JsonResponse<List<ObjectionDiaoChaVO>> export(@RequestParam("name") String jsonRequest) {
        JsonResponse<List<ObjectionDiaoChaVO>> jsonResponse = new JsonResponse<>();
        try {
            List<String> list = JsonUtil.parseObject(jsonRequest,List.class);
            JsonRequest<List<String>> jsonRequest1 = new JsonRequest();
            jsonRequest1.setReqBody(list);
            // 根据service层返回的编码做不同的操作
            ServiceResponse<List<ObjectionDiaoChaVO>> response = objectionDiaoChaAPI.export(jsonRequest1);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                jsonResponse.setRspBody(response.getRetContent());
                //转正报表
                List<String> headers = getParam();
                List<ObjectionDiaoChaVO> arr =  jsonResponse.getRspBody();
                try {
                    ExportExcelUtils.createExcelDownload("异议", "异议", "异议" +
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
        headers.add("生产厂家@deptCode@4000");
        headers.add("产品大类@productName@4000");
        headers.add("牌号@zph@4000");
        headers.add("规格@specs@4000");
        headers.add("异议量(吨)@objectionNum@8000");
        headers.add("提报日期@ast@4000");
        headers.add("受理日期@at@4000");
        headers.add("外部调查时间@eld@4000");
        headers.add("外部调查人@externalLnvestigator@4000");
        headers.add("内部调查时间@ild@4000");
        headers.add("内部调查人@internalLnvestigator@4000");
        headers.add("跟踪原因@followReason@4000");
        headers.add("确认书驳回原因@rejectReason@4000");
        return headers;
    }


    /**
     *  异议调查打印受理单
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/print",method = RequestMethod.POST)
    public JsonResponse<ObjectionDiaoChaVO> print(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionDiaoChaVO>  jsonResponse = new JsonResponse<>();
        try {
            String report = uploadConfig.getReportUrl()  +"shoulidan.rpx&claim_no="+jsonRequest.getReqBody().getClaimNo();
            ServiceResponse<ObjectionDiaoChaVO> serviceResponse = objectionDiaoChaAPI.print(jsonRequest);
            serviceResponse.getRetContent().setReport(report);
            jsonResponse.setRspBody(serviceResponse.getRetContent());

        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     *  调查报告下载pdf
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/downPdf",method = RequestMethod.POST)
    public JsonResponse<ObjectionDiaoChaVO> downPdf(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionDiaoChaVO>  jsonResponse = new JsonResponse<>();
        try {
            //1是外部调查2是内部调查
            String report = "";
            if (jsonRequest.getReqBody().getType()==1){
                report = uploadConfig.getReportUrl()  +"waibudiaocha.rpx&claim_no="+jsonRequest.getReqBody().getClaimNo();
            }else {
                report = uploadConfig.getReportUrl()  +"neibudiaocha.rpx&claim_no="+jsonRequest.getReqBody().getClaimNo();
            }

            ServiceResponse<ObjectionDiaoChaVO> serviceResponse = objectionDiaoChaAPI.downPdf(jsonRequest);
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
     *  调查报告驳回
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/reject",method = RequestMethod.POST)
    public JsonResponse<Integer> reject(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setAcctName(AssertContext.getAcctName());
            ServiceResponse<Integer> response = objectionDiaoChaAPI.reject(jsonRequest);
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
     *  内部调查/外部调查开始结束状态修改
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateState",method = RequestMethod.POST)
    public JsonResponse<Integer> updateState(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setAcctName(AssertContext.getAcctName());
            ServiceResponse<Integer> response = objectionDiaoChaAPI.updateState(jsonRequest);
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
