package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.DateUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.dao.CrmClaimApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmClaimInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.model.CrmClaimInfo;
import jq.steel.cs.services.cust.facade.model.ObjectionLedger;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionLendgerService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ObjectionLendgerServiceImpl implements ObjectionLendgerService{

    @Autowired
    private CrmClaimInfoMapper crmClaimInfoMapper;

    @Autowired
    private CrmClaimApplyMapper crmClaimApplyMapper;

    //分页条件查询
    @Override
    public PageDTO<ObjectionLedgerVO> findByPage(ObjectionLedgerVO objectionLedgerVO) {
        try {
            //转换mdel
            ObjectionLedger objectionLedger  = new ObjectionLedger();
            BeanCopyUtil.copy(objectionLedgerVO,objectionLedger);
            if(objectionLedger.getDeptCode()!=null&& objectionLedger.getDeptCode()!=""){
                //objectionLedger.setDeptCodes(null);
            }
            // 如果orgType为1为销售公司设置customerid 为质证书的zkunner
            if(objectionLedgerVO.getOrgType().equals("1")){
                CrmClaimApply h = new CrmClaimApply();
                h.setCustomerName(objectionLedger.getOrgName());
                List<CrmClaimApply> list =crmClaimApplyMapper.findMillSheetByCus(h);
                if(list.size()>0){
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++){
                        idall.add(list.get(i).getMillSheetNo());
                    }
                    objectionLedger.setMillSheetNos(idall);
                }else {
                    objectionLedger.setCustomerId(objectionLedger.getOrgName());
                }
            }
            PageDTOUtil.startPage(objectionLedgerVO);
            String startDtStr = DateFormatUtil.getStartDateStr(objectionLedger.getStartDt());
            objectionLedger.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(objectionLedger.getEndDt());
            objectionLedger.setEndDtStr(endDtStr);
            //新建状态的数据要在该界面中屏蔽掉。
            objectionLedger.setFlag("1");
            List<ObjectionLedger> ledgerList = crmClaimInfoMapper.findLedgerByPage(objectionLedger);
            //转换返回对象
            List<ObjectionLedgerVO> objectionLedgerVOS = BeanCopyUtil.copyList(ledgerList, ObjectionLedgerVO.class);
            // 分页对象
            PageDTO<ObjectionLedgerVO> transform = PageDTOUtil.transform(objectionLedgerVOS);
            BigDecimal bigDecimal1= new BigDecimal(0);
            BigDecimal bigDecimal2= new BigDecimal(0);
            BigDecimal bigDecimal3= new BigDecimal(0);
            //判断过期原因是否为空然后设置是否可以上传协议书
            for (ObjectionLedgerVO objectionLedgerVO1:transform.getResultData()) {
                objectionLedger = new ObjectionLedger();
                BeanCopyUtil.copy(objectionLedgerVO1, objectionLedger);
               /* if (objectionLedger.getDeptCode().equals("1000")) {
                    objectionLedgerVO1.setDeptCode("不锈");
                } else if (objectionLedger.getDeptCode().equals("2000")) {
                    objectionLedgerVO1.setDeptCode("炼轧");
                } else if (objectionLedger.getDeptCode().equals("2200")) {
                    objectionLedgerVO1.setDeptCode("碳钢");
                } else if (objectionLedger.getDeptCode().equals("3000")) {
                    objectionLedgerVO1.setDeptCode("榆钢");
                }*/
                //处理周期（结案时间减受理时间   7天 （没跟踪过） 20天（跟踪过））
                if(objectionLedger.getClosingTime()!=null&&objectionLedger.getAdmissibilityTime()!=null){
                    //Integer integer = DateUtil.countDays(objectionLedger.getAdmissibilityTime(),objectionLedger.getClosingTime());
                    Date dd= objectionLedger.getAdmissibilityTime();
                    String ss = DateUtil.formatDate(dd, "yyyy-MM-dd HH:mm:ss");
                    Date hh= objectionLedger.getClosingTime();
                    String gg = DateUtil.formatDate(hh, "yyyy-MM-dd HH:mm:ss");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try
                    {
                        Date d1 = df.parse(gg);
                        Date d2 = df.parse(ss);
                        long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                        long days = diff / (1000 * 60 * 60 * 24);

                        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                        objectionLedgerVO1.setCycle(""+days+"天"+hours+"小时"+minutes+"分");
                    }catch (Exception e)
                    {
                    }
                }


                //客户评价
                if (objectionLedger.getHandlerUser()!=null){
                    Integer a = objectionLedger.getHandlerResults().intValue()+objectionLedger.getHandlerUser().intValue()+objectionLedger.getHandlerTime().intValue();
                    Integer integer = a/3;
                    if (integer>4){
                        objectionLedgerVO1.setEvaluate("很满意");
                    }else if (integer>3){
                        objectionLedgerVO1.setEvaluate("满意");
                    }else if (integer>2){
                        objectionLedgerVO1.setEvaluate("一般");
                    }else if (integer>1){
                        objectionLedgerVO1.setEvaluate("不满意");
                    }else if (integer>0){
                        objectionLedgerVO1.setEvaluate("很不满意");
                    }
                }

                //质量异议台账最下面一行需要对数量和金额、重量进行汇总统计。按照查询条件进行汇总统计。
                if (objectionLedgerVO1.getObjectionNum()!=null){
                    bigDecimal1=bigDecimal1.add(objectionLedgerVO1.getObjectionNum());
                }
                if (objectionLedgerVO1.getObjectionConfirmation()!=null){
                    bigDecimal2=bigDecimal2.add(objectionLedgerVO1.getObjectionConfirmation());
                }
                if (objectionLedgerVO1.getAgreementAmount()!=null){
                    bigDecimal3=bigDecimal3.add(objectionLedgerVO1.getAgreementAmount());
                }

            }
            for (ObjectionLedgerVO objectionLedgerVO1:transform.getResultData()) {
                objectionLedgerVO1.setObjectionNumCount(bigDecimal1);
                objectionLedgerVO1.setObjectionConfirmationCount(bigDecimal2);
                objectionLedgerVO1.setAgreementAmountCount(bigDecimal3);
            }



            return transform;
        }finally {
            PageDTOUtil.endPage();
        }
    }



    //导出
    @Override
    public List<ObjectionLedgerVO> export(ObjectionLedgerVO objectionLedgerVO) {
        //转换mdel
        ObjectionLedger objectionLedger  = new ObjectionLedger();
        BeanCopyUtil.copy(objectionLedgerVO,objectionLedger);
        if(objectionLedger.getDeptCode()!=null&& objectionLedger.getDeptCode()!=""){
            //objectionLedger.setDeptCodes(null);
        }
        // 如果orgType为1为销售公司设置customerid 为质证书的zkunner
        if(objectionLedgerVO.getOrgType().equals("1")){
            CrmClaimApply h = new CrmClaimApply();
            h.setCustomerName(objectionLedger.getOrgName());
            List<CrmClaimApply> list =crmClaimApplyMapper.findMillSheetByCus(h);
            if(list.size()>0){
                List<String> idall = new ArrayList<>();
                for (int i = 0; i < list.size(); i++){
                    idall.add(list.get(i).getMillSheetNo());
                }
                objectionLedger.setMillSheetNos(idall);
            }else {
                objectionLedger.setCustomerId(objectionLedger.getOrgName());
            }
        }
        PageDTOUtil.startPage(objectionLedgerVO);
        String startDtStr = DateFormatUtil.getStartDateStr(objectionLedger.getStartDt());
        objectionLedger.setStartDtStr(startDtStr);
        String endDtStr = DateFormatUtil.getEndDateStr(objectionLedger.getEndDt());
        objectionLedger.setEndDtStr(endDtStr);
        List<ObjectionLedger> ledgerList = crmClaimInfoMapper.findLedgerByPage(objectionLedger);
        //转换返回对象
        List<ObjectionLedgerVO> objectionLedgerVOS = BeanCopyUtil.copyList(ledgerList, ObjectionLedgerVO.class);

        //判断过期原因是否为空然后设置是否可以上传协议书
        for (ObjectionLedgerVO objectionLedgerVO1:objectionLedgerVOS) {
            objectionLedger = new ObjectionLedger();
            BeanCopyUtil.copy(objectionLedgerVO1, objectionLedger);
           /* if (objectionLedger.getDeptCode().equals("1000")) {
                objectionLedgerVO1.setDeptCode("不锈");
            } else if (objectionLedger.getDeptCode().equals("2000")) {
                objectionLedgerVO1.setDeptCode("炼轧");
            } else if (objectionLedger.getDeptCode().equals("2200")) {
                objectionLedgerVO1.setDeptCode("碳钢");
            } else if (objectionLedger.getDeptCode().equals("3000")) {
                objectionLedgerVO1.setDeptCode("榆钢");
            }*/

           //生产日期 productDt
            Date date= objectionLedger.getProductDt();
            String pd = DateUtil.formatDate(date, "yyyy-MM-dd");
            objectionLedgerVO1.setPd(pd);
            //受理日期
            Date date1= objectionLedger.getAdmissibilityTime();
            String at = DateUtil.formatDate(date1, "yyyy-MM-dd");
            objectionLedgerVO1.setAt(at);
            //到达时间
            Date date2= objectionLedger.getArrivalTime();
            String at1 = DateUtil.formatDate(date2, "yyyy-MM-dd");
            objectionLedgerVO1.setAt1(at1);
            //结案日期
            Date date3= objectionLedger.getClosingTime();
            String ct = DateUtil.formatDate(date3, "yyyy-MM-dd");
            objectionLedgerVO1.setCt(ct);

            //状态
            if (objectionLedger.getClaimState().equals("NEW")){
                objectionLedgerVO1.setClaimState("新建");
            }else if (objectionLedger.getClaimState().equals("PRESENT")){
                objectionLedgerVO1.setClaimState("已提报");
            }else if (objectionLedger.getClaimState().equals("ACCEPTANCE")){
                objectionLedgerVO1.setClaimState("已受理");
            }else if (objectionLedger.getClaimState().equals("REJECT")){
                objectionLedgerVO1.setClaimState("已驳回");
            }else if (objectionLedger.getClaimState().equals("INVESTIGATION")){
                objectionLedgerVO1.setClaimState("调查中");
            }else if (objectionLedger.getClaimState().equals("HANDLE")){
                objectionLedgerVO1.setClaimState("处理中");
            }else if (objectionLedger.getClaimState().equals("END")){
                objectionLedgerVO1.setClaimState("已结案");
            }else if (objectionLedger.getClaimState().equals("EVALUATE")){
                objectionLedgerVO1.setClaimState("已评价");
            }else if (objectionLedger.getClaimState().equals("ADOPT")){
                objectionLedgerVO1.setClaimState("销售审核通过");
            }
            //处理周期（结案时间减受理时间   7天 （没跟踪过） 20天（跟踪过））
            if(objectionLedger.getClosingTime()!=null&&objectionLedger.getAdmissibilityTime()!=null){
                //Integer integer = DateUtil.countDays(objectionLedger.getAdmissibilityTime(),objectionLedger.getClosingTime());
                Date dd= objectionLedger.getAdmissibilityTime();
                String ss = DateUtil.formatDate(dd, "yyyy-MM-dd HH:mm:ss");
                Date hh= objectionLedger.getClosingTime();
                String gg = DateUtil.formatDate(hh, "yyyy-MM-dd HH:mm:ss");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    Date d1 = df.parse(gg);
                    Date d2 = df.parse(ss);
                    long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                    long days = diff / (1000 * 60 * 60 * 24);

                    long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                    long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                    objectionLedgerVO1.setCycle(""+days+"天"+hours+"小时"+minutes+"分");
                }catch (Exception e)
                {
                }
            }
            //异议类别
            if(objectionLedger.getClaimType()!= null){
                if (objectionLedger.getClaimType().equals("1")){
                    objectionLedger.setClaimType("表面外观");
                }else if(objectionLedger.getClaimType().equals("2")){
                    objectionLedger.setClaimType("理化性能");
                }else if(objectionLedger.getClaimType().equals("3")){
                    objectionLedger.setClaimType("加工使用");
                }else if(objectionLedger.getClaimType().equals("4")){
                    objectionLedger.setClaimType("尺寸公差");
                }else if(objectionLedger.getClaimType().equals("5")){
                    objectionLedger.setClaimType("实物不符");
                }else if(objectionLedger.getClaimType().equals("6")){
                    objectionLedger.setClaimType("计量");
                }else if(objectionLedger.getClaimType().equals("7")){
                    objectionLedger.setClaimType("其他");
                }
            }
            //客户评价
            if (objectionLedger.getHandlerUser()!=null){
                Integer a = objectionLedger.getHandlerResults().intValue()+objectionLedger.getHandlerUser().intValue()+objectionLedger.getHandlerTime().intValue();
                Integer integer = a/3;
                if (integer>4){
                    objectionLedgerVO1.setEvaluate("很满意");
                }else if (integer>3){
                    objectionLedgerVO1.setEvaluate("满意");
                }else if (integer>2){
                    objectionLedgerVO1.setEvaluate("一般");
                }else if (integer>1){
                    objectionLedgerVO1.setEvaluate("不满意");
                }else if (integer>0){
                    objectionLedgerVO1.setEvaluate("很不满意");
                }
            }
        }
        return objectionLedgerVOS;
    }
}
