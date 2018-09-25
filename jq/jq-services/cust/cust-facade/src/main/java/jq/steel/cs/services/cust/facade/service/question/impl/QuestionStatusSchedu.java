package jq.steel.cs.services.cust.facade.service.question.impl;

import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.facade.dao.CrmQuestionMapper;
import jq.steel.cs.services.cust.facade.model.CrmQuestion;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: QuestionStatusSchedu
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/25 10:18
 */
@Component
public class QuestionStatusSchedu {
    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmQuestionMapper crmQuestionMapper;

    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void scheduled(){
        logger.info("问卷调查状态更改");
        CrmQuestion crmQuestion = new CrmQuestion();
        crmQuestion.setEndDtStr(DateFormatUtil.getStartDateStr(new Date()));
        int i = crmQuestionMapper.updateStatusBySchedu(crmQuestion);
        logger.info("问卷调查状态更改数量 = {}", i);
    }

}
