package jq.steel.cs.services.cust.facade.controller.category;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmProductCategoryVO;
import jq.steel.cs.services.cust.facade.common.ProductCategoryStatus;
import jq.steel.cs.services.cust.facade.service.category.CrmProductCategoryService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CrmProductCategoryController
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/25 10:02
 */
@RestController
@RequestMapping("/product/category")
public class CrmProductCategoryController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmProductCategoryService crmProductCategoryService;

    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<PageDTO<CrmProductCategoryVO>>
     * @description:  产品分类分页查询
     * @author: lirunze
     * @Date: 2018/8/25
     */
    @RequestMapping("/list")
    ServiceResponse<List<CrmProductCategoryVO>> getPage(@RequestBody JsonRequest<CrmProductCategoryVO> jsonRequest) {
        logger.info("产品分类分页查询 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductCategoryVO crmProductCategoryVO = jsonRequest.getReqBody();
            List<CrmProductCategoryVO> page = crmProductCategoryService.getPage(crmProductCategoryVO);
            serviceResponse.setRetContent(page);
        } catch (Exception e) {
            logger.error("产品分类分页查询错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 保存产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/save")
    ServiceResponse<Boolean> insertCrmProductCategory(@RequestBody JsonRequest<List<CrmProductCategoryVO>> jsonRequest) {
        logger.info("保存产品分类 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            List<CrmProductCategoryVO> list = jsonRequest.getReqBody();
            for (CrmProductCategoryVO vo : list) {
                vo.setStatus(ProductCategoryStatus.SAVE.getCode());
            }
            serviceResponse = crmProductCategoryService.insertCrmProductCategory(list);
        } catch (Exception e) {
            logger.error("保存or提交产品分类错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品分类列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/down/list")
    ServiceResponse<List<CrmProductCategoryVO>> getList() {
        logger.info("产品分类分页查询");
        ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = new ServiceResponse<>();

        try {
            List<CrmProductCategoryVO> list = crmProductCategoryService.getList();
            serviceResponse.setRetContent(list);
        } catch (Exception e) {
            logger.error("产品分类分页查询错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 提交产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/submit")
    ServiceResponse<Boolean> submitCrmProductCategory(@RequestBody JsonRequest<List<CrmProductCategoryVO>> jsonRequest) {
        logger.info("保存or提交产品分类 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            List<CrmProductCategoryVO> list = jsonRequest.getReqBody();
            for (CrmProductCategoryVO vo : list) {
                vo.setStatus(ProductCategoryStatus.SUBMIT.getCode());
            }
            serviceResponse = crmProductCategoryService.insertCrmProductCategory(list);
        } catch (Exception e) {
            logger.error("保存or提交产品分类错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<PageDTO<CrmProductCategoryVO>>
     * @description:  产品分类分页查询
     * @author: lirunze
     * @Date: 2018/8/25
     */
    @RequestMapping("/introduct/list")
    ServiceResponse<List<CrmProductCategoryVO>> getIntroductList() {
        logger.info("产品分类分页查询");
        ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = new ServiceResponse<>();

        try {
            CrmProductCategoryVO crmProductCategoryVO = new CrmProductCategoryVO();
            crmProductCategoryVO.setStatus(ProductCategoryStatus.SUBMIT.getCode());
            List<CrmProductCategoryVO> page = crmProductCategoryService.getPage(crmProductCategoryVO);
            serviceResponse.setRetContent(page);
        } catch (Exception e) {
            logger.error("产品分类分页查询错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }
}
