package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.DictSys;

import java.util.List;

/**
 * @Auther: zhaotairan
 */
public interface DictSysMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictSys record);

    int insertSelective(DictSys record);

    DictSys selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictSys record);

    int updateByPrimaryKey(DictSys record);

    List<DictSys> find(DictSys dictSys);

    List<DictSys> findByType(String dictType);

    DictSys selectByTypeAndCode(DictSys dictSys);
}
