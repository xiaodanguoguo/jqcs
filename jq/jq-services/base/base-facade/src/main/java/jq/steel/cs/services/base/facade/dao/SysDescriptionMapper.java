package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.SysDescription;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDescriptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDescription record);

    int insertSelective(SysDescription record);

    SysDescription selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDescription record);

    int updateByPrimaryKey(SysDescription record);

    List<SysDescription> find(@Param("keyword") String keyword, @Param("status") Long status);
}