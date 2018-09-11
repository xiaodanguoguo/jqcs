package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetRebackApplyVO;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;
import jq.steel.cs.services.cust.facade.service.millsheet.CrmMillSheetRebackApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rebackApply")
public class CrmMillSheetRebackApplyController {
    private static Logger logger = LoggerFactory.getLogger(CrmMillSheetRebackApplyController.class);
    @Autowired
    private CrmMillSheetRebackApplyService crmMillSheetRebackApplyService;

    @RequestMapping("/applyForRetreat")
    public ServiceResponse<Integer> saveProcurementInfo(@RequestBody JsonRequest<CrmMillSheetRebackApplyVO> jsonRequest) {
        ServiceResponse<Integer> response = new ServiceResponse<Integer>();

        CrmMillSheetRebackApplyVO reqBody = jsonRequest.getReqBody();
        CrmMillSheetRebackApply copy = BeanCopyUtil.copy(reqBody, CrmMillSheetRebackApply.class);
        try {
            Integer i = crmMillSheetRebackApplyService.applyForRetreat(copy);
            response.setRetContent(i);
        } catch (BusinessException e) {
            logger.error("回退错误 = {}", e);
            response.setException(new BusinessException("500"));
        }
        return response;
    }
}
