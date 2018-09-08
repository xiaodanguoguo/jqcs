package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmCustomerInfo;
import jq.steel.cs.services.cust.facade.model.CrmLastuserInfo;

import java.util.List;

public interface CrmCustomerInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(CrmCustomerInfo record);

    int insertSelective(CrmCustomerInfo record);

    CrmCustomerInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmCustomerInfo record);

    int updateByPrimaryKey(CrmCustomerInfo record);

    int selectMaxId (CrmCustomerInfo record);

    int updateAll(CrmCustomerInfo record);

    List<CrmCustomerInfo> isOne(CrmCustomerInfo record);


    List<CrmCustomerInfo> findByPage(CrmCustomerInfo record);

    List<CrmCustomerInfo> findDefault(CrmCustomerInfo record);
}