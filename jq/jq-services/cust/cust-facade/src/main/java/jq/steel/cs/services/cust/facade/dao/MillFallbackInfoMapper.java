package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillFallbackInfo;

public interface MillFallbackInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillFallbackInfo record);

    int insertSelective(MillFallbackInfo record);

    MillFallbackInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillFallbackInfo record);

    int updateByPrimaryKey(MillFallbackInfo record);
}