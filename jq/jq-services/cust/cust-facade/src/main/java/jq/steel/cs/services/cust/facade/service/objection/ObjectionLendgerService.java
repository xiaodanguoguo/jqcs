package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;

public interface ObjectionLendgerService {

    //分页条件查询
    PageDTO<ObjectionLedgerVO> findByPage(ObjectionLedgerVO objectionLedgerVO);
}
