package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmAgreementSteps;

public interface CrmAgreementStepsMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(CrmAgreementSteps record);

    int insertSelective(CrmAgreementSteps record);

    CrmAgreementSteps selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmAgreementSteps record);

    int updateByPrimaryKey(CrmAgreementSteps record);
}