package jq.steel.cs.services.base.facade.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.DictSysVO;
import jq.steel.cs.services.base.facade.service.sysbasics.DictSysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  币种定义
 * @Auther: zhaotairan
 */
@RestController
public class DictSysController {

    private static Logger LOG = LoggerFactory.getLogger(DictSysController.class);

    @Autowired
    private DictSysService dictSysService;

    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictSysList")
    public JsonResponse dictSysList(@RequestBody JsonRequest<DictSysVO> jsonRequest){
        LOG.info("list 参数={}" , JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();

        try {
            PageDTO<DictSysVO> page = this.dictSysService.dictSysList(jsonRequest);
            jsonResponse.setRspBody(page);
        }
        catch (Exception e){
                LOG.error("系统参数 list error = {}",e);
                throw new BusinessException("0000001");
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
        LOG.info("list 参数={}" , JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();

        try {
            PageDTO<DictSysVO> page=this.dictSysService.dictSysListByType(jsonRequest);
            jsonResponse.setRspBody(page);
        }  catch (Exception e){
            LOG.error("系统参数 listByType error = {}",e);
            throw new BusinessException("0000001");
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
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;
        try {
            jsonResponse = dictSysService.dictSysKeep(jsonRequest);
        }  catch (Exception e){
            LOG.error("系统参数 添加 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;


    }


    /**
     * 系统字典 type 查询list
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictSysByTypeList")
    public JsonResponse dictSysByTypeList(@RequestBody JsonRequest<DictSysVO> jsonRequest){
        LOG.info("list 参数={}" , JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();

        try {
            List<DictSysVO> page=this.dictSysService.dictSysByTypeList(jsonRequest.getReqBody());
            jsonResponse.setRspBody(page);
        }  catch (Exception e){
            LOG.error("系统参数 listByType error = {}",e);
            throw new BusinessException("0000001");
        }
        return jsonResponse;
    }
}
