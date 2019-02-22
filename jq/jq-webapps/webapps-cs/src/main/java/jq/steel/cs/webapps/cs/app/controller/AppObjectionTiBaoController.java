package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.CrmCustomerInfoAPI;
import jq.steel.cs.services.cust.api.controller.CrmLastuserInfoAPI;
import jq.steel.cs.services.cust.api.controller.ObjectionChuLiAPI;
import jq.steel.cs.services.cust.api.controller.ObjectionTiBaoAPI;
import jq.steel.cs.services.cust.api.vo.*;
import jq.steel.cs.webapps.cs.controller.file.PdfToHtml;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
import jq.steel.cs.webapps.cs.controller.objection.CreatePdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @param:
 * @return:
 * @description:  异议提报
 * @author: lirunze
 * @Date: 2018/9/7
 */
@RestController
@RequestMapping("/app/objectionTiBao")
public class AppObjectionTiBaoController {

    private final static Logger logger = LoggerFactory.getLogger(AppObjectionTiBaoController.class);

    @Autowired
    private ObjectionTiBaoAPI objectionTiBaoAPI;

    @Autowired
    private AcctAPI backMemberAPI;

    @Autowired
    private CrmLastuserInfoAPI crmLastuserInfoAPI;

    @Autowired
    private CrmCustomerInfoAPI crmCustomerInfoAPI;


    @Autowired
    private RoleInfoAPI roleInfoAPI;

    @Autowired
    private ObjectionChuLiAPI objectionChuLiAPI;

    @Autowired
    UploadConfig uploadConfig;


    /**
     * @param:
     * @return:
     * @description: 根据不同状态计数
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping(value = "/count",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoCountVO> getCount(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoCountVO> jsonResponse = new JsonResponse<>();
        String orgType = AssertContext.getOrgType();
        String orgName =AssertContext.getOrgName();
        String acctId = AssertContext.getAcctId();
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        if (list.size()>0){
            jsonRequest.getReqBody().setDeptCodes(list);
        }else {
            jsonRequest.getReqBody().setDeptCodes(null);
        }
        try {
            jsonRequest.getReqBody().setOrgType(orgType);
            jsonRequest.getReqBody().setOrgName(orgName);
            ServiceResponse<ObjectionTiBaoCountVO> serviceResponse = objectionTiBaoAPI.getCount(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        } catch (BusinessException e) {
            logger.error("根据不同状态计数错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("根据不同状态计数错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     *  异议提报列表
     * @param  jsonRequest
     * @return
     *
     * */
    /*@RequestMapping(value = "/submit/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findTiBaoByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findTiBaoByPage(jsonRequest);
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
*/


