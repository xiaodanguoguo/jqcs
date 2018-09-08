package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetRebackApplyVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface CrmMillSheetRebackApplyAPI {

    /**
     *  申请回退
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value ="/rebackApply/applyForRetreat", method = RequestMethod.POST)
    ServiceResponse<Integer> applyForRetreat(@RequestBody JsonRequest<CrmMillSheetRebackApplyVO> jsonRequest);
}
