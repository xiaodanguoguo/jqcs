package jq.steel.cs.services.cust.facade.service.app;

import jq.steel.cs.services.cust.api.vo.CrmFeedbackVO;

/**
 * @ClassName: AppCrmFeedbackService
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 14:14
 */
public interface AppCrmFeedbackService {

    /**
     * @param:
     * @return:
     * @description:  添加客户意见反馈
     * @author: lirunze
     * @Date: 2018/9/7
     */
    Integer addCrmFeedback(CrmFeedbackVO crmFeedbackVO);
}
