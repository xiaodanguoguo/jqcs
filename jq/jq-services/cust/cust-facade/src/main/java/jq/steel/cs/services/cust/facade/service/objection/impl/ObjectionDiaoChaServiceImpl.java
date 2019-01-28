package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.AssertContext;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.DateUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.dao.*;
import jq.steel.cs.services.cust.facade.model.*;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionDiaoChaService;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
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

    @Autowired
    private CrmClaimLogMapper crmClaimLogMapper;


    //条件查询
    @Override
    public PageDTO<ObjectionDiaoChaVO> findByPage(ObjectionDiaoChaVO objectionDiaoChaVO) {
        try {
            //转换mdel
            CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
            BeanCopyUtil.copy(objectionDiaoChaVO,crmClaimOutInquire);
            if(crmClaimOutInquire.getDeptCode()!=null&& crmClaimOutInquire.getDeptCode()!=""){
                crmClaimOutInquire.setDeptCodes(null);
            }
            PageDTOUtil.startPage(objectionDiaoChaVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimOutInquire.getStartDt());
            crmClaimOutInquire.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimOutInquire.getEndDt());
            crmClaimOutInquire.setEndDtStr(endDtStr);
            //当前登录人的deptCodes  '1000/不锈钢厂' '2000/炼轧厂''2200/碳钢薄板厂''3000/榆钢工厂'
            List<CrmClaimOutInquire> list = crmClaimOutInquireMapper.findByPage(crmClaimOutInquire);
            //转换返回对象
            List<ObjectionDiaoChaVO> objectionDiaoChaVOS = BeanCopyUtil.copyList(list, ObjectionDiaoChaVO.class);
            // 分页对象
            PageDTO<ObjectionDiaoChaVO> transform = PageDTOUtil.transform(objectionDiaoChaVOS);
            for (ObjectionDiaoChaVO objectionDiaoChaVO1:transform.getResultData()){
                crmClaimOutInquire = new CrmClaimOutInquire();
                BeanCopyUtil.copy(objectionDiaoChaVO1, crmClaimOutInquire);
                if(crmClaimOutInquire.getDeptCode().equals("1000")){
                    objectionDiaoChaVO1.setDeptCode("不锈");
                }else if(crmClaimOutInquire.getDeptCode().equals("2000")){
                    objectionDiaoChaVO1.setDeptCode("炼轧");
                }else if(crmClaimOutInquire.getDeptCode().equals("2200")){
                    objectionDiaoChaVO1.setDeptCode("碳钢");
                }else if(crmClaimOutInquire.getDeptCode().equals("3000")){
                    objectionDiaoChaVO1.setDeptCode("榆钢");
                }

            }
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    //异议调查外部调查内部调查回显数据 确认书审核回显数据
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
        String orgCode = record.getOrgCode();
        String acctName = record.getAcctName();
        CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
        BeanCopyUtil.copy(record,crmClaimOutInquire);
        if (crmClaimOutInquire.getOptionType()==1){
            Integer integer;
            //先查询是否有数据
           List<CrmClaimOutInquire> list = crmClaimOutInquireMapper.findByParams(crmClaimOutInquire);
            if (list.size()>0){
                crmClaimOutInquire.setUpdateBy(acctName);
                crmClaimOutInquire.setUpdateDt(new Date());
                integer = crmClaimOutInquireMapper.update(crmClaimOutInquire);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(record.getClaimNo());
                crmClaimLog.setType("外部调查报告修改");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("外部调查报告修改");
                crmClaimLogMapper.insert(crmClaimLog);
            }else {
                //新增数据
                crmClaimOutInquire.setCreateBy(acctName);
                crmClaimOutInquire.setCreateDt(new Date());
                integer = crmClaimOutInquireMapper.insertSelective(crmClaimOutInquire);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(record.getClaimNo());
                crmClaimLog.setType("外部调查报告保存");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("外部调查报告保存");
                crmClaimLogMapper.insert(crmClaimLog);
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
            crmClaimInfo.setUpdatedBy(acctName);
            crmClaimInfo.setInquireState("TRACK");
          /*  crmClaimInfo.setTrace(orgCode);
            crmClaimInfo.setTrackingTime(new Date());*/
            //添加是否跟踪过
            crmClaimInfo.setIsTrack("Y");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
            //记录跟踪人跟踪时间
            CrmClaimOutInquire crmClaimOutInquire1  = new CrmClaimOutInquire();
            crmClaimOutInquire1.setClaimNo(record.getClaimNo());
            crmClaimOutInquire1.setUpdateDt(new Date());
            crmClaimOutInquire1.setUpdateBy(acctName);
            crmClaimOutInquire1.setTrace(acctName);
            crmClaimOutInquire1.setTrackingTime(new Date());
            crmClaimOutInquireMapper.update(crmClaimOutInquire1);

            //日志记录
            CrmClaimLog crmClaimLog = new CrmClaimLog();
            crmClaimLog.setClaimNo(record.getClaimNo());
            crmClaimLog.setType("外部调查报告跟踪");
            crmClaimLog.setCreatedBy(acctName);
            crmClaimLog.setCreatedDt(new Date());
            crmClaimLog.setOpMemo("外部调查报告跟踪");
            crmClaimLogMapper.insert(crmClaimLog);

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
            //判断是否有数据 有修改，没有删除
            List<CrmClaimOutInquire> crmClaimOutInquires =crmClaimOutInquireMapper.findByParams(crmClaimOutInquire);
            if(crmClaimOutInquires.size()>0){
                crmClaimOutInquire.setUpdateBy(acctName);
                crmClaimOutInquire.setUpdateDt(new Date());
                crmClaimOutInquireMapper.update(crmClaimOutInquire);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(record.getClaimNo());
                crmClaimLog.setType("外部调查报告提交时有数据进行修改");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("外部调查报告提交时有数据进行修改");
                crmClaimLogMapper.insert(crmClaimLog);
            }else {
                crmClaimOutInquire.setCreateBy(acctName);
                crmClaimOutInquire.setCreateDt(new Date());
                crmClaimOutInquireMapper.insertSelective(crmClaimOutInquire);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(record.getClaimNo());
                crmClaimLog.setType("外部调查报告提交时没有数据进行新增");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("外部调查报告提交时没有数据进行新增");
                crmClaimLogMapper.insert(crmClaimLog);
            }


            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(acctName);
            crmClaimInfo.setInquireState("OUTEND");
            //crmClaimInfo.setClaimState("INVESTIGATION");
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
            // 修改异议状态数据  异议状态变为处理中
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(acctName);
            crmClaimInfo.setConfirmationPerson(acctName);
            crmClaimInfo.setConfirmationTime(new Date());
            crmClaimInfo.setInquireState("CONFIRM");
            crmClaimInfo.setClaimState("HANDLE");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimApply.setUpdatedDt(new Date());
            crmClaimApply.setUpdatedBy(acctName);
            crmClaimApply.setClaimState("HANDLE");
            crmClaimApplyMapper.update(crmClaimApply);

            //添加协议书数据
            CrmAgreementInfo crmAgreementInfo = new CrmAgreementInfo();
            crmAgreementInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmAgreementInfo.setAgreementAmount(crmClaimOutInquire.getAgreementAmount());
            crmAgreementInfo.setAgreementContent(crmClaimOutInquire.getAgreementContent());
            crmAgreementInfo.setDownloadableNum(3);
            crmAgreementInfo.setDownloadedNum(0);
            crmAgreementInfo.setCreatedBy(acctName);
            crmAgreementInfoMapper.insertSelective(crmAgreementInfo);

            //日志记录
            CrmClaimLog crmClaimLog = new CrmClaimLog();
            crmClaimLog.setClaimNo(record.getClaimNo());
            crmClaimLog.setType("确认书审核通过");
            crmClaimLog.setCreatedBy(acctName);
            crmClaimLog.setCreatedDt(new Date());
            crmClaimLog.setOpMemo("确认书审核通过");
            crmClaimLogMapper.insert(crmClaimLog);
        }else {
            //确认书驳回  审核原因
           /* CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimApply.setUpdatedBy(AssertContext.getAcctName());
            crmClaimApply.setUpdatedDt(new Date());
            crmClaimApply.setClaimState("REJECT");
            crmClaimApply.setRejectReason(crmClaimOutInquire.getRejectReason());
            crmClaimApplyMapper.update(crmClaimApply);*/

            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(acctName);
            crmClaimInfo.setInquireState("OUTSTART");
            crmClaimInfo.setRejectReason(crmClaimOutInquire.getRejectReason());
            //日志记录
            CrmClaimLog crmClaimLog = new CrmClaimLog();
            crmClaimLog.setClaimNo(record.getClaimNo());
            crmClaimLog.setType("确认书审核驳回");
            crmClaimLog.setCreatedBy(acctName);
            crmClaimLog.setCreatedDt(new Date());
            crmClaimLog.setOpMemo("确认书审核驳回");
            crmClaimLogMapper.insert(crmClaimLog);
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
            return i;

        }
        crmClaimOutInquireMapper.update(crmClaimOutInquire);
        return null;
    }

    //内部调查报告（保存，提交）//1内部保存 2内部提交
    @Override
    public Integer updateInside(ObjectionDiaoChaVO record) {
        String orgCode = record.getOrgCode();
        String acctName = record.getAcctName();
        CrmClaimInnerInquire crmClaimInnerInquire = new CrmClaimInnerInquire();
        BeanCopyUtil.copy(record,crmClaimInnerInquire);
        if (crmClaimInnerInquire.getOptionType()==1){
            //查询
            Integer integer;
            List<CrmClaimInnerInquire> list = crmClaimInnerInquireMapper.findByParams(crmClaimInnerInquire);
            if (list.size()>0){
               integer =  crmClaimInnerInquireMapper.update(crmClaimInnerInquire);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(record.getClaimNo());
                crmClaimLog.setType("内部调查报告保存时有记录进行修改");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("内部调查报告保存时有记录进行修改");
                crmClaimLogMapper.insert(crmClaimLog);
            }else {
                crmClaimInnerInquire.setCreateBy(acctName);
                crmClaimInnerInquire.setCreateDt(new Date());
             integer =  crmClaimInnerInquireMapper.insertSelective(crmClaimInnerInquire);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(record.getClaimNo());
                crmClaimLog.setType("内部调查报告保存时无记录进行新增");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("内部调查报告保存时无记录进行新增");
                crmClaimLogMapper.insert(crmClaimLog);
            }
            //修改外部表
            CrmClaimOutInquire crmClaimOutInquire = new CrmClaimOutInquire();
            crmClaimOutInquire.setClaimNo(record.getClaimNo());
            crmClaimOutInquire.setShift(record.getShift());
            crmClaimOutInquire.setProductDt(record.getProductDt());
            crmClaimOutInquireMapper.update(crmClaimOutInquire);
            return integer;
        }else {
            //修改外部表
            CrmClaimOutInquire crmClaimOutInquire = new CrmClaimOutInquire();
            crmClaimOutInquire.setClaimNo(record.getClaimNo());
            crmClaimOutInquire.setShift(record.getShift());
            crmClaimOutInquireMapper.update(crmClaimOutInquire);

            //日志记录
            CrmClaimLog crmClaimLog = new CrmClaimLog();
            crmClaimLog.setClaimNo(record.getClaimNo());
            crmClaimLog.setType("内部调查提交");
            crmClaimLog.setCreatedBy(acctName);
            crmClaimLog.setCreatedDt(new Date());
            crmClaimLog.setOpMemo("内部调查提交");
            crmClaimLogMapper.insert(crmClaimLog);

            //点击“提交”则内部调查报告结束，报告的状态修改为“调查结束”，记录调查结束时间和内部调查人员信息
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimInnerInquire.getClaimNo());
            crmClaimInfo.setUpdatedDt(new Date());
            crmClaimInfo.setUpdatedBy(acctName);
            crmClaimInfo.setInquireState("INEND");
            crmClaimInfo.setClaimState("INVESTIGATION");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);

            //记录时间和提交人
            crmClaimInnerInquire.setSubmitter(acctName);
            crmClaimInnerInquire.setCompletionTime(new Date());
            crmClaimInnerInquireMapper.update(crmClaimInnerInquire);

            //修改异议申请_用户表
            CrmClaimInfo crmClaimInfo1  = new CrmClaimInfo();
            crmClaimInfo1.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfo1.setUpdatedDt(new Date());
            crmClaimInfo1.setUpdatedBy(acctName);
            crmClaimInfo.setClaimState("INVESTIGATION");
            crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
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

            //提报日期
            Date date= crmClaimOutInquire2.getPresentationDate();
            String ast = DateUtil.formatDate(date, "yyyy-MM-dd");
            crmClaimOutInquire2.setAst(ast);

            //受理日期
            Date date1= crmClaimOutInquire2.getAdmissibilityTime();
            String at = DateUtil.formatDate(date1, "yyyy-MM-dd");
            crmClaimOutInquire2.setAt(at);

            //外部调查时间
            Date date2= crmClaimOutInquire2.getExternalLnvestigationDate();
            String eld = DateUtil.formatDate(date2, "yyyy-MM-dd");
            crmClaimOutInquire2.setEld(eld);

            //内部调查时间
            Date date3= crmClaimOutInquire2.getInternalLnvestigationDate();
            String ild = DateUtil.formatDate(date3, "yyyy-MM-dd");
            crmClaimOutInquire2.setIld(ild);

            if (crmClaimOutInquire2.getClaimState().equals("NEW")){
                crmClaimOutInquire2.setClaimState("新建");
            }else if (crmClaimOutInquire2.getClaimState().equals("PRESENT")){
                crmClaimOutInquire2.setClaimState("已提报");
            }else if (crmClaimOutInquire2.getClaimState().equals("ACCEPTANCE")){
                crmClaimOutInquire2.setClaimState("已受理");
            }else if (crmClaimOutInquire2.getClaimState().equals("REJECT")){
                crmClaimOutInquire2.setClaimState("已驳回");
            }else if (crmClaimOutInquire2.getClaimState().equals("INVESTIGATION")){
                crmClaimOutInquire2.setClaimState("调查中");
            }else if (crmClaimOutInquire2.getClaimState().equals("HANDLE")){
                crmClaimOutInquire2.setClaimState("处理中");
            }else if (crmClaimOutInquire2.getClaimState().equals("END")){
                crmClaimOutInquire2.setClaimState("已结案");
            }else if (crmClaimOutInquire2.getClaimState().equals("EVALUATE")){
                crmClaimOutInquire2.setClaimState("已评价");
            }else if (crmClaimOutInquire2.getClaimState().equals("ADOPT")){
                crmClaimOutInquire2.setClaimState("销售审核通过");
            }
            crmClaimOutInquires1.add(crmClaimOutInquire2);
        }

        //转换返回对象
        List<ObjectionDiaoChaVO> list1 = BeanCopyUtil.copyList(crmClaimOutInquires1,ObjectionDiaoChaVO.class);
        return list1;
    }



    //导出
    @Override
    public List<ObjectionDiaoChaVO> exportExcel(List<String> list) {
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
            CrmClaimOutInquire crmClaimOutInquire2 = crmClaimOutInquireMapper.findDateForExcel(crmClaimOutInquire);


            //登记日期
            Date date7= crmClaimOutInquire2.getAdmissibilityTime();
            String ast7 = DateUtil.formatDate(date7, "yyyy-MM-dd");
            crmClaimOutInquire2.setEld(ast7);

            //生产日期
            Date date= crmClaimOutInquire2.getProductDt();
            String ast = DateUtil.formatDate(date, "yyyy-MM-dd");
            crmClaimOutInquire2.setAst(ast);

            //完成日期
            Date date1= crmClaimOutInquire2.getCompletionTime();
            String at = DateUtil.formatDate(date1, "yyyy-MM-dd");
            crmClaimOutInquire2.setAt(at);
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
        String orgCode = record.getOrgCode();
        String  acctName =record.getAcctName();
        //进行驳回操作，需要录入驳回原因，后状态变为“已驳回”。
        CrmClaimApply crmClaimApply = new CrmClaimApply();
        crmClaimApply.setClaimNo(record.getClaimNo());
        crmClaimApply.setUpdatedBy(acctName);
        crmClaimApply.setUpdatedDt(new Date());
        crmClaimApply.setClaimState("REJECT");
        crmClaimApply.setRejectReason(record.getReasonsForCompulsoryClosure());
        crmClaimApplyMapper.update(crmClaimApply);

        //日志记录
        CrmClaimLog crmClaimLog = new CrmClaimLog();
        crmClaimLog.setClaimNo(record.getClaimNo());
        crmClaimLog.setType("调查报告驳回");
        crmClaimLog.setCreatedBy(acctName);
        crmClaimLog.setCreatedDt(new Date());
        crmClaimLog.setOpMemo("调查报告驳回");
        crmClaimLogMapper.insert(crmClaimLog);

        CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
        crmClaimInfo.setClaimNo(record.getClaimNo());
        crmClaimInfo.setUpdatedDt(new Date());
        crmClaimInfo.setUpdatedBy(acctName);
        // crmClaimInfo.setInquireState("");
        crmClaimInfo.setClaimState("REJECT");
        int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return i;
    }

    // 内部调查/外部调查开始结束状态修改
    @Override
    public Integer updateState(ObjectionDiaoChaVO record) {
        String orgCode = record.getOrgCode();
        String orgName = record.getOrgName();
        String acctId = record.getAcctId();
        String acctName = record.getAcctName();
        CrmClaimOutInquire crmClaimOutInquire  = new CrmClaimOutInquire();
        BeanCopyUtil.copy(record,crmClaimOutInquire);
        //修改诉赔明细表状态
        CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
        crmClaimInfo.setClaimNo(crmClaimOutInquire.getClaimNo());
        crmClaimInfo.setUpdatedDt(new Date());
        crmClaimInfo.setUpdatedBy(acctName);
        // crmClaimInfo.setInquireState("");
        if(record.getType().equals(1)){
            crmClaimInfo.setInquireState("OUTSTART");
            //异议状态-----》调查中
            CrmClaimInfo crmClaimInfoa  = new CrmClaimInfo();
            crmClaimInfoa.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimInfoa.setUpdatedDt(new Date());
            crmClaimInfoa.setUpdatedBy(acctName);
            crmClaimInfo.setClaimState("INVESTIGATION");
            int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfoa);

            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimApply.setUpdatedDt(new Date());
            crmClaimApply.setUpdatedBy(acctName);
            crmClaimApply.setClaimState("INVESTIGATION");
            crmClaimApplyMapper.update(crmClaimApply);

        }else  if(record.getType().equals(2)){
            crmClaimInfo.setInquireState("INSTART");
        }else {
            //异议状态-----》受理
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimOutInquire.getClaimNo());
            crmClaimApply.setAdmissibilityTime(new Date());
            crmClaimApply.setAdmissibilityUser(acctId);
            crmClaimApply.setClaimState("ACCEPTANCE");
            crmClaimApplyMapper.update(crmClaimApply);
            //日志记录
            CrmClaimLog crmClaimLog = new CrmClaimLog();
            crmClaimLog.setClaimNo(record.getClaimNo());
            crmClaimLog.setType("诉赔受理");
            crmClaimLog.setCreatedBy(acctName);
            crmClaimLog.setCreatedDt(new Date());
            crmClaimLog.setOpMemo("诉赔受理");
            crmClaimLogMapper.insert(crmClaimLog);
            crmClaimInfo.setClaimState("ACCEPTANCE");
        }

        int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return i;
    }
}
