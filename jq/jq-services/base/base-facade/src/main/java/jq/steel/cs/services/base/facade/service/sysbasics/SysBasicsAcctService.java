package jq.steel.cs.services.base.facade.service.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.AcctInfoExcel;
import jq.steel.cs.services.base.api.vo.AcctInfoRoleVO;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.AcctRoleRealVO;
import jq.steel.cs.services.base.api.vo.AcctToRoleInfoVO;

import java.util.List;

/**
 * @Auther: wangyu
 */
public interface SysBasicsAcctService {

    AcctInfoVO LoginAcct(AcctInfoVO acctInfoVO);

    PageDTO<AcctInfoVO> listSysAcct(JsonRequest<AcctInfoVO> jsonRequest);

    List<AcctInfoExcel> sysAcctListExcel(AcctInfoVO acctInfoVO);

    JsonResponse keepSysAcct(JsonRequest<List<AcctRoleRealVO>> jsonRequest);

    JsonResponse addSysAcct(JsonRequest<AcctInfoVO> jsonRequest);

    AcctInfoRoleVO getSysAcct(AcctInfoVO reqBody) ;

    JsonResponse getSysAcctInfo(JsonRequest<AcctInfoVO> jsonRequest);

    JsonResponse sysAcctDiscontinuation(JsonRequest<AcctInfoVO> jsonRequest);

    JsonResponse sysAcctDeleteById(JsonRequest<AcctInfoVO> jsonRequest);

    JsonResponse sysAcctAddUser(JsonRequest<AcctToRoleInfoVO> jsonRequest);

    //当前用户下的角色管理 查询
    List<AcctInfoVO> listSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest);

    //当前用户下的角色管理 添加 + 删除
//    JsonResponse keepSysAcct2Role(JsonRequest<AcctInfoVO>jsonRequest);


    //当前用户下的角色管理 中间表添加
    JsonResponse addSysAcct2Role(JsonRequest<AcctRoleRealVO> jsonRequest);

    //当前用户下的角色管理 中间表删除
    JsonResponse deleteSysAcct2Role2(JsonRequest<AcctRoleRealVO> jsonRequest);

    //当前用户下的角色管理 全删除
    JsonResponse deleteSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest);

    List<AcctInfoVO> selectRoleIdAcctInfo(AcctInfoVO acctInfoVO);

    PageDTO<AcctInfoVO> selectOrgIdAcctInfo(AcctInfoVO acctInfoVO);


    //根据用户查询角色和组织信息
    AcctInfoVO getAcctInfo(AcctInfoVO acctInfoVO);

    AcctInfoVO getAcctInfoByAcctTitle(AcctInfoVO jsonRequest);


    //客户类型获取
    AcctInfoVO customerType(AcctInfoVO record);

    /**
     * @param:
     * @return:
     * @description: 编辑个人信息
     * @author: lirunze
     * @Date: 2018/9/24
     */
    ServiceResponse<Integer> updateAcctInfo(AcctInfoVO acctInfoVO);


}
