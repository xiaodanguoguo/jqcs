package jq.steel.cs.services.base.api.controller;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.DictSysVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: zhaotairan
 */
@FeignClient(value = "${ser.name.base}") //这个是服务名
public interface DictSysAPI {

    /**
     * 查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dictSysList",method = RequestMethod.GET)
    JsonResponse dictSysList(JsonRequest<DictSysVO> jsonRequest);


    /**
     * 添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dictSysKeep",method = RequestMethod.GET)
    JsonResponse dictSysKeep(JsonRequest<List<DictSysVO>> jsonRequest);

    /**
     *  by  类型列表
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dictSysListByType",method = RequestMethod.GET)
    JsonResponse dictSysListByType(JsonRequest<DictSysVO> jsonRequest);


    /**
     * 查询 type 数组查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dictSysByTypeList",method = RequestMethod.POST)
    JsonResponse dictSysByTypeList(JsonRequest<DictSysVO> jsonRequest);
}
