package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmClaimInnerInquire;

import java.util.List;

public interface CrmClaimInnerInquireMapper {

    List<CrmClaimInnerInquire> findByParams(CrmClaimInnerInquire record);

    int insert(CrmClaimInnerInquire record);

    int insertSelective(CrmClaimInnerInquire record);

    Integer update(CrmClaimInnerInquire record);

}