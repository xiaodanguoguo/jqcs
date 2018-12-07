package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillPhysicsData;

public interface MillPhysicsDataMapper {
    void deleteByPrimaryKey(MillPhysicsData record);

    int insert(MillPhysicsData record);

    int insertSelective(MillPhysicsData record);

    MillPhysicsData selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillPhysicsData record);

    int updateByPrimaryKey(MillPhysicsData record);
}