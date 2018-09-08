package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmQuestionTeamAnswer;
import org.apache.ibatis.annotations.Param;

public interface CrmQuestionTeamAnswerMapper {
    int deleteByPrimaryKey(Long questionItemAnswerId);

    int insert(CrmQuestionTeamAnswer record);

    int insertSelective(CrmQuestionTeamAnswer record);

    CrmQuestionTeamAnswer selectByPrimaryKey(Long questionItemAnswerId);

    int updateByPrimaryKeySelective(CrmQuestionTeamAnswer record);

    int updateByPrimaryKey(CrmQuestionTeamAnswer record);

    int deleteByQid(@Param("qid") Long qid);
}