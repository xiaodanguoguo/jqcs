package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.AcctRolePurchase;

import java.util.List;

public interface AcctRolePurchaseMapper {
    int deleteByPrimaryKey(Long rolePurchaseId);

    int insert(AcctRolePurchase record);

    int insertSelective(AcctRolePurchase record);

    AcctRolePurchase selectByPrimaryKey(Long rolePurchaseId);

    int updateByPrimaryKeySelective(AcctRolePurchase record);

    int updateByPrimaryKey(AcctRolePurchase record);

    List<AcctRolePurchase> selectAcctRolePurchase(AcctRolePurchase record);
}