package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmAgentInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import jq.steel.cs.services.cust.facade.service.objection.CrmAgentInfoService;
import jq.steel.cs.services.cust.facade.service.objection.CrmCustomerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agentInfo")
public class CrmAgentInfoController {
    private static Logger logger = LoggerFactory.getLogger(CrmLastuserInfoController.class);

    @Autowired
    private CrmAgentInfoService crmAgentInfoService;

    //新增/修改
    @RequestMapping(value = "/agentInfoInsert",method = RequestMethod.POST)
    public ServiceResponse<CrmAgentInfoVO> orderUnitInsert(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmAgentInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmAgentInfoVO crmAgentInfoVO = jsonRequest.getReqBody();
            CrmAgentInfoVO crmAgentInfoVO1 = crmAgentInfoService.agentInfoInsert(crmAgentInfoVO);
            serviceResponse.setRetContent(crmAgentInfoVO1);
        }catch (BusinessException e){
            logger.error("add出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //条件分页查询
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<CrmAgentInfoVO>> findByPage(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest){
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmAgentInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmAgentInfoVO crmAgentInfoVO = jsonRequest.getReqBody();
            PageDTO<CrmAgentInfoVO> pageDTO = crmAgentInfoService.findByPage(crmAgentInfoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //delete
    @RequestMapping(value = "/agentInfoDelete",method = RequestMethod.POST)
    public ServiceResponse<Integer> orderUnitDelete(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            CrmAgentInfoVO crmAgentInfoVO = jsonRequest.getReqBody();
            Integer integer = crmAgentInfoService.agentInfoDelete(crmAgentInfoVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("delete出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }



    /**
     * @param:
     * @return:
     * @description:  订货单位列表
     * @author: wushibin
     * @Date: 2019/3/4
     */
    @RequestMapping(value ="/list", method = RequestMethod.POST)
    ServiceResponse<List<CrmAgentInfoVO>> findagentInfoList(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest) {
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<CrmAgentInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmAgentInfoVO crmAgentInfoVO = jsonRequest.getReqBody();
            List<CrmAgentInfoVO> pageDTO = crmAgentInfoService.findagentInfoList(crmAgentInfoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     * @param:
     * @return:
     * @description:  详情
     * @author: wushibin
     * @Date: 2019/3/4
     */
    @RequestMapping(value ="/info", method = RequestMethod.POST)
    ServiceResponse<CrmAgentInfoVO> findagentInfo(@RequestBody JsonRequest<CrmAgentInfoVO> jsonRequest) {
        logger.info("详情 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmAgentInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmAgentInfoVO crmAgentInfoVO = jsonRequest.getReqBody();
            CrmAgentInfoVO crmAgentInfoVO1 = crmAgentInfoService.findagentInfo(crmAgentInfoVO);
            serviceResponse.setRetContent(crmAgentInfoVO1);
        }catch (BusinessException e){
            logger.error("详情出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

}
