package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.SysParam;

import java.util.List;

public interface SysParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);

    List<SysParam> find(SysParam sysParam);
}