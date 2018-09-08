package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmClaimLog;

public interface CrmClaimLogMapper {
    int insert(CrmClaimLog record);

    int insertSelective(CrmClaimLog record);
}