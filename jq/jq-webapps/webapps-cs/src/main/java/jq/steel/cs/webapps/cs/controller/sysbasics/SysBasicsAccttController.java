package jq.steel.cs.webapps.cs.controller.sysbasics;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.excel.ExcelUtils;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.AcctRoleRealApl;
import jq.steel.cs.services.base.api.controller.SysAccInfoAPI;
import jq.steel.cs.services.base.api.vo.AcctInfoExcel;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.AcctRoleRealVO;
import jq.steel.cs.services.base.api.vo.AcctToRoleInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  系统基础 平台账号管理
 * @Auther: wangyu
 */
@RestController
@RequestMapping("/sysAcct")
public class SysBasicsAccttController {

    private static Logger LOG = LoggerFactory.getLogger(SysBasicsAccttController.class);

    //开发版名称 后期 在改
//    private static String[] HEADERS_ACCT_INFO = {"列标题@acctId@columnWidth","列标题@acctId@columnWidth","列标题@acctId@columnWidth","列标题@acctId@columnWidth","列标题@集团编码","列标题@邮箱","列标题@手机号码","列标题@有效期从","列标题@有效期至"};
                                                //列标题1@beanFieldName1@columnWidth
    private static String SHEET_NAME_ACCT_INFO = "角色管理";

    private static String TITLE_ACCT_INFO = "角色管理";

    private static String FILE_NAME_ACCT_INFO = "角色管理报表";

    @Autowired
    private SysAccInfoAPI sysAccInfoAPI;

    @Autowired
    private AcctRoleRealApl acctRoleRealApl;


    /**
     * 登录
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/LoginAcct")
    public JsonResponse LoginAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
//        try{
            jsonResponse = sysAccInfoAPI.LoginAcct(jsonRequest);
//        }catch (BusinessException e){
//            jsonResponse.setRetCode(e.getErrorCode());
//            jsonResponse.setRetDesc(e.getMessage());
//        }

        return jsonResponse;
    }

    /**
     * 用户修改 根据用户查询角色 组织
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getAcctInfo")
    public JsonResponse<AcctInfoVO> getAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<AcctInfoVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgId(AssertContext.getOrgId());
            ServiceResponse<AcctInfoVO> response=sysAccInfoAPI.getAcctInfo(jsonRequest.getReqBody());
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                result.setRspBody(response.getRetContent());
                //如果需要异常信息
            } else {
                if (response.isHasError()) {
                    //系统异常
                    result.setRetCode(JsonResponse.SYS_EXCEPTION);
                    //如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
                } else {
                    //根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                    result.setRetCode(response.getRetCode());
                }
            }
        } catch (FeignException e) {
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据组织查询用户信息 采购员
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/selectOrgIdAcctInfo")
    public JsonResponse<PageDTO<AcctInfoVO>> selectOrgIdAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<PageDTO<AcctInfoVO>> result = new JsonResponse<>();
        try {
            //组织ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOrgId())){
                result.setRetCode("0102005");
                return result;
            }
            //采购员角色类型
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getPurchaseType())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<PageDTO<AcctInfoVO>> response=sysAccInfoAPI.selectOrgIdAcctInfo(jsonRequest.getReqBody());
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                result.setRspBody(response.getRetContent());
                //如果需要异常信息
            } else {
                if (response.isHasError()) {
                    //系统异常
                    result.setRetCode(JsonResponse.SYS_EXCEPTION);
                    //如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
                } else {
                    //根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                    result.setRetCode(response.getRetCode());
                }
            }
        } catch (FeignException e) {
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }


    /**
     * 根据角色查询用户信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/selectRoleIdAcctInfo")
    public JsonResponse<List<AcctInfoVO>> selectRoleIdAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<List<AcctInfoVO>> result = new JsonResponse<>();
        try {
            //角色ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleId())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<List<AcctInfoVO>> response=sysAccInfoAPI.selectRoleIdAcctInfo(jsonRequest.getReqBody());
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                result.setRspBody(response.getRetContent());
                //如果需要异常信息
            } else {
                if (response.isHasError()) {
                    //系统异常
                    result.setRetCode(JsonResponse.SYS_EXCEPTION);
                    //如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
                } else {
                    //根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                    result.setRetCode(response.getRetCode());
                }
            }
        } catch (FeignException e) {
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }


    /**
     * 平台账号 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctList")
    public JsonResponse listSysParam(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.sysAcctList(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 导出 接口
     //* @param json
     * @return
     */
    @RequestMapping("/sysAcctListExcel")
    public JsonResponse excelListSysParam(@RequestParam("name") String json){
        JsonResponse<List<AcctInfoExcel>> jsonResponse = new JsonResponse();
        try{
            AcctInfoVO acctInfoVO = JsonUtil.parseObject(json, AcctInfoVO.class);

            LOG.info("www excel 参数 = {}",json);
            jsonResponse = sysAccInfoAPI.sysParamListExcel(acctInfoVO);

            //转正报表
            List<AcctInfoExcel> resultData =  jsonResponse.getRspBody();

            ExcelUtils.OutPutWorkBookResponse(resultData,TITLE_ACCT_INFO,AcctInfoExcel.class,FILE_NAME_ACCT_INFO);

        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }


        return new JsonResponse();
    }

