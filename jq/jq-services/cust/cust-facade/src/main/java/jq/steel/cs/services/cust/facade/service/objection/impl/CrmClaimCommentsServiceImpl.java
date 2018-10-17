package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmClaimCommentsVO;
import jq.steel.cs.services.cust.facade.dao.CrmClaimApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimCommentsMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.model.CrmClaimComments;
import jq.steel.cs.services.cust.facade.model.CrmClaimInfo;
import jq.steel.cs.services.cust.facade.service.objection.CrmClaimCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CrmClaimCommentsServiceImpl implements CrmClaimCommentsService {

    @Autowired
    private CrmClaimCommentsMapper crmClaimCommentsMapper;

    @Autowired
    private CrmClaimApplyMapper crmClaimApplyMapper;

    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    @Override
    public Integer evaluate(CrmClaimCommentsVO crmClaimCommentsVO) {
        //如果这条数据是已评价的状态就不能再评价了
        CrmClaimApply apply = crmClaimApplyMapper.getByClaimNo(crmClaimCommentsVO);
        CrmClaimInfo info  = crmClaimInfoMapper.getByCaimNo(crmClaimCommentsVO);
        if(apply != null && info != null ){
            if(("EVALUATE".equals(apply.getClaimState())) || ("EVALUATE".equals(info.getClaimState()))){
                return 0;
            }
        }

        //否则新增评价状态
        CrmClaimComments crmClaimComments = BeanCopyUtil.copy(crmClaimCommentsVO, CrmClaimComments.class);
        crmClaimComments.setCreateBy(crmClaimCommentsVO.getOrgCode());
        crmClaimComments.setCreateDt(new Date());
        Integer integer = crmClaimCommentsMapper.insertSelective(crmClaimComments);
        //修改状态
        CrmClaimApply crmClaimApply = new CrmClaimApply();
        crmClaimApply.setClaimNo(crmClaimComments.getClaimNo());
        crmClaimApply.setClaimState("EVALUATE");
        CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
        crmClaimInfo.setClaimNo(crmClaimComments.getClaimNo());
        crmClaimInfo.setClaimState("EVALUATE");
        crmClaimApplyMapper.update(crmClaimApply);
        crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return integer;
    }
}
