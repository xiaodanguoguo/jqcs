package jq.steel.cs.services.cust.facade.controller.product;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;
import jq.steel.cs.services.cust.facade.service.product.CrmProductInfoService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: CrmProductInfoController
 * @Description: 产品信息
 * @Author: lirunze
 * @CreateDate: 2018/8/20 15:47
 */
@RestController
@RequestMapping("/product/info")
public class CrmProductInfoController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmProductInfoService crmProductInfoService;

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    ServiceResponse<PageDTO<CrmProductInfoVO>> getPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmProductInfoVO>> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            PageDTO<CrmProductInfoVO> page = crmProductInfoService.getPage(crmProductInfoVO);
            serviceResponse.setRetContent(page);
        } catch (Exception e) {
            logger.error("错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }


    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息详情
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/detail"}, method = RequestMethod.POST)
    ServiceResponse<CrmProductInfoVO> getDetail(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息详情 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmProductInfoVO> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            CrmProductInfoVO VO = crmProductInfoService.getDetail(crmProductInfoVO);
            serviceResponse.setRetContent(VO);
        } catch (Exception e) {
            logger.error("产品信息详情错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息删除
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    ServiceResponse<Boolean> deletePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息删除 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            serviceResponse = crmProductInfoService.deletePruduct(crmProductInfoVO);

        } catch (Exception e) {
            logger.error("产品信息删除错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息发布
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/issue"}, method = RequestMethod.POST)
    ServiceResponse<Boolean> issuePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息发布 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            serviceResponse = crmProductInfoService.issuePruduct(crmProductInfoVO);
        } catch (Exception e) {
            logger.error("产品信息发布错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息新增
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    ServiceResponse<Boolean> insertPruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息新增 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            serviceResponse = crmProductInfoService.insertPruduct(crmProductInfoVO);
        } catch (Exception e) {
            logger.error("产品信息新增错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息修改
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    ServiceResponse<Boolean> updatePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息修改 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            serviceResponse = crmProductInfoService.updatePruduct(crmProductInfoVO);
        } catch (Exception e) {
            logger.error("产品信息修改错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: @RequestBody JsonRequest
     * @return:
     * @description: 产品信息移动
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/move"}, method = RequestMethod.POST)
    ServiceResponse<Boolean> movePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息移动 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();
            serviceResponse = crmProductInfoService.movePruduct(crmProductInfoVO);
        } catch (Exception e) {
            logger.error("产品信息移动错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品推介信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/introduct/list")
    ServiceResponse<PageDTO<CrmProductInfoVO>> getIntroductPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品推介信息列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmProductInfoVO>> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();

            PageDTO<CrmProductInfoVO> page = crmProductInfoService.getIntroductPage(crmProductInfoVO);
            serviceResponse.setRetContent(page);
        } catch (Exception e) {
            logger.error("产品推介信息列表错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    @RequestMapping("/introduct/index/list")
    ServiceResponse<PageDTO<CrmProductInfoVO>> getIntroductIndexPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("首页列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmProductInfoVO>> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductInfoVO crmProductInfoVO = jsonRequest.getReqBody();

            PageDTO<CrmProductInfoVO> page = crmProductInfoService.getIntroductIndexPage(crmProductInfoVO);
            serviceResponse.setRetContent(page);
        } catch (Exception e) {
            logger.error("首页列表错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }
}
