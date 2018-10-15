package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.facade.service.objection.CrmLastuserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unitOfUse")
public class CrmLastuserInfoController {

    private static Logger logger = LoggerFactory.getLogger(CrmLastuserInfoController.class);
    @Autowired
    private CrmLastuserInfoService crmLastuserInfoService;

    //新增/修改
    @RequestMapping(value = "/unitOfUseInsert",method = RequestMethod.POST)
    public ServiceResponse<CrmLastuserInfoVO> unitOfUseInsert(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmLastuserInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmLastuserInfoVO crmLastuserInfoVO = jsonRequest.getReqBody();
            CrmLastuserInfoVO crmLastuserInfoVO1 = crmLastuserInfoService.unitOfUseInsert(crmLastuserInfoVO);
            serviceResponse.setRetContent(crmLastuserInfoVO1);
        }catch (BusinessException e){
            logger.error("add出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //条件分页查询
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<CrmLastuserInfoVO>> findByPage(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmLastuserInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmLastuserInfoVO crmLastuserInfoVO = jsonRequest.getReqBody();
            PageDTO<CrmLastuserInfoVO> pageDTO = crmLastuserInfoService.findByPage(crmLastuserInfoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //delete
    @RequestMapping(value = "/unitOfUseDelete",method = RequestMethod.POST)
    public ServiceResponse<Integer> unitOfUseDelete(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            CrmLastuserInfoVO crmLastuserInfoVO = jsonRequest.getReqBody();
            Integer integer = crmLastuserInfoService.unitOfUseDelete(crmLastuserInfoVO);
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
    /*@RequestMapping(value ="/findDefault", method = RequestMethod.POST)
    public ServiceResponse<CrmLastuserInfoVO> findDefault(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmLastuserInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            CrmLastuserInfoVO crmLastuserInfoVO = jsonRequest.getReqBody();
            CrmLastuserInfoVO crmLastuserInfoVO1 = crmLastuserInfoService.findDefault(crmLastuserInfoVO);
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
     * @description:  使用单位
     * @author: lirunze
     * @Date: 2018/9/13
     */
    @RequestMapping(value ="/list", method = RequestMethod.POST)
    ServiceResponse<List<CrmLastuserInfoVO>> findunitOfUseList(JsonRequest<CrmLastuserInfoVO> jsonRequest) {
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<CrmLastuserInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmLastuserInfoVO crmLastuserInfoVO = jsonRequest.getReqBody();
            List<CrmLastuserInfoVO> pageDTO = crmLastuserInfoService.findunitOfUseList(crmLastuserInfoVO);
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
     * @description:  使用单位详情
     * @author: lirunze
     * @Date: 2018/9/13
     */
    @RequestMapping(value ="/info", method = RequestMethod.POST)
    ServiceResponse<CrmLastuserInfoVO> findunitOfUseInfo(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest) {
        ServiceResponse<CrmLastuserInfoVO> serviceResponse = new ServiceResponse<>();
        logger.info("使用单位详情 = {}", JsonUtil.toJson(jsonRequest));
        try {
            CrmLastuserInfoVO crmLastuserInfoVO = jsonRequest.getReqBody();
            CrmLastuserInfoVO crmLastuserInfoVO1 = crmLastuserInfoService.findunitOfUseInfo(crmLastuserInfoVO);
            serviceResponse.setRetContent(crmLastuserInfoVO1);
        }catch (BusinessException e){
            logger.error("使用单位详情出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
