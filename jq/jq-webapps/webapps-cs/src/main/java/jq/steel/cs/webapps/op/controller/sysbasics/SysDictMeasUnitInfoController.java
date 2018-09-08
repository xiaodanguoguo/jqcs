package jq.steel.cs.webapps.op.controller.sysbasics;


import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.SysDictMeasUnitInfoAPI;
import jq.steel.cs.services.base.api.vo.SysDictMeasUnitInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  基础数据定义 - 单位定义
 * @Auther: zhaoyichen
 */
@RestController

public class SysDictMeasUnitInfoController {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingController.class);

    @Autowired
    private SysDictMeasUnitInfoAPI sysDictMeasUnitInfoAPI;

    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysDictMeasUnitInfoList")
    public JsonResponse listSysParam(@RequestBody JsonRequest<SysDictMeasUnitInfoVO> jsonRequest){

        LOG.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse1 = sysDictMeasUnitInfoAPI.SysDictMeasUnitInfoList(jsonRequest);

        return jsonResponse1;
    }

    /**
     * 系统参数 保存 或者删除 或者修改接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysDictMeasUnitInfoKeep")
    public JsonResponse keepSysDictMeasUnitInfo(@RequestBody JsonRequest<List<SysDictMeasUnitInfoVO>> jsonRequest){

        LOG.info(" www 系统编码保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = sysDictMeasUnitInfoAPI.SysDictMeasUnitInfoKeep(jsonRequest);

        return jsonResponse;
    }


    /**
     * 系统参数 保存 或者删除 或者修改接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysDictMeasUnitInfoDel")
    public JsonResponse delSysDictMeasUnitInfo(@RequestBody JsonRequest<SysDictMeasUnitInfoVO> jsonRequest){

        LOG.info(" www 系统编码删除 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = sysDictMeasUnitInfoAPI.SysDictMeasUnitInfoDel(jsonRequest);
        return jsonResponse;
    }
}
