package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmFeedbackVO;
import jq.steel.cs.services.cust.facade.dao.CrmFeedbackMapper;
import jq.steel.cs.services.cust.facade.model.CrmFeedback;
import jq.steel.cs.services.cust.facade.service.app.AppCrmFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AppCrmFeedbackServiceImpl
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 14:14
 */
@Service("appCrmFeedbackService")
public class AppCrmFeedbackServiceImpl implements AppCrmFeedbackService {

    @Autowired
    private CrmFeedbackMapper crmFeedbackMapper;

    @Override
    public Integer addCrmFeedback(CrmFeedbackVO crmFeedbackVO) {
        CrmFeedback crmFeedback = new CrmFeedback();
        BeanCopyUtil.copy(crmFeedbackVO, crmFeedback);
        return crmFeedbackMapper.insertSelective(crmFeedback);
    }
}
