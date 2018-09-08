package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.MillCoilInfo;

import java.util.List;

public interface MillCoilInfoMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(MillCoilInfo record);

    int insertSelective(MillCoilInfo record);

    MillCoilInfo selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(MillCoilInfo record);

    int updateByPrimaryKey(MillCoilInfo record);

    //分页查询
    List<MillCoilInfo> splitFind(MillCoilInfo record);

    List<MillCoilInfo> findMillsheetNumber (MillCoilInfo record);
}