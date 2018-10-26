package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.MillSheetNeedsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface MillSheetNeedsAPI {


    /**
     * 条件分页查询（酒钢）
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheetNeed/findByType", method = RequestMethod.POST)
    ServiceResponse<List<MillSheetNeedsVO>> findByType(@RequestBody JsonRequest<MillSheetNeedsVO> jsonRequest);

}
