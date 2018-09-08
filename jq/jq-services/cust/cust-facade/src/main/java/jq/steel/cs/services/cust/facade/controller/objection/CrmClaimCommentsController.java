package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmClaimCommentsVO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.facade.service.objection.CrmClaimCommentsService;
import jq.steel.cs.services.cust.facade.service.objection.CrmLastuserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/claimComments")
public class CrmClaimCommentsController {

    private static Logger logger = LoggerFactory.getLogger(CrmClaimCommentsController.class);
    @Autowired
    private CrmClaimCommentsService crmClaimCommentsService;

    //新增/修改
    @RequestMapping(value = "/evaluate",method = RequestMethod.POST)
    public ServiceResponse<Integer> evaluate(@RequestBody JsonRequest<CrmClaimCommentsVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            CrmClaimCommentsVO crmClaimCommentsVO = jsonRequest.getReqBody();
            Integer integer = crmClaimCommentsService.evaluate(crmClaimCommentsVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("add出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
