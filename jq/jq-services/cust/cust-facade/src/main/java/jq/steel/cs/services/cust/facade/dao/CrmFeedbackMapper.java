package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmFeedback;

public interface CrmFeedbackMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(CrmFeedback record);

    int insertSelective(CrmFeedback record);

    CrmFeedback selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(CrmFeedback record);

    int updateByPrimaryKey(CrmFeedback record);
}