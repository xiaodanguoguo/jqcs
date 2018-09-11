package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmClaimCommentsVO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface CrmClaimCommentsAPI {

    /**
     * 使用单位提交
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/claimComments/evaluate", method = RequestMethod.POST)
    ServiceResponse<Integer> evaluate(@RequestBody JsonRequest<CrmClaimCommentsVO> jsonRequest);
}
