package jq.steel.cs.services.cust.facade.service.objection;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO1;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;

import java.util.List;

public interface ObjectionLendgerService {

    //分页条件查询
    PageDTO<ObjectionLedgerVO> findByPage(ObjectionLedgerVO objectionLedgerVO);

    //导出
    List<ObjectionLedgerVO> export (ObjectionLedgerVO1 objectionTiBaoVO);
}
