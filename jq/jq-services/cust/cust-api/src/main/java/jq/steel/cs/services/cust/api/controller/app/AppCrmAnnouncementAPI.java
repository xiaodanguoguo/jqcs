package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmAnnouncementVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * api :CrmAnnouncement
 * @author 
 * @date 2018-9-14
 */

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface AppCrmAnnouncementAPI {
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/save")
	public ServiceResponse<Integer> save(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest);
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/update")
	public ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest);
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/delete")
	public ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest);
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/querydetails")
	public ServiceResponse<CrmAnnouncementVO> queryDetails(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest);
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/querypageresult")
	public ServiceResponse<PageDTO<CrmAnnouncementVO>> queryPageResult(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest);
	
	/**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/keep")
	public ServiceResponse<Integer> keep(@RequestBody JsonRequest<List<CrmAnnouncementVO>> jsonRequest);


	/**
	 * 或取最新的公告
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/app/crmAnnouncement/getNewAnnouncement")
	public ServiceResponse<CrmAnnouncementVO> getNewAnnouncement() ;
    
}