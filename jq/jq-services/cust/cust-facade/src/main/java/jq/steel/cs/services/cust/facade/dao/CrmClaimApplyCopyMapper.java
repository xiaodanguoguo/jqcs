package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmClaimApplyCopy;

public interface CrmClaimApplyCopyMapper {

    int update(CrmClaimApplyCopy record);

    int deleteByPrimaryKey(Long sid);

    int insert(CrmClaimApplyCopy record);

    int insertSelective(CrmClaimApplyCopy record);

    CrmClaimApplyCopy selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmClaimApplyCopy record);

    int updateByPrimaryKey(CrmClaimApplyCopy record);

    int delete(String claimNo);
}