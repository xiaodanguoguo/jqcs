package jq.steel.cs.webapps.cs.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.MillCustomerAPI;
import jq.steel.cs.services.cust.api.vo.MillCustomerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/millCustomer")
public class MillCustomerController {

    private final static Logger logger = LoggerFactory.getLogger(MillCustomerController.class);

    @Autowired
    private MillCustomerAPI millCustomerAPI;

    @RequestMapping(value = "/findList",method = RequestMethod.POST)
    public JsonResponse<List<MillCustomerVO>> preview(@RequestBody JsonRequest<MillCustomerVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<MillCustomerVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<List<MillCustomerVO>> serviceResponse = millCustomerAPI.findList(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
