package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.facade.model.CrmCustomerInfo;

import java.util.List;

public interface CrmCustomerInfoService {

    Integer orderUnitInsert (CrmCustomerInfoVO crmCustomerInfoVO);

    PageDTO<CrmCustomerInfoVO> findByPage(CrmCustomerInfoVO crmCustomerInfoVO);

    Integer orderUnitDelete(CrmCustomerInfoVO crmCustomerInfoVO);


    List<CrmCustomerInfoVO> findDefault(CrmCustomerInfoVO crmCustomerInfoVO);
}
