package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.AssertContext;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.dao.CrmAgreementInfoMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmAgreementInfo;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.model.CrmClaimInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionChuLiService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ObjectionChuLiServiceImpl implements ObjectionChuLiService{

    @Autowired
    private CrmClaimApplyMapper crmClaimApplyMapper;

    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    @Autowired
    private CrmAgreementInfoMapper crmAgreementInfoMapper;

    //条件查询
    @Override
    public PageDTO<ObjectionChuLiVO> findByPage(ObjectionChuLiVO record) {
        try {
            //转换mdel
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            BeanCopyUtil.copy(record,crmClaimInfo);
            PageDTOUtil.startPage(record);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimInfo.getStartDt());
            crmClaimInfo.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimInfo.getEndDt());
            crmClaimInfo.setEndDtStr(endDtStr);
            List<CrmClaimInfo> list = crmClaimInfoMapper.findByPageChuLi(crmClaimInfo);
            List<ObjectionChuLiVO> objectionDiaoChaVOS = BeanCopyUtil.copyList(list, ObjectionChuLiVO.class);
            // 分页对象
            PageDTO<ObjectionChuLiVO> transform = PageDTOUtil.transform(objectionDiaoChaVOS);
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }


    /**
     * 公共信息查询
     * 甲方（发货单位）：甘肃酒钢集团宏兴钢铁股份有限公司碳钢薄板厂
     * 甲方（发货单位）：甘肃酒钢集团宏兴钢铁股份有限公司炼轧厂
     * 甲方（发货单位）：甘肃酒钢集团宏兴钢铁股份有限公司不锈钢分公司
     * 甲方（发货单位）：酒钢集团榆中钢铁有限责任公司
     * 管理单位代码（1000：不锈钢厂 2000：炼轧厂 2200：碳钢薄板厂 3000：榆钢工厂
     ）
     * */
    @Override
    public ObjectionChuLiVO findAll(ObjectionChuLiVO reqbody) {
        CrmAgreementInfo  crmAgreementInfo  = new CrmAgreementInfo();
        BeanCopyUtil.copy(reqbody,crmAgreementInfo);
        crmAgreementInfo.setClaimNo(reqbody.getClaimNo());
        CrmAgreementInfo crmAgreementInfo1  = crmAgreementInfoMapper.findAll(crmAgreementInfo);
        if (crmAgreementInfo1.getDeptCode().equals(1000)){
            crmAgreementInfo1.setDeptName("甘肃酒钢集团宏兴钢铁股份有限公司不锈钢分公司");
        }else if(crmAgreementInfo1.getDeptCode().equals(2000)){
            crmAgreementInfo1.setDeptName("甘肃酒钢集团宏兴钢铁股份有限公司炼轧厂");
        }else if(crmAgreementInfo1.getDeptCode().equals(2200)){
            crmAgreementInfo1.setDeptName("甘肃酒钢集团宏兴钢铁股份有限公司碳钢薄板厂");
        }else {
            crmAgreementInfo1.setDeptName("酒钢集团榆中钢铁有限责任公司");
        }
        BeanCopyUtil.copy(crmAgreementInfo1,reqbody);
        return reqbody;
    }

    /**
     * 协议书保存/提交/审核
     * 1是保存2是提交3是驳回4是通过
     *
     * */
    @Override
    public Integer agreementUpdate(ObjectionChuLiVO record) {
        CrmAgreementInfo  crmAgreementInfo  = new CrmAgreementInfo();
        BeanCopyUtil.copy(record,crmAgreementInfo);
        if (crmAgreementInfo.getOptionStuts()== 1){
            //先查询是否有此协议书
            crmAgreementInfo.setClaimNo(record.getClaimNo());
            List<CrmAgreementInfo> list= crmAgreementInfoMapper.findList(crmAgreementInfo);
            if (list.size()>0){
                crmAgreementInfo.setUpdatedBy(AssertContext.getAcctName());
                crmAgreementInfo.setUpdatedDt(new Date());
                crmAgreementInfo.setAgreementState("EDIT");
                crmAgreementInfo.setClaimNo(record.getClaimNo());
                Integer integer =crmAgreementInfoMapper.updateByPrimaryKeySelective(crmAgreementInfo);
                return  integer;
            }else {
                crmAgreementInfo.setCreatedDt(new Date());
                crmAgreementInfo.setCreatedBy(AssertContext.getAcctName());
                crmAgreementInfo.setAgreementState("EDIT");
                crmAgreementInfo.setClaimNo(record.getClaimNo());
                Integer integer  = crmAgreementInfoMapper.insertSelective(crmAgreementInfo);
                return  integer;
            }
        }else if(crmAgreementInfo.getOptionStuts()== 2){
            crmAgreementInfo.setUpdatedBy(AssertContext.getAcctName());
            crmAgreementInfo.setUpdatedDt(new Date());
            crmAgreementInfo.setAgreementState("COMPLETE");
            crmAgreementInfo.setClaimNo(record.getClaimNo());
            Integer integer =crmAgreementInfoMapper.updateByPrimaryKeySelective(crmAgreementInfo);
            return  integer;
        }else if(crmAgreementInfo.getOptionStuts()== 3){
            crmAgreementInfo.setUpdatedBy(AssertContext.getAcctName());
            crmAgreementInfo.setUpdatedDt(new Date());
            crmAgreementInfo.setAgreementState("EDIT");
            crmAgreementInfo.setClaimNo(record.getClaimNo());
            Integer integer =crmAgreementInfoMapper.updateByPrimaryKeySelective(crmAgreementInfo);
            return  integer;
        }else {
            crmAgreementInfo.setUpdatedBy(AssertContext.getAcctName());
            crmAgreementInfo.setUpdatedDt(new Date());
            crmAgreementInfo.setAgreementState("EXAMINE");
            crmAgreementInfo.setClaimNo(record.getClaimNo());
            Integer integer =crmAgreementInfoMapper.updateByPrimaryKeySelective(crmAgreementInfo);
            return  integer;
        }
    }

    //异议处理导出
    @Override
    public List<ObjectionChuLiVO> export(List<String> list) {
        //list---->对象
        List<CrmClaimInfo> crmClaimInfos = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(list.get(i));
            crmClaimInfos.add(crmClaimInfo);
        }
        List<CrmClaimInfo> crmClaimInfos1 =new ArrayList<>();
        for (CrmClaimInfo crmClaimApply1:crmClaimInfos){
            CrmClaimInfo crmClaimInfo  = new CrmClaimInfo();
            crmClaimInfo.setClaimNo(crmClaimApply1.getClaimNo());
            CrmClaimInfo crmClaimInfo1 = crmClaimInfoMapper.findByPage(crmClaimInfo);
            crmClaimInfos1.add(crmClaimInfo1);
        }
        //转换返回对象
        List<ObjectionChuLiVO> list1 = BeanCopyUtil.copyList(crmClaimInfos1,ObjectionChuLiVO.class);
        return list1;
    }

    //打印/预览 实时生成pdf并且返回url地址
    @Override
    public ObjectionChuLiVO preview(ObjectionChuLiVO record) {
        String report = "";
        Integer templateType = record.getTemplateType();
        CreatePdf createPdf = new CreatePdf();
        //判断
        if(templateType ==1){
            //协议书预览(图片)
            CrmAgreementInfo  crmAgreementInfo  = new CrmAgreementInfo();
            crmAgreementInfo.setClaimNo(record.getClaimNo());
            List<CrmAgreementInfo> list= crmAgreementInfoMapper.findList(crmAgreementInfo);
            report = list.get(0).getAgreementUrl();
            record.setReport(report);
           // record.setTemplateType("1");
        }else if(templateType ==3){
            //打印受理单操作 3   查看pdf接口
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            String  pdfName = record.getClaimNo() + "S.pdf";
            String reportReturn = createPdf.createPdf(record.getClaimNo() ,record.getReport(),pdfName,"shoulidan");
            record.setReport(reportReturn);
        }else if(templateType ==6 ){
            //异议处理。协议书数据模板查看下载。   6。 查看pdf接口
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            String  pdfName = record.getClaimNo() + "X.pdf";
            String reportReturn = createPdf.createPdf(record.getClaimNo() ,record.getReport(),pdfName,"xieyishu");
            //record.setTemplateType("3");
            record.setReport(reportReturn);
        }else if (templateType ==7 ){
            //打印通知单。7。   查看pdf接口
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            String  pdfName = record.getClaimNo() + "T.pdf";
            String reportReturn = createPdf.createPdf(record.getClaimNo() ,record.getReport(),pdfName,"tongzhidan");
            //record.setTemplateType("3");
            record.setReport(reportReturn);
        }
        return record;
    }

    //下载 返回文件流
    public List<ObjectionChuLiVO> download(ObjectionChuLiVO list) {
        CreatePdf createPdf = new CreatePdf();
        List<ObjectionChuLiVO> liVOS = new ArrayList<>();
        Integer templateType = list.getTemplateType();
        String claimNo = (String) list.getClaimNos().get(0);
        if (templateType==1){
            //异议提报协议书图片下载
            CrmAgreementInfo  crmAgreementInfo  = new CrmAgreementInfo();
            crmAgreementInfo.setClaimNo(claimNo);
            List<CrmAgreementInfo> crmAgreementInfos= crmAgreementInfoMapper.findList(crmAgreementInfo);
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            objectionChuLiVO.setReport(crmAgreementInfos.get(0).getAgreementUrl());
            objectionChuLiVO.setTemplateType(1);
            liVOS.add(objectionChuLiVO);
        }else if(templateType==2){
            //异议提报详情页面下载
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            String  pdfName = claimNo + "Y.pdf";
            String report = createPdf.createPdf(claimNo,list.getReport(),pdfName,"yiyibaogao");
            objectionChuLiVO.setTemplateType(2);
            objectionChuLiVO.setReport(report);
            liVOS.add(objectionChuLiVO);
        }else if(templateType==4){
            //下载内部调查  4  下载接口
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            String  pdfName = claimNo + "N.pdf";
            String report = createPdf.createPdf(claimNo,list.getReport(),pdfName,"neibudiaocha");
            objectionChuLiVO.setTemplateType(4);
            objectionChuLiVO.setReport(report);
            liVOS.add(objectionChuLiVO);
        }else if(templateType==5){
            //下载外部调查  5  下载接口
            ObjectionChuLiVO objectionChuLiVO = new ObjectionChuLiVO();
            String  pdfName = claimNo + "W.pdf";
            String report = createPdf.createPdf(claimNo,list.getReport(),pdfName,"waibudiaocha");
            objectionChuLiVO.setTemplateType(5);
            objectionChuLiVO.setReport(report);
            liVOS.add(objectionChuLiVO);
        }

       return liVOS;
    }

    // 强制结案
    @Override
    public Integer compulsorySettlement(ObjectionChuLiVO record) {
        CrmClaimApply crmClaimApply = new CrmClaimApply();
        CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
        BeanCopyUtil.copy(record,crmClaimApply);
        BeanCopyUtil.copy(record,crmClaimInfo);
        crmClaimApply.setUpdatedDt(new Date());
        crmClaimApply.setUpdatedBy(AssertContext.getAcctName());
        crmClaimApply.setClaimState("END");
        crmClaimApplyMapper.update(crmClaimApply);
        crmClaimInfo.setUpdatedBy(AssertContext.getAcctName());
        crmClaimInfo.setUpdatedDt(new Date());
        crmClaimInfo.setClaimState("END");
        int i =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
        return i;
    }
}
