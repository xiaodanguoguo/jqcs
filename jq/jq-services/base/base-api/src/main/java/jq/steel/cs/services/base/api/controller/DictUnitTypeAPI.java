package jq.steel.cs.services.base.api.controller;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.DictUnitTypeVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: zhaotairan
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface DictUnitTypeAPI {

    //列表
    @RequestMapping(value = "/dictUnitTypeList",method = RequestMethod.GET)
    JsonResponse dictUnitTypeList(JsonRequest<DictUnitTypeVO> jsonRequest);

    /**
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value="dictUnitTypeKeep",method =RequestMethod.GET)
    JsonResponse dictUnitTypeKeep(JsonRequest<List<DictUnitTypeVO>> jsonRequest);
}

