package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmAgreementInfo;
import jq.steel.cs.services.cust.facade.model.CrmClaimInfo;

import java.util.List;

public interface CrmClaimInfoMapper {

    CrmClaimInfo select(CrmClaimInfo record);

    int deleteByPrimaryKey(String claimNo);

    int insert(CrmClaimInfo record);

    int insertSelective(CrmClaimInfo record);

    CrmClaimInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmClaimInfo record);

    int updateByPrimaryKey(CrmClaimInfo record);

    //导出查询
    CrmClaimInfo findByPage(CrmClaimInfo record);

    //分页查询
    List<CrmClaimInfo> findByPageChuLi (CrmClaimInfo record);

    CrmClaimInfo  findByParams(CrmClaimInfo record);

}