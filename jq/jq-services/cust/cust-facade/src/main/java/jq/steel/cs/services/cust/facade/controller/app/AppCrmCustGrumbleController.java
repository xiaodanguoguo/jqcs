package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import jq.steel.cs.services.cust.facade.service.app.AppCrmCustGrumbleService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: AppCrmCustGrumbleController
 * @Description: 客户抱怨
 * @Author: lirunze
 * @CreateDate: 2018/9/7 13:23
 */
@RestController
@RequestMapping("/app/grumble")
public class AppCrmCustGrumbleController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AppCrmCustGrumbleService appCrmCustGrumbleService;

    /**
     * @param:
     * @return:
     * @description:  添加客户抱怨
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/add")
    ServiceResponse<Integer> addCrmCustGrumble(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("添加客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();

        try {
            CrmCustGrumbleVO crmCustGrumbleVO = jsonRequest.getReqBody();
            Integer i = appCrmCustGrumbleService.addCrmCustGrumble(crmCustGrumbleVO);
            serviceResponse.setRetContent(i);
        } catch (Exception e) {
            logger.error("添加客户抱怨错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }



    /**
     * @param:
     * @return:
     * @description:  添加客户抱怨
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/delete")
    ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("删除客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustGrumbleVO crmCustGrumbleVO = jsonRequest.getReqBody();
            Integer i = appCrmCustGrumbleService.delete(crmCustGrumbleVO);
            serviceResponse.setRetContent(i);
        } catch (Exception e) {
            logger.error("删除客户抱怨错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }



    /**
     * @param:
     * @return:
     * @description:  修改----->反馈
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/update")
    ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("修改客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustGrumbleVO crmCustGrumbleVO = jsonRequest.getReqBody();
            Integer i = appCrmCustGrumbleService.update(crmCustGrumbleVO);
            serviceResponse.setRetContent(i);
        } catch (Exception e) {
            logger.error("修改客户抱怨错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }




    /**
     * @param:
     * @return:
     * @description:  查询客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/findByPage")
    ServiceResponse<PageDTO<CrmCustGrumbleVO>> findByPage(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("查询客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmCustGrumbleVO>> serviceResponse = new ServiceResponse<>();
        try {
            CrmCustGrumbleVO crmCustGrumbleVO = jsonRequest.getReqBody();
            PageDTO<CrmCustGrumbleVO> list= appCrmCustGrumbleService.findByPage(crmCustGrumbleVO);
            serviceResponse.setRetContent(list);
        } catch (Exception e) {
            logger.error("查询客户抱怨错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return serviceResponse;
    }
}
