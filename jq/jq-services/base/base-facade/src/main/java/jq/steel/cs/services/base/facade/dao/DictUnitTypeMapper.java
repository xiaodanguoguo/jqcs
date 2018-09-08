package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.DictUnitType;

import java.util.List;

/**
 * @Auther: zhaotairan
 */
public interface DictUnitTypeMapper {
    int deleteByPrimaryKey(String unitTypeCode);

    int insert(DictUnitType record);

    int insertSelective(DictUnitType record);

    DictUnitType selectByPrimaryKey(String unitTypeCode);

    int updateByPrimaryKeySelective(DictUnitType record);

    int updateByPrimaryKey(DictUnitType record);

    List<DictUnitType> find(DictUnitType dictUnitType);
}