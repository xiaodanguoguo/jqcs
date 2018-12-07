package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillSheetExpand;

public interface MillSheetExpandMapper {
    void deleteByPrimaryKey(MillSheetExpand record);

    int insert(MillSheetExpand record);

    int insertSelective(MillSheetExpand record);

    MillSheetExpand selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillSheetExpand record);

    int updateByPrimaryKey(MillSheetExpand record);
}