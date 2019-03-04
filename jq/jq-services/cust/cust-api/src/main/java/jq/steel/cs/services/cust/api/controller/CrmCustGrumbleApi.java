package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName:
 * @Description:
 * @Author: wushibin
 * @CreateDate:
 */
@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface CrmCustGrumbleApi {
    /**
     * @param:
     * @return:
     * @description: 添加客户抱怨/表扬
     * @author: wushibin
     * @Date: 2018/9/7
     */
    @RequestMapping("/grumble/add")
    ServiceResponse<Integer> addCrmCustGrumble(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);


    /**
     * @param:
     * @return:
     * @description: 删除客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/grumble/delete")
    ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);


    /**
     * @param:
     * @return:
     * @description: 修改----->反馈
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/grumble/update")
    ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);



    /**
     * @param:
     * @return:
     * @description:  查询客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/grumble/findByPage")
    ServiceResponse<PageDTO<CrmCustGrumbleVO>> findByPage(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);
}
