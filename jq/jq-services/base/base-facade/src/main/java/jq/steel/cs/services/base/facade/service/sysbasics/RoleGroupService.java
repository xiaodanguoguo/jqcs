package jq.steel.cs.services.base.facade.service.sysbasics;


import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.base.api.vo.RoleGroupVO;

import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public interface RoleGroupService {

    PageDTO<RoleGroupVO> roleGroupList(RoleGroupVO jsonRequest);

    List<RoleGroupVO> findAll(RoleGroupVO jsonRequest);

    RoleGroupVO keepRoleGroup(RoleGroupVO jsonRequest);

    String verificationDeleteRoleGroup(RoleGroupVO jsonRequest);
}
