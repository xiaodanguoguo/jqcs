package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;

public interface CrmMillSheetRebackApplyMapper {
    int insert(CrmMillSheetRebackApply record);

    int insertSelective(CrmMillSheetRebackApply record);
}