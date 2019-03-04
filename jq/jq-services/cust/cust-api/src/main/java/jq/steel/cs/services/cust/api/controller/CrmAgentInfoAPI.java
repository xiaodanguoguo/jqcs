package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmAgentInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}")
public interface CrmAgentInfoAPI {

    /**
     * 使用单位提交
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/agentInfo/agentInfoInsert", method = RequestMethod.POST)
    ServiceResponse<CrmAgentInfoVO> agentInfoInsert(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest);

    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/agentInfo/findByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<CrmAgentInfoVO>> findByPage(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest);



    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/agentInfo/agentInfoDelete", method = RequestMethod.POST)
    ServiceResponse<Integer> agentInfoDelete(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest);


    /**
     * 诉赔界面返回默认联系人
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/agentInfo/findDefault", method = RequestMethod.POST)
    ServiceResponse<CrmAgentInfoVO> findDefault(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest);

    /**
     * @param:
     * @return:
     * @description:  订货单位列表
     * @author: wushibin
     * @Date: 2019/3/4
     */
    @RequestMapping(value ="/agentInfo/list", method = RequestMethod.POST)
    ServiceResponse<List<CrmAgentInfoVO>> findagentInfoList(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest);

    /**
     * @param:
     * @return:
     * @description:  详情
     * @author: wushibin
     * @Date: 2019/3/4
     */
    @RequestMapping(value ="/agentInfo/info", method = RequestMethod.POST)
    ServiceResponse<CrmAgentInfoVO> findagentInfo(JsonRequest<CrmAgentInfoVO> jsonRequest);
}
