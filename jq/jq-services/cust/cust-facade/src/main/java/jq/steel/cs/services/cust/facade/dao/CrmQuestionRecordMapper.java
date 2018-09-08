package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmQuestionRecord;

import java.util.List;

public interface CrmQuestionRecordMapper {
    int deleteByPrimaryKey(Long answerRecordId);

    int insert(CrmQuestionRecord record);

    int insertSelective(CrmQuestionRecord record);

    CrmQuestionRecord selectByPrimaryKey(Long answerRecordId);

    int updateByPrimaryKeySelective(CrmQuestionRecord record);

    int updateByPrimaryKey(CrmQuestionRecord record);

    List<CrmQuestionRecord> getList(CrmQuestionRecord crmQuestionRecord);

    CrmQuestionRecord selectOne(CrmQuestionRecord crmQuestionRecord);
}