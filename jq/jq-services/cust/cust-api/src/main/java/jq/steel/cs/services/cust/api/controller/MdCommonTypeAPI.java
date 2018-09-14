package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.MdCommonTypeVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface MdCommonTypeAPI {

    /**
     * 查询质证书地址
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/md/findItemsByTypeId", method = RequestMethod.POST)
    ServiceResponse<List<MdCommonTypeVO>> findItemsByTypeId(@RequestBody JsonRequest<MdCommonTypeVO> jsonRequest);
}
