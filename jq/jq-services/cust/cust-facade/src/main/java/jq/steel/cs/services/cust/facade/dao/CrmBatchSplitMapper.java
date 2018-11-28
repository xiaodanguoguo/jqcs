package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmBatchSplit;

public interface CrmBatchSplitMapper {
    int insert(CrmBatchSplit record);

    int insertSelective(CrmBatchSplit record);
}