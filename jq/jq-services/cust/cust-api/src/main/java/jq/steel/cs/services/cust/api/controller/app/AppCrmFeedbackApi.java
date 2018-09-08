package jq.steel.cs.services.cust.api.controller.app;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmFeedbackVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: AppCrmFeedbackApi
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 14:08
 */
@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface AppCrmFeedbackApi {

    /**
     * @param:
     * @return:
     * @description:  添加客户意见反馈
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/app/feedback/add")
    ServiceResponse<Integer> addCrmFeedback(@RequestBody JsonRequest<CrmFeedbackVO> jsonRequest);
}
