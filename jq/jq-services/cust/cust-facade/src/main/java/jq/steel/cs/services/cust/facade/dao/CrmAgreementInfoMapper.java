package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmAgreementInfo;

import java.util.List;

public interface CrmAgreementInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(CrmAgreementInfo record);

    int insertSelective(CrmAgreementInfo record);

    CrmAgreementInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(CrmAgreementInfo record);

    int updateByPrimaryKey(CrmAgreementInfo record);

    CrmAgreementInfo findByParms(CrmAgreementInfo record);

    List<CrmAgreementInfo> findList(CrmAgreementInfo record);

    //公共信息查询
    CrmAgreementInfo findAll(CrmAgreementInfo record);

    //分页查询
    List<CrmAgreementInfo> findByPage(CrmAgreementInfo record);

    List<CrmAgreementInfo> findByParams(CrmAgreementInfo recoed);


}