package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillLabelVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * api :	AppMillLabelAPI
 * @author 	lujiawei
 * @date 	2018-9-19 15:00
 */
 
@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface AppMillLabelAPI {
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/millLabel/save")
	public ServiceResponse<Integer> save(@RequestBody JsonRequest<MillLabelVO> jsonRequest);
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/millLabel/update")
	public ServiceResponse<Integer> update(@RequestBody JsonRequest<MillLabelVO> jsonRequest);
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/millLabel/delete")
	public ServiceResponse<Integer> delete(@RequestBody JsonRequest<MillLabelVO> jsonRequest);
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/millLabel/querydetails")
	public ServiceResponse<MillLabelVO> queryDetails(@RequestBody JsonRequest<MillLabelVO> jsonRequest);
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/millLabel/querypageresult")
	public ServiceResponse<PageDTO<MillLabelVO>> queryPageResult(@RequestBody JsonRequest<MillLabelVO> jsonRequest);
	
	/**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/millLabel/keep")
	public ServiceResponse<Integer> keep(@RequestBody JsonRequest<List<MillLabelVO>> jsonRequest);



	/**
	 * 标签扫描验真,返回质证书结构化数据
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/app/millLabel/queyByQrCode", method = RequestMethod.POST)
	public ServiceResponse<List<CrmMillCoilInfoVO>>  queryByQrCode(@RequestBody JsonRequest<String> jsonRequest);


}