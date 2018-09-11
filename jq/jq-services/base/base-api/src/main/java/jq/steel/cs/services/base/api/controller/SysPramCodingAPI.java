package jq.steel.cs.services.base.api.controller;


import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.SysParamVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: wangyu
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface SysPramCodingAPI {

    /**
     * list
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysParamList",method = RequestMethod.POST)
    JsonResponse<PageDTO<SysParamVO>> sysParamList(@RequestBody JsonRequest<SysParamVO> jsonRequest);

    /**
     * 保存
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysParamKeep",method = RequestMethod.POST)
    JsonResponse<Boolean> sysParamKeep(@RequestBody JsonRequest<List<SysParamVO>> jsonRequest);

    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/sysParamDel",method = RequestMethod.POST)
    JsonResponse<Integer> sysParamDel(@RequestBody JsonRequest<SysParamVO> jsonRequest);


}
