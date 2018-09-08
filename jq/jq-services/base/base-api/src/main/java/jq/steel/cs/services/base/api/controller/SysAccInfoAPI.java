package jq.steel.cs.services.base.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.AcctInfoExcel;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.AcctRoleRealVO;
import jq.steel.cs.services.base.api.vo.AcctToRoleInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: wangyu
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface SysAccInfoAPI {


    /**
     * 登录
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/LoginAcct",method = RequestMethod.GET)
    JsonResponse LoginAcct(JsonRequest<AcctInfoVO> jsonRequest);

    /**
     * 根据角色查询用户
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/selectRoleIdAcctInfo",method = RequestMethod.POST)
    ServiceResponse<List<AcctInfoVO>> selectRoleIdAcctInfo(@RequestBody AcctInfoVO jsonRequest);

    /**
     * 用户修改 根据用户查询角色 组织
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/getAcctInfo",method = RequestMethod.POST)
    ServiceResponse<AcctInfoVO> getAcctInfo(@RequestBody AcctInfoVO jsonRequest);

    /**
     * 根据组织查询用户 采购员
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/selectOrgIdAcctInfo",method = RequestMethod.POST)
    ServiceResponse<PageDTO<AcctInfoVO>> selectOrgIdAcctInfo(@RequestBody AcctInfoVO jsonRequest);

    /**
     * list
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctList",method = RequestMethod.POST)
    JsonResponse<PageDTO<AcctInfoVO>> sysParamList(@RequestBody JsonRequest<AcctInfoVO> jsonRequest);

    /**
     * excel 导出
     * @param acctInfoVO
     * @return
     */
    @RequestMapping(value = "/sysAcctListExcel",method = RequestMethod.POST)
    JsonResponse<List<AcctInfoExcel>> sysParamListExcel(AcctInfoVO acctInfoVO);


    /**
     * 保存
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctKeep",method = RequestMethod.POST)
    JsonResponse sysAcctKeep(JsonRequest<List<AcctRoleRealVO>> jsonRequest);

    /**
     * 保存 单条
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctAdd",method = RequestMethod.POST)
    JsonResponse sysAcctAdd(JsonRequest<AcctInfoVO> jsonRequest);

    /**
     * 获得详情接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctGet",method = RequestMethod.POST)
    JsonResponse getSysAcct(JsonRequest<AcctInfoVO> jsonRequest);


    /**
     * 获得详情接口 添加 账号
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/getSysAcctInfo",method = RequestMethod.POST)
    JsonResponse sysAcctInfoget(JsonRequest<AcctInfoVO> jsonRequest);


    /**
     * 当前用户下角色管理 - 查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctToAcctRoleRealById",method = RequestMethod.GET)
    JsonResponse listSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest);


    /**
     * 当前用户下角色管理 - 全删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctToRoleInfoDelete",method = RequestMethod.GET)
    JsonResponse deleteSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest);

    /**
     * 用户管理 停用/启用
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctDiscontinuation",method = RequestMethod.GET)
    JsonResponse sysAcctDiscontinuation(JsonRequest<AcctInfoVO> jsonRequest);


    /**
     * 用户管理 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctDeleteById",method = RequestMethod.GET)
    JsonResponse sysAcctDeleteById(JsonRequest<AcctInfoVO> jsonRequest);

    /**
     * 用户管理 新增用户
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctAddUser",method = RequestMethod.GET)
    JsonResponse sysAcctAddUser(JsonRequest<AcctToRoleInfoVO> jsonRequest);

    /**
     * 列表查询
     * @param jsonRequest
     * @return
     */

    @RequestMapping(value = "/sysAcctList",method = RequestMethod.GET)
    JsonResponse sysAcctList(JsonRequest<AcctInfoVO> jsonRequest);

    /**
     * 根据用户名获取查询角色和组织信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getAcctInfo/acctTitle")
    public ServiceResponse<AcctInfoVO> getAcctInfoByTitle(@RequestBody AcctInfoVO jsonRequest);

    /**
     * 客户类型
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/customerType",method = RequestMethod.POST)
    ServiceResponse<AcctInfoVO> customerType(JsonRequest<AcctInfoVO> jsonRequest);
}

