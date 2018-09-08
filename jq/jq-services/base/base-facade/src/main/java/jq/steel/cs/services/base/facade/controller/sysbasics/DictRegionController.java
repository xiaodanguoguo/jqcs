package jq.steel.cs.services.base.facade.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.DictRegionVO;
import jq.steel.cs.services.base.facade.service.sysbasics.DictRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  系统编码系统参数 - 基础数据定义 - 地区定义
 * @Auther: zhaoyichen
 */
@RestController
public class DictRegionController {
    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingController.class);


    @Autowired
    private DictRegionService dictRegionService;


    /**
     * 系统参数 结构树-模糊查询接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping( value = "/dictRegionList" ,method = RequestMethod.POST)
    public JsonResponse listDictRegion(@RequestBody JsonRequest<DictRegionVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            List<DictRegionVO> list = dictRegionService.listDictRegion(jsonRequest);
            jsonResponse.setRspBody(list);
          }catch (Exception e){
             LOG.error("系统参数 list error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }

    /**
     * 系统参数 结构树-父查子 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictRegionTreeList")
    public JsonResponse listDictRegionTree(@RequestBody JsonRequest<DictRegionVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            List<DictRegionVO> list = dictRegionService.listDictRegionTree(jsonRequest);
            jsonResponse.setRspBody(list);
        }catch (Exception e){
            LOG.error("系统参数 list error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }

    /**
     * 系统参数 子查父 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictRegionTreeListSon")
    public JsonResponse listDictRegionTreeSon(@RequestBody JsonRequest<DictRegionVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            List<DictRegionVO> list = dictRegionService.listDictRegionTreeSon(jsonRequest);
            jsonResponse.setRspBody(list);
        }catch (Exception e){
            LOG.error("系统参数 list error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }






   /* *//**
     * 系统参数 保存 或者删除 或者修改接口
     * @param jsonRequest
     * @return
     *//*
    @RequestMapping("/sysParamKeep")
    public JsonResponse keepSysParam(@RequestBody JsonRequest<List<SysParamVO>> jsonRequest){
        LOG.info("keep 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = sysParamCodingService.keepSysParam(jsonRequest);

        return jsonResponse;
    }

    *//**
     * 删除
     * @param jsonRequest
     * @return
     *//*
    @RequestMapping("/sysParamDel")
    public JsonResponse delSysParam(@RequestBody JsonRequest<SysParamVO> jsonRequest){
        LOG.info("del 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = sysParamCodingService.delSysParam(jsonRequest);

        return jsonResponse;

    }*/

}
