package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @description: 添加客户抱怨
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/app/grumble/add")
    ServiceResponse<Integer> addCrmCustGrumble(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest);
}
