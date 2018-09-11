package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.MillCustomerVO;
import jq.steel.cs.services.cust.facade.service.millsheet.MillCustomeService;
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

    private static Logger logger = LoggerFactory.getLogger(MillCustomerController.class);

    @Autowired
    private MillCustomeService millCustomeService;

    //条件分页查询
    @RequestMapping(value = "/findList",method = RequestMethod.POST)
    public ServiceResponse<List<MillCustomerVO>> splitInsert(@RequestBody JsonRequest<List<MillCustomerVO>> jsonRequest){
        logger.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<MillCustomerVO>> serviceResponse = new ServiceResponse<>();
        List<MillCustomerVO> reqBody = jsonRequest.getReqBody();
        try{
            List<MillCustomerVO> downUrl = millCustomeService.findList();
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("查询客户名称集合失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }
}
