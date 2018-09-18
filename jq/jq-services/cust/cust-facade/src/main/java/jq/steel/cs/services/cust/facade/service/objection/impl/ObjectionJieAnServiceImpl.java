package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.AssertContext;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionJieAnVO;
import jq.steel.cs.services.cust.facade.dao.CrmAgreementInfoMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmAgreementInfo;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.model.CrmClaimInfo;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionJieAnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ObjectionJieAnServiceImpl implements ObjectionJieAnService{

    @Autowired
    private CrmAgreementInfoMapper crmAgreementInfoMapper;
    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    @Autowired
    private CrmClaimApplyMapper crmClaimApplyMapper;

    //上传协议书文件
    @Override
    public Integer upload(ObjectionJieAnVO record) {
        int i;
        CrmAgreementInfo crmAgreementInfo = new CrmAgreementInfo();
        crmAgreementInfo.setClaimNo(record.getClaimNo());
        BeanCopyUtil.copy(record,crmAgreementInfo);
        //存储文件地址
        CrmAgreementInfo crmAgreementInfo1 = new CrmAgreementInfo();
        crmAgreementInfo1.setClaimNo(record.getClaimNo());
        crmAgreementInfo1.setAgreementUrl(record.getFilePath());
        crmAgreementInfoMapper.updateByPrimaryKeySelective(crmAgreementInfo1);
        //查询是否有文件
        List<CrmAgreementInfo> crmAgreementInfos = crmAgreementInfoMapper.findList(crmAgreementInfo);
        if (crmAgreementInfos.get(0).getAgreementUrlName() !=null){
            return 0;
        }
        return 1;
    }

    //异议结案撤销
    @Override
    public Integer revoke(ObjectionJieAnVO record) {
        //撤销后，协议书状态变为“编辑中”，异议状态仍为“处理中”。
        CrmAgreementInfo crmAgreementInfo = new CrmAgreementInfo();
        CrmClaimApply crmClaimApply = new CrmClaimApply();
        CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
        BeanCopyUtil.copy(record,crmAgreementInfo);
        BeanCopyUtil.copy(record,crmClaimApply);
        BeanCopyUtil.copy(record,crmClaimInfo);
        crmAgreementInfo.setClaimNo(record.getClaimNo());
        crmAgreementInfo.setAgreementState("EDIT");
        Integer integer =crmAgreementInfoMapper.updateByPrimaryKeySelective(crmAgreementInfo);

        crmClaimApply.setClaimNo(record.getClaimNo());
        crmClaimApply.setUpdatedBy(AssertContext.getAcctName());
        crmClaimApply.setUpdatedDt(new Date());
        crmClaimApply.setClaimState("HANDLE");
        crmClaimApplyMapper.update(crmClaimApply);
        crmClaimInfo.setClaimNo(record.getClaimNo());
        crmClaimInfo.setUpdatedDt(new Date());
        crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
        crmClaimInfo.setClaimState("HANDLE");
        int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return i;
    }

    //打印通知单
    @Override
    public ObjectionJieAnVO print(ObjectionJieAnVO record) {
        return null;
    }

    //查看文件
    @Override
    public ObjectionJieAnVO look(ObjectionJieAnVO record) {
        CrmAgreementInfo crmAgreementInfo = new CrmAgreementInfo();
        BeanCopyUtil.copy(record,crmAgreementInfo);
        //查询是否有文件
        crmAgreementInfo.setClaimNo(record.getClaimNo());
        CrmAgreementInfo crmAgreementInfos = crmAgreementInfoMapper.findAll(crmAgreementInfo);
        crmAgreementInfo.setClaimNoUrl(crmAgreementInfos.getAgreementUrlName());
        BeanCopyUtil.copy(crmAgreementInfos,record);
        return  record;
    }
}
