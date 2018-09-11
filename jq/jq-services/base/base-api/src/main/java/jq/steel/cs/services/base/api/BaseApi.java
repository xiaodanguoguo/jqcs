package jq.steel.cs.services.base.api;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.EcmPtProjectVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 测试controller类
 * @Auther: kim
 */
@FeignClient(value = "${ser.name.base}")
public interface BaseApi {

    /**
     * @param:  ecmPtProjectVO
     * @return:  ServiceResponse<PageDTO<EcmPtProjectVO>>
     * @description:  分页查询
     * @author: lirunze
     * @Date: 2018/8/10
     */
    @RequestMapping(path = "/test", method = RequestMethod.POST)
    ServiceResponse<PageDTO<EcmPtProjectVO>> test(@RequestBody JsonRequest<EcmPtProjectVO> jsonRequest);
}
