package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;
import jq.steel.cs.services.cust.facade.model.CrmProductInfo;

import java.util.List;

public interface CrmProductInfoMapper {
    int deleteByPrimaryKey(Long pid);

    int insert(CrmProductInfo record);

    int insertSelective(CrmProductInfo record);

    CrmProductInfo selectByPrimaryKey(Long pid);

    int updateByPrimaryKeySelective(CrmProductInfo record);

    int updateByPrimaryKey(CrmProductInfo record);

    List<CrmProductInfo> getList(CrmProductInfoVO record);

    CrmProductInfo getOne(CrmProductInfo record);

    Long getMaxSortNumber();
}