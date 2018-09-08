package jq.steel.cs.services.base.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.SysDescriptionVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by MrLi on 2018/7/23.
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface SysDescriptionCodingAPI {

	/**
	 * 分页查询
	 * @param jsonRequest
	 * @return
	 */
    @RequestMapping(value = "/SysDescription/sysDescriptionList",method = RequestMethod.POST)
    ServiceResponse<PageDTO<SysDescriptionVO>> sysDescriptionList(@RequestBody JsonRequest<SysDescriptionVO> jsonRequest);

    /**
	 * opt:增加（insert） 修改（修改） 删除（delete）
	 * @param jsonRequest
	 * @return
	 */
    @RequestMapping(value = "/SysDescription/sysDescriptionKeep",method = RequestMethod.POST)
    ServiceResponse<Integer> sysDescriptionKeep(@RequestBody JsonRequest<List<SysDescriptionVO>> jsonRequest);

    /**
     * 删除数据
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/SysDescription/sysDescriptionDel",method = RequestMethod.POST)
    ServiceResponse<Integer> sysDescriptionDel(@RequestBody JsonRequest<SysDescriptionVO> jsonRequest);
}
