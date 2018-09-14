package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface MillCoilInfoAPI {
    /**
     *  拆分申请（强制拆分）数据查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value ="/coilinfo/splitFind", method = RequestMethod.POST)
    ServiceResponse<PageDTO<MillCoilInfoVO>> splitFind(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest);

    /**
     * 诉赔提报界面校验钢卷编号是否正确
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/coilinfo/findIsTrue",method = RequestMethod.POST)
    ServiceResponse<MillCoilInfoVO> findIsTrue(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest);
}
