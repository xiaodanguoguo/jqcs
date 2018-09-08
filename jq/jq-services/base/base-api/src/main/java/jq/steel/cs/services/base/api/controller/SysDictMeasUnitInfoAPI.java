package jq.steel.cs.services.base.api.controller;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.SysDictMeasUnitInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: zhaoyichen
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface SysDictMeasUnitInfoAPI {

    /**
     * list
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysDictMeasUnitInfoList",method = RequestMethod.GET)
    JsonResponse SysDictMeasUnitInfoList(@RequestBody JsonRequest<SysDictMeasUnitInfoVO> jsonRequest);

    /**
     * 保存
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysDictMeasUnitInfoKeep",method = RequestMethod.GET)
    JsonResponse SysDictMeasUnitInfoKeep(@RequestBody JsonRequest<List<SysDictMeasUnitInfoVO>> jsonRequest);

    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysDictMeasUnitInfoDel",method = RequestMethod.GET)
    JsonResponse SysDictMeasUnitInfoDel(@RequestBody JsonRequest<SysDictMeasUnitInfoVO> jsonRequest);
}
