package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcctInfoMapper {
    int deleteByPrimaryKey(Long acctId);

    int insert(AcctInfo record);

    int insertSelective(AcctInfo record);

    AcctInfo selectByPrimaryKey(Long acctId);

    int updateByPrimaryKeySelective(AcctInfo record);

    int updateByPrimaryKey(AcctInfo record);

    //查询用户列表
    List<AcctInfo> find(AcctInfoVO reqBody);

    //查询用户分页列表
    List<AcctInfo> findPage(AcctInfoVO reqBody);

    List<AcctInfo> findAcctRoleInfo(AcctInfo reqBody);

    //添加用户
    int insertAcctInfo(AcctInfo acctInfo);

    //修改用户信息
    int updateAcctInfo(AcctInfo acctInfo);

    //用户下角色管理 - 查询
    List<AcctInfo> listSysAcct2Role(AcctInfo acctInfo);

//    //用户下角色管理 - 添加
//    List<AcctInfo> addSysAcct2Role(AcctInfo acctInfo);

    //用户下角色管理 - 删除
    List<AcctInfo> deleteSysAcct2Role(AcctInfo acctInfo);
    
    //查询用户下是否绑定组织机构
	int getAcctOrgid(@Param("cascadeDeletionOrgInfo") List<String> cascadeDeletionOrgInfo);
	
	//根据用户id查询该用户绑定的组织
	String getAcctInfo(Long acctId);

    AcctInfo LoginAcct(AcctInfo acctInfo);

    //根据角色查询用户
    List<AcctInfo> selectRoleIdAcctInfo(AcctInfo acctInfo);

    List<AcctInfo> selectOrgIdAcctInfo(AcctInfo acctInfo);

    //根据账号名查询账号对象
    AcctInfo selectByLogin(String acctId);

    AcctInfo findByOrgId(@Param("id") String id);

    AcctInfo getAcctInfoByAcctTitle(AcctInfo acctInfo);

    AcctInfo customerType(AcctInfo acctInfo);
}