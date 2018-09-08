package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.AcctOperPrivRela;
import jq.steel.cs.services.base.facade.model.FunctionManage;

public interface AcctOperPrivRelaMapper {
    int deleteByPrimaryKey(Long relaId);

    int deleteRoleId(Long roleId);

    int deleteFunctionId(Long functionId);

    int deleteFunctionIdAll(FunctionManage functionManage);

    int insert(AcctOperPrivRela record);

    int insertSelective(AcctOperPrivRela record);

    AcctOperPrivRela selectByPrimaryKey(Long relaId);

    int updateByPrimaryKeySelective(AcctOperPrivRela record);

    int updateByPrimaryKey(AcctOperPrivRela record);
}