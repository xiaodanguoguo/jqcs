package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmProductCategory;

import java.util.List;

public interface CrmProductCategoryMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(CrmProductCategory record);

    int insertSelective(CrmProductCategory record);

    CrmProductCategory selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(CrmProductCategory record);

    int updateByPrimaryKey(CrmProductCategory record);

    List<CrmProductCategory> getList(CrmProductCategory crmProductCategory);

    CrmProductCategory getOne(CrmProductCategory crmProductCategory);

    List<CrmProductCategory> getIntroductList(CrmProductCategory crmProductCategory);
}