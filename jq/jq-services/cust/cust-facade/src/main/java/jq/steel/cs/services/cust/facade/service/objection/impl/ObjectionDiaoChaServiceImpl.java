package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.AssertContext;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.dao.*;
import jq.steel.cs.services.cust.facade.model.*;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionDiaoChaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ObjectionDiaoChaServiceImpl implements ObjectionDiaoChaService{

    @Autowired
    private CrmClaimOutInquireMapper crmClaimOutInquireMapper;

    @Autowired
    private CrmClaimInnerInquireMapper crmClaimInnerInquireMapper;

    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    @Autowired
    private CrmClaimApplyMapper crmClaimApplyMapper;

    @Autowired
    private CrmAgreementInfoMapper crmAgreementInfoMapper;

    //条件查询
    @Override
    public PageDTO<ObjectionDiaoChaVO> findByPage(ObjectionDiaoChaVO objectionDiaoChaVO) {
        try {
            //转换mdel
            CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
            BeanCopyUtil.copy(objectionDiaoChaVO,crmClaimOutInquire);
            PageDTOUtil.startPage(objectionDiaoChaVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimOutInquire.getStartDt());
            crmClaimOutInquire.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimOutInquire.getEndDt());
            crmClaimOutInquire.setEndDtStr(endDtStr);
            List<CrmClaimOutInquire> list = crmClaimOutInquireMapper.findByPage(crmClaimOutInquire);
            //转换返回对象
            List<ObjectionDiaoChaVO> objectionDiaoChaVOS = BeanCopyUtil.copyList(list, ObjectionDiaoChaVO.class);
            // 分页对象
            PageDTO<ObjectionDiaoChaVO> transform = PageDTOUtil.transform(objectionDiaoChaVOS);
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    //异议调查外部调查内部调查回显数据
    @Override
    public ObjectionDiaoChaVO findDetails(ObjectionDiaoChaVO objectionDiaoChaVO) {
        CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
        BeanCopyUtil.copy(objectionDiaoChaVO,crmClaimOutInquire);
        CrmClaimOutInquire crmClaimOutInquire1  = crmClaimOutInquireMapper.findDetails(crmClaimOutInquire);
        BeanCopyUtil.copy(crmClaimOutInquire1,objectionDiaoChaVO);
        return objectionDiaoChaVO;
    }

    /**
     * 外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回） 1外部保存2外部跟踪3外部提4确认书通过5确认书审核驳回
     * 外部调查开始OUTSTART   已跟踪TRACK  外部调查结束OUTEND  内部调查开始INSTART   调查结束INEND   已确认CONFIRM
     * */
    @Override
    public Integer update(ObjectionDiaoChaVO record) {
        CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
        BeanCopyUtil.copy(record,crmClaimOutInquire);
        if (crmClaimOutInquire.getOptionType()==1){
            Integer integer;
                    //先查询是否有数据
           List<CrmClaimOutInquire> list = crmClaimOutInquireMapper.findByParams(crmClaimOutInquire);
            if (list.size()>0){
                crmClaimOutInquire.setUpdateBy(AssertContext.getAcctName());
                crmClaimOutInquire.setUpdateDt(new Date());
                integer = crmClaimOutInquireMapper.update(crmClaimOutInquire);
            }else {
                //新增数据
                crmClaimOutInquire.setCreateBy(AssertContext.getAcctName());
                crmClaimOutInquire.setCreateDt(new Date());
                integer = crmClaimOutInquireMapper.insert(crmClaimOutInquire);
            }
            //修改  货物所在地 lastUserAddr  缺陷名称 proProblem 异议确认量（吨）OBJECTION_CONFIRMATION
            CrmClaimInfo crmClaimInfo1 = new CrmClaimInfo();
            crmClaimInfo1.setClaimNo(record.getClaimNo());
            crmClaimInfo1.setLastUserAddr(record.getLastUserAddr());
            crmClaimInfo1.setProProblem(record.getProProblem());
            crmClaimInfo1.setObjectionConfirmation(record.getObjectionConfirmation());
            crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo1);
            return integer;

        }else if(crmClaimOutInquire.getOptionType()==2){
            //修改外部数据 + 修改外部调查状态
            Integer integer = crmClaimOutInquireMapper.update(crmClaimOutInquire);
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
            crmClaimInfo.setInquireState("TRACK");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);

            //修改  货物所在地 lastUserAddr  缺陷名称 proProblem 异议确认量（吨）OBJECTION_CONFIRMATION
            CrmClaimInfo crmClaimInfo1 = new CrmClaimInfo();
            crmClaimInfo1.setClaimNo(record.getClaimNo());
            crmClaimInfo1.setLastUserAddr(record.getLastUserAddr());
            crmClaimInfo1.setProProblem(record.getProProblem());
            crmClaimInfo1.setObjectionConfirmation(record.getObjectionConfirmation());
            crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo1);
            return  i;
        }else if (crmClaimOutInquire.getOptionType()==3){
            //外部调查报告状态变为“外部调查结束”；异议状态变为或者保持“调查中”，记录外部调查报告提交时间和提交人。
            crmClaimOutInquire.setUpdateBy(AssertContext.getAcctName());
            crmClaimOutInquire.setUpdateDt(new Date());
            crmClaimOutInquireMapper.update(crmClaimOutInquire);

            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
            crmClaimInfo.setInquireState("OUTEND");
            crmClaimInfo.setClaimState("INVESTIGATION");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);

            //修改  货物所在地 lastUserAddr  缺陷名称 proProblem 异议确认量（吨）OBJECTION_CONFIRMATION
            CrmClaimInfo crmClaimInfo1 = new CrmClaimInfo();
            crmClaimInfo1.setClaimNo(record.getClaimNo());
            crmClaimInfo1.setLastUserAddr(record.getLastUserAddr());
            crmClaimInfo1.setProProblem(record.getProProblem());
            crmClaimInfo1.setObjectionConfirmation(record.getObjectionConfirmation());
            crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo1);
            return i;
        }else if(crmClaimOutInquire.getOptionType()==4){
            //确认书（外部调查报告）状态变由“待确认”变为为“已确认” ,记录审核通过时间和审核人员信息。
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
            crmClaimInfo.setInquireState("CONFIRM");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);

            //添加协议书数据
            CrmAgreementInfo crmAgreementInfo = new CrmAgreementInfo();
            crmAgreementInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmAgreementInfo.setDownloadableNum(3);
            crmAgreementInfo.setDownloadedNum(0);
            crmAgreementInfo.setCreatedBy(AssertContext.getAcctId());
            crmAgreementInfoMapper.insertSelective(crmAgreementInfo);
        }else {
            //确认书驳回
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimApply.setUpdatedBy(AssertContext.getAcctName());
            crmClaimApply.setUpdatedDt(new Date());
            crmClaimApply.setClaimState("REJECT");
            crmClaimApply.setRejectReason(crmClaimOutInquire.getRejectReason());
            crmClaimApplyMapper.update(crmClaimApply);

            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
           // crmClaimInfo.setInquireState("");
            crmClaimInfo.setClaimState("REJECT");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
            return i;

        }
        crmClaimOutInquireMapper.update(crmClaimOutInquire);
        return null;
    }

    //内部调查报告（保存，提交）//1内部保存 2内部提交
    @Override
    public Integer updateInside(ObjectionDiaoChaVO record) {

        CrmClaimInnerInquire crmClaimInnerInquire = new CrmClaimInnerInquire();
        BeanCopyUtil.copy(record,crmClaimInnerInquire);
        if (crmClaimInnerInquire.getOptionType()==1){
            //查询
            List<CrmClaimInnerInquire> list = crmClaimInnerInquireMapper.findByParams(crmClaimInnerInquire);
            if (list.size()>0){
               Integer integer =  crmClaimInnerInquireMapper.update(crmClaimInnerInquire);
                return integer;
            }else {
                Integer integer =  crmClaimInnerInquireMapper.insertSelective(crmClaimInnerInquire);
                return integer;
            }
        }else {
            //点击“提交”则内部调查报告结束，报告的状态修改为“调查结束”，记录调查结束时间和内部调查人员信息
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimInnerInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
            // crmClaimInfo.setInquireState("");
            crmClaimInfo.setClaimState("INEND");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
            return i;
        }
    }

    //导出
    @Override
    public List<ObjectionDiaoChaVO> export(List<String> list) {
        //list---->对象
        List<CrmClaimOutInquire> crmClaimOutInquires = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            CrmClaimOutInquire crmClaimOutInquire = new CrmClaimOutInquire();
            crmClaimOutInquire.setClaimNo(list.get(i));
            crmClaimOutInquires.add(crmClaimOutInquire);
        }
        List<CrmClaimOutInquire> crmClaimOutInquires1 =new ArrayList<>();
        for (CrmClaimOutInquire crmClaimOutInquire:crmClaimOutInquires){
            CrmClaimOutInquire crmClaimOutInquire1  = new CrmClaimOutInquire();
            crmClaimOutInquire1.setClaimNo(crmClaimOutInquire.getClaimNo());
            CrmClaimOutInquire crmClaimOutInquire2 = crmClaimOutInquireMapper.find(crmClaimOutInquire);
            crmClaimOutInquires1.add(crmClaimOutInquire2);
        }

        //转换返回对象
        List<ObjectionDiaoChaVO> list1 = BeanCopyUtil.copyList(crmClaimOutInquires1,ObjectionDiaoChaVO.class);
        return list1;
    }

    //异议调查打印受理单+调查报告下载
    @Override
    public ObjectionDiaoChaVO print(ObjectionDiaoChaVO record) {
        //根据润乾报表然后地址
        return null;
    }

    // 调查报告驳回
    @Override
    public Integer reject(ObjectionDiaoChaVO record) {
        //进行驳回操作，需要录入驳回原因，后状态变为“已驳回”。
        //CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
       // BeanCopyUtil.copy(record,crmClaimOutInquire);
        CrmClaimApply crmClaimApply = new CrmClaimApply();
        crmClaimApply.setClaimNo(record.getClaimNo());
        crmClaimApply.setUpdatedBy(AssertContext.getAcctName());
        crmClaimApply.setUpdatedDt(new Date());
        crmClaimApply.setClaimState("REJECT");
        crmClaimApply.setRejectReason(record.getRejectReason());
        crmClaimApplyMapper.update(crmClaimApply);

        CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
        crmClaimInfo.setClaimNo(record.getClaimNo());
        crmClaimInfo.setUpdatedDt(new Date());
        crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
        // crmClaimInfo.setInquireState("");
        crmClaimInfo.setClaimState("REJECT");
        int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return i;
    }

    // 内部调查/外部调查开始结束状态修改
    @Override
    public Integer updateState(ObjectionDiaoChaVO record) {
        CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
        BeanCopyUtil.copy(record,crmClaimOutInquire);
        //修改诉赔明细表状态
        CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
        crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
        crmClaimInfo.setUpdatedDt(new Date());
        crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
        // crmClaimInfo.setInquireState("");
        if(record.getType().equals(1)){
            crmClaimInfo.setInquireState("OUTSTART");
        }else  if(record.getType().equals(2)){
            crmClaimInfo.setInquireState("INSTART");
        }else {
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimApply.setClaimState("ACCEPTANCE");
            crmClaimApplyMapper.update(crmClaimApply);

            CrmClaimInfo crmClaimInfo1  = new CrmClaimInfo();
            crmClaimInfo1.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo1.setClaimState("ACCEPTANCE");
        }

        int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return i;
    }
}
