package jq.steel.cs.webapps.cs.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.MillCoilInfoAPI;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coilinfo")
public class MillCoilInfoController {
    private final static Logger logger = LoggerFactory.getLogger(MillCoilInfoController.class);

    @Autowired
    private MillCoilInfoAPI millCoilInfoAPI;
    /**
     *  拆分申请（强制拆分）数据查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/splitFind",method = RequestMethod.POST)
    public JsonResponse<PageDTO<MillCoilInfoVO>> splitFind(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest){
//        logger.info("分页",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<MillCoilInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<MillCoilInfoVO>> serviceResponse = millCoilInfoAPI.splitFind(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
