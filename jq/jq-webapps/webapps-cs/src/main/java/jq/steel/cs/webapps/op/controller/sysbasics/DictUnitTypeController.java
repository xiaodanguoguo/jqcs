package jq.steel.cs.webapps.op.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.DictUnitTypeAPI;
import jq.steel.cs.services.base.api.vo.DictUnitTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块- 单位类别定义
 * @Auther: zhaotairan
 */
@RestController
@RequestMapping("/dictUnitType")
public class DictUnitTypeController {

    private static Logger LOG = LoggerFactory.getLogger(DictUnitTypeController.class);
    @Autowired
    private DictUnitTypeAPI dictUnitTypeAPI;

    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictUnitTypeList")
    public JsonResponse dictUnitTypeList(@RequestBody JsonRequest<DictUnitTypeVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = dictUnitTypeAPI.dictUnitTypeList(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }





    /**
     * 系统参数 保存 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictUnitTypeKeep")
    public JsonResponse dictUnitTypeKeep(@RequestBody JsonRequest<List<DictUnitTypeVO>> jsonRequest){

        LOG.info(" www 系统编码保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = dictUnitTypeAPI.dictUnitTypeKeep(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }



}
