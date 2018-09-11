package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.MillSecurityInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface MillSecurityInfoAPI {

    /**
     * 防伪码验真
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheetcheck/fangWeiMa", method = RequestMethod.POST)
    ServiceResponse<MillSecurityInfoVO> fangWeiMa(@RequestBody JsonRequest<MillSecurityInfoVO> jsonRequest);

    /**
     * 防伪码验真
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheetcheck/fuJian", method = RequestMethod.POST)
    ServiceResponse<MillSecurityInfoVO> fuJian(@RequestBody JsonRequest<MillSecurityInfoVO> jsonRequest);


}
