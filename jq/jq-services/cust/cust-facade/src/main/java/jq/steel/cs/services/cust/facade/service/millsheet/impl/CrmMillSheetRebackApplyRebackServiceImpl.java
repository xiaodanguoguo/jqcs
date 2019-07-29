package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import jq.steel.cs.services.cust.facade.dao.CrmMillSheetRebackApplyMapper;
import jq.steel.cs.services.cust.facade.dao.MillFallbackInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;
import jq.steel.cs.services.cust.facade.model.MillFallbackInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.millsheet.CrmMillSheetRebackApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CrmMillSheetRebackApplyRebackServiceImpl implements CrmMillSheetRebackApplyService{

    @Autowired
    private CrmMillSheetRebackApplyMapper crmMillSheetRebackApplyMapper;

    @Autowired
    private MillFallbackInfoMapper millFallbackInfoMapper;
    @Autowired
    private MillSheetHostsMapper millSheetHostsMapper;
    @Override
    public CrmMillSheetRebackApply applyForRetreat(CrmMillSheetRebackApply crmMillSheetRebackApply) {

        //校验是否回退过
        MillFallbackInfo millFallbackInfo1 = new MillFallbackInfo();
        millFallbackInfo1.setMillSheetNo(crmMillSheetRebackApply.getMillSheetNo());
        List<MillFallbackInfo> millFallbackInfoList = millFallbackInfoMapper.selectByPrimaryKey(millFallbackInfo1);
        MillSheetHosts millSheetHosts = millSheetHostsMapper.selectByMillSheetNo(crmMillSheetRebackApply.getMillSheetNo());
        if (millFallbackInfoList.size() > 0 && !(millSheetHosts.getDeptCode().equals("1000"))){
            crmMillSheetRebackApply.setIsReback("Y");
        }else {
            crmMillSheetRebackApply.setIsReback("N");
            MillFallbackInfo millFallbackInfo = new MillFallbackInfo();
            millFallbackInfo.setMillSheetNo(crmMillSheetRebackApply.getMillSheetNo());
            millFallbackInfo.setCreatedBy(crmMillSheetRebackApply.getOrgCode());
            millFallbackInfo.setFallbackReason(crmMillSheetRebackApply.getRegresses());
            millFallbackInfo.setCreatedDt(new Date());
            Integer i  = millFallbackInfoMapper.insertSelective(millFallbackInfo);
        }

        return  crmMillSheetRebackApply;
    }
}
