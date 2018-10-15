package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;

import java.util.List;

public interface CrmCustomerInfoService {

    CrmCustomerInfoVO orderUnitInsert (CrmCustomerInfoVO crmCustomerInfoVO);

    PageDTO<CrmCustomerInfoVO> findByPage(CrmCustomerInfoVO crmCustomerInfoVO);

    Integer orderUnitDelete(CrmCustomerInfoVO crmCustomerInfoVO);


    List<CrmCustomerInfoVO> findDefault(CrmCustomerInfoVO crmCustomerInfoVO);

    List<CrmCustomerInfoVO> findorderUnitList(CrmCustomerInfoVO crmCustomerInfoVO);

    CrmCustomerInfoVO findorderUnitInfo(CrmCustomerInfoVO crmCustomerInfoVO);
}
