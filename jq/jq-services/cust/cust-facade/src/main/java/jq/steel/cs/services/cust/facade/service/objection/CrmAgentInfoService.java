package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmAgentInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;

import java.util.List;

public interface CrmAgentInfoService {

    CrmAgentInfoVO agentInfoInsert(CrmAgentInfoVO crmAgentInfoVO);

    PageDTO<CrmAgentInfoVO> findByPage(CrmAgentInfoVO crmAgentInfoVO);

    Integer agentInfoDelete(CrmAgentInfoVO crmAgentInfoVO);


    List<CrmAgentInfoVO> findDefault(CrmAgentInfoVO crmAgentInfoVO);

    List<CrmAgentInfoVO> findagentInfoList(CrmAgentInfoVO crmAgentInfoVO);

    CrmAgentInfoVO findagentInfo(CrmAgentInfoVO crmAgentInfoVO);
}
