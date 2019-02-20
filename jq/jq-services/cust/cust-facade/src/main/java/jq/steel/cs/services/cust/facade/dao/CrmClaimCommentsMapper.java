package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmClaimComments;

import java.util.List;

public interface CrmClaimCommentsMapper {
    int insert(CrmClaimComments record);

    int insertSelective(CrmClaimComments record);

    List<CrmClaimComments> findByPage(CrmClaimComments record);
}