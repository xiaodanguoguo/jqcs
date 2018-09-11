package jq.steel.cs.webapps.cs.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.SysPramCodingAPI;
import jq.steel.cs.services.base.api.vo.SysParamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  系统编码系统参数
 * @Auther: wangyu
 */
@RestController
@RequestMapping("/sys/paramCoding")
public class SysParamCodingController {

    private static Logger LOG = LoggerFactory.getLogger(SysParamCodingController.class);


    @Autowired
    private SysPramCodingAPI sysPramCodingAPI;


    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysParamList")
    public JsonResponse listSysParam(@RequestBody JsonRequest<SysParamVO> jsonRequest){

        LOG.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysPramCodingAPI.sysParamList(jsonRequest);
            
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * 系统参数 保存 或者删除 或者修改接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysParamKeep")
    public JsonResponse keepSysParam(@RequestBody JsonRequest<List<SysParamVO>> jsonRequest){

        LOG.info(" www 系统编码保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysPramCodingAPI.sysParamKeep(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }

        return jsonResponse;
    }


    /**
     * 系统参数 保存 或者删除 或者修改接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysParamDel")
    public JsonResponse delSysParam(@RequestBody JsonRequest<SysParamVO> jsonRequest){

        LOG.info(" www 系统编码删除 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysPramCodingAPI.sysParamDel(jsonRequest);
        }catch (BusinessException e){
            jsonResponse.setRetCode(e.getErrorCode());
            jsonResponse.setRetDesc(e.getMessage());
        }


        return jsonResponse;
    }



}
