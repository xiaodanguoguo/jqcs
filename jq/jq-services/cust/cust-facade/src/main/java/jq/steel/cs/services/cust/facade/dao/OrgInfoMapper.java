package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.OrgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrgInfoMapper {
	//根据组织名查询id
	List<OrgInfo>findIdByOrgName(OrgInfo reqBody);
}
