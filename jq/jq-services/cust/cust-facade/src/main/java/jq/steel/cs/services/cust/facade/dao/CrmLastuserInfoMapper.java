package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmLastuserInfo;

import java.util.List;

public interface CrmLastuserInfoMapper {
    int deleteByPrimaryKey(Long  sid);

    int insert(CrmLastuserInfo record);

    int insertSelective(CrmLastuserInfo record);

    CrmLastuserInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmLastuserInfo record);

    int updateByPrimaryKey(CrmLastuserInfo record);

    int selectMaxId (CrmLastuserInfo record);

    int updateAll(CrmLastuserInfo record);

    List<CrmLastuserInfo> isOne(CrmLastuserInfo crmLastuserInfo);


    List<CrmLastuserInfo> findByPage(CrmLastuserInfo crmLastuserInfo);

    List<CrmLastuserInfo>  findDefault(CrmLastuserInfo crmLastuserInfo);
}