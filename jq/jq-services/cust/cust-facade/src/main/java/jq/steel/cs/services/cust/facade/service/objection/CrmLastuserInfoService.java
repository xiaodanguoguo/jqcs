package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;

import java.util.List;

public interface CrmLastuserInfoService {

    Integer unitOfUseInsert (CrmLastuserInfoVO crmLastuserInfoVO);

    PageDTO<CrmLastuserInfoVO> findByPage(CrmLastuserInfoVO crmLastuserInfoVO);

    Integer unitOfUseDelete(CrmLastuserInfoVO crmLastuserInfoVO);

    List<CrmLastuserInfoVO> findDefault(CrmLastuserInfoVO crmLastuserInfoVO);

    List<CrmLastuserInfoVO> findunitOfUseList(CrmLastuserInfoVO crmLastuserInfoVO);

    CrmLastuserInfoVO findunitOfUseInfo(CrmLastuserInfoVO crmLastuserInfoVO);
}
