package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface CrmLastuserInfoAPI {

    /**
     * 使用单位提交
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/unitOfUse/unitOfUseInsert", method = RequestMethod.POST)
    ServiceResponse<Integer> unitOfUseInsert(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest);

    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/unitOfUse/findByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<CrmLastuserInfoVO>> findByPage(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest);



    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/unitOfUse/unitOfUseDelete", method = RequestMethod.POST)
    ServiceResponse<Integer> unitOfUseDelete(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest);


    /**
     * 诉赔界面返回默认联系人
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/unitOfUse/findDefault", method = RequestMethod.POST)
    ServiceResponse<CrmLastuserInfoVO> findDefault(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest);

}
