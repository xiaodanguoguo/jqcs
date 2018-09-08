package jq.steel.cs.webapps.op.controller.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.AcctRoleGroupRoleAPI;
import jq.steel.cs.services.base.api.controller.RoleGroupAPI;
import jq.steel.cs.services.base.api.vo.AcctRoleGroupRoleVO;
import jq.steel.cs.services.base.api.vo.RoleGroupVO;
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
 * 系统基础模块-  系统功能管理  -  系统角色组定义
 * @Auther: zhaoyuhang
 */

@RestController
@RequestMapping("/roleGroup")
public class RoleGroupController {

    private final static Logger logger = LoggerFactory.getLogger(RoleGroupController.class);

    @Autowired
    private RoleGroupAPI roleGroupAPI;

    @Autowired
    private AcctRoleGroupRoleAPI acctRoleGroupRoleAPI;


    /**
     *  系统角色组分页查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleGroupList",method = RequestMethod.POST)
    public JsonResponse<PageDTO<RoleGroupVO>> roleGroupList(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){

        JsonResponse<PageDTO<RoleGroupVO>> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<PageDTO<RoleGroupVO>> response=roleGroupAPI.roleGroupList(jsonRequest.getReqBody());
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
     *  分类删除验证
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/verificationDeleteRoleGroup",method = RequestMethod.POST)
    public JsonResponse<String> verificationDeleteRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<String> result = new JsonResponse<>();

        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<String> response=roleGroupAPI.verificationDeleteRoleGroup(jsonRequest.getReqBody());
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
     *  系统角色组查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/roleGroupAll",method = RequestMethod.POST)
    public JsonResponse<List<RoleGroupVO>> roleGroupAll(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<List<RoleGroupVO>> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<List<RoleGroupVO>> response=roleGroupAPI.roleGroupAll(jsonRequest.getReqBody());
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
     *  系统角色组修改
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/keepRoleGroup",method = RequestMethod.POST)
    public JsonResponse<RoleGroupVO> keepRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){

        JsonResponse<RoleGroupVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<RoleGroupVO> response=roleGroupAPI.keepRoleGroup(jsonRequest.getReqBody());
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
     *  系统角色组添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/InsertRoleGroup",method = RequestMethod.POST)
    public JsonResponse<RoleGroupVO> InsertRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<RoleGroupVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<RoleGroupVO> response=roleGroupAPI.keepRoleGroup(jsonRequest.getReqBody());
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
     * 系统角色组删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/DeleteRoleGroup",method = RequestMethod.POST)
    public JsonResponse<RoleGroupVO> DeleteRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){

        JsonResponse<RoleGroupVO> result = new JsonResponse<>();
        try {
            //根据service层返回的编码做不同的操作
            ServiceResponse<RoleGroupVO> response=roleGroupAPI.keepRoleGroup(jsonRequest.getReqBody());
            RoleGroupVO reqBody=jsonRequest.getReqBody();
            if(!StringUtils.isEmpty(reqBody.getOpt())){
                if(reqBody.getOpt().equals("delete")){
                    if(!StringUtils.isEmpty(reqBody.getIsDelete())){
                        if(reqBody.getIsDelete().equals("1")){
                            AcctRoleGroupRoleVO acrg=new AcctRoleGroupRoleVO();
                            acrg.setRoleGroupId(reqBody.getRoleGroupId());
                            //角色组删除，角色组与角色关联删除
                            acctRoleGroupRoleAPI.delAcctRoleGroupRole(acrg);
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
}

