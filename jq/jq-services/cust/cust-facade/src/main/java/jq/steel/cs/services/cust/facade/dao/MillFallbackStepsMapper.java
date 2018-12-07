package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillFallbackSteps;

public interface MillFallbackStepsMapper {
    void deleteByPrimaryKey(MillFallbackSteps record);

    int insert(MillFallbackSteps record);

    int insertSelective(MillFallbackSteps record);

    MillFallbackSteps selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillFallbackSteps record);

    int updateByPrimaryKey(MillFallbackSteps record);
}