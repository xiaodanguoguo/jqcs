package jq.steel.cs.services.base.api.controller;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.DictRegionVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: zhaoyichen
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface DictRegionAPI {

    @RequestMapping(value = "/dictRegionList",method = RequestMethod.POST)
    JsonResponse dictRegionList(@RequestBody JsonRequest<DictRegionVO> jsonRequest);

    @RequestMapping(value = "/dictRegionTreeList",method = RequestMethod.GET)
    JsonResponse dictRegionTreeList(@RequestBody JsonRequest<DictRegionVO> jsonRequest);

    @RequestMapping(value = "/dictRegionTreeListSon",method = RequestMethod.GET)
    JsonResponse dictRegionTreeListSon(@RequestBody JsonRequest<DictRegionVO> jsonRequest);
}
