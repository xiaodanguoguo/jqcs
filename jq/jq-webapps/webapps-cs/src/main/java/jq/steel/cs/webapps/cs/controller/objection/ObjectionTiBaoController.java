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
import jq.steel.cs.services.cust.api.controller.ObjectionTiBaoAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/objectionTiBao")
public class ObjectionTiBaoController {

    private final static Logger logger = LoggerFactory.getLogger(ObjectionTiBaoController.class);

    @Autowired
    private ObjectionTiBaoAPI objectionTiBaoAPI;

    @Autowired
    UploadConfig uploadConfig;


    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findByPage(jsonRequest);
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
     *  新增查询/修改查询和详情查询和销售审核查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findDetails",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoVO> findDetails(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<ObjectionTiBaoVO> response = objectionTiBaoAPI.findDetails(jsonRequest);
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
     * 新增修改销售审核保存驳回通过  保存数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setPresentationUser(AssertContext.getAcctId());

            ServiceResponse<Integer> response = objectionTiBaoAPI.update(jsonRequest);
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
     * 提交/删除
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public JsonResponse<Integer> submit(@RequestBody JsonRequest<List<ObjectionTiBaoVO>> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().get(0).setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().get(0).setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().get(0).setPresentationUser(AssertContext.getAcctId());
            ServiceResponse<Integer> response = objectionTiBaoAPI.submit(jsonRequest);
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
     * 导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public JsonResponse<List<ObjectionTiBaoVO>> export(@RequestParam("name") String jsonRequest) {
        JsonResponse<List<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        try {
            List<String> list = JsonUtil.parseObject(jsonRequest,List.class);
            JsonRequest<List<String>> jsonRequest1 = new JsonRequest();
            jsonRequest1.setReqBody(list);
            ServiceResponse<List<ObjectionTiBaoVO>> response = objectionTiBaoAPI.export(jsonRequest1);
            // 根据service层返回的编码做不同的操作
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                jsonResponse.setRspBody(response.getRetContent());
                //转正报表
                List<String> headers = getParam();
                List<ObjectionTiBaoVO> arr =  jsonResponse.getRspBody();
                try {
                    ExportExcelUtils.createExcelDownload("异议提报", "异议提报", "异议提报" +
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
        }

        return jsonResponse;

    }
    private List<String> getParam() {
        List<String> headers = new ArrayList<>();
        headers.add("异议编号@claimNo@4000");
        headers.add("异议状态@claimState@4000");
        headers.add("生产厂家@deptCode@4000");
        headers.add("产品大类@zcpmc@4000");
        headers.add("牌号@zph@4000");
        headers.add("规格@specs@4000");
        headers.add("异议量(吨)@objectionNum@8000");
        headers.add("提报日期@ast@4000");
        headers.add("驳回原因@rejectReason@4000");
        headers.add("强制结案原因@reasonsForCompulsoryClosure@4000");
        return headers;
    }



    /**
     * 协议书下载
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/downFile",method = RequestMethod.POST)
    public void downFile(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest,HttpServletResponse response){
        logger.info("参数={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO>  jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<ObjectionTiBaoVO> serviceResponse = objectionTiBaoAPI.downFile(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
            String agreementPath = serviceResponse.getRetContent().getAgreementPath();
            String agreementName = serviceResponse.getRetContent().getAgreementName();


            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");

            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫zms.jpg,这里是设置名称)
            response.setHeader("Content-Disposition", "attachment;fileName="+ agreementName);

            ServletOutputStream out=null;
            FileInputStream inputStream=null;
            File file = new File(agreementPath);
            try {
                inputStream  = new FileInputStream(file);

                //3.通过response获取ServletOutputStream对象(out)
                out = response.getOutputStream();

                int b = 0;
                byte[] buffer = new byte[512];
                while (b != -1){
                    b = inputStream.read(buffer);
                    if(b != -1){
                        out.write(buffer,0,b);//4.写到输出流(out)中
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try{
                    if(inputStream!=null){
                        inputStream.close();
                    }
                    if(out!=null){
                        out.close();
                        out.flush();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (BusinessException e) {
            logger.error("下载报错", e);
            e.printStackTrace();
        }
    }


    /**
     * 详情页面回显润乾（下载+打印）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/down",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoVO> down(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO>  jsonResponse = new JsonResponse<>();
        try {
            //报告
            String report = uploadConfig.getReportUrl() +"/"+ uploadConfig.getPathPattern() +"baogao.rpx&claim_no="+jsonRequest.getReqBody().getClaimNo();
            ServiceResponse<ObjectionTiBaoVO> serviceResponse = objectionTiBaoAPI.down(jsonRequest);
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
     * 报告附件查看
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/lookPhoto",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoVO> lookPhoto(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            ServiceResponse<ObjectionTiBaoVO> response = objectionTiBaoAPI.lookPhoto(jsonRequest);
            response.getRetContent().setUrl( response.getRetContent().getUrl());

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


}
