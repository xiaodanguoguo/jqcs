package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmQuestion;

import java.util.List;

public interface CrmQuestionMapper {
    int deleteByPrimaryKey(Long qid);

    int insert(CrmQuestion record);

    int insertSelective(CrmQuestion record);

    CrmQuestion selectByPrimaryKey(Long qid);

    int updateByPrimaryKeySelective(CrmQuestion record);

    int updateByPrimaryKey(CrmQuestion record);

    List<CrmQuestion> getList(CrmQuestion record);

    CrmQuestion selectOne(CrmQuestion record);

    List<CrmQuestion> getQuestionList(CrmQuestion crmQuestion);

    int getCount(CrmQuestion crmQuestion);

    int updateStatusBySchedu(CrmQuestion crmQuestion);
}