package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmMillSheetCheckLog;

public interface CrmMillSheetCheckLogMapper {
    int insert(CrmMillSheetCheckLog record);

    int insertSelective(CrmMillSheetCheckLog record);
}