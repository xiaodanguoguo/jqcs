package jq.steel.cs.services.base.facade.service.sysbasics;


import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.model.OrgInfo;

import java.util.List;

/**
 * 
 * @author zhangx
 *
 */
public interface OrgInfoService {
	
	Long addOrgInfo(OrgInfo orgInfo);
	
	Integer removeOrgInfo(OrgInfo orgInfo);
	
	List<OrgInfo> getListTreeOrgInfo(OrgInfo orgInfo);
	
	OrgInfo getChildTreeOrgInfo(OrgInfo orgInfo);
	
	Integer saveOrgInfo(OrgInfo orgInfo);
	
	OrgInfo getOrgInfo(OrgInfo orgInfo);

	PageDTO<OrgInfoVO> getListOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);

	List<OrgInfo> getMaterielOrginfo(AcctInfo acctInfo);


	/**
	 * @param:
	 * @return:
	 * @description:  审核列表
	 * @author: lirunze
	 * @Date: 2018/8/31
	 */
    PageDTO<OrgInfoVO> getAuditOrgList(JsonRequest<OrgInfoVO> jsonRequest);

    /**
     * @param:
     * @return:
     * @description:  审核
     * @author: lirunze
     * @Date: 2018/8/31
     */
	Integer getAuditOrg(OrgInfoVO orgInfoVO);

	/**
	 * @param:
	 * @return:
	 * @description:  验证用户是否有客户名称
	 * @author: wushibin
	 * @Date: 2018/9/3
	 */
	OrgInfo selectOrgName(OrgInfoVO orgInfoVO);
}
