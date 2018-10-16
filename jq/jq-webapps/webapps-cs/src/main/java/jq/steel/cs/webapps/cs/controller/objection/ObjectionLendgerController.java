package jq.steel.cs.webapps.cs.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.ObjectionLendgerAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/objectionLendger")
public class ObjectionLendgerController {


    private final static Logger logger = LoggerFactory.getLogger(ObjectionLendgerController.class);

    @Autowired
    private ObjectionLendgerAPI objectionLendgerAPI;



    /**
     *  质量异议统计台账
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findLedgerByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionLedgerVO>> findLedgerByPage(@RequestBody JsonRequest<ObjectionLedgerVO> jsonRequest){
        logger.info("参数={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionLedgerVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<ObjectionLedgerVO>> serviceResponse = objectionLendgerAPI.findLedgerByPage(jsonRequest);
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
