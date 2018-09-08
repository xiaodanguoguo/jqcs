package jq.steel.cs.services.base.facade.controller.sysbasics;



import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.SysParamVO;
import jq.steel.cs.services.base.facade.service.sysbasics.SysParamCodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  系统编码系统参数 controller
 * @Auther: wangyu
 */
@RestController
public class SysParamCodingController {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingController.class);


    @Autowired
    private SysParamCodingService sysParamCodingService;

    
    
    

    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysParamList")
    public JsonResponse<PageDTO<SysParamVO>> listSysParam(@RequestBody JsonRequest<SysParamVO> jsonRequest){
        JsonResponse<PageDTO<SysParamVO>> jsonResponse = new JsonResponse();
        LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

        try{
            SysParamVO sysParamVO = jsonRequest.getReqBody();

            PageDTO<SysParamVO> page = sysParamCodingService.listSysParam(sysParamVO);
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
    @RequestMapping("/sysParamKeep")
    public JsonResponse<Boolean> keepSysParam(@RequestBody JsonRequest<List<SysParamVO>> jsonRequest){
        LOG.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Boolean> jsonResponse = new JsonResponse();

        try{
            List<SysParamVO> arr = jsonRequest.getReqBody();

            Boolean boo = sysParamCodingService.keepSysParam(arr);
            jsonResponse.setRspBody(boo);
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
    @RequestMapping("/sysParamDel")
    public JsonResponse<Integer> delSysParam(@RequestBody JsonRequest<SysParamVO> jsonRequest){
        LOG.info("del 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse<Integer> jsonResponse = new JsonResponse();

        try{
            SysParamVO sysParamVO = jsonRequest.getReqBody();

            jsonResponse.setRspBody(sysParamCodingService.delSysParam(sysParamVO));
        }catch (Exception e){
            LOG.error("系统参数 删除 error = {}",e);
            throw new BusinessException("0000001");
        }

        return jsonResponse;

    }




}
