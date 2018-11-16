package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface MillSheetHeadAPI {

    @RequestMapping(value = "/app/millsheetHead/findMillSheetHeadByMillSheetNo", method = RequestMethod.POST)
    public ServiceResponse<MillSheetHeadVO> getMillSheetHeadByMillSheetNo(@RequestBody JsonRequest<MillSheetHeadVO> jsonRequest);

    @RequestMapping(value = "/app/millsheetHead/findCoilByZcharg", method = RequestMethod.POST)
    public ServiceResponse<MillCoilInfoVO> getCoilByZcharg(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest);


    @RequestMapping(value = "/app/millsheetHead/findMillSheetBySaleCompany", method = RequestMethod.POST)
    public ServiceResponse<CrmMillSheetSplitInfoVO> findMillSheetForSaleCompany(@RequestBody JsonRequest<CrmMillSheetSplitInfoVO> jsonRequest);
}
