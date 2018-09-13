package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName:      AppMillSheetHostsDetailAPI
 * @Description:
 * @Author:         lujiawei
 * @CreateDate:     2018/9/13
 */
@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface AppMillSheetHostsDetailAPI {

    @RequestMapping("/app/millsheet/findMillSheetDeatilByMillSheet")
    ServiceResponse<List<CrmMillCoilInfoVO>> getCoilDetailByMillSheet(@RequestBody JsonRequest<CrmMillSheetDetailVO> jsonRequest);

}
