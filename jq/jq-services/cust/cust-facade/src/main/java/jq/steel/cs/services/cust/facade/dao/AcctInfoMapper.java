package jq.steel.cs.services.cust.facade.dao;


import jq.steel.cs.services.cust.facade.model.AcctInfo;

import java.util.List;

public interface AcctInfoMapper {

    List<AcctInfo> findNameByorgId(AcctInfo acctInfo);

}