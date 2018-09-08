package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.DictMeasUnitInfo;

import java.util.List;

public interface DictMeasUnitInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictMeasUnitInfo record);

    int insertSelective(DictMeasUnitInfo record);

    DictMeasUnitInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictMeasUnitInfo record);

    int updateByPrimaryKey(DictMeasUnitInfo record);

    List<DictMeasUnitInfo> find(DictMeasUnitInfo dictMeasUnitInfo);
}