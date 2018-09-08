package jq.steel.cs.services.base.facade.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.FunctionManageVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.service.sysbasics.FunctionManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 系统基础模块-  系统功能定义
 * @Auther: zhaoyuhang
 */
@RestController
public class FunctionManageController {

    private static Logger LOG = LoggerFactory.getLogger(FunctionManageController.class);

    private static Logger logger = LoggerFactory.getLogger(FunctionManageController.class);

    @Autowired
    private FunctionManageService functionManageService;



    /**
     * 系统功能删除验证
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/verificationDeleteFunction")
    public ServiceResponse<String> verificationDeleteFunction(@RequestBody FunctionManageVO jsonRequest){
        ServiceResponse<String> jsonResponse = new ServiceResponse();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            String ver = functionManageService.verificationDeleteFunction(jsonRequest);
            jsonResponse.setRetContent(ver);
        } catch (Exception e) {
            jsonResponse.setException(new BusinessException("0102002", new Object[]{jsonRequest}));
            logger.error(e.getMessage());
        }
        return jsonResponse;
    }

    /**
     * 系统功能管理 list 接口
     * @param functionManageVO
     * @return
     */
    @RequestMapping("/functionManageList")
    public ServiceResponse<HashMap> functionManageList(@RequestBody FunctionManageVO functionManageVO){
        ServiceResponse<HashMap> response = new ServiceResponse<>();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(functionManageVO));
            List<FunctionManageVO> page = functionManageService.functionManageList(functionManageVO);
            HashMap objData = new HashMap();
            objData.put("resultData",page);
            response.setRetContent(objData);
        } catch (Exception e) {
            response.setException(new BusinessException("0102001", new Object[]{functionManageVO}));
            logger.error(e.getMessage());
        }
        return response;
    }

    /**
     * 角色功能查询功能管理树状图 list 接口
     * @param functionManageVO
     * @return
     */
    @RequestMapping("/functionManageRoleList")
    public ServiceResponse<HashMap> functionManageRoleList(@RequestBody FunctionManageVO functionManageVO){
        ServiceResponse<HashMap> response = new ServiceResponse<>();
		try {
			LOG.info("list 参数 = {}", JsonUtil.toJson(functionManageVO));
            if(StringUtils.isEmpty(functionManageVO.getOrgId())){
                response.setRetCode("0102005");
                return response;
            }
            if(StringUtils.isEmpty(functionManageVO.getRoleId())){
                response.setRetCode("0102005");
                return response;
            }
			List<FunctionManageVO> functionManageVOList = functionManageService.functionManageRoleList(functionManageVO);
            HashMap objData = new HashMap();
			objData.put("resultData",functionManageVOList);
            response.setRetContent(objData);
		} catch (Exception e) {
            response.setException(new BusinessException("0102001", new Object[]{functionManageVO}));
            logger.error(e.getMessage());
		}
        return response;
    }


    /**
     * 系统功能管理 修改功能使用状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/updateFunctionManageStatus")
    public ServiceResponse<Integer> updateFunctionManageStatus(@RequestBody List<FunctionManageVO> jsonRequest){
        ServiceResponse<Integer> jsonResponse = new ServiceResponse<>();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            Integer updateStatus = functionManageService.updateFunctionManageStatus(jsonRequest);
            jsonResponse.setRetContent(updateStatus);
        } catch (Exception e) {
            jsonResponse.setException(new BusinessException("0102004", new Object[]{jsonResponse}));
            logger.error(e.getMessage());
        }
        return jsonResponse;
    }

    /**
     * 系统功能管理 修改功能使用状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/keepFunctionManage")
    public ServiceResponse<FunctionManageVO> keepFunctionManage(@RequestBody FunctionManageVO jsonRequest){
        ServiceResponse<FunctionManageVO> jsonResponse = new ServiceResponse<>();
        try {
            //opt update
            if(StringUtils.isEmpty(jsonRequest.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(SysPramType.DELETE.getMsg().equals(jsonRequest.getOpt())) {
                    //功能ID
                    if(StringUtils.isEmpty(jsonRequest.getFunctionId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(SysPramType.UPDATE.getMsg().equals(jsonRequest.getOpt())) {
                    //功能ID
                    if(StringUtils.isEmpty(jsonRequest.getFunctionId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //组织ID
                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //功能名称
                    if(StringUtils.isEmpty(jsonRequest.getFunctionTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //功能路径
                    if(StringUtils.isEmpty(jsonRequest.getFunctionPath())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(SysPramType.INSERT.getMsg().equals(jsonRequest.getOpt())) {
                    //功能名称
                    if(StringUtils.isEmpty(jsonRequest.getFunctionTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //组织ID
                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //功能路径
                    if(StringUtils.isEmpty(jsonRequest.getFunctionPath())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //功能名称是否重复
//                    List<FunctionManageVO> functionManageVO = functionManageService.verificationFunIsTtitle(jsonRequest);
//                    if(CollectionUtils.isEmpty(functionManageVO)){
//
//                    }else{
//                        jsonResponse.setRetCode("0102006");
//                        return jsonResponse;
//                    }
                }
            }

            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            FunctionManageVO functionManageVO = functionManageService.keepFunctionManage(jsonRequest);
            jsonResponse.setRetContent(functionManageVO);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.setException(new BusinessException("0102003", new Object[]{jsonResponse}));
            logger.error(e.getMessage());
        }
        return jsonResponse;
    }

    /**
     * 功能列表
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/function/list")
    public ServiceResponse<List<FunctionManageVO>> functionList(@RequestBody FunctionManageVO jsonRequest){
        logger.info("功能列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<FunctionManageVO>> serviceResponse = new ServiceResponse<>();

        try {
            List<FunctionManageVO> list = functionManageService.getFunctionList(jsonRequest);
            serviceResponse.setRetContent(list);
        } catch (Exception e) {
            logger.error("功能列表错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * 根据用户id获取用户权限
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/function/user/list")
    public ServiceResponse<List<FunctionManageVO>> getUserfunctionList(@RequestBody FunctionManageVO jsonRequest){
        logger.info("功能列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<FunctionManageVO>> serviceResponse = new ServiceResponse<>();

        try {
            List<FunctionManageVO> list = functionManageService.getUserfunctionList(jsonRequest);
            serviceResponse.setRetContent(list);
        } catch (Exception e) {
            logger.error("功能列表错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }
}
