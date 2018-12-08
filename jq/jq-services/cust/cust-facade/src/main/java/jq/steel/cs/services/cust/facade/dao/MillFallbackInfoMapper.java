package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillFallbackInfo;

import java.util.List;

public interface MillFallbackInfoMapper {
    void deleteByPrimaryKey(MillFallbackInfo record);

    int insert(MillFallbackInfo record);

    int insertSelective(MillFallbackInfo record);

    List<MillFallbackInfo> selectByPrimaryKey(MillFallbackInfo record);

    int updateByPrimaryKeySelective(MillFallbackInfo record);

    int updateByPrimaryKey(MillFallbackInfo record);
}