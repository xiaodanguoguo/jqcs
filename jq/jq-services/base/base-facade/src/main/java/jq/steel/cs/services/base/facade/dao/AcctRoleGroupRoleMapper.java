package jq.steel.cs.services.base.facade.dao;


import jq.steel.cs.services.base.facade.model.AcctRoleGroupRole;

public interface AcctRoleGroupRoleMapper {
    int deleteByPrimaryKey(Long relaId);

    int insert(AcctRoleGroupRole record);

    int insertSelective(AcctRoleGroupRole record);

    AcctRoleGroupRole selectByPrimaryKey(Long relaId);

    int updateByPrimaryKeySelective(AcctRoleGroupRole record);

    int updateByPrimaryKey(AcctRoleGroupRole record);

    int deleteRoleGroupId(Long roleGroupId);

    int deleteRoleId(Long roleId);
}