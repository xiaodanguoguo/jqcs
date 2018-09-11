package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.AssertContext;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetRebackApplyMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;
import jq.steel.cs.services.cust.facade.service.millsheet.CrmMillSheetRebackApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CrmMillSheetRebackApplyRebackServiceImpl implements CrmMillSheetRebackApplyService{

    @Autowired
    private CrmMillSheetRebackApplyMapper crmMillSheetRebackApplyMapper;
    @Override
    public Integer applyForRetreat(CrmMillSheetRebackApply crmMillSheetRebackApply) {
        crmMillSheetRebackApply.setCreatedBy(AssertContext.getAcctName());
        crmMillSheetRebackApply.setCreatedDt(new Date());
        Integer i = crmMillSheetRebackApplyMapper.insertSelective(crmMillSheetRebackApply);
        return  i;
    }
}
