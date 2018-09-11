package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillCustomer;

import java.util.List;

public interface MillCustomerMapper {
    //查询所以客户名称
    List<MillCustomer> findList();

    int insert(MillCustomer record);

    int insertSelective(MillCustomer record);
}