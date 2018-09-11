package jq.steel.cs.services.base.api.controller;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.AcctRoleRealVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @Auther: zhaoyichen
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface AcctRoleRealApl {

    /**
     * 当前用户下角色管理 - 添加 zhaoyichen
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctToRoleInfoAdd",method = RequestMethod.GET)
    JsonResponse addSysAcct2Role(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest);


    /**
     * 当前用户下角色管理 - 中间表删除 zhaoyichen
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctToRoleInfoDelete",method = RequestMethod.GET)
    JsonResponse deleteSysAcct2Role2(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest);


    /**
     * 当前用户下角色管理 - 全删除 zhaoyichen
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysAcctToRoleInfoDelete1",method = RequestMethod.GET)
    JsonResponse deleteSysAcct2Role(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest);
}
