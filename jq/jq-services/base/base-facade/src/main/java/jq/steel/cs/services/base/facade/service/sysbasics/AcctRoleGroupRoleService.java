package jq.steel.cs.services.base.facade.service.sysbasics;


import jq.steel.cs.services.base.api.vo.AcctRoleGroupRoleVO;

/**
 * @Auther: zhaoyuhang
 */
public interface AcctRoleGroupRoleService {

    Integer delAcctRoleGroupRole(AcctRoleGroupRoleVO jsonRequest);

    Integer addAcctRoleGroupRole(AcctRoleGroupRoleVO jsonRequest);
}
