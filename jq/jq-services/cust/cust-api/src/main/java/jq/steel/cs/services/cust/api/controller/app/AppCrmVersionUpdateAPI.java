package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmVersionUpdateVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * api :		CrmVersionUpdate
 * @author 		lujiawei
 * @date 		2018-9-13 14:20
 */

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface AppCrmVersionUpdateAPI {
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/save")
	public ServiceResponse<Integer> save(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest);
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/update")
	public ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest);
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/delete")
	public ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest);
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/querydetails")
	public ServiceResponse<CrmVersionUpdateVO> queryDetails(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest);
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/querypageresult")
	public ServiceResponse<PageDTO<CrmVersionUpdateVO>> queryPageResult(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest);
	
	/**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/keep")
	public ServiceResponse<Integer> keep(@RequestBody JsonRequest<List<CrmVersionUpdateVO>> jsonRequest);


	/**
	 * 返回最新的版本信息
	 * @return
	 */
	@RequestMapping("/app/crmVersionUpdate/getNewVersion")
	public ServiceResponse<CrmVersionUpdateVO> newVersion(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest);

}