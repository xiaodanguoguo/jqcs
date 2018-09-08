package jq.steel.cs.services.base.facade.service.sysbasics;


import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;

import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public interface RoleInfoService {

    PageDTO<RoleInfoVO> roleInfoList(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleInfoAll(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleInfoAllLike(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleRoleAcctInfo(RoleInfoVO jsonRequest);

    List<RoleInfoVO> roleInfoTree(RoleInfoVO jsonRequest);

    List<RoleInfoVO> verificationIsTtitleRole(RoleInfoVO jsonRequest);

    RoleInfoVO keepRoleInfo(RoleInfoVO jsonRequest);

    String verificationDeleteRoelInfo(RoleInfoVO jsonRequest);
}
