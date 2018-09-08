package jq.steel.cs.services.base.facade.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.DictUnitTypeVO;
import jq.steel.cs.services.base.facade.service.sysbasics.DictUnitTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  单位类别定义
 * @Auther: zhaotairan
 */
@RestController
public class DictUnitTypeController {

    private static Logger LOG = LoggerFactory.getLogger(DictSysController.class);

    @Autowired
    private DictUnitTypeService dictUnitTypeService;

    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictUnitTypeList")
    public JsonResponse dictSysList(@RequestBody JsonRequest<DictUnitTypeVO> jsonRequest){
        LOG.info("list 参数={}" , JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();
        try {
            PageDTO<DictUnitTypeVO> page=this.dictUnitTypeService.dictUnitTypeController(jsonRequest);
            jsonResponse.setRspBody(page);
        }  catch (Exception e){
            LOG.error("系统参数 list error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }


    /**
     * 系统参数 添加 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictUnitTypeKeep")
    public JsonResponse dictSysKeep(@RequestBody JsonRequest<List<DictUnitTypeVO>> jsonRequest){
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;
        try {
            jsonResponse = this.dictUnitTypeService.dictUnitTypeKeep(jsonRequest);
        } catch (Exception e){
            LOG.error("系统参数 添加 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;
    }

}
