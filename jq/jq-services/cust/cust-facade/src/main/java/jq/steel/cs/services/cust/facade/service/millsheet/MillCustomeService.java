package jq.steel.cs.services.cust.facade.service.millsheet;

import jq.steel.cs.services.cust.api.vo.MillCustomerVO;
import jq.steel.cs.services.cust.facade.model.MillCustomer;

import java.util.List;

public interface MillCustomeService {
    List<MillCustomerVO> findList();
}
