package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmVersionUpdate;

public interface CrmVersionUpdateMapper {
    int deleteByPrimaryKey(Long vid);

    int insert(CrmVersionUpdate record);

    int insertSelective(CrmVersionUpdate record);

    CrmVersionUpdate selectByPrimaryKey(Long vid);

    int updateByPrimaryKeySelective(CrmVersionUpdate record);

    int updateByPrimaryKey(CrmVersionUpdate record);
}