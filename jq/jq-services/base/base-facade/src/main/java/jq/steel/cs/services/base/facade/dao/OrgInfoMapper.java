package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.OrgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;





public interface OrgInfoMapper {

	//根据parentId添加组织
	Long insertOrgInfo(OrgInfo record);

	String getOrgInfoId(String id);

	int deleteOrgInfo(@Param("cascadeDeletionOrgInfo")List<String> cascadeDeletionOrgInfo);

	List<String> getOrgInfoIdAll(OrgInfo orgInfo);

	List<OrgInfo> getChildOrgInfo(OrgInfo child);

	List<OrgInfo> selectListOrgInfoAll(OrgInfo orgInfo);

	List<OrgInfo> queryChildOrgInfo(OrgInfo orgInfo);

	//根据组织id修改组织
	int updateOrgInfo(OrgInfo record);

	List<OrgInfo> getMaterielOrginfo(@Param("acctInfoId")String acctInfoId);

	//根据当前parentId查询组织
	OrgInfo getOrgInfo(OrgInfo orgInfo);

	//根据组织名称查询出组织id
	String getOrgInfoName(@Param("orgName")String orgName);

	OrgInfo selectOrgInfo(OrgInfo record);

	List<OrgInfo> selectListOrgInfo(OrgInfo reqBody);



	List<OrgInfo>findOrgNameByOrgId(OrgInfo reqBody);





	OrgInfo selectOrgInfo1(OrgInfo record);


	OrgInfo selectOrgInfo2(OrgInfo record);

	//用户关联组织详情查询
	OrgInfo selectOrgInfoAcctInfo(OrgInfo record);


    List<OrgInfo> selectAuditOrg(OrgInfo orgInfo);

    //wushibin  验证是否有客户名称
	List<OrgInfo> selectOrgName(OrgInfo orgInfo);

	List<OrgInfo> queryExistsOrgInfo(OrgInfo orgInfo);

	Long getCode();

	OrgInfo getOrgCode(@Param("sapCode") String sapCode);
}
