package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmClaimCommentsVO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;

public interface CrmClaimCommentsService {

    Integer evaluate (CrmClaimCommentsVO crmClaimCommentsVO);

    PageDTO<CrmClaimCommentsVO> findByPage(CrmClaimCommentsVO crmClaimCommentsVO);
}
