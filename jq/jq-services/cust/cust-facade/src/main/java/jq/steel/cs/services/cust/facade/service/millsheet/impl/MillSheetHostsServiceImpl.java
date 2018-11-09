package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetRebackApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetSplitApplyMapper;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillOperationHisMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitApply;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillOperationHis;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetHostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    //分页查询
    @Override
    public PageDTO<MillSheetHostsVO> findMillSheetByPage(MillSheetHostsVO millSheetHostsVO) {
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
        PageDTOUtil.startPage(millSheetHostsVO);
        String startDtStr = DateFormatUtil.getStartDateStr(millSheetHosts.getStartDt());
        millSheetHosts.setStartDtStr(startDtStr);
        String endDtStr = DateFormatUtil.getEndDateStr(millSheetHosts.getEndDt());
        millSheetHosts.setEndDtStr(endDtStr);
        List<MillSheetHosts> millSheetByPage = millSheetHostsMapper.findMillSheetByPage(millSheetHosts);
        // 分页对象
        PageDTO<MillSheetHostsVO> transform = PageDTOUtil.transform(millSheetByPage, MillSheetHostsVO.class);
            for (MillSheetHostsVO millSheetHosts2:transform.getResultData()){
                millSheetHosts = new MillSheetHosts();
                BeanCopyUtil.copy(millSheetHosts2, millSheetHosts);
                List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.findFmillSheet(millSheetHosts);
                if (crmMillSheetSplitApplies.size()>0){
                    millSheetHosts2.setIsSplit(1);
                }else {
                    millSheetHosts2.setIsSplit(0);
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
            PageDTOUtil.startPage(millSheetHostsVO);
            String startDtStr = DateFormatUtil.getStartDateStr(millSheetHosts.getStartDt());
            millSheetHosts.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(millSheetHosts.getEndDt());
            millSheetHosts.setEndDtStr(endDtStr);
            List<MillSheetHosts> millSheetByPage = millSheetHostsMapper.findMillSheetByPage1(millSheetHosts);
            // 分页对象
            PageDTO<MillSheetHostsVO> transform = PageDTOUtil.transform(millSheetByPage, MillSheetHostsVO.class);
            for (MillSheetHostsVO millSheetHosts2:transform.getResultData()){
                millSheetHosts = new MillSheetHosts();
                BeanCopyUtil.copy(millSheetHosts2, millSheetHosts);
                List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.findFmillSheet(millSheetHosts);
                if (crmMillSheetSplitApplies.size()>0){
                    millSheetHosts2.setIsSplit(1);
                }else {
                    millSheetHosts2.setIsSplit(0);
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
            if(millSheetHosts.getOperationType().equals(1)){
                //1是预览  2是打印
                millOperationHis.setOperator(millSheetHostsVO.getAcctName());
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
                millOperationHis.setOperator(millSheetHostsVO.getAcctName());
                millOperationHis.setOperationIp(ip);
                millSheetHosts.setState("PRINTED");
                millSheetHosts.setPrintableNum(millSheetByPage.getPrintableNum()-1);
                millSheetHosts.setPrintedNum(millSheetByPage.getPrintedNum()+1);
            }else{
                //3下载只进行记录日志，次数下载在findDownUrl方法内
                millOperationHis.setOperationType("DOWNLOADED");
                millOperationHis.setOperator(millSheetHostsVO.getAcctName());
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
            String millSheetPath = url.getMillSheetUrl()+"/"+url.getMillSheetName();
            if(millSheetPath!=null){
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
    public Integer updateNumber(List<MillSheetHostsVO> record) {
        for(MillSheetHostsVO millSheetHostsVO:record){
            //转换mdel
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            BeanCopyUtil.copy(millSheetHostsVO,millSheetHosts);
            MillSheetHosts millSheetByPage = millSheetHostsMapper.findUrl(millSheetHosts);
            if(millSheetHosts.getOperationType().equals(1)){
                //1是打印次数修改  2是下载次数修改
                millSheetHosts.setPrintableNum(millSheetByPage.getPrintableNum()+1);
            }else {
                millSheetHosts.setDownableNum(millSheetByPage.getDownableNum()+1);
            }
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
}
