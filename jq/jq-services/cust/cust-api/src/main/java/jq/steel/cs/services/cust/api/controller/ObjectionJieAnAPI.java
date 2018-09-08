package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.vo.ObjectionJieAnVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface ObjectionJieAnAPI {

    /**
     *  上传协议书文件
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionJieAn/upload",method = RequestMethod.POST)
    ServiceResponse<Integer> upload(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest);

    /**
     *  异议结案撤销
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionJieAn/revoke",method = RequestMethod.POST)
    ServiceResponse<Integer> revoke(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest);

    /**
     *  打印通知单
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionJieAn/print",method = RequestMethod.POST)
    ServiceResponse<ObjectionJieAnVO> print(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest);


    /**
     * 查看文件
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionJieAn/look",method = RequestMethod.POST)
    ServiceResponse<ObjectionJieAnVO> look(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest);
}
