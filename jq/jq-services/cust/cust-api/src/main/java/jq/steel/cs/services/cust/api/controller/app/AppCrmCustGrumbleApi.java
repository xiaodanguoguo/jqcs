package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: AppCrmCustGrumbleApi
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 13:26
 */
@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface AppCrmCustGrumbleApi {
    /**
     * @param:
     * @return:
     * @description: 添加客户抱怨/表扬
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/app/grumble/add")
    ServiceResponse<Integer> addCrmCustGrumble(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);


    /**
     * @param:
     * @return:
     * @description: 删除客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/app/grumble/delete")
    ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);


    /**
     * @param:
     * @return:
     * @description: 修改----->反馈
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/app/grumble/update")
    ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);



    /**
     * @param:
     * @return:
     * @description:  查询客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/app/grumble/findByPage")
    ServiceResponse<PageDTO<CrmCustGrumbleVO>> findByPage(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);
}
