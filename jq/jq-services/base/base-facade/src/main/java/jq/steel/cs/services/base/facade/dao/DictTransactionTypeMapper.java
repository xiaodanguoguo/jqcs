package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.DictTransactionType;

public interface DictTransactionTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictTransactionType record);

    int insertSelective(DictTransactionType record);

    DictTransactionType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictTransactionType record);

    int updateByPrimaryKey(DictTransactionType record);
}