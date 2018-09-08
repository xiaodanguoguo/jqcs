package jq.steel.cs.services.base.facade.service.sysbasics;


import jq.steel.cs.services.base.api.vo.FunctionManageVO;

import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
public interface FunctionManageService {

    List<FunctionManageVO> functionManageList(FunctionManageVO jsonRequest);

    List<FunctionManageVO> functionManageRoleList(FunctionManageVO functionManageVO);

    List<FunctionManageVO> verificationFunIsTtitle(FunctionManageVO functionManageVO);

    Integer updateFunctionManageStatus(List<FunctionManageVO> jsonRequest);

    FunctionManageVO keepFunctionManage(FunctionManageVO jsonRequest);

    String verificationDeleteFunction(FunctionManageVO jsonRequest);

    List<FunctionManageVO> getFunctionList(FunctionManageVO jsonRequest);

    List<FunctionManageVO> getUserfunctionList(FunctionManageVO jsonRequest);
}
