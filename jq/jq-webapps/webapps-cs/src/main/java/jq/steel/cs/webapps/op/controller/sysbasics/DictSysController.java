package jq.steel.cs.webapps.op.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.DictSysAPI;
import jq.steel.cs.services.base.api.vo.DictSysVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  币值定义
 * @Auther: zhaotairan
 */
@RestController
@RequestMapping("/dictSys")
public class DictSysController {

    private static Logger LOG = LoggerFactory.getLogger(DictSysController.class);


    @Autowired
    private DictSysAPI dictSysAPI;


    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictSysByTypeList")
    public JsonResponse dictSysByTypeList(@RequestBody JsonRequest<DictSysVO> jsonRequest){


        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = dictSysAPI.dictSysByTypeList(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictSysList")
    public JsonResponse dictSysList(@RequestBody JsonRequest<DictSysVO> jsonRequest){


        JsonResponse jsonResponse = new JsonResponse();
            try {
            jsonResponse = dictSysAPI.dictSysList(jsonRequest);
         }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
    }

        return jsonResponse;
    }
    /**
     * 系统参数 dictSysListByType 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictSysListByType")
    public JsonResponse dictSysListByType(@RequestBody JsonRequest<DictSysVO> jsonRequest){

        LOG.info(" www 系统编码保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = dictSysAPI.dictSysListByType(jsonRequest);
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
    @RequestMapping("/dictSysKeep")
    public JsonResponse dictSysKeep(@RequestBody JsonRequest<List<DictSysVO>> jsonRequest){

        LOG.info(" www 系统编码保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = this.dictSysAPI.dictSysKeep(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }



}