    /**
     * get
     * @param jsonRequest 就有一个 acctId
     * @return
     */
    @RequestMapping("/getAcct")
    public JsonResponse getSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www 获得详情 关系 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.getSysAcct(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }


    /**
     * get 保存关系
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctKeep")
    public JsonResponse sysAcctKeep(@RequestBody JsonRequest<List<AcctRoleRealVO>> jsonRequest){
        LOG.info("www 保存 关系 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.sysAcctKeep(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 新建用户
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/addAcct")
    public JsonResponse addAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www 新建账号 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.sysAcctAdd(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }


    /**
     * 获得详情
     * @param jsonRequest 就有一个 acctId
     * @return
     */
   @RequestMapping("/getSysAcctInfo")
    public JsonResponse getSysAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www 获得账号详情 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.sysAcctInfoget(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 用户下角色管理 - 查询
     * @param jsonRequest 就有一个 acctId
     * @Auther: zhaoyichen
     */
    @RequestMapping("/sysAcctToAcctRoleRealById")
    public JsonResponse listSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.listSysAcct2Role(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }


    /**
     * 用户下角色管理 - 中间表添加
     * @param jsonRequest 就有一个 acctId
     * @Auther: zhaoyichen
     */
    @RequestMapping("/sysAcctToRoleInfoAdd")
    public JsonResponse addSysAcct2Role(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest){
        LOG.info("www list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;
        try{
            jsonResponse = acctRoleRealApl.addSysAcct2Role(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }
        return jsonResponse;
    }

    /**
     * 用户下角色管理 - 中间表删除
     * @param jsonRequest 就有一个 acctId
     * @Auther: zhaoyichen
     */
    @RequestMapping("/deleteSysAcct2Role2")
    public JsonResponse deleteSysAcct2Role2(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest){
        LOG.info("www list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = acctRoleRealApl.deleteSysAcct2Role2(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 用户下角色管理 - 全删除
     * @param jsonRequest 就有一个 acctId
     * @Auther: zhaoyichen
     */
    @RequestMapping("/sysAcctToRoleInfoDelete")
    public JsonResponse deleteSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.deleteSysAcct2Role(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 用户管理 停用/启用
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctDiscontinuation")
    public JsonResponse sysAcctDiscontinuation(@RequestBody JsonRequest<AcctInfoVO> jsonRequest) {
        LOG.info("www 停用/启用 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = sysAccInfoAPI.sysAcctDiscontinuation(jsonRequest);
        } catch (BusinessException e) {
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }
        return jsonResponse;
    }



    /**
     * 用户管理 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctDeleteById")
    public JsonResponse sysAcctDeleteById(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("www 删除 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysAccInfoAPI.sysAcctDeleteById(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }


    /**
     * 用户管理 新建用户
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctAddUser")
    public JsonResponse sysAcctAddUser(@RequestBody JsonRequest<AcctToRoleInfoVO> jsonRequest){
        LOG.info("www 新建用户 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            if (AssertContext.getAcctType().equals("0")) {
                jsonRequest.getReqBody().setAcctType(1L);
            } else if (AssertContext.getAcctType().equals("1")) {
                jsonRequest.getReqBody().setAcctType(2L);
            } else {
                jsonRequest.getReqBody().setAcctType(2L);
            }
            jsonResponse = sysAccInfoAPI.sysAcctAddUser(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 获取客户类型
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/customerType")
    public JsonResponse<AcctInfoVO>  customerType(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<AcctInfoVO>  jsonResponse = new JsonResponse();
        try{
            AcctInfoVO acctInfoVO = new AcctInfoVO();
            acctInfoVO.setAcctType(Long.valueOf(AssertContext.getAcctType()));
            if (AssertContext.getAcctType().equals("2")) {
                acctInfoVO.setOrgName(AssertContext.getOrgName());
            }
            acctInfoVO.setOrgCode(AssertContext.getOrgCode());
            jsonResponse.setRspBody(acctInfoVO);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * @param:
     * @return:
     * @description: 编辑个人信息
     * @author: lirunze
     * @Date: 2018/9/24
     */
    @RequestMapping("/update/acctInfo")
    public JsonResponse<Integer>  updateAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest) {
        LOG.info("编辑个人信息 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<Integer> serviceResponse = sysAccInfoAPI.updateAcctInfo(jsonRequest);
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
            LOG.error("编辑个人信息错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            LOG.error("编辑个人信息错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

}
