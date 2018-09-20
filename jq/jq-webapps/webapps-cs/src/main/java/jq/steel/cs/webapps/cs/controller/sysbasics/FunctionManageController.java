package jq.steel.cs.webapps.cs.controller.sysbasics;


import com.ebase.core.AssertContext;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.FunctionManageAPI;
import jq.steel.cs.services.base.api.vo.FunctionManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 系统基础模块-  系统功能管理  -  系统功能定义
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/functionManage")
public class FunctionManageController {

    private final static Logger logger = LoggerFactory.getLogger(FunctionManageController.class);

    @Autowired
    private FunctionManageAPI functionManageAPI;


    /**
     * 系统功能管理 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/functionManageList",method = RequestMethod.POST)
    public JsonResponse<HashMap> functionManageList(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<HashMap> result = new JsonResponse<>();
        try {
            //组织ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOrgIdAll())){
                result.setRetCode("0102005");
                return result;
            }

            FunctionManageVO functionManageVO=jsonRequest.getReqBody();
            functionManageVO.setAcctId(AssertContext.getAcctId());

            //根据service层返回的编码做不同的操作
            ServiceResponse<HashMap> response=functionManageAPI.functionManageList(functionManageVO);
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     * 验证是否删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/verificationDeleteFunction",method = RequestMethod.POST)
    public JsonResponse<String> verificationDeleteFunction(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<String> result = new JsonResponse<>();
        try {
            //功能ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionId())){
                result.setRetCode("0102005");
                return result;
            }
            ServiceResponse<String> response = functionManageAPI.verificationDeleteFunction(jsonRequest.getReqBody());
            //根据service层返回的编码做不同的操作
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     * 系统功能管理 用户查询功能 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/functionManageRoleList",method = RequestMethod.POST)
    public JsonResponse<HashMap> functionManageRoleList(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<HashMap> result = new JsonResponse<>();
        try {
            //组织ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOrgId())){
                result.setRetCode("0102005");
                return result;
            }
            //角色ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleId())){
                result.setRetCode("0102005");
                return result;
            }
            FunctionManageVO functionManageVO=jsonRequest.getReqBody();
            functionManageVO.setAcctId(AssertContext.getAcctId());
            //根据service层返回的编码做不同的操作
            ServiceResponse<HashMap> response=functionManageAPI.functionManageRoleList(functionManageVO);
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     * 系统功能管理 系统功能定义
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/updateFunctionManageStatus",method = RequestMethod.POST)
    public JsonResponse<Integer> updateFunctionManageStatus(@RequestBody JsonRequest<List<FunctionManageVO>> jsonRequest){

        JsonResponse<Integer> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response=functionManageAPI.updateFunctionManageStatus(jsonRequest.getReqBody());
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }


    /**
     * 系统功能删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/DeleteFunctionManage",method = RequestMethod.POST)
    public JsonResponse<FunctionManageVO> DeleteFunctionManage(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<FunctionManageVO> result = new JsonResponse<>();
        try {
            //opt delete
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOpt())){
                result.setRetCode("0102005");
                return result;
            }
            //功能ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionId())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<FunctionManageVO> response=functionManageAPI.keepFunctionManage(jsonRequest.getReqBody());
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     * 系统功能添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/InsertFunctionManage",method = RequestMethod.POST)
    public JsonResponse<FunctionManageVO> InsertFunctionManage(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<FunctionManageVO> result = new JsonResponse<>();
        try {
            //opt insert
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOpt())){
                result.setRetCode("0102005");
                return result;
            }
            //功能名称
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionTitle())){
                result.setRetCode("0102005");
                return result;
            }
            //功能路径
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionPath())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<FunctionManageVO> response=functionManageAPI.keepFunctionManage(jsonRequest.getReqBody());
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     * 系统功能修改
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/keepFunctionManage",method = RequestMethod.POST)
    public JsonResponse<FunctionManageVO> keepFunctionManage(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<FunctionManageVO> result = new JsonResponse<>();
        try {
            //opt update
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOpt())){
                result.setRetCode("0102005");
                return result;
            }
            //功能ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionId())){
                result.setRetCode("0102005");
                return result;
            }
            //功能名称
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionTitle())){
                result.setRetCode("0102005");
                return result;
            }
            //功能路径
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionPath())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<FunctionManageVO> response=functionManageAPI.keepFunctionManage(jsonRequest.getReqBody());
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
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

}
