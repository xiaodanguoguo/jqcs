package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillChemistryData;

public interface MillChemistryDataMapper {
    void deleteByPrimaryKey(MillChemistryData record);

    int insert(MillChemistryData record);

    int insertSelective(MillChemistryData record);

    MillChemistryData selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillChemistryData record);

    int updateByPrimaryKey(MillChemistryData record);
}