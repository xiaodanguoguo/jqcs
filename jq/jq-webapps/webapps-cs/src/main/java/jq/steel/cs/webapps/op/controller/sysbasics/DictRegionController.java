package jq.steel.cs.webapps.op.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.DictRegionAPI;
import jq.steel.cs.services.base.api.vo.DictRegionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统基础模块-  系统编码系统参数 - 基础数据定义 - 地区定义
 * @Auther: zhaoyichen
 */
@RestController
public class DictRegionController {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingController.class);
    @Autowired
    private DictRegionAPI dictRegionAPI;

    /**
     * 系统参数 list 接口
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping( value = "/dictRegionList" ,method = RequestMethod.POST)
    public JsonResponse listDictRegion(@RequestBody JsonRequest<DictRegionVO> jsonRequest) {

        LOG.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse1 = null;
        try {
            jsonResponse1 = dictRegionAPI.dictRegionList(jsonRequest);
        } catch (BusinessException e) {
            jsonResponse1.setRetCode(e.getErrorCode());
            jsonResponse1.setRetDesc(e.getMessage());
        }

        return jsonResponse1;
    }

    /**
     * 系统参数  地区 - 结构树 接口
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictRegionTreeList")
    public JsonResponse listRegionTree(@RequestBody JsonRequest<DictRegionVO> jsonRequest) {

        LOG.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse1 = null;
        try {
            jsonResponse1 = dictRegionAPI.dictRegionTreeList(jsonRequest);
        } catch (BusinessException e) {
            jsonResponse1.setRetCode(e.getErrorCode());
            jsonResponse1.setRetDesc(e.getMessage());
        }
        return jsonResponse1;
    }

    /**
     * 系统参数  地区 - 结构树 子查父 接口
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/dictRegionTreeListSon")
    public JsonResponse listRegionTreeSon(@RequestBody JsonRequest<DictRegionVO> jsonRequest) {

        LOG.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse1 = null;
        try {
            jsonResponse1 = dictRegionAPI.dictRegionTreeListSon(jsonRequest);
        } catch (BusinessException e) {
            jsonResponse1.setRetCode(e.getErrorCode());
            jsonResponse1.setRetDesc(e.getMessage());
        }
        return jsonResponse1;
    }
}