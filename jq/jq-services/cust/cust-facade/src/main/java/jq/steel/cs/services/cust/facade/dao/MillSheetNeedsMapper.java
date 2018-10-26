package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillSheetNeeds;

import java.util.List;

public interface MillSheetNeedsMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillSheetNeeds record);

    int insertSelective(MillSheetNeeds record);

    MillSheetNeeds selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillSheetNeeds record);

    int updateByPrimaryKey(MillSheetNeeds record);


    List<MillSheetNeeds> findByType(MillSheetNeeds record);
}