package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillOperationHis;

public interface MillOperationHisMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillOperationHis record);

    int insertSelective(MillOperationHis record);

    MillOperationHis selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillOperationHis record);

    int updateByPrimaryKey(MillOperationHis record);
}