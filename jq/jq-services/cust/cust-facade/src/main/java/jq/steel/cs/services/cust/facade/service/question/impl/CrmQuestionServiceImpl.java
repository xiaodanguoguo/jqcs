package jq.steel.cs.services.cust.facade.service.question.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.core.AssertContext;
import com.ebase.core.cache.CacheService;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.DateUtil;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmQuestionItemVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionRecordDetailVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionRecordVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionTeamAnswerVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionVO;
import jq.steel.cs.services.cust.facade.common.QuestionStatus;
import jq.steel.cs.services.cust.facade.common.SysPramType;
import jq.steel.cs.services.cust.facade.dao.CrmQuestionItemMapper;
import jq.steel.cs.services.cust.facade.dao.CrmQuestionMapper;
import jq.steel.cs.services.cust.facade.dao.CrmQuestionRecordDetailMapper;
import jq.steel.cs.services.cust.facade.dao.CrmQuestionRecordMapper;
import jq.steel.cs.services.cust.facade.dao.CrmQuestionTeamAnswerMapper;
import jq.steel.cs.services.cust.facade.model.CrmQuestion;
import jq.steel.cs.services.cust.facade.model.CrmQuestionItem;
import jq.steel.cs.services.cust.facade.model.CrmQuestionRecord;
import jq.steel.cs.services.cust.facade.model.CrmQuestionRecordDetail;
import jq.steel.cs.services.cust.facade.model.CrmQuestionTeamAnswer;
import jq.steel.cs.services.cust.facade.service.question.CrmQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CrmQuestionServiceImpl
 * @Description: 问卷调查
 * @Author: lirunze
 * @CreateDate: 2018/8/21 13:27
 */
@Service("crmQuestionService")
public class CrmQuestionServiceImpl implements CrmQuestionService {

    private final static Long SEQ = 1L;

    @Autowired
    private CrmQuestionMapper crmQuestionMapper;

    @Autowired
    private CrmQuestionItemMapper crmQuestionItemMapper;

    @Autowired
    private CrmQuestionTeamAnswerMapper crmQuestionTeamAnswerMapper;

    @Autowired
    private CrmQuestionRecordMapper crmQuestionRecordMapper;

    @Autowired
    private CrmQuestionRecordDetailMapper crmQuestionRecordDetailMapper;

    @Autowired
    private CacheService cacheService;

    @Override
    public PageDTO<CrmQuestionVO> getPage(CrmQuestionVO crmQuestionVO) {
        CrmQuestion crmQuestion = new CrmQuestion();
        BeanCopyUtil.copy(crmQuestionVO, crmQuestion);

        int count = crmQuestionMapper.getCount(crmQuestion);
        crmQuestion.setCreateDtStr(DateFormatUtil.getStartDateStr(crmQuestion.getCreateDt()));
        crmQuestion.setEndDtStr(DateFormatUtil.getEndDateStr(crmQuestion.getEndDt()));
        List<CrmQuestion> crmQuestions = crmQuestionMapper.getList(crmQuestion);

        PageDTO<CrmQuestionVO> pageVO = new PageDTO<>(crmQuestionVO.getPageNum(), crmQuestionVO.getPageSize());
        pageVO.setTotal(count);

        if (CollectionUtils.isEmpty(crmQuestions)) {
            PageDTO returnPage = new PageDTO<>();
            returnPage.setResultData(new ArrayList());
            return returnPage;
        }

        List<CrmQuestionVO> result = new ArrayList<>();
        for (CrmQuestion question : crmQuestions) {
            CrmQuestionVO questionVO = new CrmQuestionVO();
            BeanCopyUtil.copy(question, questionVO);
            CrmQuestionItem crmQuestionItem = new CrmQuestionItem();
            crmQuestionItem.setQid(question.getQid());
            List<CrmQuestionItemVO> questionItems = crmQuestionItemMapper.getList(crmQuestionItem);

            for (CrmQuestionItemVO questionItemVO : questionItems) {

                for (CrmQuestionTeamAnswerVO questionTeamAnswerVO : questionItemVO.getCrmQuestionTeamAnswerVOList()) {
                    questionTeamAnswerVO.setQid(questionItemVO.getQid());
                    questionTeamAnswerVO.setQuestionItemId(questionItemVO.getQuestionItemId());
                }
            }

            questionVO.setCrmQuestionItemVOList(questionItems);
            result.add(questionVO);
        }


        pageVO.setResultData(result);
        return pageVO;
    }

    @Override
    @Transactional
    public ServiceResponse<Boolean> saveQuestion(List<CrmQuestionVO> crmQuestionVOList) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        boolean flag = false;
        serviceResponse.setRetContent(flag);

