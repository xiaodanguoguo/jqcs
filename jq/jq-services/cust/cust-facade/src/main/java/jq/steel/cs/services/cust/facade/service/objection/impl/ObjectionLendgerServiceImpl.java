package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.dao.CrmClaimInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.model.ObjectionLedger;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionLendgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectionLendgerServiceImpl implements ObjectionLendgerService{

    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    //分页条件查询
    @Override
    public PageDTO<ObjectionLedgerVO> findByPage(ObjectionLedgerVO objectionLedgerVO) {
        try {
            //转换mdel
            ObjectionLedger objectionLedger  = new ObjectionLedger();
            BeanCopyUtil.copy(objectionLedgerVO,objectionLedger);
            PageDTOUtil.startPage(objectionLedgerVO);
            List<ObjectionLedger> ledgerList = crmClaimInfoMapper.findLedgerByPage(objectionLedger);
            //转换返回对象
            List<ObjectionLedgerVO> objectionLedgerVOS = BeanCopyUtil.copyList(ledgerList, ObjectionLedgerVO.class);
            // 分页对象
            PageDTO<ObjectionLedgerVO> transform = PageDTOUtil.transform(objectionLedgerVOS);
            return transform;
        }finally {
            PageDTOUtil.endPage();
        }
    }
}
