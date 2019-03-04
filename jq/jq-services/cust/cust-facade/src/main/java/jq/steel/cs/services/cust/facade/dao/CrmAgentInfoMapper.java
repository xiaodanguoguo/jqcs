package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmAgentInfo;

import java.util.List;

public interface CrmAgentInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(CrmAgentInfo record);

    int insertSelective(CrmAgentInfo record);

    CrmAgentInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmAgentInfo record);

    int updateByPrimaryKey(CrmAgentInfo record);

    int selectMaxId (CrmAgentInfo record);

    int updateAll(CrmAgentInfo record);

    List<CrmAgentInfo> isOne(CrmAgentInfo record);


    List<CrmAgentInfo> findByPage(CrmAgentInfo record);

    List<CrmAgentInfo> findDefault(CrmAgentInfo record);
}