        if (CollectionUtils.isEmpty(crmQuestionVOList)) {
            serviceResponse.setResponseCode("0000001");
            return serviceResponse;
        }

        CrmQuestion record = new CrmQuestion();
        List<CrmQuestion> crmQuestions = crmQuestionMapper.getList(record);
        List<String> questionTitle = new ArrayList<>();

        for (CrmQuestionVO crmQuestionVO : crmQuestionVOList) {
            // 删除
            if (SysPramType.DELETE.getMsg().equals(crmQuestionVO.getOpt())) {
                for (CrmQuestion crmQuestion : crmQuestions) {
                    if (crmQuestionVO.getQid().equals(crmQuestion.getQid())) {
                        if (!QuestionStatus.NEW_CREATE.getCode().equals(crmQuestion.getQuestionStatus())) {
                            serviceResponse.setRetCode("0901001",new Object[]{crmQuestion.getQuestionTitle()});
                            return serviceResponse;
                        } else {
                            questionTitle.add(crmQuestion.getQuestionTitle());
                            crmQuestionMapper.deleteByPrimaryKey(crmQuestion.getQid());
                            crmQuestionItemMapper.deleteByQid(crmQuestion.getQid());
                            crmQuestionTeamAnswerMapper.deleteByQid(crmQuestion.getQid());
                        }
                        break;
                    }
                }
            }
            // 修改
            if (SysPramType.UPDATE.getMsg().equals(crmQuestionVO.getOpt())) {
                for (CrmQuestion crmQuestion : crmQuestions) {
                    if (crmQuestionVO.getQuestionTitle().equals(crmQuestion.getQuestionTitle()) && !crmQuestionVO.getQid().equals(crmQuestion.getQid())) {
                        serviceResponse.setRetCode("0901003",new Object[]{crmQuestion.getQuestionTitle()});
                        return serviceResponse;
                    }
                }

                for (CrmQuestion crmQuestion : crmQuestions) {
                    if (crmQuestionVO.getQid().equals(crmQuestion.getQid())) {
                        if (QuestionStatus.NEW_CREATE.getCode().equals(crmQuestion.getQuestionStatus())) {
                            CrmQuestion question = new CrmQuestion();
                            BeanCopyUtil.copy(crmQuestionVO, question);
                            question.setUpdateBy(AssertContext.getAcctId());
                            question.setUpdateDt(new Date());
                            crmQuestionMapper.updateByPrimaryKeySelective(question);
                        }
                        break;
                    }
                }
            }
            // 添加
            if (SysPramType.INSERT.getMsg().equals(crmQuestionVO.getOpt())) {
                for (CrmQuestion crmQuestion : crmQuestions) {
                    if (crmQuestionVO.getQuestionTitle().equals(crmQuestion.getQuestionTitle())) {
                        boolean b = false;
                        for (String title : questionTitle) {
                            if (crmQuestionVO.getQuestionTitle().equals(title)) {
                                b = true;
                            }
                        }

                        if (!b) {
                            serviceResponse.setRetCode("0901003", new Object[]{crmQuestion.getQuestionTitle()});
                            return serviceResponse;
                        }
                    }

                }

                CrmQuestion question1 = new CrmQuestion();
                BeanCopyUtil.copy(crmQuestionVO, question1);
                question1.setQuestionStatus(QuestionStatus.NEW_CREATE.getCode());
                // 信息编号
                String date = DateUtil.formatDate(new Date(), DateUtil.YYYYMMDD);
                date = date.substring(2,6);
                String seq = cacheService.incr("QUESTION_" + date, SEQ).toString();

                int zeroLength = 0;
                if(seq.length() < 3) {
                    zeroLength = 3 - seq.length();
                }
                while (zeroLength > 0) {
                    seq = "0" + seq;
                    zeroLength--;
                }
                question1.setMessageNumber(date + seq);
                question1.setCreateBy(AssertContext.getAcctId());
                question1.setCreateDt(new Date());
                crmQuestionMapper.insert(question1);


            }
        }
            flag = true;
            serviceResponse.setRetContent(flag);
            return serviceResponse;
    }

    @Override
    @Transactional
    public ServiceResponse<Boolean> saveDetail(CrmQuestionVO crmQuestionVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        boolean flag = false;
        serviceResponse.setRetContent(flag);

        crmQuestionItemMapper.deleteByQid(crmQuestionVO.getQid());
        crmQuestionTeamAnswerMapper.deleteByQid(crmQuestionVO.getQid());

        List<CrmQuestionItemVO> crmQuestionItemVOList = crmQuestionVO.getCrmQuestionItemVOList();

        if (CollectionUtils.isEmpty(crmQuestionItemVOList)) {
            serviceResponse.setResponseCode("0000001");
            return serviceResponse;
        }

        for (CrmQuestionItemVO crmQuestionItemVO : crmQuestionItemVOList) {

            CrmQuestionItem crmQuestionItem = new CrmQuestionItem();
            BeanCopyUtil.copy(crmQuestionItemVO, crmQuestionItem);
            // 添加问题项目
            crmQuestionItem.setQid(crmQuestionVO.getQid());
            crmQuestionItemMapper.insertSelective(crmQuestionItem);

            for (CrmQuestionTeamAnswerVO crmQuestionTeamAnswerVO : crmQuestionItemVO.getCrmQuestionTeamAnswerVOList()) {
                CrmQuestionTeamAnswer crmQuestionTeamAnswer = new CrmQuestionTeamAnswer();
                BeanCopyUtil.copy(crmQuestionTeamAnswerVO, crmQuestionTeamAnswer);
                crmQuestionTeamAnswer.setQid(crmQuestionItem.getQid());
                crmQuestionTeamAnswer.setQuestionItemId(crmQuestionItem.getQuestionItemId());
                crmQuestionTeamAnswerMapper.insertSelective(crmQuestionTeamAnswer);
            }
        }

        flag = true;
        serviceResponse.setRetContent(flag);
        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> sentDownQuestion(CrmQuestionVO crmQuestionVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        boolean flag = false;

        crmQuestionVO.setQuestionStatus(QuestionStatus.ISSUE.getCode());
        CrmQuestion crmQuestion = new CrmQuestion();
        BeanCopyUtil.copy(crmQuestionVO, crmQuestion);
        crmQuestion.setPushRegion(JsonUtil.toJson(crmQuestionVO.getPushRegions()));
        crmQuestion.setUpdateBy(AssertContext.getAcctId());
        crmQuestion.setCreateDt(new Date());

        CrmQuestionItem crmQuestionItem = new CrmQuestionItem();
        crmQuestionItem.setQid(crmQuestionVO.getQid());
        List<CrmQuestionItemVO> questionItems = crmQuestionItemMapper.getList(crmQuestionItem);

        if (CollectionUtils.isEmpty(questionItems)) {
            serviceResponse.setRetCode("500");
            serviceResponse.setRetMessage("请添加问卷内容");
            return serviceResponse;
        }
        int i = crmQuestionMapper.updateByPrimaryKeySelective(crmQuestion);

        if (i > 0 ) {
            flag = true;
        }

        serviceResponse.setRetContent(flag);
        return serviceResponse;
    }

    @Override
    public ServiceResponse<CrmQuestionVO> getPreview(CrmQuestionVO crmQuestionVO) {
        ServiceResponse<CrmQuestionVO> serviceResponse = new ServiceResponse<>();
        CrmQuestion crmQuestion = crmQuestionMapper.selectByPrimaryKey(crmQuestionVO.getQid());

        if (null == crmQuestion) {
            serviceResponse.setRetCode("0901004",new Object[]{crmQuestionVO.getQid()});
            return serviceResponse;
        }

        CrmQuestionItem crmQuestionItem = new CrmQuestionItem();
        crmQuestionItem.setQid(crmQuestionVO.getQid());
        List<CrmQuestionItemVO> questionItems = crmQuestionItemMapper.getList(crmQuestionItem);

        for (CrmQuestionItemVO questionItemVO : questionItems) {

            for (CrmQuestionTeamAnswerVO questionTeamAnswerVO : questionItemVO.getCrmQuestionTeamAnswerVOList()) {
                questionTeamAnswerVO.setQid(questionItemVO.getQid());
                questionTeamAnswerVO.setQuestionItemId(questionItemVO.getQuestionItemId());
            }
        }
        BeanCopyUtil.copy(crmQuestion, crmQuestionVO);

        crmQuestionVO.setCrmQuestionItemVOList(questionItems);
        serviceResponse.setRetContent(crmQuestionVO);

        return serviceResponse;
    }

    @Override
    @Transactional
    public ServiceResponse<CrmQuestionVO> insertCrmQuestionRecord(CrmQuestionRecordVO crmQuestionRecordVO) {
        ServiceResponse<CrmQuestionVO> serviceResponse = new ServiceResponse<>();
        // 查看记录
        CrmQuestionRecord record = new CrmQuestionRecord();
        BeanCopyUtil.copy(crmQuestionRecordVO, record);
        record.setRecordBy(crmQuestionRecordVO.getAcctId());
        List<CrmQuestionRecord> records = crmQuestionRecordMapper.getList(record);

        if (CollectionUtils.isNotEmpty(records)) {
            serviceResponse.setRetCode("500");
            serviceResponse.setRetMessage("您已填写过此调查问卷");
            return serviceResponse;
        }

        int recordValue = 0;

        List<CrmQuestionRecordDetailVO> list = crmQuestionRecordVO.getCrmQuestionRecordDetailVOList();

        for (CrmQuestionRecordDetailVO crmQuestionRecordDetailVO : list) {
            recordValue += crmQuestionRecordDetailVO.getAnswerValue();
        }

        CrmQuestionRecord crmQuestionRecord = new CrmQuestionRecord();
        BeanCopyUtil.copy(crmQuestionRecordVO, crmQuestionRecord);
        crmQuestionRecord.setRecordByName(AssertContext.getAcctName());
        crmQuestionRecord.setRecordDt(new Date());
        crmQuestionRecord.setRecordValue(recordValue);

        crmQuestionRecordMapper.insertSelective(crmQuestionRecord);

        for (CrmQuestionRecordDetailVO crmQuestionRecordDetailVO : list) {
            crmQuestionRecordDetailVO.setQid(crmQuestionRecord.getQid());
            crmQuestionRecordDetailVO.setAnswerRecordId(crmQuestionRecord.getAnswerRecordId());
            CrmQuestionRecordDetail crmQuestionRecordDetail = new CrmQuestionRecordDetail();
            BeanCopyUtil.copy(crmQuestionRecordDetailVO, crmQuestionRecordDetail);
            crmQuestionRecordDetailMapper.insert(crmQuestionRecordDetail);
        }

        CrmQuestionVO crmQuestionVO = new CrmQuestionVO();
//        crmQuestionVO.setAcctId(crmQuestionRecordVO.getAcctId());
//        crmQuestionVO.setOrgType(crmQuestionRecordVO.getOrgType());
//        CrmQuestionVO crmQuestionVO1 = this.getQuestion(crmQuestionVO);
        serviceResponse.setRetContent(crmQuestionVO);

        return serviceResponse;
    }

    @Override
    public List<CrmQuestionVO> getList() {
        CrmQuestion crmQuestion = new CrmQuestion();
        List<CrmQuestion> crmQuestions = crmQuestionMapper.getList(crmQuestion);
        List<CrmQuestionVO> list = BeanCopyUtil.copyList(crmQuestions, CrmQuestionVO.class);
        return list;
    }

    @Override
    public CrmQuestionVO getQuestion(CrmQuestionVO crmQuestionVO) {
        CrmQuestionVO vo = new CrmQuestionVO();
        vo.setCount(0);
        CrmQuestion crmQuestion = new CrmQuestion();
        BeanCopyUtil.copy(crmQuestionVO, crmQuestion);
        crmQuestion.setEndDtStr(DateFormatUtil.getEndDateStr(new Date()));
        crmQuestion.setQuestionStatus(QuestionStatus.ISSUE.getCode());
        crmQuestion.setPushRegion(crmQuestionVO.getOrgType());
        // 是否有问卷
        List<CrmQuestion> crmQuestions = crmQuestionMapper.getQuestionList(crmQuestion);

        // 如果集合为空直接返回
        if (CollectionUtils.isEmpty(crmQuestions)) {
            vo.setCount(0);
            return vo;
        }

        List<Long> qids = new ArrayList<>();

        for (CrmQuestion crmQuestion1 : crmQuestions) {
            qids.add(crmQuestion1.getQid());
        }

        // 查看记录
        CrmQuestionRecord crmQuestionRecord = new CrmQuestionRecord();
        crmQuestionRecord.setRecordBy(crmQuestionVO.getAcctId());
        crmQuestionRecord.setQuestionIds(qids);
        List<CrmQuestionRecord> list = crmQuestionRecordMapper.getList(crmQuestionRecord);

        if (CollectionUtils.isEmpty(list)) {
            vo.setQid(crmQuestions.get(0).getQid());
            vo.setCount(crmQuestions.size());
        } else {
            Map<Long, CrmQuestionRecord> map = new HashMap<>();
            for (CrmQuestionRecord record : list) {
                map.put(record.getQid(), record);
            }

            for (CrmQuestion q : crmQuestions) {
                if (null ==map.get(q.getQid())) {
                    vo.setQid(q.getQid());
                    vo.setCount(crmQuestions.size() - list.size());
                }

            }
        }

        return vo;
    }
}
