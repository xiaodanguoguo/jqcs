package jq.steel.cs.services.base.facade.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.AcctInfoExcel;
import jq.steel.cs.services.base.api.vo.AcctInfoRoleVO;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.AcctRoleRealVO;
import jq.steel.cs.services.base.api.vo.AcctToRoleInfoVO;
import jq.steel.cs.services.base.facade.service.sysbasics.SysBasicsAcctService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  系统基础 平台账号管理
 * @Auther: wangyu zhaotairn zhaoyichen
 */
@RestController
public class SysBasicsAcctController {

    private static Logger LOG = LoggerFactory.getLogger(SysBasicsAcctController.class);


    @Autowired
    private SysBasicsAcctService sysBasicsAcctService;


    /**
     * 角色查询用户信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/selectOrgIdAcctInfo")
    public ServiceResponse<PageDTO<AcctInfoVO>> selectOrgIdAcctInfo(@RequestBody AcctInfoVO jsonRequest){
        ServiceResponse<PageDTO<AcctInfoVO>> jsonResponse = new ServiceResponse();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

            PageDTO<AcctInfoVO> ver= sysBasicsAcctService.selectOrgIdAcctInfo(jsonRequest);
            jsonResponse.setRetContent(ver);
        } catch (Exception e) {
            throw new BusinessException("0103002");
        }
        return jsonResponse;
    }

    /**
     * 角色查询用户信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/selectRoleIdAcctInfo")
    public ServiceResponse<List<AcctInfoVO>> selectRoleIdAcctInfo(@RequestBody AcctInfoVO jsonRequest){
        ServiceResponse<List<AcctInfoVO>> jsonResponse = new ServiceResponse();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            if(StringUtils.isEmpty(jsonRequest.getRoleId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            List<AcctInfoVO> ver= sysBasicsAcctService.selectRoleIdAcctInfo(jsonRequest);
            jsonResponse.setRetContent(ver);
        } catch (Exception e) {
            throw new BusinessException("0103002");
        }
        return jsonResponse;
    }


    /**
     * 用户修改时查询角色和组织信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getAcctInfo")
    public ServiceResponse<AcctInfoVO> getAcctInfo(@RequestBody AcctInfoVO jsonRequest){
        ServiceResponse<AcctInfoVO> jsonResponse = new ServiceResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));

            AcctInfoVO ver= sysBasicsAcctService.getAcctInfo(jsonRequest);
            jsonResponse.setRetContent(ver);
        } catch (Exception e) {
            throw new BusinessException("0103002");
        }
        return jsonResponse;
    }

    /**
     * 根据用户名获取查询角色和组织信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getAcctInfo/acctTitle")
    public ServiceResponse<AcctInfoVO> getAcctInfoByTitle(@RequestBody AcctInfoVO jsonRequest){
        ServiceResponse<AcctInfoVO> jsonResponse = new ServiceResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));

            AcctInfoVO ver= sysBasicsAcctService.getAcctInfoByAcctTitle(jsonRequest);
            jsonResponse.setRetContent(ver);
        } catch (Exception e) {
            throw new BusinessException("0103002");
        }
        return jsonResponse;
    }

    /**
     * 平台账号 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/LoginAcct")
    public JsonResponse<AcctInfoVO> LoginAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<AcctInfoVO> jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

//        try{
           AcctInfoVO page = sysBasicsAcctService.LoginAcct(jsonRequest.getReqBody());
           jsonResponse.setRspBody(page);
//        }catch (Exception e){
//            LOG.error("查询列表 失败 error = {}",e);
//            throw new BusinessException("0000001");
//        }
        return jsonResponse;
    }

    /**
     * 平台账号 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctList")
    public JsonResponse<PageDTO<AcctInfoVO>> listSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<PageDTO<AcctInfoVO>> jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            PageDTO<AcctInfoVO> page = sysBasicsAcctService.listSysAcct(jsonRequest);
            jsonResponse.setRspBody(page);
        }catch (Exception e){
            LOG.error("查询列表 失败 error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }

    /**
     * 平台账号 导出 接口
     * @param acctInfoVO
     * @return
     */
    @RequestMapping("/sysAcctListExcel")
    public JsonResponse excelListSysParam(@RequestBody AcctInfoVO acctInfoVO ){
        JsonResponse jsonResponse = new JsonResponse();
        LOG.info("导出 参数 = {}", JsonUtil.toJson(acctInfoVO));

        try{
            List<AcctInfoExcel> page = sysBasicsAcctService.sysAcctListExcel(acctInfoVO);
            jsonResponse.setRspBody(page);
        }catch (Exception e){
            LOG.error("导出excel 失败 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }


    /**
     * 获得详情
     * @param jsonRequest 就有个id
     * @return
     */
    @RequestMapping("/sysAcctGet")
    public JsonResponse getSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("详情 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            AcctInfoVO reqBody = jsonRequest.getReqBody();

            AcctInfoRoleVO acctInfoRoleVO = sysBasicsAcctService.getSysAcct(reqBody);
            jsonResponse.setRspBody(acctInfoRoleVO);
        }catch (Exception e){
            LOG.error("获得详情 失败 error = {}",e);

            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }

    /**
     * 平台账号 关系  keep 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctKeep")
    public JsonResponse keepSysAcct(@RequestBody JsonRequest<List<AcctRoleRealVO>> jsonRequest){
        LOG.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.keepSysAcct(jsonRequest);
        }catch (Exception e){
            LOG.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }

    /**
     * 新建接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctAdd")
    public JsonResponse addSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.addSysAcct(jsonRequest);
        }catch (Exception e){
            LOG.error("保存 参数 失败 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }

    /**
     * 获得详情接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getSysAcctInfo")
    public JsonResponse getSysAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("获得详情 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.getSysAcctInfo(jsonRequest);
        }catch (Exception e){
            LOG.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }




    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理查询接口 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping( value = "/sysAcctToAcctRoleRealById" ,method = RequestMethod.POST)
    public JsonResponse listSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            List<AcctInfoVO> list = sysBasicsAcctService.listSysAcct2Role(jsonRequest);
            jsonResponse.setRspBody(list);
        }catch (Exception e){
            LOG.error("系统参数 list error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }


    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理  中间表添加 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctToRoleInfoAdd")
    public JsonResponse addSysAcct2Role(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest){
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.addSysAcct2Role(jsonRequest);
        }catch (Exception e){
            LOG.error("系统参数 添加 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }

    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理  中间表删除 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctToRoleInfoDelete")
    public JsonResponse deleteSysAcct2Role2(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest){
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.deleteSysAcct2Role2(jsonRequest);
        }catch (Exception e){
            LOG.error("系统参数 删除 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }

    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理  删除 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctToRoleInfoDelete1")
    public JsonResponse deleteSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.deleteSysAcct2Role(jsonRequest);
        }catch (Exception e){
            LOG.error("系统参数 删除 error = {}",e);
            throw new BusinessException("0000001");
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
            jsonResponse = sysBasicsAcctService.sysAcctDiscontinuation(jsonRequest);
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
            jsonResponse = sysBasicsAcctService.sysAcctDeleteById(jsonRequest);
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
            jsonResponse = sysBasicsAcctService.sysAcctAddUser(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 客户类型
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/customerType")
    public ServiceResponse<AcctInfoVO> customerType(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        ServiceResponse<AcctInfoVO> serviceResponse = new ServiceResponse<>();
        AcctInfoVO acctInfoVO = sysBasicsAcctService.customerType(jsonRequest.getReqBody());
        serviceResponse.setRetContent(acctInfoVO);
        return  serviceResponse;
    }

    /**
     * @param:
     * @return:
     * @description: 编辑个人信息
     * @author: lirunze
     * @Date: 2018/9/24
     */
    @RequestMapping("/update/acctInfo")
    ServiceResponse<Integer> updateAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest) {
        LOG.info("编辑个人信息 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();

        try {
            AcctInfoVO acctInfoVO = jsonRequest.getReqBody();
            serviceResponse = sysBasicsAcctService.updateAcctInfo(acctInfoVO);
        } catch (Exception e) {
            LOG.error("编辑个人信息错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

}
