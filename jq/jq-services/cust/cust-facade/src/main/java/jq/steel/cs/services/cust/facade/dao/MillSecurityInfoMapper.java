package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillSecurityInfo;

import java.util.List;

public interface MillSecurityInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillSecurityInfo record);

    int insertSelective(MillSecurityInfo record);

    MillSecurityInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillSecurityInfo record);

    int updateByPrimaryKey(MillSecurityInfo record);


    List<MillSecurityInfo> findByParams(MillSecurityInfo record);
}