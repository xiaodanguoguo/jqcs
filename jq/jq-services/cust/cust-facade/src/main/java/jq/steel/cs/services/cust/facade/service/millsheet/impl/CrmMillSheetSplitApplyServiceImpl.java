package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.AssertContext;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.JDBCUtils;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.dao.*;
import jq.steel.cs.services.cust.facade.model.*;
import jq.steel.cs.services.cust.facade.service.millsheet.CrmMillSheetSplitApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrmMillSheetSplitApplyServiceImpl implements CrmMillSheetSplitApplyService{
    @Autowired
    private CrmMillSheetSplitApplyMapper crmMillSheetSplitApplyMapper;
    @Autowired
    private CrmMillSheetSplitInfoMapper crmMillSheetSplitInfoMapper;
    @Autowired 
    private MillSheetHostsMapper millSheetHostsMapper;
    @Autowired
    private MillCoilInfoMapper coilInfoMapper;
    @Autowired
    private CrmBatchSplitMapper crmBatchSplitMapper;

    //申请拆分数据插入
    @Override
    public CrmMillSheetSplitApplyVO splitInsert(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList) {
        String acctName =crmMillSheetSplitApplyVOList.get(0).getAcctName();
        List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList = BeanCopyUtil.copyList(crmMillSheetSplitApplyVOList, CrmMillSheetSplitInfo.class);
        //查询一下最大质证书编号
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        millSheetHosts.setMillSheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
        String mill ="";
        if( url.get(0).getSplitMaxValue()==null){
            //质证书编号+00
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo()+"00";
        }else {
            mill = url.get(0).getSplitMaxValue();
        }

        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(mill);
        String result = "";
        String A = mill.replaceAll("[0-9]", "");
        while (m.find()) {
            result = m.replaceAll("").trim();
        }
        BigInteger b = new BigInteger(result);
        BigInteger big2 = new BigInteger("1");
        BigInteger millSheetNo = b.add(big2) ;
        //质证书拆分申请表添加数据
        CrmMillSheetSplitApply crmMillSheetSplitApply1 = new CrmMillSheetSplitApply();
        crmMillSheetSplitApply1.setCreatedBy(acctName);
        crmMillSheetSplitApply1.setCreatedDt(new Date());
        String newMillSheetNo= A + millSheetNo+ "";
        crmMillSheetSplitApply1.setMillsheetNo(newMillSheetNo);
        crmMillSheetSplitApply1.setFatherMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        crmMillSheetSplitApply1.setMillsheetType(crmMillSheetSplitInfoList.get(0).getMillsheetType());
        crmMillSheetSplitApply1.setCreationTime(crmMillSheetSplitInfoList.get(0).getCreatedDt());
        crmMillSheetSplitApply1.setZkunnr(crmMillSheetSplitInfoList.get(0).getZkunnr());
        crmMillSheetSplitApply1.setZcpmc(crmMillSheetSplitInfoList.get(0).getZcpmc());
        crmMillSheetSplitApply1.setSpiltCustomer(crmMillSheetSplitInfoList.get(0).getSpiltCustomer());
        crmMillSheetSplitApply1.setStatus("1");
        crmMillSheetSplitApplyMapper.insertSelective(crmMillSheetSplitApply1);
        //添加明细表数据
        for (CrmMillSheetSplitInfo crmMillSheetSplitInfo:crmMillSheetSplitInfoList){
            crmMillSheetSplitInfo.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
            crmMillSheetSplitInfo.setFatherMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
            crmMillSheetSplitInfo.setMillsheetNo(newMillSheetNo);
            crmMillSheetSplitInfo.setMillsheetType(crmMillSheetSplitInfo.getMillsheetType());
            crmMillSheetSplitInfo.setZkunnr(crmMillSheetSplitInfo.getZkunnr());
            crmMillSheetSplitInfo.setCreationTime(crmMillSheetSplitInfo.getCreatedDt());
            crmMillSheetSplitInfo.setCreatedDt(new Date());
            crmMillSheetSplitInfo.setCreatedBy(acctName);
            crmMillSheetSplitInfo.setZcpmc(crmMillSheetSplitInfo.getZcpmc());
            crmMillSheetSplitInfo.setSpecs(crmMillSheetSplitInfo.getSpecs());
            crmMillSheetSplitInfo.setZchehao(crmMillSheetSplitInfo.getZchehao());
            crmMillSheetSplitInfo.setStatus("1");
            crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
            //调用存储过程
           Map<String,Object> map = new HashMap<String,Object>();
            this.cunChu(crmMillSheetSplitInfo.getFatherMillsheetNo(),newMillSheetNo,crmMillSheetSplitInfo.getMillsheetType(),
                    crmMillSheetSplitInfo.getZcharg(),crmMillSheetSplitInfo.getZjishu(),crmMillSheetSplitInfo.getZlosmenge(),
                    crmMillSheetSplitInfo.getSpiltCustomer());
        }
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        crmMillSheetSplitApplyVOS.setMillsheetNo(newMillSheetNo);
        return crmMillSheetSplitApplyVOS;
    }
    //调用存储过程
     void cunChu(String inmillSheetNoOld, String inmillSheetNo, String inmillSheetType, String inzcharg, Long inzjishu, BigDecimal inzlosmenge, String inspiltCustomer) {
        String sql="{call PRO_MILL_SPILT(?,?,?,?,?,?,?)}";
        Connection conn=null;
        //CallableStatement是用于执行SQL存储过程的接口
        CallableStatement call=null;
        try {
            conn= JDBCUtils.getConnection();
            call=conn.prepareCall(sql);
            //赋值
            call.setString(1, inmillSheetNoOld);
            call.setString(2, inmillSheetNo);
            call.setString(3, inmillSheetType);
            call.setString(4, inzcharg);
            //call.setInt(5, inzjishu);
            call.setLong(5,inzjishu);
            //call.setInt(6, inzlosmenge);
            call.setBigDecimal(6,inzlosmenge);
            call.setString(7, inspiltCustomer);
            //对于out参数，申明
            //call.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
            //call.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
            //call.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);

            //调用
            call.execute();

            //取出结果
           /* String name = call.getString(2);
            double sal = call.getDouble(3);
            String job = call.getString(4);

            System.out.println("工号为1110的员工信息:");

            System.out.println("姓名:"+name);
            System.out.println("薪水:"+sal);
            System.out.println("职位:"+job);*/

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.release(conn, call, null);
        }

    }

    //拆分详情查询
    @Override
    public List<CrmMillSheetSplitDetailVO> splitFindAll(CrmMillSheetSplitDetailVO crmMillSheetSplitDetailVO) {
        List<CrmMillSheetSplitDetailVO> crmMillSheetSplitDetailVOList = new ArrayList<CrmMillSheetSplitDetailVO>();

        //转换model
        MillCoilInfo coilInfo = new MillCoilInfo();
        coilInfo.setMillsheetNo(crmMillSheetSplitDetailVO.getMillSheetNo());
        CrmMillSheetSplitApply crmMillSheetSplitApply = new CrmMillSheetSplitApply();
        crmMillSheetSplitApply.setMillsheetNo(crmMillSheetSplitDetailVO.getMillSheetNo());
        crmMillSheetSplitApply.setStatus("1");
        CrmMillSheetSplitInfo crmMillSheetSplitInfo = new CrmMillSheetSplitInfo();
        crmMillSheetSplitInfo.setMillsheetNo(crmMillSheetSplitDetailVO.getMillSheetNo());

        //查询钢卷数据
        List<MillCoilInfo> millCoilInfos = coilInfoMapper.splitFind(coilInfo);

        //转换vo对象
        List<MillCoilInfoVO> millCoilInfoVOS = BeanCopyUtil.copyList(millCoilInfos, MillCoilInfoVO.class);
        crmMillSheetSplitDetailVO.setMillCoilInfoVOS(millCoilInfoVOS);

        //查询拆分申请表数据
        List<CrmMillSheetSplitApply> crmMillSheetSplitApplies = crmMillSheetSplitApplyMapper.find(crmMillSheetSplitApply);
        if (crmMillSheetSplitApplies.size()>0){
            for (CrmMillSheetSplitApply crmMillSheetSplitApply1 : crmMillSheetSplitApplies){
                CrmMillSheetSplitInfo crmMillSheetSplitInfo1  = new CrmMillSheetSplitInfo();
                crmMillSheetSplitInfo1.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
                crmMillSheetSplitInfo1.setStatus("1");
                List<CrmMillSheetSplitInfo> crmMillSheetSplitInfos =crmMillSheetSplitInfoMapper.findByParams(crmMillSheetSplitInfo1);
                for (CrmMillSheetSplitInfo crmMillSheetSplitInfo2 :crmMillSheetSplitInfos){
                    //主表送达方给子表送达方
                    crmMillSheetSplitInfo2.setShipToParty(crmMillSheetSplitApply1.getShipToParty());
                }
                List<CrmMillSheetSplitInfoVO> crmMillSheetSplitInfoVOS = BeanCopyUtil.copyList(crmMillSheetSplitInfos, CrmMillSheetSplitInfoVO.class);

                crmMillSheetSplitApply1.setCrmMillSheetSplitInfoVOS(crmMillSheetSplitInfoVOS);
            }
        }

        //转换拆分数据
        List<CrmMillSheetSplitApplyVO> crmMillSheetRebackApplyVOS = BeanCopyUtil.copyList(crmMillSheetSplitApplies, CrmMillSheetSplitApplyVO.class);
        crmMillSheetSplitDetailVO.setCrmMillSheetSplitApplyVOS(crmMillSheetRebackApplyVOS);
        crmMillSheetSplitDetailVOList.add(crmMillSheetSplitDetailVO);
        return crmMillSheetSplitDetailVOList;
    }




    //批量导入excel拆分
    @Override
    public CrmMillSheetSplitApplyVO splitInsertAll(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList) {
        String acctName =crmMillSheetSplitApplyVOList.get(0).getAcctName();
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList = BeanCopyUtil.copyList(crmMillSheetSplitApplyVOList, CrmMillSheetSplitInfo.class);
        //校验
        String findExist ="";
        String findAllow ="";
        String findVolume ="";
        String findNum ="";
        String findType = "";
        for (CrmMillSheetSplitInfo cmssi:crmMillSheetSplitInfoList){
            //插入batchSplit表
            CrmBatchSplit crmBatchSplit = new CrmBatchSplit();
            crmBatchSplit.setCreatedBy(acctName);
            crmBatchSplit.setCreatedDt(new Date());
            crmBatchSplit.setSpecs(cmssi.getSpecs());
            crmBatchSplit.setSpiltCustomer(cmssi.getSpiltCustomer());
            crmBatchSplit.setZcharg(cmssi.getZcharg());
            crmBatchSplit.setZchehao(cmssi.getZchehao());
            crmBatchSplit.setZjishu(cmssi.getZjishu());
            crmBatchSplit.setMillsheetNo(cmssi.getMillsheetNo());
            crmBatchSplitMapper.insertSelective(crmBatchSplit);
            MillSheetHosts millSheetHosts =new MillSheetHosts();
            millSheetHosts.setMillSheetNo(cmssi.getMillsheetNo());
            //是否有此质证书
            List<MillSheetHosts> millSheetHostsList=millSheetHostsMapper.findExist(millSheetHosts);
            if (millSheetHostsList.size()>0){
            }else {
                findExist+=",此质证书"+""+millSheetHosts.getMillSheetNo()+"不存在，";
            }
            //质证书编号是否允许拆分
            List<MillSheetHosts> alist=millSheetHostsMapper.findAllow(millSheetHosts);

            if (alist.size()>0){
            }else {
                //String state = alist.get(0).getState();
               /* if(state.equals("NEW")){
                    millSheetHosts.setState("新建");
                }else if(state.equals("CREATED")){
                    millSheetHosts.setState("已生成");
                }else if(state.equals("EXAMINED")){
                    millSheetHosts.setState("已审核");
                }else if(millSheetHosts.getState().equals("CONFIRMED")){
                    millSheetHosts.setState("已确认");
                }else if(millSheetHosts.getState().equals("FALLBACKED")){
                    millSheetHosts.setState("已回退");
                }else if(millSheetHosts.getState().equals("PENDING")){
                    millSheetHosts.setState("待处理");
                }else if(millSheetHosts.getState().equals("DISSUSED")){
                    millSheetHosts.setState("已置废");
                }else if(millSheetHosts.getState().equals("DOWNLOADED")){
                    millSheetHosts.setState("已下载");
                }else if(millSheetHosts.getState().equals("PRIVIEWED")){
                    millSheetHosts.setState("已预览");
                }else if(millSheetHosts.getState().equals("PRINTED")){
                    millSheetHosts.setState("已打印");
                }else if(millSheetHosts.getState().equals("DELETED")){
                    millSheetHosts.setState("已删除");
                }else if(millSheetHosts.getState().equals("SPLITED")){
                    millSheetHosts.setState("已拆分");
                }*/
                findAllow+=","+millSheetHosts.getMillSheetNo()+"状态不允许拆分";
            }
            //是否是孙质证书
            List<MillSheetHosts> blist=millSheetHostsMapper.findType(millSheetHosts);
            if (blist.size()>0){
            }else {
                findType+=","+millSheetHosts.getMillSheetNo()+"为孙质证书，不可拆分";
            }
            //是否有这个卷（质证书+批板卷+规格）
            MillCoilInfo coilInfo = new MillCoilInfo();
            coilInfo.setMillSheetNo(cmssi.getMillsheetNo());
            coilInfo.setZcharg(cmssi.getZcharg());
            coilInfo.setSpecs(cmssi.getSpecs());
            List<MillCoilInfo> millCoilInfos =coilInfoMapper.findVolume(coilInfo);
            /*
            如果指定的数与参数相等返回0。如果指定的数小于参数返回 -1。如果指定的数大于参数返回 1
            Integer x = 5;
                System.out.println(x.compareTo(3));  1
                System.out.println(x.compareTo(5));  0
                System.out.println(x.compareTo(8));  -1
            */
            if (millCoilInfos.size()>0){
                //该卷是否有量可拆
                int i=millCoilInfos.get(0).getSurplusZjishu().compareTo(BigDecimal.ZERO);
                if (i==1){

                }else if(i==-1){
                    //小于0
                    findNum+=","+coilInfo.getMillSheetNo()+"质证书'"+coilInfo.getSpecs()+"'规格'"+coilInfo.getZcharg()+"'卷可拆数量为0";
                }else if(i==0){
                    //等于0
                    findNum+=","+coilInfo.getMillSheetNo()+"质证书'"+coilInfo.getSpecs()+"'规格'"+coilInfo.getZcharg()+"'卷可拆数量为0";
                }
            }else {
                findVolume+=","+coilInfo.getMillSheetNo()+"质证书'"+coilInfo.getSpecs()+"'规格'"+coilInfo.getZcharg()+"'卷不存在";
            }

        }
        if (!findExist.equals("")) {
            crmMillSheetSplitApplyVOS.setCode(-1);
            crmMillSheetSplitApplyVOS.setMessage(findExist.substring(1));
            return crmMillSheetSplitApplyVOS;
        } else {
            if (!findAllow.equals("")) {
                crmMillSheetSplitApplyVOS.setCode(-1);
                crmMillSheetSplitApplyVOS.setMessage(findAllow.substring(1));
                return crmMillSheetSplitApplyVOS;
            } else {
                if (!findType.equals("")) {
                    crmMillSheetSplitApplyVOS.setCode(-1);
                    crmMillSheetSplitApplyVOS.setMessage(findType.substring(1));
                    return crmMillSheetSplitApplyVOS;
                } else {
                    if (!findVolume.equals("")) {
                        crmMillSheetSplitApplyVOS.setCode(-1);
                        crmMillSheetSplitApplyVOS.setMessage(findVolume.substring(1));
                        return crmMillSheetSplitApplyVOS;
                    } else {
                        if (!findNum.equals("")) {
                            crmMillSheetSplitApplyVOS.setCode(-1);
                            crmMillSheetSplitApplyVOS.setMessage(findNum.substring(1));
                            return crmMillSheetSplitApplyVOS;
                        }
                    }
                }
            }
        }


        //执行拆分准备
        for (CrmMillSheetSplitInfo cmssi:crmMillSheetSplitInfoList) {
            //查询一下最大质证书编号
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            millSheetHosts.setMillSheetNo(cmssi.getMillsheetNo());
            List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
            String mill = "";
            if (url.get(0).getSplitMaxValue() == null) {
                //质证书编号+00
                mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
            } else {
                mill = url.get(0).getSplitMaxValue();
            }

            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(mill);
            String result = "";
            String A = mill.replaceAll("[0-9]", "");
            while (m.find()) {
                result = m.replaceAll("").trim();
            }
            BigInteger b = new BigInteger(result);
            BigInteger big2 = new BigInteger("1");
            BigInteger millSheetNo = b.add(big2);
            //质证书拆分申请表添加数据
            CrmMillSheetSplitApply crmMillSheetSplitApply1 = new CrmMillSheetSplitApply();
            crmMillSheetSplitApply1.setCreatedBy(acctName);
            crmMillSheetSplitApply1.setCreatedDt(new Date());
            String newMillSheetNo = A + millSheetNo + "";
            crmMillSheetSplitApply1.setMillsheetNo(newMillSheetNo);
            crmMillSheetSplitApply1.setFatherMillsheetNo(cmssi.getMillsheetNo());
            //判断类型进行塞值
            MillSheetHosts millSheetHosts1 = new MillSheetHosts();
            millSheetHosts1.setMillSheetNo(cmssi.getMillsheetNo());
            List<MillSheetHosts> millSheetHosts2 = millSheetHostsMapper.findMillSheetType(millSheetHosts1);
            if (millSheetHosts2.get(0).getMillSheetType().equals("M")){
                crmMillSheetSplitApply1.setMillsheetType("Z");
            }else if(millSheetHosts2.get(0).getMillSheetType().equals("Z")){
                crmMillSheetSplitApply1.setMillsheetType("S");
            }
            crmMillSheetSplitApply1.setCreationTime(new Date());
            MillCoilInfo millCoilInfo = new MillCoilInfo();
            millCoilInfo.setMillSheetNo(cmssi.getMillsheetNo());
            millCoilInfo.setZcharg(cmssi.getZcharg());
            millCoilInfo.setSpecs(cmssi.getSpecs());
            //查询钢卷数据
            List<MillCoilInfo> millCoilInfos = coilInfoMapper.findInfo(millCoilInfo);
            String netWeight ="";
            System.out.println("************Long类型件数"+millCoilInfos.get(0).getZjishu()+"*******************");
            BigDecimal L =new BigDecimal(millCoilInfos.get(0).getZjishu());
            System.out.println("************总件数"+L+"*******************");
            BigDecimal b1 = millCoilInfos.get(0).getZlosmenge();
            System.out.println("************总重量"+b1+"*******************");
            BigDecimal aa = new BigDecimal(1);
            /*if(a.compareTo(b)==0) 结果是true
              比较大小可以用 a.compareTo(b)
              返回值    -1 小于   0 等于    1 大于*/
            if(millCoilInfos.get(0).getSurplusZjishu().compareTo(aa)==1){
                //四舍五入
                BigDecimal a =b1.divide(L,3,BigDecimal.ROUND_HALF_UP);
                System.out.println("************净量"+a+"*******************");
                cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                System.out.println("*****净重乘件数*******+"+a.multiply(new BigDecimal(cmssi.getZjishu()))+"*******************");
            }else{
                //最后一件不四舍五入
                BigDecimal a =b1.divide(L);
                System.out.println("************净量"+a+"*******************");
                cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                System.out.println("*****净重乘件数*******+"+a.multiply(new BigDecimal(cmssi.getZjishu()))+"*******************");
            }
            //售达方
            crmMillSheetSplitApply1.setZkunnr(millCoilInfos.get(0).getZkunnr());
            //产品类别
            crmMillSheetSplitApply1.setZcpmc(millCoilInfos.get(0).getZcpmc());
            crmMillSheetSplitApply1.setSpiltCustomer(cmssi.getSpiltCustomer());
            crmMillSheetSplitApply1.setStatus("1");
            crmMillSheetSplitApplyMapper.insertSelective(crmMillSheetSplitApply1);
            //添加明细表数据
            CrmMillSheetSplitInfo crmMillSheetSplitInfo = new CrmMillSheetSplitInfo();
            crmMillSheetSplitInfo.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
            crmMillSheetSplitInfo.setFatherMillsheetNo(cmssi.getMillsheetNo());
            crmMillSheetSplitInfo.setMillsheetNo(newMillSheetNo);
            crmMillSheetSplitInfo.setMillsheetType(crmMillSheetSplitApply1.getMillsheetType());
            crmMillSheetSplitInfo.setZkunnr(crmMillSheetSplitApply1.getZkunnr());
            crmMillSheetSplitInfo.setCreationTime(crmMillSheetSplitApply1.getCreatedDt());
            //发车日期
            crmMillSheetSplitInfo.setCreatedDt(new Date());
            crmMillSheetSplitInfo.setCreatedBy(acctName);
            crmMillSheetSplitInfo.setZcpmc(crmMillSheetSplitInfo.getZcpmc());
            crmMillSheetSplitInfo.setSpecs(crmMillSheetSplitInfo.getSpecs());
            crmMillSheetSplitInfo.setZchehao(crmMillSheetSplitInfo.getZchehao());
            crmMillSheetSplitInfo.setStatus("1");
            crmMillSheetSplitInfo.setZlosmenge(cmssi.getZlosmenge());
            crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
            //调用存储过程
            this.cunChuAll(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitInfo.getMillsheetType(),
                    cmssi.getZcharg(), cmssi.getZjishu(), cmssi.getZlosmenge(),
                    cmssi.getSpiltCustomer(), cmssi.getZchehao());

        }
        crmMillSheetSplitApplyVOS.setCode(1);
        return crmMillSheetSplitApplyVOS;
    }



    //调用存储过程
    void cunChuAll(String inmillSheetNoOld, String inmillSheetNo, String inmillSheetType, String inzcharg, Long inzjishu, BigDecimal inzlosmenge, String inspiltCustomer,String inzchehao) {
        String sql="{call PRO_MILL_SPILT_ALL(?,?,?,?,?,?,?,?)}";
        Connection conn=null;
        //CallableStatement是用于执行SQL存储过程的接口
        CallableStatement call=null;
        try {
            conn= JDBCUtils.getConnection();
            call=conn.prepareCall(sql);
            //赋值
            call.setString(1, inmillSheetNoOld);
            call.setString(2, inmillSheetNo);
            call.setString(3, inmillSheetType);
            call.setString(4, inzcharg);
            //call.setInt(5, inzjishu);
            call.setLong(5,inzjishu);
            //call.setInt(6, inzlosmenge);
            call.setBigDecimal(6,inzlosmenge);
            call.setString(7, inspiltCustomer);
            call.setString(8, inzchehao);
            //对于out参数，申明
            //call.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
            //call.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
            //call.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);

            //调用
            call.execute();

            //取出结果
           /* String name = call.getString(2);
            double sal = call.getDouble(3);
            String job = call.getString(4);

            System.out.println("工号为1110的员工信息:");

            System.out.println("姓名:"+name);
            System.out.println("薪水:"+sal);
            System.out.println("职位:"+job);*/

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.release(conn, call, null);
        }

    }
}
