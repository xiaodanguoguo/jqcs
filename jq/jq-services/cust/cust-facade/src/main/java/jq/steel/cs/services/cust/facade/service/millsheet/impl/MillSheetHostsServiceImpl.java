package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import com.ebase.utils.math.MathHelper;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.dao.*;
import jq.steel.cs.services.cust.facade.model.*;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetHostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MillSheetHostsServiceImpl implements MillSheetHostsService{

    @Autowired
    private  MillSheetHostsMapper millSheetHostsMapper;
    @Autowired
    private CrmMillSheetSplitApplyMapper crmMillSheetSplitApplyMapper;
    @Autowired
    private MillCoilInfoMapper millCoilInfoMapper;
    @Autowired
    private CrmMillSheetRebackApplyMapper crmMillSheetRebackApplyMapper;
    @Autowired
    private MillOperationHisMapper millOperationHisMapper;
    @Autowired
    private MillSheetHeadMapper millSheetHeadMapper;
    @Autowired
    private OrgInfoMapper orgInfoMapper;
    @Autowired
    private AcctInfoMapper acctInfoMapper;
    @Autowired
    private CrmMillSheetSplitInfoMapper crmMillSheetSplitInfoMapper;

    //分页查询（质证书已经拆分给其他用户并且该用户在客服平台有账号的，则本级不能再对该质证书进行打印、下载操作。如拆分出来的质证书接受单位在平台中没有对应的账号，本级还可以对该质证书进行下载和打印操作）
    @Override
    public PageDTO<MillSheetHostsVO> findMillSheetByPage(MillSheetHostsVO millSheetHostsVO) {
        String orgName = millSheetHostsVO.getOrgName();
        try {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        if(millSheetHosts.getZcharg()!=null && millSheetHosts.getZcharg()!=""){
            MillCoilInfo coilInfo = new MillCoilInfo();
            coilInfo.setZcharg(millSheetHosts.getZcharg());
            List<MillCoilInfo> list = millCoilInfoMapper.findMillsheetNumber(coilInfo);
            if(list.size()>0){
                List<String> idall = new ArrayList<>();
                for (int i = 0; i < list.size(); i++){
                    idall.add(list.get(i).getMillsheetNo());
                }
                millSheetHosts.setMillSheetNos(idall);
            }else {
                List<String> idall = new ArrayList<>();
                idall.add("-99");
                millSheetHosts.setMillSheetNos(idall);
            }

        }
        if(millSheetHosts.getDeptCode()!=null&& millSheetHosts.getDeptCode()!=""){
            millSheetHosts.setDeptCodes(null);
        }
        //质证书数据匹配显示的时候，加一层对虚拟质证书的判断，即车号是以“—”开头的，就在质证书管理和质证书管理（酒钢）界面不显示。
        MillSheetHead millSheetHead = new MillSheetHead();
        List<MillSheetHead> millSheetHeads =   millSheetHeadMapper.selectAll(millSheetHead);
        List<String> idall = new ArrayList<>();
        if (millSheetHeads.size()>0){
            for(MillSheetHead millSheetHead1:millSheetHeads){
                if(millSheetHead1.getZchehao().startsWith("-")){
                    idall.add(millSheetHead1.getMillSheetNo());
                }
            }
            if (idall.size()>0){
                millSheetHosts.setNoMillSheetNos(idall);
            }
        }
        PageDTOUtil.startPage(millSheetHostsVO);
        String startDtStr = DateFormatUtil.getStartDateStr(millSheetHosts.getStartDt());
        millSheetHosts.setStartDtStr(startDtStr);
        String endDtStr = DateFormatUtil.getEndDateStr(millSheetHosts.getEndDt());
        millSheetHosts.setEndDtStr(endDtStr);
        List<MillSheetHosts> millSheetByPage = millSheetHostsMapper.findMillSheetByPage(millSheetHosts);
        // 分页对象
        PageDTO<MillSheetHostsVO> transform = PageDTOUtil.transform(millSheetByPage, MillSheetHostsVO.class);
            for (MillSheetHostsVO millSheetHosts2:transform.getResultData()){
                CrmMillSheetSplitApply crmMillSheetSplitApply  = new CrmMillSheetSplitApply();
                crmMillSheetSplitApply.setMillsheetNo(millSheetHosts2.getMillSheetNo());
                crmMillSheetSplitApply.setStatus("1");
                List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.findFmillSheet(crmMillSheetSplitApply);
                if (crmMillSheetSplitApplies.size()>0){
                    millSheetHosts2.setIsSplit(1);
                    String lowerMillSheetNos ="";
                    for (int i = 0; i < crmMillSheetSplitApplies.size(); i++) {
                        //已拆分的需要返回给前台哪些下级质证书需要撤销
                        String shuxing =crmMillSheetSplitApplies.get(i).getFatherMillsheetNo();
                        lowerMillSheetNos+=","+shuxing;
                    }
                    millSheetHosts2.setLowerMillSheetNos(lowerMillSheetNos);
                }else {
                    millSheetHosts2.setIsSplit(0);
                }
                //原件次 剩余件次  原重量 剩余重量
                Long zjishu=new Long(0);;
                BigDecimal surplusZjishu= new BigDecimal(0);
                BigDecimal zlosmenge= new BigDecimal(0);
                BigDecimal surplusZlosmenge= new BigDecimal(0);
                MillCoilInfo millCoilInfo = new MillCoilInfo();
                millCoilInfo.setMillSheetNo(millSheetHosts2.getMillSheetNo());
                List<MillCoilInfo> millCoilInfos = millCoilInfoMapper.findCoil(millCoilInfo);
                for (MillCoilInfo millCoilInfo1 :millCoilInfos){
                    zjishu=zjishu+millCoilInfo1.getZjishu();
                    if(millCoilInfo1.getSurplusZjishu()!=null){
                        surplusZjishu=surplusZjishu.add(millCoilInfo1.getSurplusZjishu());
                    }
                    zlosmenge=zlosmenge.add(millCoilInfo1.getZlosmenge());
                    if(millCoilInfo1.getSurplusZlosmenge()!=null){
                        surplusZlosmenge=surplusZlosmenge.add(millCoilInfo1.getSurplusZlosmenge());
                    }

                }
                millSheetHosts2.setZjishu(zjishu);
                millSheetHosts2.setSurplusZjishu(surplusZjishu);
                millSheetHosts2.setZlosmenge(zlosmenge);
                millSheetHosts2.setSurplusZlosmenge(surplusZlosmenge);
                if(millSheetHosts2.getJcFlag()!=null){
                    //判断是否允许下载(建材类不让下载)
                    if(millSheetHosts2.getJcFlag()==0){
                        millSheetHosts2.setIsAllow("N");
                    }else {
                        if (millSheetHosts2.getMillSheetType().equals("Z")||millSheetHosts2.getMillSheetType().equals("S")){
                            if (millSheetHosts2.getSpiltCustomer().equals(orgName)){
                                millSheetHosts2.setIsAllow("Y");
                            }else {
                                //查询拆分单位下是否有账号有的话不让下载 没有的话让下载打印
                                OrgInfo orgInfo = new OrgInfo();
                                orgInfo.setOrgName(millSheetHosts2.getSpiltCustomer());
                                List<OrgInfo> list =orgInfoMapper.findIdByOrgName(orgInfo);
                                if(list.size()>0){
                                    AcctInfo acctInfo = new AcctInfo();
                                    acctInfo.setoInfoId(list.get(0).getId());
                                    List<AcctInfo> acctInfos =acctInfoMapper.findNameByorgId(acctInfo);
                                    if (acctInfos.size()>0){
                                        millSheetHosts2.setIsAllow("N");
                                    }else {
                                        millSheetHosts2.setIsAllow("Y");
                                    }
                                }else{
                                    millSheetHosts2.setIsAllow("N");
                                }
                            }
                        }else {
                            millSheetHosts2.setIsAllow("Y");
                        }

                    }
                }

            }
        return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    //分页查询（酒钢）
    @Override
    public PageDTO<MillSheetHostsVO> findMillSheetByPage1(MillSheetHostsVO millSheetHostsVO) {
        String orgName = millSheetHostsVO.getOrgName();
        try {
            //转换mdel
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
            if(millSheetHosts.getZcharg()!=null && millSheetHosts.getZcharg()!=""){
                MillCoilInfo coilInfo = new MillCoilInfo();
                coilInfo.setZcharg(millSheetHosts.getZcharg());
                List<MillCoilInfo> list = millCoilInfoMapper.findMillsheetNumber(coilInfo);
                if(list.size()>0){
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++){
                        idall.add(list.get(i).getMillsheetNo());
                    }
                    millSheetHosts.setMillSheetNos(idall);
                }else {
                    List<String> idall = new ArrayList<>();
                    idall.add("-99");
                    millSheetHosts.setMillSheetNos(idall);
                }
            }
            if(millSheetHosts.getDeptCode()!=null&& millSheetHosts.getDeptCode()!=""){
                millSheetHosts.setDeptCodes(null);
            }
            //质证书数据匹配显示的时候，加一层对虚拟质证书的判断，即车号是以“—”开头的，就在质证书管理和质证书管理（酒钢）界面不显示。
            MillSheetHead millSheetHead = new MillSheetHead();
            List<MillSheetHead> millSheetHeads =   millSheetHeadMapper.selectAll(millSheetHead);
            List<String> idall = new ArrayList<>();
            if (millSheetHeads.size()>0){
                for(MillSheetHead millSheetHead1:millSheetHeads){
                    if(millSheetHead1.getZchehao().startsWith("-")){
                        idall.add(millSheetHead1.getMillSheetNo());
                    }
                }
                if (idall.size()>0){
                    millSheetHosts.setNoMillSheetNos(idall);
                }
            }
            PageDTOUtil.startPage(millSheetHostsVO);
            String startDtStr = DateFormatUtil.getStartDateStr(millSheetHosts.getStartDt());
            millSheetHosts.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(millSheetHosts.getEndDt());
            millSheetHosts.setEndDtStr(endDtStr);
            List<MillSheetHosts> millSheetByPage = millSheetHostsMapper.findMillSheetByPage1(millSheetHosts);
            // 分页对象
            PageDTO<MillSheetHostsVO> transform = PageDTOUtil.transform(millSheetByPage, MillSheetHostsVO.class);
            for (MillSheetHostsVO millSheetHosts2:transform.getResultData()){
                CrmMillSheetSplitApply crmMillSheetSplitApply  = new CrmMillSheetSplitApply();
                crmMillSheetSplitApply.setMillsheetNo(millSheetHosts2.getMillSheetNo());
                crmMillSheetSplitApply.setStatus("1");
                List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.findFmillSheet(crmMillSheetSplitApply);
                if (crmMillSheetSplitApplies.size()>0){
                    millSheetHosts2.setIsSplit(1);
                    String lowerMillSheetNos ="";
                    for (int i = 0; i < crmMillSheetSplitApplies.size(); i++) {
                        //已拆分的需要返回给前台哪些下级质证书需要撤销
                        if(crmMillSheetSplitApplies.get(i).getMillsheetNo()!=null){
                            String shuxing =crmMillSheetSplitApplies.get(i).getMillsheetNo();
                            lowerMillSheetNos+=","+shuxing;
                        }
                    }
                    millSheetHosts2.setLowerMillSheetNos(lowerMillSheetNos.substring(1));
                }else {
                    millSheetHosts2.setIsSplit(0);
                }
                //原件次 剩余件次  原重量 剩余重量
                Long zjishu=new Long(0);;
                BigDecimal surplusZjishu= new BigDecimal(0);
                BigDecimal zlosmenge= new BigDecimal(0);
                BigDecimal surplusZlosmenge= new BigDecimal(0);
                MillCoilInfo millCoilInfo = new MillCoilInfo();
                millCoilInfo.setMillSheetNo(millSheetHosts2.getMillSheetNo());
                List<MillCoilInfo> millCoilInfos = millCoilInfoMapper.findCoil(millCoilInfo);
                for (MillCoilInfo millCoilInfo1 :millCoilInfos){
                    zjishu=zjishu+millCoilInfo1.getZjishu();
                    if(millCoilInfo1.getSurplusZjishu()!=null){
                        surplusZjishu=surplusZjishu.add(millCoilInfo1.getSurplusZjishu());
                    }
                    zlosmenge=zlosmenge.add(millCoilInfo1.getZlosmenge());
                    if(millCoilInfo1.getSurplusZlosmenge()!=null){
                        surplusZlosmenge=surplusZlosmenge.add(millCoilInfo1.getSurplusZlosmenge());
                    }

                }
                millSheetHosts2.setZjishu(zjishu);
                millSheetHosts2.setSurplusZjishu(surplusZjishu);
                millSheetHosts2.setZlosmenge(zlosmenge);
                millSheetHosts2.setSurplusZlosmenge(surplusZlosmenge);

                if(millSheetHosts2.getJcFlag()!=null){
                    //判断是否允许下载(建材类不让下载)
                    if(millSheetHosts2.getJcFlag()==0){
                        millSheetHosts2.setIsAllow("N");
                    }else {
                        if (millSheetHosts2.getMillSheetType().equals("Z")||millSheetHosts2.getMillSheetType().equals("S")){
                            if (millSheetHosts2.getSpiltCustomer().equals(orgName)){
                                millSheetHosts2.setIsAllow("Y");
                            }else {
                                //查询拆分单位下是否有账号有的话不让下载 没有的话让下载打印
                                OrgInfo orgInfo = new OrgInfo();
                                orgInfo.setOrgName(millSheetHosts2.getSpiltCustomer());
                                List<OrgInfo> list =orgInfoMapper.findIdByOrgName(orgInfo);
                                if(list.size()>0){
                                    AcctInfo acctInfo = new AcctInfo();
                                    acctInfo.setoInfoId(list.get(0).getId());
                                    List<AcctInfo> acctInfos =acctInfoMapper.findNameByorgId(acctInfo);
                                    if (acctInfos.size()>0){
                                        millSheetHosts2.setIsAllow("N");
                                    }else {
                                        millSheetHosts2.setIsAllow("Y");
                                    }
                                }else{
                                    millSheetHosts2.setIsAllow("N");
                                }
                            }
                        }else {
                            millSheetHosts2.setIsAllow("Y");
                        }

                    }
                }
            }
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public List<MillSheetHostsVO> findUrl(List<MillSheetHostsVO> millSheetHostsVOS,HttpServletRequest request) {
        String ip=request.getRemoteAddr();
        String acctName = millSheetHostsVOS.get(0).getAcctName();
        System.out.println("*******************************"+acctName+"************************");
        for(MillSheetHostsVO millSheetHostsVO:millSheetHostsVOS){
            //转换mdel
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
            MillSheetHosts millSheetByPage = millSheetHostsMapper.findUrl(millSheetHosts);
            millSheetByPage.setMillSheetPath(millSheetByPage.getMillSheetUrl() +"/"+millSheetByPage.getMillSheetName());
            BeanCopyUtil.copy(millSheetByPage,millSheetHostsVO);
            //添加日志操作记录
            MillOperationHis millOperationHis = new MillOperationHis();
            millOperationHis.setMillSheetNo(millSheetHostsVO.getMillSheetNo());
            millOperationHis.setOperator(acctName);
            if(millSheetHosts.getOperationType().equals(1)){
                //1是预览  2是打印
                millOperationHis.setOperationType("PRIVIEWED");
                millOperationHis.setOperationIp(ip);
                //打印完的预览不改状态  下载完的预览不改变状态
                if (millSheetByPage.getState().equals("PRINTED")|| millSheetByPage.getState().equals("DOWNLOADED") ||millSheetByPage.getState().equals("SPLITED")){
                    millSheetHosts.setState("");
                }else {
                    millSheetHosts.setState("PRIVIEWED");
                }
            }else  if(millSheetHosts.getOperationType().equals(2)) {
                //减少打印次数
                millOperationHis.setOperationType("PRINTED");
                millOperationHis.setOperationIp(ip);
                millSheetHosts.setState("PRINTED");
                millSheetHosts.setPrintableNum(millSheetByPage.getPrintableNum()-1);
                millSheetHosts.setPrintedNum(millSheetByPage.getPrintedNum()+1);
            }else{
                //3下载只进行记录日志，次数下载在findDownUrl方法内
                millOperationHis.setOperationType("DOWNLOADED");
                millOperationHis.setOperationIp(ip);
            }
            millOperationHis.setOperationTime(new Date());
            millOperationHisMapper.insertSelective(millOperationHis);
            millSheetHostsMapper.updateNum(millSheetHosts);
        }
        return millSheetHostsVOS;
    }

    //打印返回文件流
    @Override
    public List<MillSheetHostsVO> findUrl1(List<String> list,HttpServletRequest request) {
        List<MillSheetHosts> millSheetHosts = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            MillSheetHosts millSheetHosts1 = new MillSheetHosts();
            millSheetHosts1.setMillSheetNo(list.get(i));
            millSheetHosts.add(millSheetHosts1);
        }
        String ip=request.getRemoteAddr();
        for(MillSheetHosts millSheetHosts2:millSheetHosts){
            //转换mdel
            MillSheetHosts millSheetHosts1 = new MillSheetHosts();
            BeanCopyUtil.copy(millSheetHosts2,millSheetHosts1);
            MillSheetHosts millSheetByPage = millSheetHostsMapper.findUrl(millSheetHosts1);
            millSheetByPage.setMillSheetPath(millSheetByPage.getMillSheetUrl() +"/"+millSheetByPage.getMillSheetName());
            BeanCopyUtil.copy(millSheetByPage,millSheetHosts2);
            //添加日志操作记录
            MillOperationHis millOperationHis = new MillOperationHis();
            millOperationHis.setMillSheetNo(millSheetHosts2.getMillSheetNo());
            millOperationHis.setOperationTime(new Date());
           // millOperationHisMapper.insertSelective(millOperationHis);
        }
        //转换返回对象
        List<MillSheetHostsVO> millSheetHostsVOS = BeanCopyUtil.copyList(millSheetHosts, MillSheetHostsVO.class);
        return millSheetHostsVOS;
    }

    @Override
    public List<MillSheetHostsVO> findDownUrl(List<String> list) {
        List<MillSheetHosts> millSheetHosts = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            MillSheetHosts millSheetHosts1 = new MillSheetHosts();
            millSheetHosts1.setMillSheetNo(list.get(i));
            millSheetHosts.add(millSheetHosts1);
        }
        for (MillSheetHosts millSheetHosts1:millSheetHosts){
            MillSheetHosts url = millSheetHostsMapper.findUrl(millSheetHosts1);
            if( url.getMillSheetUrl()!=null&&url.getMillSheetName()!=null){
                //质证书有文件
                millSheetHosts1.setSpecialNeed(url.getSpecialNeed());
                millSheetHosts1.setMillSheetPath(url.getMillSheetUrl()+"/"+url.getMillSheetName());
                millSheetHosts1.setMillSheetUrl(url.getMillSheetUrl());
                millSheetHosts1.setMillSheetName(url.getMillSheetName());
                //修改下载次数 + 状态
                millSheetHosts1.setDownableNum(url.getDownableNum()-1);
                millSheetHosts1.setDownNum(url.getDownNum()+1);
                //millSheetHosts1.setUpdatedBy(orgName);
                millSheetHosts1.setUpdatedDt(new Date());
                //打印完的下载不改状态
                if (url.getState().equals("PRINTED")){
                    millSheetHosts1.setState("");
                }else {
                    millSheetHosts1.setState("DOWNLOADED");
                }
                millSheetHostsMapper.updateNum(millSheetHosts1);
                //日志表
              /*  MillOperationHis millOperationHis = new MillOperationHis();
                millOperationHis.setMillSheetNo(millSheetHosts1.getMillSheetNo());
                millOperationHis.setOperationType("DOWNLOADED");
                millOperationHis.setOperationTime(new Date());
                millOperationHisMapper.insertSelective(millOperationHis);*/
            }

        }
        //转换返回对象
        List<MillSheetHostsVO> millSheetHostsVOS = BeanCopyUtil.copyList(millSheetHosts, MillSheetHostsVO.class);
        return millSheetHostsVOS;
    }


    @Override
    public MillSheetHostsVO rollbackQuery(MillSheetHostsVO millSheetHostsVO) {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        List<MillSheetHosts> list = millSheetHostsMapper.findMillsheetNo(millSheetHosts);
        if (list.size()>0){
            millSheetHostsVO.setTrue(true);
            //校验是否回退过
            CrmMillSheetRebackApply millSheetRebackApply = new CrmMillSheetRebackApply();
            millSheetRebackApply.setMillSheetNo(millSheetHostsVO.getMillSheetNo());
            List<CrmMillSheetRebackApply> crmMillSheetRebackApplies = crmMillSheetRebackApplyMapper.find(millSheetRebackApply);
            if (crmMillSheetRebackApplies.size()>0){
                millSheetHostsVO.setIsReback("Y");
                millSheetHostsVO.setMillSheetUrl(list.get(0).getMillSheetUrl());
                millSheetHostsVO.setMillSheetName(list.get(0).getMillSheetName());
                millSheetHostsVO.setMillSheetPath(list.get(0).getMillSheetUrl()+"/"+list.get(0).getMillSheetName());
            }else {
                millSheetHostsVO.setIsReback("N");
                millSheetHostsVO.setMillSheetUrl(list.get(0).getMillSheetUrl());
                millSheetHostsVO.setMillSheetName(list.get(0).getMillSheetName());
                millSheetHostsVO.setMillSheetPath(list.get(0).getMillSheetUrl()+"/"+list.get(0).getMillSheetName());
            }
        }else {
            //校验质证书编号
            millSheetHostsVO.setTrue(false);
        }
        return millSheetHostsVO;
    }

    //查询质证书编号是否有效
    @Override
    public MillSheetHostsVO findIsTrue(MillSheetHostsVO millSheetHostsVO) {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        List<MillSheetHosts> list =millSheetHostsMapper.findIsTrue(millSheetHosts);
        if (list.size()>0){
            millSheetHosts.setTrue(true);
            list.get(0).setTrue(true);
            BeanCopyUtil.copy(list.get(0),millSheetHostsVO);
        }else {
            millSheetHosts.setTrue(false);
            millSheetHosts.setCheckInstructions("请核实质证书编号"+millSheetHostsVO.getMillSheetNo());
            BeanCopyUtil.copy(millSheetHosts,millSheetHostsVO);
        }
        return millSheetHostsVO;
    }


    //查询条件校验钢卷编号是否正确
    @Override
    public MillSheetHostsVO checkCoil(MillSheetHostsVO millSheetHostsVO) {
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
        List<MillSheetHosts> list =millSheetHostsMapper.checkCoil(millSheetHosts);
        if (list.size()>0){
            List<MillSheetHosts> alist =millSheetHostsMapper.checkCoil1(millSheetHosts);
            if(alist.size()>0){
                millSheetHosts.setTrue(true);
                list.get(0).setTrue(true);
                BeanCopyUtil.copy(list.get(0),millSheetHostsVO);
            }else {
                millSheetHosts.setTrue(false);
                millSheetHosts.setCheckInstructions("此批/板/卷号"+millSheetHostsVO.getZcharg()+"所在的质证书的状态不符合查询条件");
                BeanCopyUtil.copy(millSheetHosts,millSheetHostsVO);
            }
        }else {
            millSheetHosts.setTrue(false);
            millSheetHosts.setCheckInstructions("此批/板/卷号"+millSheetHostsVO.getZcharg()+"不存在");
            BeanCopyUtil.copy(millSheetHosts,millSheetHostsVO);
        }
        return millSheetHostsVO;
    }



    //返回app端质证书下载路径
    public MillSheetHostsVO getUrlForApp(JsonRequest<MillSheetHostsVO> jsonRequest) {
        String millSheetNo = jsonRequest.getReqBody().getMillSheetNo();
       MillSheetHosts millSheetHosts = millSheetHostsMapper.getUrlForApp(millSheetNo);
        MillSheetHostsVO vo = new MillSheetHostsVO();
        BeanCopyUtil.copy(millSheetHosts,vo);
        return vo;
    }

    //修改打印次数下载次数
    @Override
    public Integer updateNumber(List<MillSheetHostsVO> record,HttpServletRequest request) {
        String ip=request.getRemoteAddr();
        String acctName =record.get(0).getAcctName();
        for(MillSheetHostsVO millSheetHostsVO:record){
            //转换mdel
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
            MillSheetHosts millSheetByPage = millSheetHostsMapper.findUrl(millSheetHosts);
            //日志表
            MillOperationHis millOperationHis = new MillOperationHis();
            millOperationHis.setMillSheetNo(millSheetHostsVO.getMillSheetNo());
            millOperationHis.setOperationTime(new Date());
            millOperationHis.setOperationIp(ip);
            millOperationHis.setOperator(acctName);
            if(millSheetHosts.getOperationType().equals(1)){
                //1是打印次数修改  2是下载次数修改
                millSheetHosts.setPrintableNum(millSheetByPage.getPrintableNum()+1);

                millOperationHis.setOperationType("PRINTING");
                millOperationHis.setOperationTime(new Date());
                millOperationHis.setContent("打印次数加1");
            }else {
                millSheetHosts.setDownableNum(millSheetByPage.getDownableNum()+1);
                millOperationHis.setOperationType("DOWNLOADS");
                millOperationHis.setContent("下载次数加1");
            }
            millOperationHisMapper.insertSelective(millOperationHis);
            millSheetHostsMapper.updateNum(millSheetHosts);
        }
        return 1;
    }

    @Override
    @Transactional
    public void updateStateAndPrintNum(String millSheetNo) {

       MillSheetHosts mshs = millSheetHostsMapper.selectByMillSheetNo(millSheetNo);
       if(mshs.getDownableNum() == 0){
            throw new BusinessException("质证书下载次数已经为0,不能再次下载");
       }
         millSheetHostsMapper.updateStateAndPrintNum(millSheetNo);
    }

    //拆分撤销
    /*质证书拆分界面，拆分出来的子/孙级别质证书需要有撤销的功能。
建议做法如下：
1、在质证书管理的两个界面新增“撤销”按钮，当质证书类别为Z和S时，且状态不为“已拆分”才出现。
2、当操作撤销子/孙证书的时候，需要判断子/孙质证书的状态，如果为“已审核”则直接撤销。如果状态为“已预览、已下载和已打印”状态则需要和用户线下沟通后，录入撤销原因，然后才能够撤销。如果为“已拆分”则不能操作撤销，并提示“已拆分状态的质证书不能够撤销，如要撤销，请先将下级质证书撤销后再操作。”
3、撤销操作成功后，需要判断质证书的类别，是Z还是S,本级质证书有几个，需要先从底层开始逐级撤销，撤销后，该Z/S级质证书的量需要返回到M/Z级质证书上。注意如果是本级最后一张质证书发生了撤销，则需要对应的M级质证书的状态修改为“已预览”状态。*/
    @Override
    public Integer revoke(MillSheetHostsVO record,HttpServletRequest request) {
        String ip=request.getRemoteAddr();
        String acctName =record.getAcctName();
        //查询CRM_MILL_SHEET_SPLIT_APPLY表遍历集合然后查询CRM_MILL_SHEET_SPLIT_INFO遍历修改父亲coilinfo表信息
        // 删除host表 head表coilinfo表有关下级质证书的信息  拆分数据状态修改为0  并记录日志（撤销多少件）    最后修改此质证书状态为已预览

            CrmMillSheetSplitApply crmMillSheetSplitApply = new CrmMillSheetSplitApply();
            crmMillSheetSplitApply.setMillsheetNo(record.getMillSheetNo());
            crmMillSheetSplitApply.setStatus("1");
            List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.findInfo(crmMillSheetSplitApply);
            String content = "";
            for (CrmMillSheetSplitApply crmMillSheetSplitApply1:crmMillSheetSplitApplies){
                CrmMillSheetSplitInfo crmMillSheetSplitInfo = new CrmMillSheetSplitInfo();
                crmMillSheetSplitInfo.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
                List<CrmMillSheetSplitInfo> crmMillSheetSplitInfos =crmMillSheetSplitInfoMapper.findByParams(crmMillSheetSplitInfo);

                for(CrmMillSheetSplitInfo crmMillSheetSplitInfo1:crmMillSheetSplitInfos){
                    MillCoilInfo millCoilInfo = new MillCoilInfo();
                    //批次  规格  质证书
                    millCoilInfo.setMillSheetNo(crmMillSheetSplitInfo1.getFatherMillsheetNo());
                    millCoilInfo.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                    millCoilInfo.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                    MillCoilInfo millCoilInfo1 = millCoilInfoMapper.findDate(millCoilInfo);
                    millCoilInfo.setUpdatedBy(acctName);
                    millCoilInfo.setUpdatedDt(new Date());
                    //剩余件数+拆分件数
                    millCoilInfo.setZjishu(millCoilInfo1.getZjishu()+crmMillSheetSplitInfo1.getZjishu());
                    BigDecimal bigDecimal = MathHelper.add(millCoilInfo1.getZlosmenge(),crmMillSheetSplitInfo1.getZlosmenge());
                    millCoilInfo.setZlosmenge(bigDecimal);
                    millCoilInfoMapper.updateDate(millCoilInfo);
                    //修改拆分数据
                    crmMillSheetSplitInfo1.setUpdatedBy(acctName);
                    crmMillSheetSplitInfo1.setUpdatedDt(new Date());
                    crmMillSheetSplitInfo1.setStatus("0");
                    crmMillSheetSplitInfoMapper.updateStatus(crmMillSheetSplitInfo1);
                    content+=","+"批/板/卷号"+crmMillSheetSplitInfo1.getZcharg()+"撤销拆分"+crmMillSheetSplitInfo1.getZjishu()+"件";
                }
                //修改拆分数据
                crmMillSheetSplitApply1.setStatus("0");
                crmMillSheetSplitApply1.setUpdatedBy(acctName);
                crmMillSheetSplitApply1.setUpdatedDt(new Date());
                crmMillSheetSplitApplyMapper.updateStatus(crmMillSheetSplitApply1);
                //删除数据
                MillCoilInfo coilInfo = new MillCoilInfo();
                coilInfo.setMillSheetNo(crmMillSheetSplitApply1.getMillsheetNo());
                millCoilInfoMapper.deleteMillSheetNo(coilInfo);
                MillSheetHosts millSheetHosts =new MillSheetHosts();
                millSheetHosts.setMillSheetNo(crmMillSheetSplitApply1.getMillsheetNo());
                millSheetHostsMapper.deleteMillSheetNo(millSheetHosts);
                MillSheetHead millSheetHead = new MillSheetHead();
                millSheetHead.setMillSheetNo(crmMillSheetSplitApply1.getMillsheetNo());
                millSheetHeadMapper.deleteMillSheetNo(millSheetHead);

                //判断是否
                CrmMillSheetSplitInfo gg = new CrmMillSheetSplitInfo();
                gg.setStatus("1");
                gg.setFatherMillsheetNo(crmMillSheetSplitApply1.getFatherMillsheetNo());
                gg.setMillsheetNo(crmMillSheetSplitApply1.getMillsheetNo());
                List crmMillSheetSplitInfoMapperDate =crmMillSheetSplitInfoMapper.findDate(gg);
                if(crmMillSheetSplitInfoMapperDate.size()>0){

                }else{
                    MillSheetHosts hh =new MillSheetHosts();
                    hh.setMillSheetNo(crmMillSheetSplitApply1.getFatherMillsheetNo());
                    hh.setUpdatedDt(new Date());
                    hh.setUpdatedBy(acctName);
                    hh.setState("PRIVIEWED");
                    millSheetHostsMapper.updateNum(hh);

                    //日志表
                    MillOperationHis millOperationHis = new MillOperationHis();
                    millOperationHis.setMillSheetNo(crmMillSheetSplitApply1.getFatherMillsheetNo());
                    millOperationHis.setOperationTime(new Date());
                    millOperationHis.setOperationIp(ip);
                    millOperationHis.setOperator(acctName);
                    millOperationHis.setOperationType("REVOKE");
                    millOperationHis.setOperationTime(new Date());
                    millOperationHis.setContent("撤销子质证书之后修改质证书状态为已预览");
                    millOperationHisMapper.insertSelective(millOperationHis);
                }

            }
            //日志表
            MillOperationHis millOperationHis = new MillOperationHis();
            millOperationHis.setMillSheetNo(record.getMillSheetNo());
            millOperationHis.setOperationTime(new Date());
            millOperationHis.setOperationIp(ip);
            millOperationHis.setOperator(acctName);
            millOperationHis.setOperationType("REVOKE");
            millOperationHis.setOperationTime(new Date());
            millOperationHis.setContent(content);
            millOperationHisMapper.insertSelective(millOperationHis);
        return 1;
    }
}
