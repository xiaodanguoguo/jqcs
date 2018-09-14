package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}")
public interface CrmCustomerInfoAPI {

    /**
     * 使用单位提交
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/orderUnit/orderUnitInsert", method = RequestMethod.POST)
    ServiceResponse<Integer> orderUnitInsert(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest);

    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/orderUnit/findByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<CrmCustomerInfoVO>> findByPage(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest);



    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/orderUnit/orderUnitDelete", method = RequestMethod.POST)
    ServiceResponse<Integer> orderUnitDelete(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest);


    /**
     * 诉赔界面返回默认联系人
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/orderUnit/findDefault", method = RequestMethod.POST)
    ServiceResponse<CrmCustomerInfoVO> findDefault(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest);

    /**
     * @param:
     * @return:
     * @description:  订货单位列表
     * @author: lirunze
     * @Date: 2018/9/13
     */
    @RequestMapping(value ="/orderUnit/list", method = RequestMethod.POST)
    ServiceResponse<List<CrmCustomerInfoVO>> findorderUnitList(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest);

    /**
     * @param:
     * @return:
     * @description:  详情
     * @author: lirunze
     * @Date: 2018/9/14
     */
    @RequestMapping(value ="/orderUnit/info", method = RequestMethod.POST)
    ServiceResponse<CrmCustomerInfoVO> findorderUnitInfo(JsonRequest<CrmCustomerInfoVO> jsonRequest);
}
