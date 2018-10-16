package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionLendgerService;
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

    private static Logger logger = LoggerFactory.getLogger(ObjectionLendgerController.class);


    @Autowired
    private ObjectionLendgerService objectionLendgerService;

    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findLedgerByPage", method = RequestMethod.POST)
    public ServiceResponse<PageDTO<ObjectionLedgerVO>> findLedgerByPage(@RequestBody JsonRequest<ObjectionLedgerVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<ObjectionLedgerVO>> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionLedgerVO objectionLedgerVO = jsonRequest.getReqBody();
            PageDTO<ObjectionLedgerVO> pageDTO = objectionLendgerService.findByPage(objectionLedgerVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
