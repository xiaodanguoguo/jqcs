package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.CrmUserRecord;

import java.util.List;

public interface CrmUserRecordMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(CrmUserRecord record);

    int insertSelective(CrmUserRecord record);

    CrmUserRecord selectByPrimaryKey(Long rid);

    int updateByPrimaryKeySelective(CrmUserRecord record);

    int updateByPrimaryKey(CrmUserRecord record);

    List<CrmUserRecord> getList(CrmUserRecord record);
}