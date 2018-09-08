package jq.steel.cs.services.base.facade.dao;


import jq.steel.cs.services.base.facade.model.DictRegion;

import java.util.List;

public interface DictRegionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictRegion record);

    int insertSelective(DictRegion record);

    DictRegion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictRegion record);

    int updateByPrimaryKey(DictRegion record);

    //模糊查询
    List<DictRegion> findDictRegion(DictRegion dictRegion);

    //父查子
    List<DictRegion> findDictRegionTree(DictRegion dictRegion);

    //子查父
    List<DictRegion> findDictRegionTreeSon(DictRegion dictRegion);



}