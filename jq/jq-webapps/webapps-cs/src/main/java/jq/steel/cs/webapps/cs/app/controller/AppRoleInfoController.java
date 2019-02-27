package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
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
@RequestMapping("/app/roleInfo")
public class AppRoleInfoController {

    private final static Logger logger = LoggerFactory.getLogger(AppRoleInfoController.class);

    @Autowired
    private RoleInfoAPI roleInfoAPI;


    @RequestMapping("/getRoleCodeByAcctId")
    public JsonResponse<List<RoleInfoVO>> getRoleCodeByAcctId(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse <List<RoleInfoVO>> jsonResponse = new JsonResponse();
        try {
            String acctId = AssertContext.getAcctId();
            System.out.println("***********************************acctId**************** "+acctId);
            ServiceResponse<List<RoleInfoVO>> list = roleInfoAPI.getRoleCodeByAcctId(acctId);
            jsonResponse.setRspBody(list.getRetContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("0103001");
        }
        return jsonResponse;
    }
}

