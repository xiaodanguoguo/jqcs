package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.DateUtil;
import com.ebase.utils.StringUtil;
import jq.steel.cs.services.cust.api.vo.*;
import jq.steel.cs.services.cust.facade.dao.*;
import jq.steel.cs.services.cust.facade.model.*;
import jq.steel.cs.services.cust.facade.service.objection.CrmAgentInfoService;
import jq.steel.cs.services.cust.facade.service.objection.CrmCustomerInfoService;
import jq.steel.cs.services.cust.facade.service.objection.CrmLastuserInfoService;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionTiBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ObjectionTiBaoServiceImpl implements ObjectionTiBaoService{

    @Autowired
    private CrmClaimApplyMapper crmClaimApplyMapper;

    @Autowired
    private MillSheetHostsMapper millSheetHostsMapper;

    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    @Autowired
    private CrmCustomerInfoService crmCustomerInfoService;

    @Autowired
    private CrmLastuserInfoService crmLastuserInfoService;

    @Autowired
    private CrmAgentInfoService crmAgentInfoService;

    @Autowired
    private CrmAgreementInfoMapper crmAgreementInfoMapper;

    @Autowired
    private MillSheetHeadMapper millSheetHeadMapper;

    @Autowired
    private MdCommonTypeMapper mdCommonTypeMapper;

    @Autowired
    private CrmClaimApplyCopyMapper crmClaimApplyCopyMapper;

    @Autowired
    private CrmClaimLogMapper crmClaimLogMapper;




    //分页条件查询
    @Override
    public PageDTO<ObjectionTiBaoVO> findByPage(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            if(crmClaimApply.getDeptCode()!=null&& crmClaimApply.getDeptCode()!=""){
                crmClaimApply.setDeptCodes(null);
            }
          /*  if(crmClaimApply.getCustomerId()!=null && !crmClaimApply.getCustomerId().equals("")){

            }else {
                crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
            }*/
            // 如果orgType为1为销售公司设置customerid 为质证书的zkunner
            if(crmClaimApply.getOrgType().equals("1")){
                CrmClaimApply h = new CrmClaimApply();
                h.setCustomerName(crmClaimApply.getOrgName());
                List<CrmClaimApply> list =crmClaimApplyMapper.findMillSheetByCus(h);
                if(list.size()>0){
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++){
                        idall.add(list.get(i).getMillSheetNo());
                    }
                    crmClaimApply.setMillSheetNos(idall);
                }else {
                    crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
                }
            }
            PageDTOUtil.startPage(objectionTiBaoVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimApply.getStartDt());
            crmClaimApply.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimApply.getEndDt());
            crmClaimApply.setEndDtStr(endDtStr);
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findByPage(crmClaimApply);
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);
            for (ObjectionTiBaoVO objectionTiBaoVO1:transform.getResultData()){
                crmClaimApply = new CrmClaimApply();
                BeanCopyUtil.copy(objectionTiBaoVO1, crmClaimApply);
                if(crmClaimApply.getDeptCode().equals("1000")){
                    objectionTiBaoVO1.setDeptCode("不锈");
                }else if(crmClaimApply.getDeptCode().equals("2000")){
                    objectionTiBaoVO1.setDeptCode("炼轧");
                }else if(crmClaimApply.getDeptCode().equals("2200")){
                    objectionTiBaoVO1.setDeptCode("碳钢");
                }else if(crmClaimApply.getDeptCode().equals("3000")){
                    objectionTiBaoVO1.setDeptCode("榆钢");
                }

            }

            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }


    //新增查询修改查询和详情查询和销售审核查询
    @Override
    public ObjectionTiBaoVO findDetails(ObjectionTiBaoVO objectionTiBaoVO) {
        String orgCode = objectionTiBaoVO.getOrgCode();
        String orgName = objectionTiBaoVO.getOrgName();
        //1是新建2是修改3是详情4销售审核
        //转换model
        CrmClaimApply crmClaimApply  = new CrmClaimApply();
        BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
        CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
        BeanCopyUtil.copy(objectionTiBaoVO,crmClaimInfo);
        if(crmClaimApply.getOptionType()==1){
            //新建给返回使用单位客户单位就行
            CrmCustomerInfoVO crmCustomerInfoVO = new CrmCustomerInfoVO();
            crmCustomerInfoVO.setCustomerId(orgCode);
            List<CrmCustomerInfoVO> list= crmCustomerInfoService.findDefault(crmCustomerInfoVO);
            if (list.size()>0){
                CrmLastuserInfoVO crmLastuserInfoVO = new CrmLastuserInfoVO();
                crmLastuserInfoVO.setCustomerId(orgCode);
                List<CrmLastuserInfoVO> list1 = crmLastuserInfoService.findDefault(crmLastuserInfoVO);
                objectionTiBaoVO.setCustomerId(list.get(0).getCustomerId());
                objectionTiBaoVO.setCustomerName(list.get(0).getCustomerName());
                objectionTiBaoVO.setCustAddr(list.get(0).getCustAddr());
                objectionTiBaoVO.setCustEmpNo(list.get(0).getCustEmpNo());
                objectionTiBaoVO.setCustTel(list.get(0).getCustTel());
                if (list1.size()>0){
                    objectionTiBaoVO.setLastUser(list1.get(0).getLastUser());
                    objectionTiBaoVO.setLastUserId(list1.get(0).getLastUserId());
                    objectionTiBaoVO.setLastUserAddr(list1.get(0).getLastUserAddr());
                    objectionTiBaoVO.setCreateEmpNo(list1.get(0).getCreateEmpNo());
                    objectionTiBaoVO.setLastUserTel(list1.get(0).getLastUserTel());

                    //代理单位查询
                    CrmAgentInfoVO crmAgentInfoVO = new CrmAgentInfoVO();
                    crmAgentInfoVO.setAgentId(orgCode);
                    List<CrmAgentInfoVO> crmAgentInfoVOList = crmAgentInfoService.findDefault(crmAgentInfoVO);
                    if (crmAgentInfoVOList.size()>0){
                        objectionTiBaoVO.setAgentId(crmAgentInfoVOList.get(0).getAgentId());
                        objectionTiBaoVO.setAgentName(crmAgentInfoVOList.get(0).getAgentName());
                        objectionTiBaoVO.setAgentAddr(crmAgentInfoVOList.get(0).getAgentAddr());
                        objectionTiBaoVO.setAgentEmpNo(crmAgentInfoVOList.get(0).getAgentEmpNo());
                        objectionTiBaoVO.setAgentTel(crmAgentInfoVOList.get(0).getAgentTel());
                        return objectionTiBaoVO;
                    }else {
                        objectionTiBaoVO.setAgentId(orgCode);
                    }
                    crmClaimApply.setExplain("请填写默认代理单位");
                    return objectionTiBaoVO;
                }else{
                    objectionTiBaoVO.setLastUserId(orgCode);
                }
                crmClaimApply.setExplain("请填写默认使用单位");
                return objectionTiBaoVO;
            }else{
                objectionTiBaoVO.setCustomerId(orgCode);
                crmClaimApply.setExplain("请填写默认订货单位");
                return objectionTiBaoVO;

            }

        }else if(crmClaimApply.getOptionType()==2){
            //修改返回apply表数据
            CrmClaimApply crmClaimApply1 = crmClaimApplyMapper.findByParams(crmClaimApply);
            BeanCopyUtil.copy(crmClaimApply1,objectionTiBaoVO);
            return  objectionTiBaoVO;
        }else if(crmClaimApply.getOptionType()==3){
            //详情返回apply表数据
            CrmClaimApply crmClaimApply1 = crmClaimApplyMapper.findByParams(crmClaimApply);
            BeanCopyUtil.copy(crmClaimApply1,objectionTiBaoVO);
            return  objectionTiBaoVO;
        }else if(crmClaimApply.getOptionType()==4){
            //详情返回info表数据
            CrmClaimInfo crmClaimInfo1 =crmClaimInfoMapper.findByParams(crmClaimInfo);
            BeanCopyUtil.copy(crmClaimInfo1,objectionTiBaoVO);
            return  objectionTiBaoVO;
        }
        return null;
    }




    //新增查询修改查询和详情查询和销售审核查询(app)
    @Override
    public ObjectionTiBaoVO findDetailsForApp(ObjectionTiBaoVO objectionTiBaoVO) {
        String orgCode = objectionTiBaoVO.getOrgCode();
        String orgName = objectionTiBaoVO.getOrgName();
        //1是新建2是修改3是详情4销售审核
        //转换model
        CrmClaimApply crmClaimApply  = new CrmClaimApply();
        BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
        CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
        BeanCopyUtil.copy(objectionTiBaoVO,crmClaimInfo);
        if(crmClaimApply.getOptionType()==1){
            //新建给返回使用单位客户单位就行
            CrmCustomerInfoVO crmCustomerInfoVO = new CrmCustomerInfoVO();
            crmCustomerInfoVO.setCustomerId(orgCode);
            List<CrmCustomerInfoVO> list= crmCustomerInfoService.findDefault(crmCustomerInfoVO);
            if (list.size()>0){
                CrmLastuserInfoVO crmLastuserInfoVO = new CrmLastuserInfoVO();
                crmLastuserInfoVO.setCustomerId(orgCode);
                List<CrmLastuserInfoVO> list1 = crmLastuserInfoService.findDefault(crmLastuserInfoVO);
                objectionTiBaoVO.setCustomerId(list.get(0).getCustomerId());
                objectionTiBaoVO.setCustomerName(list.get(0).getCustomerName());
                objectionTiBaoVO.setCustAddr(list.get(0).getCustAddr());
                objectionTiBaoVO.setCustEmpNo(list.get(0).getCustEmpNo());
                objectionTiBaoVO.setCustTel(list.get(0).getCustTel());
                if (list1.size()>0){
                    objectionTiBaoVO.setLastUser(list1.get(0).getLastUser());
                    objectionTiBaoVO.setLastUserId(list1.get(0).getLastUserId());
                    objectionTiBaoVO.setLastUserAddr(list1.get(0).getLastUserAddr());
                    objectionTiBaoVO.setCreateEmpNo(list1.get(0).getCreateEmpNo());
                    objectionTiBaoVO.setLastUserTel(list1.get(0).getLastUserTel());

                    //代理单位查询
                    CrmAgentInfoVO crmAgentInfoVO = new CrmAgentInfoVO();
                    crmAgentInfoVO.setAgentId(orgCode);
                    List<CrmAgentInfoVO> crmAgentInfoVOList = crmAgentInfoService.findDefault(crmAgentInfoVO);
                    if (crmAgentInfoVOList.size()>0){
                        objectionTiBaoVO.setAgentId(crmAgentInfoVOList.get(0).getAgentId());
                        objectionTiBaoVO.setAgentName(crmAgentInfoVOList.get(0).getAgentName());
                        objectionTiBaoVO.setAgentAddr(crmAgentInfoVOList.get(0).getAgentAddr());
                        objectionTiBaoVO.setAgentEmpNo(crmAgentInfoVOList.get(0).getAgentEmpNo());
                        objectionTiBaoVO.setAgentTel(crmAgentInfoVOList.get(0).getAgentTel());
                        return objectionTiBaoVO;
                    }else {
                        objectionTiBaoVO.setAgentId(orgCode);
                    }
                    crmClaimApply.setExplain("请填写默认代理单位");
                    return objectionTiBaoVO;
                }else{
                    objectionTiBaoVO.setLastUserId(orgCode);
                }
                crmClaimApply.setExplain("请填写默认使用单位");
                return objectionTiBaoVO;
            }else{
                objectionTiBaoVO.setCustomerId(orgCode);
                crmClaimApply.setExplain("请填写默认订货单位");
                return objectionTiBaoVO;

            }

        }else if(crmClaimApply.getOptionType()==2){
            //修改返回apply表数据
            CrmClaimApply crmClaimApply1 = crmClaimApplyMapper.findByParamsForApp(crmClaimApply);
            BeanCopyUtil.copy(crmClaimApply1,objectionTiBaoVO);
            return  objectionTiBaoVO;
        }else if(crmClaimApply.getOptionType()==3){
            //详情返回apply表数据
            CrmClaimApply crmClaimApply1 = crmClaimApplyMapper.findByParamsForApp(crmClaimApply);
            BeanCopyUtil.copy(crmClaimApply1,objectionTiBaoVO);
            return  objectionTiBaoVO;
        }else if(crmClaimApply.getOptionType()==4){
            //详情返回info表数据
            CrmClaimInfo crmClaimInfo1 =crmClaimInfoMapper.findByParams(crmClaimInfo);
            BeanCopyUtil.copy(crmClaimInfo1,objectionTiBaoVO);
            return  objectionTiBaoVO;
        }
        return null;
    }


    //新增修改销售审核保存驳回通过  保存数据
    @Override
    @Transactional
    public ObjectionTiBaoVO update(ObjectionTiBaoVO objectionTiBaoVO) {
        String orgCode = objectionTiBaoVO.getOrgCode();
        String orgName = objectionTiBaoVO.getOrgName();
        String acctName = objectionTiBaoVO.getAcctName();
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
            //诉赔申请表备份
            CrmClaimApplyCopy crmClaimApplyCopy = new CrmClaimApplyCopy();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimInfo);
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApplyCopy);
            //1审核保存操作2驳回操作3通过操作4修改保存5新增保存
            if (crmClaimApply.getOptionStuts()== 1){
                crmClaimApply.setUpdatedBy(acctName);
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApply.setUpdatedBy(acctName);
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApplyMapper.update(crmClaimApply);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(objectionTiBaoVO.getClaimNo());
                crmClaimLog.setType("销售审核保存");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo(crmClaimInfo.toString());
                crmClaimLogMapper.insert(crmClaimLog);
                Integer integer =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
                return  objectionTiBaoVO;
            }else if(crmClaimApply.getOptionStuts()== 2){
                CrmClaimApply h  = new CrmClaimApply();
                h.setUpdatedDt(new Date());
                h.setUpdatedBy(acctName);
                h.setClaimNo(crmClaimApply.getClaimNo());
                h.setClaimState("REJECT");
                h.setRejectReason(crmClaimApply.getRejectReason());
                h.setProProblem(crmClaimApply.getProProblem());
                crmClaimApplyMapper.update(h);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(objectionTiBaoVO.getClaimNo());
                crmClaimLog.setType("销售审核驳回");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("销售审核驳回");
                crmClaimLogMapper.insert(crmClaimLog);
                CrmClaimInfo g  = new CrmClaimInfo();
                g.setUpdatedDt(new Date());
                g.setUpdatedBy(acctName);
                g.setClaimNo(crmClaimApply.getClaimNo());
                g.setClaimState("REJECT");
                g.setRejectReason(crmClaimApply.getRejectReason());
                h.setProProblem(crmClaimApply.getProProblem());
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(g);
                return  objectionTiBaoVO;
            }else if(crmClaimApply.getOptionStuts()== 3){
                CrmClaimApply h  = new CrmClaimApply();
                h.setUpdatedDt(new Date());
                h.setUpdatedBy(acctName);
                h.setClaimNo(crmClaimApply.getClaimNo());
                h.setClaimState("ADOPT");
                h.setAuditor(acctName);
                h.setAuditTime(new Date());
                //h.setAdmissibilityTime(new Date());
                //h.setAdmissibilityUser(acctName);
                h.setProProblem(crmClaimApply.getProProblem());
                crmClaimApplyMapper.update(h);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(objectionTiBaoVO.getClaimNo());
                crmClaimLog.setType("销售审核通过");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("销售审核通过");
                crmClaimLogMapper.insert(crmClaimLog);
                CrmClaimInfo g  = new CrmClaimInfo();
                g.setUpdatedDt(new Date());
                g.setUpdatedBy(acctName);
                g.setClaimNo(crmClaimApply.getClaimNo());
                g.setClaimState("ADOPT");
                g.setProProblem(crmClaimInfo.getProProblem());
                g.setClaimType(crmClaimInfo.getClaimType());
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(g);
                return  objectionTiBaoVO;
            }else if(crmClaimApply.getOptionStuts()== 4){
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(objectionTiBaoVO.getClaimNo());
                crmClaimLog.setType("用戶诉赔修改保存");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo(crmClaimApply.toString());
                crmClaimLogMapper.insert(crmClaimLog);
                //修改保存
                crmClaimApplyCopyMapper.update(crmClaimApplyCopy);
                crmClaimApplyMapper.update(crmClaimApply);
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
                return  objectionTiBaoVO;
            }else if(crmClaimApply.getOptionStuts()== 5){

                String millsheetNO = objectionTiBaoVO.getMillSheetNo();
                //获取生产厂家
                MillSheetHosts millSheetHosts = new MillSheetHosts();
                millSheetHosts.setMillSheetNo(millsheetNO);
                List<MillSheetHosts> millSheetHosts1  = millSheetHostsMapper.findDeptCode(millSheetHosts);
                if(CollectionUtils.isEmpty(millSheetHosts1)){
                    objectionTiBaoVO.setCheckCode(-100);
                    return objectionTiBaoVO;
                }
                String deptCode = millSheetHosts1.get(0).getDeptCode();
                if(StringUtil.isEmpty(deptCode)){
                    objectionTiBaoVO.setCheckCode(-101);
                    return objectionTiBaoVO;
                }
                Integer dissentingUnit;
                //1000：3不锈钢厂2000：1炼轧厂2200：2碳钢薄板厂3000：4榆钢工厂
                if (deptCode.equals("1000")){
                    dissentingUnit=3;
                }else if(deptCode.equals("2000")){
                    dissentingUnit=1;
                }else if (deptCode.equals("3000")){
                    dissentingUnit=4;
                }else {
                    dissentingUnit=2;
                }
                //获取head表产品名称
                MillSheetHead millSheetHead = new MillSheetHead();
                millSheetHead.setMillSheetNo(millsheetNO);
                MillSheetHead millSheetHead1 = millSheetHeadMapper.findCategoryCode(millSheetHead);
                //查询主数据获取到产品大类名称
                MdCommonType mdCommonType = new MdCommonType();
                mdCommonType.setTypeName(millSheetHead1.getZcpmc());
                MdCommonType mdCommonType1 = mdCommonTypeMapper.find(mdCommonType);
                String code = mdCommonType1.getTypeId().substring(0,2);
                String claimNo = this.getClaimNo(millsheetNO,code);
                //新增 apply表  再新增 info表
                crmClaimApply.setClaimState("NEW");
                crmClaimApply.setClaimNo(claimNo);
                crmClaimApply.setCreatedBy(acctName);
                crmClaimApply.setCustomerId(orgCode);
                //crmClaimApply.setCustomerId(objectionTiBaoVO.getCustomerId());
                crmClaimApply.setCreatedDt(new Date());
              //  Integer integer = crmClaimApplyMapper.insertSelective(crmClaimApply);
                Integer integer = crmClaimApplyMapper.insert(crmClaimApply);
                //备份表
                crmClaimApplyCopy.setClaimState("NEW");
                crmClaimApplyCopy.setClaimNo(claimNo);
                crmClaimApplyCopy.setCreatedBy(acctName);
                crmClaimApplyCopy.setCustomerId(orgCode);
                //crmClaimApplyCopy.setCustomerId(objectionTiBaoVO.getCustomerId());
                crmClaimApplyCopy.setCreatedDt(new Date());
                crmClaimApplyCopyMapper.insert(crmClaimApplyCopy);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(claimNo);
                crmClaimLog.setType("用戶诉赔新增保存");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo(crmClaimApply.toString());
                crmClaimLogMapper.insert(crmClaimLog);
                if (integer>0){
                    crmClaimInfo.setClaimState("NEW");
                    crmClaimInfo.setCreatedDt(new Date());
                    crmClaimInfo.setCreatedBy(acctName);
                    crmClaimInfo.setCustomerId(orgCode);
                    //crmClaimInfo.setCustomerId(objectionTiBaoVO.getCustomerId());
                    crmClaimInfo.setClaimNo(claimNo);
                    crmClaimInfo.setDissentingUnit(dissentingUnit);
                    Integer integer1 =crmClaimInfoMapper.insertSelective(crmClaimInfo);
                    objectionTiBaoVO.setClaimNo(claimNo);
                    return  objectionTiBaoVO;
                }
                objectionTiBaoVO.setClaimNo(claimNo);
                return  objectionTiBaoVO;

            }
            return null;

    }

    //提报/删除
    @Override
    public Integer submit(List<ObjectionTiBaoVO> objectionTiBaoVOS) {
        for (ObjectionTiBaoVO objectionTiBaoVO:objectionTiBaoVOS) {
            String orgCode = objectionTiBaoVO.getOrgCode();
            String orgName = objectionTiBaoVO.getOrgName();
            String acctName =objectionTiBaoVO.getAcctName();
            //转换mdel
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
            CrmClaimApplyCopy crmClaimApplyCopy = new CrmClaimApplyCopy();
            BeanCopyUtil.copy(objectionTiBaoVO, crmClaimApply);
            BeanCopyUtil.copy(objectionTiBaoVO, crmClaimInfo);
            BeanCopyUtil.copy(objectionTiBaoVO, crmClaimApplyCopy);
            if (crmClaimApply.getOptionType() == 1) {
                //提交
                crmClaimApply.setClaimState("PRESENT");
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApply.setUpdatedBy(acctName);
//            crmClaimApply.setPresentationUser(orgCode);
                crmClaimApply.setPresentationDate(new Date());
                crmClaimApplyMapper.update(crmClaimApply);
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(objectionTiBaoVO.getClaimNo());
                crmClaimLog.setType("用戶诉赔提报");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("用戶诉赔提报");
                crmClaimLogMapper.insert(crmClaimLog);
                crmClaimInfo.setClaimState("PRESENT");
                crmClaimInfo.setUpdatedBy(acctName);
                crmClaimInfo.setUpdatedDt(new Date());
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
                return integer;
            } else {
                //删除
                crmClaimApplyMapper.delete(crmClaimApply.getClaimNo());
                crmClaimApplyCopyMapper.delete(crmClaimApplyCopy.getClaimNo());
                //日志记录
                CrmClaimLog crmClaimLog = new CrmClaimLog();
                crmClaimLog.setClaimNo(objectionTiBaoVO.getClaimNo());
                crmClaimLog.setType("用戶诉赔删除");
                crmClaimLog.setCreatedBy(acctName);
                crmClaimLog.setCreatedDt(new Date());
                crmClaimLog.setOpMemo("用戶诉赔删除");
                crmClaimLogMapper.insert(crmClaimLog);
                crmClaimInfo.setClaimState("PRESENT");
                crmClaimInfo.setUpdatedBy(acctName);
                crmClaimInfo.setUpdatedDt(new Date());
                Integer integer = crmClaimInfoMapper.deleteByPrimaryKey(crmClaimInfo.getClaimNo());
                return integer;
            }
        }
        return 0;
    }

    //导出
    @Override
    public List<ObjectionTiBaoVO> export(List<String> list) {
        //list---->对象
        List<CrmClaimApply> crmClaimApplies = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setClaimNo(list.get(i));
            crmClaimApplies.add(crmClaimApply);
        }
        List<CrmClaimApply> crmClaimApplyList =new ArrayList<>();
        for (CrmClaimApply crmClaimApply1:crmClaimApplies){
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            crmClaimApply.setClaimNo(crmClaimApply1.getClaimNo());
            CrmClaimApply crmClaimApply2 = crmClaimApplyMapper.findByParams(crmClaimApply);
            Date pDATE= crmClaimApply2.getPresentationDate();
            String ast = DateUtil.formatDate(pDATE, "yyyy-MM-dd");
            crmClaimApply2.setAst(ast);
            if (crmClaimApply2.getClaimState().equals("NEW")){
                crmClaimApply2.setClaimState("新建");
            }else if (crmClaimApply2.getClaimState().equals("PRESENT")){
                crmClaimApply2.setClaimState("已提报");
            }else if (crmClaimApply2.getClaimState().equals("ACCEPTANCE")){
                crmClaimApply2.setClaimState("已受理");
            }else if (crmClaimApply2.getClaimState().equals("REJECT")){
                crmClaimApply2.setClaimState("已驳回");
            }else if (crmClaimApply2.getClaimState().equals("INVESTIGATION")){
                crmClaimApply2.setClaimState("调查中");
            }else if (crmClaimApply2.getClaimState().equals("HANDLE")){
                crmClaimApply2.setClaimState("处理中");
            }else if (crmClaimApply2.getClaimState().equals("END")){
                crmClaimApply2.setClaimState("已结案");
            }else if (crmClaimApply2.getClaimState().equals("EVALUATE")){
                crmClaimApply2.setClaimState("已评价");
            }else if (crmClaimApply2.getClaimState().equals("ADOPT")){
                crmClaimApply2.setClaimState("销售审核通过");
            }
            crmClaimApplyList.add(crmClaimApply2);
        }
        //转换返回对象
        List<ObjectionTiBaoVO> list1 = BeanCopyUtil.copyList(crmClaimApplyList,ObjectionTiBaoVO.class);
        return list1;
    }




    /**
     *  异议编号生成规则  LZ-JC-1808001 BX-BC-1808001 TB-RZ-1807002
     *  1-2位 厂别 LZ -炼轧厂 TG -碳钢薄板 BX -不锈钢 YG -榆中钢铁
     *  3位 分隔符 -
     *  4-5位 品种名称  JC -建材 BC -板材 PT -盘条 QT -其他 BC-不锈钢黑卷类 CC-不锈钢冷轧卷类 WP-不锈钢白皮中厚板 WC-不锈钢白卷类 BP-不锈钢黑皮中厚板 RZ-碳钢热轧卷 DX-碳钢镀锌卷 LZ-碳钢冷轧卷（含冷硬、重卷）
     *  6位 分隔符 -
     *  7-8位 年份后两位 18、17、16……
     * 9-10位 两位月份 01、02、03、04、05、06、07、08、09、10、11、12
     * 11-13位   三位顺序号 001、002、003……
     *
     */
    //管理单位代码（1000：不锈钢厂 2000：炼轧厂 2200：碳钢薄板厂 3000：榆钢工厂)

    public String getClaimNo (String millSheetNo,String categoryCode){
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        millSheetHosts.setMillSheetNo(millSheetNo);
        MillSheetHosts millSheetHosts1=millSheetHostsMapper.findUrl(millSheetHosts);
        String one = "";
        if (millSheetHosts1.getDeptCode().equals("1000")){
            one = "BX";
        }else if (millSheetHosts1.getDeptCode().equals("2000")){
            one = "LZ";
        }else if(millSheetHosts1.getDeptCode().equals("2200")){
            one = "TG";
        }else if (millSheetHosts1.getDeptCode().equals("3000")){
            one = "YG";
        }else {
            one = "XX";
        }
        String two = categoryCode;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String three = sdf.format(date).substring(2);
        String identification = one + "-" + two + "-" + three;
        Map<String, Object> map = new HashMap<>();
        // identification =  LZ-JC-1808
        map.put("claimNo", identification);
        int findNum = crmClaimApplyMapper.findClaimNumMax(map);
        int a5 = findNum + 1;
        String string = String.valueOf(a5);
        String newClaimNo = null;
        if (string.length() == 1) {
            newClaimNo = "00" + a5;
        } else if (string.length() == 2) {
            newClaimNo = "0" + a5;
        } else if (string.length() == 3) {
            newClaimNo = "" + a5;
        }
        String claimNo = one + "-" + two + "-" + three + newClaimNo;
        return claimNo;
    }



    //协议书下载

    @Override
    public ObjectionTiBaoVO findDownUrl(ObjectionTiBaoVO objectionTiBaoVO) {
        CrmAgreementInfo crmAgreementInfo = new CrmAgreementInfo();
        BeanCopyUtil.copy(objectionTiBaoVO,crmAgreementInfo);
        CrmAgreementInfo crmAgreementInfo1 = crmAgreementInfoMapper.findByParms(crmAgreementInfo);
        objectionTiBaoVO.setAgreementPath(crmAgreementInfo1.getAgreementUrlName());
        objectionTiBaoVO.setAgreementName(crmAgreementInfo1.getAgreementName());
        return objectionTiBaoVO;
    }

    //详情页面回显润乾（下载+打印）

    //报告附件查看

    @Override
    public ObjectionTiBaoVO lookPhoto(ObjectionTiBaoVO objectionTiBaoVO) {
        CrmClaimApply crmClaimApply = new CrmClaimApply();
        crmClaimApply.setClaimNo(objectionTiBaoVO.getClaimNo());
        CrmClaimApply crmClaimApplies = crmClaimApplyMapper.findByParams(crmClaimApply);
        objectionTiBaoVO.setUrl(crmClaimApplies.getReportPictures());

        return objectionTiBaoVO;
    }

    @Override
    public ObjectionTiBaoCountVO getCount(CrmClaimApply crmClaimApply) {
        if(crmClaimApply.getDeptCode()!=null&& crmClaimApply.getDeptCode()!=""){
            crmClaimApply.setDeptCodes(null);
        }
       System.out.println("deptcodes"+crmClaimApply.getDeptCodes());
        System.out.println("deptcode"+crmClaimApply.getDeptCode());
       //1銷售公司 2一級代理 3供应商 4终端客户 5酒钢管理
        if(crmClaimApply.getOrgType().equals("1")){
            CrmClaimApply h = new CrmClaimApply();
            h.setCustomerName(crmClaimApply.getOrgName());
            List<CrmClaimApply> list =crmClaimApplyMapper.findMillSheetByCus(h);
            if(list.size()>0){
                List<String> idall = new ArrayList<>();
                for (int i = 0; i < list.size(); i++){
                    idall.add(list.get(i).getMillSheetNo());
                }
                crmClaimApply.setMillSheetNos(idall);
            }else {
                crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
            }
        }
        if(crmClaimApply.getOrgType().equals("2")||crmClaimApply.getOrgType().equals("3")||crmClaimApply.getOrgType().equals("4")){
            crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
        }
        ObjectionTiBaoCountVO vo = new ObjectionTiBaoCountVO();
        List<ObjectionTiBaoCountVO> objectionTiBaoCountVOS = crmClaimApplyMapper.getCount(crmClaimApply);
        if(objectionTiBaoCountVOS.size()>0){
            Integer created= new Integer(0);
            Integer present= new Integer(0);
            Integer acceptance= new Integer(0);
            Integer reject= new Integer(0);
            Integer investigation= new Integer(0);
            Integer handle= new Integer(0);
            Integer end= new Integer(0);
            Integer evaluate= new Integer(0);
            Integer adopt= new Integer(0);
            for (int i = 0; i < objectionTiBaoCountVOS.size(); i++){
                created+=objectionTiBaoCountVOS.get(i).getCreated();
                present+=objectionTiBaoCountVOS.get(i).getPresent();
                acceptance+=objectionTiBaoCountVOS.get(i).getAcceptance();
                reject+=objectionTiBaoCountVOS.get(i).getReject();
                investigation+=objectionTiBaoCountVOS.get(i).getInvestigation();
                handle+=objectionTiBaoCountVOS.get(i).getHandle();
                end+=objectionTiBaoCountVOS.get(i).getEnd();
                evaluate+=objectionTiBaoCountVOS.get(i).getEvaluate();
                adopt+=objectionTiBaoCountVOS.get(i).getAdopt();
            }
            vo.setCreated(created);
            vo.setPresent(present);
            vo.setAcceptance(acceptance);
            vo.setReject(reject);
            vo.setInvestigation(investigation);
            vo.setHandle(handle);
            vo.setEnd(end);
            vo.setEvaluate(evaluate);
            vo.setAdopt(adopt);
            vo.setAll(vo.getCreated() + vo.getPresent() + vo.getReject());
        }else {
            // 新建created 已提报present 已受理acceptance 已驳回reject 调查中investigation 处理中handle 已结案end  已评价evaluate 销售审核通过adopt;
            vo.setCreated(0);
            vo.setPresent(0);
            vo.setAcceptance(0);
            vo.setReject(0);
            vo.setInvestigation(0);
            vo.setHandle(0);
            vo.setEnd(0);
            vo.setEvaluate(0);
            vo.setAdopt(0);
            vo.setAll(0);
        }

        return vo;
    }