    /**
     *  异议提报列表
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/submit/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findTiBaoByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        String acctId = AssertContext.getAcctId();
        String orgType = AssertContext.getOrgType();
        String orgName = AssertContext.getOrgName();
        String orgId = AssertContext.getOrgId();
        jsonRequest.getReqBody().setOrgType(orgType);
        jsonRequest.getReqBody().setOrgName(orgName);
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        if (list.size()>0){
            jsonRequest.getReqBody().setDeptCodes(list);
        }else {
            jsonRequest.getReqBody().setDeptCodes(null);
        }
        //销售公司下的客户名称集合
        List<String> customers = new ArrayList<>();
        if(orgType.equals("2")||orgType.equals("3")||orgType.equals("4")){
            jsonRequest.getReqBody().setCustomerId(orgName);
        }
        try {
            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findTiBaoByPage(jsonRequest);
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
     *  app异议跟踪审核查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findShenHeByPageForApp",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findShenHeByPageForApp(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        String acctId = AssertContext.getAcctId();
        String orgType = AssertContext.getOrgType();
        String orgName = AssertContext.getOrgName();
        String orgId = AssertContext.getOrgId();
        jsonRequest.getReqBody().setOrgType(orgType);
        jsonRequest.getReqBody().setOrgName(orgName);
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        if (list.size()>0){
            jsonRequest.getReqBody().setDeptCodes(list);
        }else {
            jsonRequest.getReqBody().setDeptCodes(null);
        }
        //销售公司下的客户名称集合
        List<String> customers = new ArrayList<>();
        if(orgType.equals("2")||orgType.equals("3")||orgType.equals("4")){
            jsonRequest.getReqBody().setCustomerId(orgName);
        }
        try {
            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findShenHeByPageForApp(jsonRequest);
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
     *  异议跟踪列表
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/tailafter/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findgenzongByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        String acctId = AssertContext.getAcctId();
        String orgType = AssertContext.getOrgType();
        String orgName = AssertContext.getOrgName();
        String orgId = AssertContext.getOrgId();
        jsonRequest.getReqBody().setOrgType(orgType);
        jsonRequest.getReqBody().setOrgName(orgName);
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        if (list.size()>0){
            jsonRequest.getReqBody().setDeptCodes(list);
        }else {
            jsonRequest.getReqBody().setDeptCodes(null);
        }
        //销售公司下的客户名称集合
        List<String> customers = new ArrayList<>();
        if(orgType.equals("1")){
           /* ServiceResponse<List<OrgInfoVO>>  hh = orgInfoServiceAPI.findOrgNameByOrgId(orgId);
            for (OrgInfoVO orgInfoVO:hh.getRetContent()){
                customers.add(orgInfoVO.getOrgName());
            }*/
            //设置customerid 为质证书的zkunner

        }
        if(customers.size()>0){
            jsonRequest.getReqBody().setCustomerIds(customers);
        }
        try {

            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findgenzongByPage(jsonRequest);
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
    @RequestMapping(value = "/tailafter/findDetails",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoVO> findDetails(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            String acctId = AssertContext.getAcctId();
            ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
            List<String> list = new ArrayList<>();
            for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
                list.add(roleInfoVO.getRoleCode());
            }
            if (list.size()>0){
                jsonRequest.getReqBody().setDeptCodes(list);
            }else {
                jsonRequest.getReqBody().setDeptCodes(null);
            }
            ServiceResponse<ObjectionTiBaoVO> response = objectionTiBaoAPI.findDetailsForApp(jsonRequest);
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
    public JsonResponse<ObjectionTiBaoVO> update(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            if(5 == jsonRequest.getReqBody().getOptionStuts()){
                jsonRequest.getReqBody().setCustomerId(AssertContext.getOrgCode());
            }
            ServiceResponse<ObjectionTiBaoVO> response = objectionTiBaoAPI.update(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())){
                jsonResponse.setRspBody(response.getRetContent());
            }else if (response.isHasError()) {
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            }else {
                // todo业务异常编码
                jsonResponse.setRetCode("10010010");
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
     *订货单位
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/orderUnit/list",method = RequestMethod.POST)
    public JsonResponse<List<CrmCustomerInfoVO>>  findorderUnit(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmCustomerInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setCustomerId(AssertContext.getOrgCode());
            ServiceResponse<List<CrmCustomerInfoVO>> serviceResponse = crmCustomerInfoAPI.findorderUnitList(jsonRequest);
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
     *订货单位详情
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/orderUnit/info",method = RequestMethod.POST)
    public JsonResponse<CrmCustomerInfoVO> findorderUnitInfo(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmCustomerInfoVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmCustomerInfoVO> serviceResponse = crmCustomerInfoAPI.findorderUnitInfo(jsonRequest);
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
     *使用单位
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/unitOfUse/list",method = RequestMethod.POST)
    public JsonResponse<List<CrmLastuserInfoVO>>  findunitOfUse(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmLastuserInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setCustomerId(AssertContext.getOrgCode());
            ServiceResponse<List<CrmLastuserInfoVO>> serviceResponse = crmLastuserInfoAPI.findunitOfUseList(jsonRequest);
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
     *使用单位详情
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/unitOfUse/info",method = RequestMethod.POST)
    public JsonResponse<CrmLastuserInfoVO> findunitOfUseInfo(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmLastuserInfoVO> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmLastuserInfoVO> serviceResponse = crmLastuserInfoAPI.findunitOfUseInfo(jsonRequest);
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
     * app 异议调用润乾实时生成pdf并且返回url地址最后转html给前台
     *
     * 1、质量异议报告--新建异议保存后，就出现预览报告按钮，可以调用异议报告模板，查看报告及数据。
     2、外部调查报告--外部调查保存后，就出现预览报告按钮，可以调用外部调查报告模板，查看报告及数据。
     3、内部调查报告--内部调查保存后，就出现预览报告按钮，可以调用内部调查报告模板，查看报告及数据。
     4、确认书--确认书审核界面，加预览按钮，可以直接查看确认书报告。
     5、协议书--协议书编辑和协议书审核界面，可以直接直接点击“预览”按钮来查看协议书模板及数据。
     * */
    @RequestMapping(value = "/look",method = RequestMethod.POST)
    public JsonResponse<ObjectionChuLiVO> look(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        CreatePdf createPdf = new CreatePdf();
        String pathPattern = uploadConfig.getPathPattern();
        JsonResponse<ObjectionChuLiVO>  jsonResponse = new JsonResponse<>();
        String createPdfPath = uploadConfig.getModelUrl();
        jsonRequest.getReqBody().setReport(createPdfPath);
        try {
            ServiceResponse<ObjectionChuLiVO> serviceResponse = objectionChuLiAPI.look(jsonRequest);
            String report = "";
            if (jsonRequest.getReqBody().getTemplateType()==1){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "Y.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"yiyibaogao");
                SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
                String dirT = sdf.format(new Date());
                String dirName = createPdfPath + dirT;
                String pdfurl = dirName+pdfName;
                //pdf文件地址转html地址
                String url = PdfToHtml.PdfToImage(pdfurl,dirName);
                String hh1 = url.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==2) {
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "W.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"waibudiaocha");
                SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
                String dirT = sdf.format(new Date());
                String dirName = createPdfPath + dirT;
                String pdfurl = dirName+pdfName;
                //pdf文件地址转html地址
                String url = PdfToHtml.PdfToImage(pdfurl,dirName);
                String hh1 = url.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==3){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "N.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"neibudiaocha");
                SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
                String dirT = sdf.format(new Date());
                String dirName = createPdfPath + dirT;
                String pdfurl = dirName+pdfName;
                //pdf文件地址转html地址
                String url = PdfToHtml.PdfToImage(pdfurl,dirName);
                String hh1 = url.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==4){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "Q.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"waibudiaocha");
                SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
                String dirT = sdf.format(new Date());
                String dirName = createPdfPath + dirT;
                String pdfurl = dirName+pdfName;
                //pdf文件地址转html地址
                String url = PdfToHtml.PdfToImage(pdfurl,dirName);
                String hh1 = url.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }else if(jsonRequest.getReqBody().getTemplateType()==5){
                String  pdfName = jsonRequest.getReqBody().getClaimNo() + "X.pdf";
                String report1 = createPdf.createPdf(jsonRequest.getReqBody().getClaimNo() ,createPdfPath,pdfName,"xieyishu");
                SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
                String dirT = sdf.format(new Date());
                String dirName = createPdfPath + dirT;
                String pdfurl = dirName+pdfName;
                //pdf文件地址转html地址
                String url = PdfToHtml.PdfToImage(pdfurl,dirName);
                String hh1 = url.replace("/data/kf_web","/res");
                report =uploadConfig.getDomain()+hh1;
            }
            serviceResponse.getRetContent().setReport(report);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("打印报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }




}
