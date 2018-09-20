package jq.steel.cs.webapps.cs.controller.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.AcctOperPrivRelaAPI;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.AcctOperPrivRelaVO;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统基础模块-  系统功能管理  -  系统角色定义
 * @Auther: zhaoyuhang
 */

@RestController
@RequestMapping("/roleInfo")
public class RoleInfoController {

    private final static Logger logger = LoggerFactory.getLogger(RoleInfoController.class);

    @Autowired
    private RoleInfoAPI roleInfoAPI;

    @Autowired
    private AcctOperPrivRelaAPI acctOperPrivRelaAPI;

    /**
     *  系统角色定义 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleInfoList",method = RequestMethod.POST)
    public JsonResponse<PageDTO<RoleInfoVO>> roleInfoList(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<PageDTO<RoleInfoVO>> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<PageDTO<RoleInfoVO>> response=roleInfoAPI.roleInfoList(jsonRequest.getReqBody());
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
     *  系统角色组-角色-树状图查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleInfoTree",method = RequestMethod.POST)
    public JsonResponse<Map> roleInfoTree(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<Map> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
//            jsonRequest.getReqBody().setOrgIdAll(AssertContext.getOrgId());
            if (jsonRequest.getReqBody().getOrgIdAll().equals("1")){
                jsonRequest.getReqBody().setOrgIdAll("101");
            }
            ServiceResponse<Map> response=roleInfoAPI.roleInfoTree(jsonRequest.getReqBody());
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
     *  删除验证是否关联
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/verificationDeleteRoelInfo",method = RequestMethod.POST)
    public JsonResponse<String> verificationDeleteRoelInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<String> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<String> response=roleInfoAPI.verificationDeleteRoelInfo(jsonRequest.getReqBody());
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
     *  系统角色定义 用户关联角色是否关联
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleRoleAcctInfo",method = RequestMethod.POST)
    public JsonResponse<Map> roleRoleAcctInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<Map> result = new JsonResponse<>();
        try {
            //用户ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getAcctId())){
                result.setRetCode("0102005");
                return result;
            }
            //组织ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getOrgId())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<Map> response=roleInfoAPI.roleRoleAcctInfo(jsonRequest.getReqBody());
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
     *  系统角色定义 所有可用角色 接口   模糊组织id查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleInfoAllLike",method = RequestMethod.POST)
    public JsonResponse<List<RoleInfoVO>> roleInfoAllLike(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<List<RoleInfoVO>> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<List<RoleInfoVO>> response=roleInfoAPI.roleInfoAllLike(jsonRequest.getReqBody());
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
     *  系统角色定义 所有可用角色 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleInfoAll",method = RequestMethod.POST)
    public JsonResponse<List<RoleInfoVO>> roleInfoAll(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<List<RoleInfoVO>> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<List<RoleInfoVO>> response=roleInfoAPI.roleInfoAll(jsonRequest.getReqBody());
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
     *  系统角色定义 修改
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/keepRoleInfo",method = RequestMethod.POST)
    public JsonResponse<RoleInfoVO> keepRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<RoleInfoVO> response=roleInfoAPI.keepRoleInfo(jsonRequest.getReqBody());
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
     *  系统角色定义 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/DeleteRoleInfo",method = RequestMethod.POST)
    public JsonResponse<RoleInfoVO> DeleteRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<RoleInfoVO> response=roleInfoAPI.keepRoleInfo(jsonRequest.getReqBody());
            RoleInfoVO reqBody=jsonRequest.getReqBody();
            if(!StringUtils.isEmpty(reqBody.getOpt())) {
                if (reqBody.getOpt().equals("delete")) {
                    if(!StringUtils.isEmpty(reqBody.getIsDelete())){
                        if(reqBody.getIsDelete().equals("1")){
                            AcctOperPrivRelaVO aopr=new AcctOperPrivRelaVO();
                            aopr.setRoleId(reqBody.getRoleId());
                            acctOperPrivRelaAPI.delAcctOperPrivRela(aopr);
                        }
                    }
                }
            }
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
     *  系统角色定义 添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/InsertRoleInfo",method = RequestMethod.POST)
    public JsonResponse<RoleInfoVO> InsertRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<RoleInfoVO> response=roleInfoAPI.keepRoleInfo(jsonRequest.getReqBody());
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

