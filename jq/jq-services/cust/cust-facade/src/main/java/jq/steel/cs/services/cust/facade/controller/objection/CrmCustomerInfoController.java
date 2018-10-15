package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
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
@RequestMapping("/orderUnit")
public class CrmCustomerInfoController {
    private static Logger logger = LoggerFactory.getLogger(CrmLastuserInfoController.class);

    @Autowired
    private CrmCustomerInfoService crmCustomerInfoService;

    //新增/修改
    @RequestMapping(value = "/orderUnitInsert",method = RequestMethod.POST)
    public ServiceResponse<CrmCustomerInfoVO> orderUnitInsert(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmCustomerInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustomerInfoVO crmCustomerInfoVO = jsonRequest.getReqBody();
            CrmCustomerInfoVO CrmCustomerInfoVO1 = crmCustomerInfoService.orderUnitInsert(crmCustomerInfoVO);
            serviceResponse.setRetContent(CrmCustomerInfoVO1);
        }catch (BusinessException e){
            logger.error("add出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //条件分页查询
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<CrmCustomerInfoVO>> findByPage(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmCustomerInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustomerInfoVO crmCustomerInfoVO = jsonRequest.getReqBody();
            PageDTO<CrmCustomerInfoVO> pageDTO = crmCustomerInfoService.findByPage(crmCustomerInfoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //delete
    @RequestMapping(value = "/orderUnitDelete",method = RequestMethod.POST)
    public ServiceResponse<Integer> orderUnitDelete(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustomerInfoVO crmCustomerInfoVO = jsonRequest.getReqBody();
            Integer integer = crmCustomerInfoService.orderUnitDelete(crmCustomerInfoVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("delete出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }



    /**
     * 诉赔界面返回默认联系人
     * @param jsonRequest
     * @return
     */
  /*  @RequestMapping(value ="/findDefault", method = RequestMethod.POST)
    public ServiceResponse<CrmCustomerInfoVO> findDefault(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmCustomerInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustomerInfoVO crmCustomerInfoVO = jsonRequest.getReqBody();
            CrmCustomerInfoVO crmLastuserInfoVO1 = crmCustomerInfoService.findDefault(crmCustomerInfoVO);
            serviceResponse.setRetContent(crmLastuserInfoVO1);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }*/

    /**
     * @param:
     * @return:
     * @description:  订货单位列表
     * @author: lirunze
     * @Date: 2018/9/13
     */
    @RequestMapping(value ="/list", method = RequestMethod.POST)
    ServiceResponse<List<CrmCustomerInfoVO>> findorderUnitList(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest) {
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<CrmCustomerInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustomerInfoVO crmCustomerInfoVO = jsonRequest.getReqBody();
            List<CrmCustomerInfoVO> pageDTO = crmCustomerInfoService.findorderUnitList(crmCustomerInfoVO);
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
     * @author: lirunze
     * @Date: 2018/9/14
     */
    @RequestMapping(value ="/info", method = RequestMethod.POST)
    ServiceResponse<CrmCustomerInfoVO> findorderUnitInfo(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest) {
        logger.info("详情 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmCustomerInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustomerInfoVO crmCustomerInfoVO = jsonRequest.getReqBody();
            CrmCustomerInfoVO crmLastuserInfoVO1 = crmCustomerInfoService.findorderUnitInfo(crmCustomerInfoVO);
            serviceResponse.setRetContent(crmLastuserInfoVO1);
        }catch (BusinessException e){
            logger.error("详情出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

}
