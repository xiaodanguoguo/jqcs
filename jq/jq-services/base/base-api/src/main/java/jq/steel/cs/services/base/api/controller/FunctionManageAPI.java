package jq.steel.cs.services.base.api.controller;


import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.base.api.vo.FunctionManageVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface FunctionManageAPI {


    /**
     * 查询所有
     * @param functionManageVO
     * @return
     */
    @RequestMapping(value = "/functionManageList",method = RequestMethod.POST)
    ServiceResponse<HashMap> functionManageList(@RequestBody FunctionManageVO functionManageVO);

    /**
     * 删除验证是否有关联
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/verificationDeleteFunction",method = RequestMethod.POST)
    ServiceResponse<String> verificationDeleteFunction(@RequestBody FunctionManageVO jsonRequest);

    /**
     * 用户查询已管理和启用的功能
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/functionManageRoleList",method = RequestMethod.POST)
    ServiceResponse<HashMap> functionManageRoleList(@RequestBody FunctionManageVO jsonRequest);

    /**
     * 修改状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/updateFunctionManageStatus",method = RequestMethod.POST)
    ServiceResponse<Integer> updateFunctionManageStatus(@RequestBody List<FunctionManageVO> jsonRequest);


    /**
     * 修改  删除  添加
     * @param jsonRequest
     * @return  FunctionManageVO  返回添加，修改，后的信息  删除，返回上级ID
     */
    @RequestMapping(value = "/keepFunctionManage",method = RequestMethod.POST)
    ServiceResponse<FunctionManageVO> keepFunctionManage(@RequestBody FunctionManageVO jsonRequest);

    /**
     * 功能列表
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/function/list")
    public ServiceResponse<List<FunctionManageVO>> functionList(@RequestBody FunctionManageVO jsonRequest);

    /**
     * 根据用户id获取用户权限
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/function/user/list")
    public ServiceResponse<List<FunctionManageVO>> getUserfunctionList(@RequestBody FunctionManageVO jsonRequest);

}
