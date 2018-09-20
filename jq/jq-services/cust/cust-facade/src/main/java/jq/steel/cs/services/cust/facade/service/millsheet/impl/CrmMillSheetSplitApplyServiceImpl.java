package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.AssertContext;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.JDBCUtils;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetSplitApplyMapper;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetSplitInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
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

    //申请拆分数据插入
    @Override
    public CrmMillSheetSplitApplyVO splitInsert(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList) {
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
        crmMillSheetSplitApply1.setCreatedBy(AssertContext.getAcctName());
        crmMillSheetSplitApply1.setCreatedDt(new Date());
        String newMillSheetNo= A + millSheetNo+ "";
        crmMillSheetSplitApply1.setMillsheetNo(newMillSheetNo);
        crmMillSheetSplitApply1.setFatherMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        crmMillSheetSplitApply1.setMillSheetType(crmMillSheetSplitInfoList.get(0).getMillSheetType());
        crmMillSheetSplitApply1.setSaleParty(crmMillSheetSplitInfoList.get(0).getSaleParty());
        crmMillSheetSplitApplyMapper.insertSelective(crmMillSheetSplitApply1);
        //添加明细表数据
        for (CrmMillSheetSplitInfo crmMillSheetSplitInfo:crmMillSheetSplitInfoList){
            crmMillSheetSplitInfo.setCreatedBy(AssertContext.getAcctName());
            crmMillSheetSplitInfo.setCreatedDt(new Date());
            crmMillSheetSplitInfo.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
            crmMillSheetSplitInfo.setMillsheetNo(newMillSheetNo);
            crmMillSheetSplitInfo.setFatherMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
            crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
            //调用存储过程
           Map<String,Object> map = new HashMap<String,Object>();
           // map.put("inmillSheetNoOld",crmMillSheetSplitInfo.getMillsheetNo());
           // map.put("inmillSheetNo",newMillSheetNo);
            //map.put("inmillSheetType",crmMillSheetSplitInfo.getMillSheetType());
            //map.put("inzcharg",crmMillSheetSplitInfo.getZcharg());
           // map.put("inzjishu",crmMillSheetSplitInfo.getZjishu());
            //map.put("inzlosmenge",crmMillSheetSplitInfo.getZlosmenge());
            //map.put("inspiltCustomer",crmMillSheetSplitInfo.getSpiltCustomer());
           /* map.put("inmillSheetNoOld","A1809040299");
            map.put("inmillSheetNo","A180904029904");
            map.put("inmillSheetType","M");
            map.put("inzcharg","8888888");
            map.put("inzjishu",1);
            map.put("inzlosmenge",1);
            map.put("inspiltCustomer","ceshi");*/
            //this.cunChu("A1809040299","A180904029904","M","8888888",1,1,"ceshi");
            this.cunChu(crmMillSheetSplitInfo.getMillsheetNo(),newMillSheetNo,crmMillSheetSplitInfo.getMillsheetType(),
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
        crmMillSheetSplitApply.setMillsheetNo(crmMillSheetSplitDetailVO.getMillSheetNo());CrmMillSheetSplitInfo crmMillSheetSplitInfo = new CrmMillSheetSplitInfo();
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

}
