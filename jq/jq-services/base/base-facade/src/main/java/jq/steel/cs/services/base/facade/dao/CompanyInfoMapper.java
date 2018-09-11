package jq.steel.cs.services.base.facade.dao;


import jq.steel.cs.services.base.facade.model.CompanyInfo;

public interface CompanyInfoMapper {
    int deleteByPrimaryKey(Long companyId);

    int insert(CompanyInfo record);

    int insertSelective(CompanyInfo record);

    CompanyInfo selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(CompanyInfo record);

    int updateByPrimaryKey(CompanyInfo record);
}