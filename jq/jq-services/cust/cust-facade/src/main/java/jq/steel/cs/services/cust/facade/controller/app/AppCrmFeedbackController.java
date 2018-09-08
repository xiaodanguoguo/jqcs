package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmFeedbackVO;
import jq.steel.cs.services.cust.facade.service.app.AppCrmFeedbackService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AppCrmFeedbackController
 * @Description:  意见反馈
 * @Author: lirunze
 * @CreateDate: 2018/9/7 14:01
 */
@RestController
@RequestMapping("/app/feedback")
public class AppCrmFeedbackController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AppCrmFeedbackService appCrmFeedbackService;

    /**
     * @param:
     * @return:
     * @description:  添加客户意见反馈
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping("/add")
    ServiceResponse<Integer> addCrmFeedback(@RequestBody JsonRequest<CrmFeedbackVO> jsonRequest) {
        logger.info("添加客户意见反馈 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();

        try {
            CrmFeedbackVO crmFeedbackVO = jsonRequest.getReqBody();
            Integer i = appCrmFeedbackService.addCrmFeedback(crmFeedbackVO);
            serviceResponse.setRetContent(i);
        } catch (Exception e) {
            logger.error("错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }
}
