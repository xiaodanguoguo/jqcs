package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmCustGrumble;

public interface CrmCustGrumbleMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(CrmCustGrumble record);

    int insertSelective(CrmCustGrumble record);

    CrmCustGrumble selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(CrmCustGrumble record);

    int updateByPrimaryKey(CrmCustGrumble record);
}