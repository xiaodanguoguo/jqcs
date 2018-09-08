package jq.steel.cs.services.base.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.ProcurementInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 采购自荐信息
 * @author 18338
 *
 */
@FeignClient(value = "${ser.name.base}")
public interface ProcurementInfoAPI {
	
	/**
     * 获取采购自荐信息分页列表
     * @param jsonRequest
     * @return
     */
	@RequestMapping(value = "/procurementInfo/getProcurementInfo", method = RequestMethod.POST)
    public ServiceResponse<PageDTO<ProcurementInfoVO>> getProcurementInfo(@RequestBody JsonRequest<ProcurementInfoVO> jsonRequest);
	
	/**
	 * 修改 采购自荐信息
	 * @param jsonRequest
	 * @return
	 */
    @RequestMapping(value = "/procurementInfo/saveProcurementInfo", method = RequestMethod.POST)
	public ServiceResponse<Integer> saveProcurementInfo(@RequestBody JsonRequest<ProcurementInfoVO> jsonRequest);

}
