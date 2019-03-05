package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.OrgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrgInfoMapper {
	//根据组织名查询id
	List<OrgInfo>findIdByOrgName(OrgInfo reqBody);
	//根据id查询industrialCode
	List<OrgInfo>findIdByCode(OrgInfo reqBody);

	List<OrgInfo> selectAuditOrg(OrgInfo orgInfo);
}
