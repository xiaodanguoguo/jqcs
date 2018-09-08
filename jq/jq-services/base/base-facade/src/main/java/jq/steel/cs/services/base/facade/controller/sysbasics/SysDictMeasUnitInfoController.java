package jq.steel.cs.services.base.facade.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.SysDictMeasUnitInfoVO;
import jq.steel.cs.services.base.facade.service.sysbasics.SysDictMeasUnitInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  系统编码系统参数 controller
 * @Auther: zhaoyichen
 */
@RestController

public class SysDictMeasUnitInfoController {
    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingController.class);


    @Autowired
    private SysDictMeasUnitInfoService sysDictMeasUnitInfoService;





    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysDictMeasUnitInfoList")
    public JsonResponse listSysParam(@RequestBody JsonRequest<SysDictMeasUnitInfoVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            PageDTO<SysDictMeasUnitInfoVO> page = sysDictMeasUnitInfoService.listSysDictMeasUnitInfo (jsonRequest);
            jsonResponse.setRspBody(page);
        }catch (Exception e){
            LOG.error("系统参数 list error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }


    /**
     * 系统参数 保存 或者删除 或者修改接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysDictMeasUnitInfoKeep")
    public JsonResponse keepSysParam(@RequestBody JsonRequest<List<SysDictMeasUnitInfoVO>> jsonRequest){
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysDictMeasUnitInfoService.keepSysDictMeasUnitInfo(jsonRequest);
        }catch (Exception e){
            LOG.error("系统参数 保存 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }

    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysDictMeasUnitInfoDel")
    public JsonResponse delSysParam(@RequestBody JsonRequest<SysDictMeasUnitInfoVO> jsonRequest){
        LOG.info("del 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysDictMeasUnitInfoService.delSysDictMeasUnitInfo(jsonRequest);
        }catch (Exception e){
            LOG.error("系统参数 删除 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;

    }

}
