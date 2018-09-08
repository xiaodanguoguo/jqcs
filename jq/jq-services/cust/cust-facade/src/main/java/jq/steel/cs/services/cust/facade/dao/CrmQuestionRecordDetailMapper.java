package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmQuestionRecordDetail;

public interface CrmQuestionRecordDetailMapper {
    int deleteByPrimaryKey(Long recordDetailId);

    int insert(CrmQuestionRecordDetail record);

    int insertSelective(CrmQuestionRecordDetail record);

    CrmQuestionRecordDetail selectByPrimaryKey(Long recordDetailId);

    int updateByPrimaryKeySelective(CrmQuestionRecordDetail record);

    int updateByPrimaryKey(CrmQuestionRecordDetail record);
}