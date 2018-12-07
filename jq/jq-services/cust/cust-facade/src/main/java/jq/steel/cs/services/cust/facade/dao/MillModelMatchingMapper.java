package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillModelMatching;

public interface MillModelMatchingMapper {
    void deleteByPrimaryKey(MillModelMatching record);

    int insert(MillModelMatching record);

    int insertSelective(MillModelMatching record);

    MillModelMatching selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillModelMatching record);

    int updateByPrimaryKey(MillModelMatching record);
}