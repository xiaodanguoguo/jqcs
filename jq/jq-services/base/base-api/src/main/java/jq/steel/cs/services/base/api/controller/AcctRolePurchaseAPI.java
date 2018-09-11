package jq.steel.cs.services.base.api.controller;

import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.base.api.vo.AcctRolePurchaseVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface AcctRolePurchaseAPI {

    /**
     * 修改删除添加  关联信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/keepAcctRolePurchase",method = RequestMethod.POST)
    ServiceResponse<AcctRolePurchaseVO> keepAcctRolePurchase(AcctRolePurchaseVO jsonRequest);


    /**
     * list 查询信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/selectAcctRolePurchase",method = RequestMethod.POST)
    ServiceResponse<List<AcctRolePurchaseVO>> selectAcctRolePurchase(@RequestBody AcctRolePurchaseVO jsonRequest);
}
