package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.StringUtil;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoCountVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.dao.CrmAgreementInfoMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MdCommonTypeMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHeadMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmAgreementInfo;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.model.CrmClaimInfo;
import jq.steel.cs.services.cust.facade.model.MdCommonType;
import jq.steel.cs.services.cust.facade.model.MillSheetHead;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
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
    private CrmAgreementInfoMapper crmAgreementInfoMapper;

    @Autowired
    private MillSheetHeadMapper millSheetHeadMapper;

    @Autowired
    private MdCommonTypeMapper mdCommonTypeMapper;


    //分页条件查询
    @Override
    public PageDTO<ObjectionTiBaoVO> findByPage(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
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
                    return objectionTiBaoVO;
                }
                crmClaimApply.setExplain("请填写默认使用单位");
                return objectionTiBaoVO;
            }else{
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

    //新增修改销售审核保存驳回通过  保存数据
    @Override
    @Transactional
    public Integer update(ObjectionTiBaoVO objectionTiBaoVO) {
        String orgCode = objectionTiBaoVO.getOrgCode();
        String orgName = objectionTiBaoVO.getOrgName();
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimInfo);
            //1审核保存操作2驳回操作3通过操作4修改保存5新增保存
            if (crmClaimApply.getOptionStuts()== 1){
                crmClaimApply.setUpdatedBy(orgCode);
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApply.setUpdatedBy(orgCode);
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApplyMapper.update(crmClaimApply);
                Integer integer =  crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
                return  integer;
            }else if(crmClaimApply.getOptionStuts()== 2){
                CrmClaimApply h  = new CrmClaimApply();
                h.setUpdatedDt(new Date());
                h.setUpdatedBy(orgCode);
                h.setClaimNo(crmClaimApply.getClaimNo());
                h.setClaimState("REJECT");
                h.setRejectReason(crmClaimApply.getRejectReason());
                crmClaimApplyMapper.update(h);
                CrmClaimInfo g  = new CrmClaimInfo();
                g.setUpdatedDt(new Date());
                g.setUpdatedBy(orgCode);
                g.setClaimNo(crmClaimApply.getClaimNo());
                g.setClaimState("REJECT");
                g.setRejectReason(crmClaimApply.getRejectReason());
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(g);
                return  integer;
            }else if(crmClaimApply.getOptionStuts()== 3){
                CrmClaimApply h  = new CrmClaimApply();
                h.setUpdatedDt(new Date());
                h.setUpdatedBy(orgCode);
                h.setClaimNo(crmClaimApply.getClaimNo());
                h.setClaimState("ADOPT");
                h.setAdmissibilityTime(new Date());
                h.setAdmissibilityUser(orgCode);
                h.setProProblem(crmClaimApply.getProProblem());
                crmClaimApplyMapper.update(h);
                CrmClaimInfo g  = new CrmClaimInfo();
                g.setUpdatedDt(new Date());
                g.setUpdatedBy(orgCode);
                g.setClaimNo(crmClaimApply.getClaimNo());
                g.setClaimState("ADOPT");
                g.setProProblem(crmClaimInfo.getProProblem());
                g.setClaimType(crmClaimInfo.getClaimType());
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(g);
                return  integer;
            }else if(crmClaimApply.getOptionStuts()== 4){
                //修改保存
                crmClaimApplyMapper.update(crmClaimApply);
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
                return  integer;
            }else if(crmClaimApply.getOptionStuts()== 5){

                String millsheetNO = objectionTiBaoVO.getMillSheetNo();
                //获取生产厂家
                MillSheetHosts millSheetHosts = new MillSheetHosts();
                millSheetHosts.setMillSheetNo(millsheetNO);
                List<MillSheetHosts> millSheetHosts1  = millSheetHostsMapper.findDeptCode(millSheetHosts);
                if(CollectionUtils.isEmpty(millSheetHosts1)){
                    return -100;
                }
                String deptCode = millSheetHosts1.get(0).getDeptCode();
                if(StringUtil.isEmpty(deptCode)){
                    return  -101;
                }
                Integer dissentingUnit;
                //1000：不锈钢厂2000：炼轧厂2200：碳钢薄板厂3000：榆钢工厂
                if (deptCode.equals("1000")){
                    dissentingUnit=3;
                }else if(deptCode.equals("2000")){
                    dissentingUnit=1;
                }else if (deptCode.equals("3000")){
                    dissentingUnit=2;
                }else {
                    dissentingUnit=4;
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
                crmClaimApply.setCreatedBy(orgCode);
                crmClaimApply.setCustomerId(orgCode);
                crmClaimApply.setCreatedDt(new Date());
                Integer integer = crmClaimApplyMapper.insertSelective(crmClaimApply);
                if (integer>0){
                    crmClaimInfo.setClaimState("NEW");
                    crmClaimInfo.setCreatedDt(new Date());
                    crmClaimInfo.setCreatedBy(orgCode);
                    crmClaimInfo.setCustomerId(orgCode);
                    crmClaimInfo.setClaimNo(claimNo);
                    crmClaimInfo.setDissentingUnit(dissentingUnit);
                    Integer integer1 =crmClaimInfoMapper.insertSelective(crmClaimInfo);
                    return  integer;
                }
                return  integer;

            }
            return 00;

    }

    //提报/删除
    @Override
    public Integer submit(List<ObjectionTiBaoVO> objectionTiBaoVOS) {
        for (ObjectionTiBaoVO objectionTiBaoVO:objectionTiBaoVOS) {
            String orgCode = objectionTiBaoVO.getOrgCode();
            String orgName = objectionTiBaoVO.getOrgName();
            //转换mdel
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            CrmClaimInfo crmClaimInfo = new CrmClaimInfo();
            BeanCopyUtil.copy(objectionTiBaoVO, crmClaimApply);
            BeanCopyUtil.copy(objectionTiBaoVO, crmClaimInfo);
            if (crmClaimApply.getOptionType() == 1) {
                //提交
                crmClaimApply.setClaimState("PRESENT");
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApply.setUpdatedBy(orgCode);
//            crmClaimApply.setPresentationUser(orgCode);
                crmClaimApply.setPresentationDate(new Date());
                crmClaimApplyMapper.update(crmClaimApply);
                crmClaimInfo.setClaimState("PRESENT");
                crmClaimInfo.setUpdatedBy(orgCode);
                crmClaimInfo.setUpdatedDt(new Date());
                Integer integer = crmClaimInfoMapper.updateByPrimaryKeySelective(crmClaimInfo);
                return integer;
            } else {
                //删除
                crmClaimApply.setUpdatedDt(new Date());
                crmClaimApply.setUpdatedBy(orgCode);
                crmClaimApplyMapper.delete(crmClaimApply.getClaimNo());
                crmClaimInfo.setClaimState("PRESENT");
                crmClaimInfo.setUpdatedBy(orgCode);
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
        if (millSheetHosts1.getDeptCode().equals(1000)){
            one = "BX";
        }else if (millSheetHosts1.getDeptCode().equals(2000)){
            one = "LZ";
        }else if(millSheetHosts1.getDeptCode().equals(2200)){
            one = "TG";
        }else if (millSheetHosts1.getDeptCode().equals(3000)){
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
        objectionTiBaoVO.setUrl(crmClaimApplies.getFilePath());

        return objectionTiBaoVO;
    }

    @Override
    public ObjectionTiBaoCountVO getCount(CrmClaimApply crmClaimApply) {
        ObjectionTiBaoCountVO vo = crmClaimApplyMapper.getCount(crmClaimApply);
        vo.setAll(vo.getCreated() + vo.getPresent() + vo.getReject());

        return vo;
    }

    @Override
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
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findTiBaoByPage(crmClaimApply);
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);


            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public PageDTO<ObjectionTiBaoVO> findgenzongByPage(ObjectionTiBaoVO objectionTiBaoVO) {
        try {
            //转换mdel
            CrmClaimApply crmClaimApply  = new CrmClaimApply();
            BeanCopyUtil.copy(objectionTiBaoVO,crmClaimApply);
            PageDTOUtil.startPage(objectionTiBaoVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmClaimApply.getStartDt());
            crmClaimApply.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmClaimApply.getEndDt());
            crmClaimApply.setEndDtStr(endDtStr);
            List<CrmClaimApply> crmClaimApplies = crmClaimApplyMapper.findgenzongByPage(crmClaimApply);
            //转换返回对象
            List<ObjectionTiBaoVO> objectionTiBaoVOS = BeanCopyUtil.copyList(crmClaimApplies, ObjectionTiBaoVO.class);
            // 分页对象
            PageDTO<ObjectionTiBaoVO> transform = PageDTOUtil.transform(objectionTiBaoVOS);


            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }
}