/*    @Override
    public PageDTO<ObjectionTiBaoVO> findTiBaoByPage(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            PageDTOUtil.startPage(objectionTiBaoVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimApply.getStartDt());
            crmClaimApply.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimApply.getEndDt());
            crmClaimApply.setEndDtStr(endDtStr);

            //如果是已受理的状态,就使用查询受理人和受理人电话；已驳回状态，销售驳回原因和生产驳回原因的sql。
            if("ACCEPTANCE".equals(crmClaimApply.getClaimState())){
                List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findTiBaoByPageAndAcceptanceState(crmClaimApply);
                //转换返回对象
                List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
                // 分页对象
                PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);
                return transform;

            }
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findTiBaoByPage(crmClaimApply);
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);

            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }*/


    @Override
    public PageDTO<ObjectionTiBaoVO> findTiBaoByPage(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            if(crmClaimApply.getDeptCode()!=null&& crmClaimApply.getDeptCode()!=""){
                crmClaimApply.setDeptCodes(null);
            }
          /*  if(crmClaimApply.getCustomerId()!=null && !crmClaimApply.getCustomerId().equals("")){

            }else {
                crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
            }*/
            // 如果orgType为1为销售公司设置customerid 为质证书的zkunner
            if(crmClaimApply.getOrgType().equals("1")){
                CrmClaimApply h = new CrmClaimApply();
                h.setCustomerName(crmClaimApply.getOrgName());
                List<CrmClaimApply> list =crmClaimApplyMapper.findMillSheetByCusForApp(h);
                if(list.size()>0){
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++){
                        idall.add(list.get(i).getMillSheetNo());
                    }
                    crmClaimApply.setMillSheetNos(idall);
                }else {
                    crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
                }
            }
            PageDTOUtil.startPage(objectionTiBaoVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimApply.getStartDt());
            crmClaimApply.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimApply.getEndDt());
            crmClaimApply.setEndDtStr(endDtStr);
            //crmClaimApply.removeDeptCodeAndDeptCodes();---by lujiawei---
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findByPageForApp(crmClaimApply);
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);
            for (ObjectionTiBaoVO objectionTiBaoVO1:transform.getResultData()){
                crmClaimApply = new CrmClaimApply();
                BeanCopyUtil.copy(objectionTiBaoVO1, crmClaimApply);
                if(crmClaimApply.getDeptCode().equals("1000")){
                    objectionTiBaoVO1.setDeptCode("不锈");
                }else if(crmClaimApply.getDeptCode().equals("2000")){
                    objectionTiBaoVO1.setDeptCode("炼轧");
                }else if(crmClaimApply.getDeptCode().equals("2200")){
                    objectionTiBaoVO1.setDeptCode("碳钢");
                }else if(crmClaimApply.getDeptCode().equals("3000")){
                    objectionTiBaoVO1.setDeptCode("榆钢");
                }

            }

            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    //app异议跟踪审核查询  只查询以提报状态
    @Override
    public PageDTO<ObjectionTiBaoVO> findShenHeByPageForApp(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO, crmClaimApply);
            if (crmClaimApply.getDeptCode() != null && crmClaimApply.getDeptCode() != "") {
                crmClaimApply.setDeptCodes(null);
            }
            // 如果orgType为1为销售公司设置customerid 为质证书的zkunner
            if (crmClaimApply.getOrgType().equals("1")) {
                CrmClaimApply h = new CrmClaimApply();
                h.setCustomerName(crmClaimApply.getOrgName());
                List<CrmClaimApply> list = crmClaimApplyMapper.findMillSheetByCusForApp(h);
                if (list.size() > 0) {
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        idall.add(list.get(i).getMillSheetNo());
                    }
                    crmClaimApply.setMillSheetNos(idall);
                } else {
                    crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
                }
            }
            PageDTOUtil.startPage(objectionTiBaoVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimApply.getStartDt());
            crmClaimApply.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimApply.getEndDt());
            crmClaimApply.setEndDtStr(endDtStr);
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findShenHeByPageForApp(crmClaimApply);
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);
            for (ObjectionTiBaoVO objectionTiBaoVO1 : transform.getResultData()) {
                crmClaimApply = new CrmClaimApply();
                BeanCopyUtil.copy(objectionTiBaoVO1, crmClaimApply);
                if (crmClaimApply.getDeptCode().equals("1000")) {
                    objectionTiBaoVO1.setDeptCode("不锈");
                } else if (crmClaimApply.getDeptCode().equals("2000")) {
                    objectionTiBaoVO1.setDeptCode("炼轧");
                } else if (crmClaimApply.getDeptCode().equals("2200")) {
                    objectionTiBaoVO1.setDeptCode("碳钢");
                } else if (crmClaimApply.getDeptCode().equals("3000")) {
                    objectionTiBaoVO1.setDeptCode("榆钢");
                }
            }
            return transform;
        } finally {
            PageDTOUtil.endPage();
        }
    }


    @Override
    public PageDTO<ObjectionTiBaoVO> findgenzongByPage(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            if(crmClaimApply.getDeptCode()!=null&& crmClaimApply.getDeptCode()!=""){
                crmClaimApply.setDeptCodes(null);
            }
            if(crmClaimApply.getOrgType().equals("1")){
                CrmClaimApply h = new CrmClaimApply();
                h.setCustomerName(crmClaimApply.getOrgName());
                List<CrmClaimApply> list =crmClaimApplyMapper.findMillSheetByCusForApp(h);
                if(list.size()>0){
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++){
                        idall.add(list.get(i).getMillSheetNo());
                    }
                    crmClaimApply.setMillSheetNos(idall);
                }else {
                    crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
                }
            }
            if(crmClaimApply.getOrgType().equals("2")||crmClaimApply.getOrgType().equals("3")||crmClaimApply.getOrgType().equals("4")){
                crmClaimApply.setCustomerId(crmClaimApply.getOrgName());
            }
            PageDTOUtil.startPage(objectionTiBaoVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimApply.getStartDt());
            crmClaimApply.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimApply.getEndDt());
            crmClaimApply.setEndDtStr(endDtStr);
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findgenzongByPage(crmClaimApply);
            for (CrmClaimApply apply:crmClaimApplies){
                apply.setCountForApp(crmClaimApplies.size());
            }
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);
            for (ObjectionTiBaoVO objectionTiBaoVO1 : transform.getResultData()) {
                crmClaimApply = new CrmClaimApply();
                BeanCopyUtil.copy(objectionTiBaoVO1, crmClaimApply);
                if (crmClaimApply.getDeptCode().equals("1000")) {
                    objectionTiBaoVO1.setDeptCode("不锈");
                } else if (crmClaimApply.getDeptCode().equals("2000")) {
                    objectionTiBaoVO1.setDeptCode("炼轧");
                } else if (crmClaimApply.getDeptCode().equals("2200")) {
                    objectionTiBaoVO1.setDeptCode("碳钢");
                } else if (crmClaimApply.getDeptCode().equals("3000")) {
                    objectionTiBaoVO1.setDeptCode("榆钢");
                }
            }

            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }
}
