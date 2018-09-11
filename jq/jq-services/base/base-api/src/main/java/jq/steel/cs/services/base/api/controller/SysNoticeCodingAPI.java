package jq.steel.cs.services.base.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.SysNoticeVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by MrLi on 2018/7/19.
 */
@FeignClient(value = "${ser.name.base}") // 这个是服务名
public interface SysNoticeCodingAPI {
	
	/**
	 * 分页查询
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/SysNotice/sysNoticeList", method = RequestMethod.POST)
    ServiceResponse<List<SysNoticeVO>> sysNoticeList(@RequestBody JsonRequest<SysNoticeVO> jsonRequest);

	/**
	 * 查询全部
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/SysNotice/getSysNoticeAll", method = RequestMethod.POST)
    ServiceResponse<Map<String, List<SysNoticeVO>>> getSysNoticeAll(@RequestBody JsonRequest<SysNoticeVO> jsonRequest);

	/**
	 * 查询明细
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/SysNotice/getSysNoticeByKey", method = RequestMethod.POST)
    ServiceResponse<SysNoticeVO> getSysNoticeByKey(@RequestBody JsonRequest<SysNoticeVO> jsonRequest);

	/**
	 * opt:增加（insert） 修改（修改） 删除（delete）
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/SysNotice/sysNoticeKeep", method = RequestMethod.POST)
    ServiceResponse<Integer> sysNoticeKeep(@RequestBody JsonRequest<List<SysNoticeVO>> jsonRequest);

	/**
	 * 删除
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/SysNotice/sysNoticeAdd", method = RequestMethod.POST)
    ServiceResponse<SysNoticeVO> sysNoticeAdd(@RequestBody JsonRequest<SysNoticeVO> jsonRequest);
}
