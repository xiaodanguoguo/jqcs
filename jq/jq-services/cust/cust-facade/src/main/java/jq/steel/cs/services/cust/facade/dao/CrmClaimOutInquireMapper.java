package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmClaimOutInquire;

import java.util.List;

public interface CrmClaimOutInquireMapper {

    List<CrmClaimOutInquire> findByParams(CrmClaimOutInquire record);

    List<CrmClaimOutInquire> findByPage (CrmClaimOutInquire record);

    CrmClaimOutInquire find (CrmClaimOutInquire record);

    CrmClaimOutInquire findDetails (CrmClaimOutInquire record);

    int insert(CrmClaimOutInquire record);

    int insertSelective(CrmClaimOutInquire record);


    int update(CrmClaimOutInquire record);
}