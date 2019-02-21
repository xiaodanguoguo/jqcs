package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @RequestMapping(value = "/app/millsheet/findMillSheetDeatilByQrCode", method = RequestMethod.POST)
    public ServiceResponse<List<CrmMillCoilInfoVO>> getCoilDetailByQrCode(@RequestBody JsonRequest<CrmMillSheetDetailVO> jsonRequest);


    @RequestMapping(value = "/app/millsheet/getSheetMsgs" , method = RequestMethod.POST)
    ServiceResponse<PageDTO<MillSheetHostsVO>> getSheetMsg(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest);

    /**
     *改变质证书为已下载状态,同时减少可下载次数
     * @param jsonRequest
     */
    @RequestMapping(value = "/app/millsheet/updateMillSheetHostsState",method = RequestMethod.POST)
    public void updateMillSheetHostsState(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);

}
