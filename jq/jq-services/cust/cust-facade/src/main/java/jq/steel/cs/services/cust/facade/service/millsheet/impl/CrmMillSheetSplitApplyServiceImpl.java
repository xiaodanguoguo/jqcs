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
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
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
public class CrmMillSheetSplitApplyServiceImpl implements CrmMillSheetSplitApplyService {
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
        String acctName = crmMillSheetSplitApplyVOList.get(0).getAcctName();
        List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList = BeanCopyUtil.copyList(crmMillSheetSplitApplyVOList, CrmMillSheetSplitInfo.class);
        //查询一下最大质证书编号
       /* MillSheetHosts millSheetHosts = new MillSheetHosts();
        millSheetHosts.setMillSheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
        String mill = "";
        if (url.get(0).getSplitMaxValue() == null) {
            //质证书编号+00
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
        } else {
            mill = url.get(0).getSplitMaxValue();
        }*/
        String mill = "";
        CrmMillSheetSplitInfo cmss1 = new CrmMillSheetSplitInfo();
        cmss1.setMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        cmss1.setStatus("1");
        List<CrmMillSheetSplitInfo> alist = crmMillSheetSplitInfoMapper.findMillSheetNo(cmss1);
        if(alist.size()>0){
            Map<String, Object> map = new HashMap<>();
            map.put("millsheetNo", crmMillSheetSplitInfoList.get(0).getMillsheetNo());
            mill= crmMillSheetSplitInfoMapper.findMillSheetNoMax(map);
            System.out.println("拆分最大值"+mill);
        }else {
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
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
        crmMillSheetSplitApply1.setFatherMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        crmMillSheetSplitApply1.setMillsheetType(crmMillSheetSplitInfoList.get(0).getMillsheetType());
        crmMillSheetSplitApply1.setCreationTime(crmMillSheetSplitInfoList.get(0).getCreatedDt());
        crmMillSheetSplitApply1.setZkunnr(crmMillSheetSplitInfoList.get(0).getZkunnr());
        crmMillSheetSplitApply1.setZcpmc(crmMillSheetSplitInfoList.get(0).getZcpmc());
        crmMillSheetSplitApply1.setSpiltCustomer(crmMillSheetSplitInfoList.get(0).getSpiltCustomer());
        crmMillSheetSplitApply1.setStatus("1");
        crmMillSheetSplitApplyMapper.insertSelective(crmMillSheetSplitApply1);
        //添加明细表数据
        for (CrmMillSheetSplitInfo crmMillSheetSplitInfo : crmMillSheetSplitInfoList) {
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
            Map<String, Object> map = new HashMap<String, Object>();
            this.cunChu(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitInfo.getMillsheetType(),
                    crmMillSheetSplitInfo.getZcharg(),crmMillSheetSplitInfo.getSpecs(), crmMillSheetSplitInfo.getZjishu(), crmMillSheetSplitInfo.getZlosmenge(),
                    crmMillSheetSplitInfo.getSpiltCustomer());
        }
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        crmMillSheetSplitApplyVOS.setMillsheetNo(newMillSheetNo);
        return crmMillSheetSplitApplyVOS;
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
        if (crmMillSheetSplitApplies.size() > 0) {
            for (CrmMillSheetSplitApply crmMillSheetSplitApply1 : crmMillSheetSplitApplies) {
                CrmMillSheetSplitInfo crmMillSheetSplitInfo1 = new CrmMillSheetSplitInfo();
                crmMillSheetSplitInfo1.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
                crmMillSheetSplitInfo1.setStatus("1");
                List<CrmMillSheetSplitInfo> crmMillSheetSplitInfos = crmMillSheetSplitInfoMapper.findByParams(crmMillSheetSplitInfo1);
                for (CrmMillSheetSplitInfo crmMillSheetSplitInfo2 : crmMillSheetSplitInfos) {
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
        String acctName = crmMillSheetSplitApplyVOList.get(0).getAcctName();
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList = BeanCopyUtil.copyList(crmMillSheetSplitApplyVOList, CrmMillSheetSplitInfo.class);
        //导入的质证书对于的总值
        /*Map<String, Object> map = new HashMap<>();
        for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
            if (map.size() > 0) {
                if (map.containsKey(cmssi.getMillsheetNo())) {
                    long l = Long.valueOf(String.valueOf(map.get(cmssi.getMillsheetNo())));
                    long jishu = l + cmssi.getZjishu();
                    map.put(cmssi.getMillsheetNo(), jishu);
                } else {
                    map.put(cmssi.getMillsheetNo(), cmssi.getZjishu());
                }
            } else {
                map.put(cmssi.getMillsheetNo(), cmssi.getZjishu());
            }
        }*/
        //导入的质证书对于的总值
        //拿取重复数据到集合noListMillSheetNo1
        List<CrmMillSheetSplitInfo> noListMillSheetNo1 = new ArrayList<>();
        List<CrmMillSheetSplitInfo> hh1 = new ArrayList<>();
        for (int i = 0; i < crmMillSheetSplitInfoList.size(); i++) {
            CrmMillSheetSplitInfo crmMillSheetSplitInfo = crmMillSheetSplitInfoList.get(i);
            for (int j = i + 1; j < crmMillSheetSplitInfoList.size(); j++) {
                CrmMillSheetSplitInfo crmMillSheetSplitInfo1 = crmMillSheetSplitInfoList.get(j);
                if (crmMillSheetSplitInfo.getMillsheetNo().equals(crmMillSheetSplitInfo1.getMillsheetNo())) {
                    if (crmMillSheetSplitInfo.getZcharg().equals(crmMillSheetSplitInfo1.getZcharg())) {
                        if (crmMillSheetSplitInfo.getSpecs().equals(crmMillSheetSplitInfo1.getSpecs())) {
                            if (hh1.size() > 0) {
                                String aa = "";
                                for (CrmMillSheetSplitInfo gg : hh1) {
                                    if (gg.getMillsheetNo().equals(crmMillSheetSplitInfo1.getMillsheetNo()) && gg.getZcharg().equals(crmMillSheetSplitInfo1.getZcharg()) && gg.getSpecs().equals(crmMillSheetSplitInfo1.getSpecs())) {
                                        aa = "a";
                                    }
                                }
                                if (!aa.equals("")) {
                                    //重复数据
                                    CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                    crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                    crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                    crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                    crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                    crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                    crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                    crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                    noListMillSheetNo1.add(crmMillSheetSplitInfo3);
                                } else {
                                    //可能质证书一样拆分单位不一样；
                                    CrmMillSheetSplitInfo crmMillSheetSplitInfo2 = new CrmMillSheetSplitInfo();
                                    crmMillSheetSplitInfo2.setAcctName(crmMillSheetSplitInfo.getAcctName());
                                    crmMillSheetSplitInfo2.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                                    crmMillSheetSplitInfo2.setZchehao(crmMillSheetSplitInfo.getZchehao());
                                    crmMillSheetSplitInfo2.setZjishu(crmMillSheetSplitInfo.getZjishu());
                                    crmMillSheetSplitInfo2.setZcharg(crmMillSheetSplitInfo.getZcharg());
                                    crmMillSheetSplitInfo2.setSpecs(crmMillSheetSplitInfo.getSpecs());
                                    crmMillSheetSplitInfo2.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                                    CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                    crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                    crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                    crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                    crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                    crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                    crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                    crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                    noListMillSheetNo1.add(crmMillSheetSplitInfo2);
                                    noListMillSheetNo1.add(crmMillSheetSplitInfo3);
                                    i--;
                                    hh1.add(crmMillSheetSplitInfo2);
                                }
                            } else {
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo2 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo2.setAcctName(crmMillSheetSplitInfo.getAcctName());
                                crmMillSheetSplitInfo2.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                                crmMillSheetSplitInfo2.setZchehao(crmMillSheetSplitInfo.getZchehao());
                                crmMillSheetSplitInfo2.setZjishu(crmMillSheetSplitInfo.getZjishu());
                                crmMillSheetSplitInfo2.setZcharg(crmMillSheetSplitInfo.getZcharg());
                                crmMillSheetSplitInfo2.setSpecs(crmMillSheetSplitInfo.getSpecs());
                                crmMillSheetSplitInfo2.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                noListMillSheetNo1.add(crmMillSheetSplitInfo2);
                                noListMillSheetNo1.add(crmMillSheetSplitInfo3);
                                i--;
                                hh1.add(crmMillSheetSplitInfo2);
                            }
                        }
                    }
                }
            }
        }


        //校验
        String findExist = "";
        String findAllow = "";
        String findVolume = "";
        String findNum = "";
        String findType = "";
        for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
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
            MillSheetHosts millSheetHosts = new MillSheetHosts();
            millSheetHosts.setMillSheetNo(cmssi.getMillsheetNo());
            //是否有此质证书
            List<MillSheetHosts> millSheetHostsList = millSheetHostsMapper.findExist(millSheetHosts);
            if (millSheetHostsList.size() > 0) {
            } else {
                findExist += ",此质证书" + "" + millSheetHosts.getMillSheetNo() + "不存在，";
            }
            //质证书编号是否允许拆分
            List<MillSheetHosts> alist = millSheetHostsMapper.findAllow(millSheetHosts);
            if (alist.size() > 0) {
            } else {
                findAllow += "," + millSheetHosts.getMillSheetNo() + "状态不允许拆分";
            }
            //是否是孙质证书
            List<MillSheetHosts> blist = millSheetHostsMapper.findType(millSheetHosts);
            if (blist.size() > 0) {
            } else {
                findType += "," + millSheetHosts.getMillSheetNo() + "为孙质证书，不可拆分";
            }
            //是否有这个卷（质证书+批板卷+规格）
            MillCoilInfo coilInfo = new MillCoilInfo();
            coilInfo.setMillSheetNo(cmssi.getMillsheetNo());
            coilInfo.setZcharg(cmssi.getZcharg());
            coilInfo.setSpecs(cmssi.getSpecs());
            List<MillCoilInfo> millCoilInfos = coilInfoMapper.findVolume(coilInfo);
            /*
            如果指定的数与参数相等返回0。如果指定的数小于参数返回 -1。如果指定的数大于参数返回 1  compareTo 大于的意思
            Integer x = 5;
                System.out.println(x.compareTo(3));  1
                System.out.println(x.compareTo(5));  0
                System.out.println(x.compareTo(8));  -1
            */
            if (millCoilInfos.size() > 0) {
                //该卷是否有量可拆
                int i = millCoilInfos.get(0).getSurplusZjishu().compareTo(BigDecimal.ZERO);
                if (i == 1) {
                    //判断是否超出数量
                    Long mm =new Long(0);

                    for (CrmMillSheetSplitInfo tt:noListMillSheetNo1){
                        if (tt.getMillsheetNo().equals(coilInfo.getMillsheetNo())){
                            if (tt.getZcharg().equals(coilInfo.getZcharg())){
                                if (tt.getSpecs().equals(coilInfo.getSpecs())){
                                    mm+=tt.getZjishu();
                                }
                            }
                        }
                    }
                    if (mm>0){

                    }else {
                        for (CrmMillSheetSplitInfo tt:crmMillSheetSplitInfoList){
                            if (tt.getMillsheetNo().equals(coilInfo.getMillsheetNo())){
                                if (tt.getZcharg().equals(coilInfo.getZcharg())){
                                    if (tt.getSpecs().equals(coilInfo.getSpecs())){
                                        mm=tt.getZjishu();
                                    }
                                }
                            }
                        }
                    }
                   // BigDecimal bigDecimal = this.getBigDecimal(map.get(cmssi.getMillsheetNo()));
                    BigDecimal bigDecimal =new BigDecimal(mm);
                    int j = millCoilInfos.get(0).getSurplusZjishu().compareTo(bigDecimal);
                    if (j == 1) {
                    } else if (j == -1) {
                        findNum += "," + coilInfo.getMillSheetNo() + "质证书'" + coilInfo.getSpecs() + "'规格'" + coilInfo.getZcharg() + "'卷可拆数量不足，剩余" + millCoilInfos.get(0).getSurplusZjishu() + "件";
                    }
                } else if (i == -1) {
                    //小于0
                    findNum += "," + coilInfo.getMillSheetNo() + "质证书'" + coilInfo.getSpecs() + "'规格'" + coilInfo.getZcharg() + "'卷可拆数量为0";
                } else if (i == 0) {
                    //等于0
                    findNum += "," + coilInfo.getMillSheetNo() + "质证书'" + coilInfo.getSpecs() + "'规格'" + coilInfo.getZcharg() + "'卷可拆数量为0";
                }
            } else {
                findVolume += "," + coilInfo.getMillSheetNo() + "质证书'" + coilInfo.getSpecs() + "'规格'" + coilInfo.getZcharg() + "'卷不存在";
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
        String millSheetNos = "";
        List<CrmMillSheetSplitInfo> noListMillSheetNo = new ArrayList<>();
        List<CrmMillSheetSplitInfo> hh = new ArrayList<>();
        List<CrmMillSheetSplitInfo> hh7 = new ArrayList<>();
        //拿取重复元素到集合noListMillSheetNo
        for (int i = 0; i < crmMillSheetSplitInfoList.size(); i++) {
            CrmMillSheetSplitInfo crmMillSheetSplitInfo = crmMillSheetSplitInfoList.get(i);
            for (int j = i + 1; j < crmMillSheetSplitInfoList.size(); j++) {
                CrmMillSheetSplitInfo crmMillSheetSplitInfo1 = crmMillSheetSplitInfoList.get(j);
                if (crmMillSheetSplitInfo.getMillsheetNo().equals(crmMillSheetSplitInfo1.getMillsheetNo())) {
                    if (crmMillSheetSplitInfo.getSpiltCustomer().equals(crmMillSheetSplitInfo1.getSpiltCustomer())) {
                        if (hh.size() > 0) {
                            String aa = "";
                            for (CrmMillSheetSplitInfo gg : hh) {
                                if (gg.getMillsheetNo().equals(crmMillSheetSplitInfo.getMillsheetNo())
                                        && gg.getSpiltCustomer().equals(crmMillSheetSplitInfo.getSpiltCustomer())) {
                                    aa = "a";
                                }
                            }
                            String bb = "";
                            for (CrmMillSheetSplitInfo gg : hh) {
                                if (gg.getMillsheetNo().equals(crmMillSheetSplitInfo1.getMillsheetNo())
                                        && gg.getSpiltCustomer().equals(crmMillSheetSplitInfo1.getSpiltCustomer())) {
                                    bb = "b";
                                }
                            }

                            //第二条数据
                            String cc = "";
                            for (CrmMillSheetSplitInfo gg : hh7) {
                                if (gg.getMillsheetNo().equals(crmMillSheetSplitInfo.getMillsheetNo())
                                        && gg.getSpiltCustomer().equals(crmMillSheetSplitInfo.getSpiltCustomer())
                                        && gg.getSpecs().equals(crmMillSheetSplitInfo.getSpecs())) {
                                    cc = "c";
                                }
                            }
                            String dd = "";
                            for (CrmMillSheetSplitInfo gg : hh7) {
                                if (gg.getMillsheetNo().equals(crmMillSheetSplitInfo1.getMillsheetNo())
                                        && gg.getSpiltCustomer().equals(crmMillSheetSplitInfo1.getSpiltCustomer())
                                        && gg.getSpecs().equals(crmMillSheetSplitInfo1.getSpecs())) {
                                    dd = "d";
                                }
                            }
                            if (!aa.equals("")) {
                                //存在于hh中
                                if(!cc.equals("")){
                                    //存在于hh7中
                                }else {
                                    CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                    crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo.getAcctName());
                                    crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                                    crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo.getZchehao());
                                    crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo.getZjishu());
                                    crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo.getZcharg());
                                    crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo.getSpecs());
                                    crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                                    noListMillSheetNo.add(crmMillSheetSplitInfo3);
                                    hh7.add(crmMillSheetSplitInfo3);
                                }
                            }else{
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo.getAcctName());
                                crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                                crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo.getZchehao());
                                crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo.getZjishu());
                                crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo.getZcharg());
                                crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo.getSpecs());
                                crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                                noListMillSheetNo.add(crmMillSheetSplitInfo3);
                                hh7.add(crmMillSheetSplitInfo3);
                                hh.add(crmMillSheetSplitInfo3);
                            }
                            if (!bb.equals("")) {
                                //存在于hh中
                                if(!dd.equals("")){
                                    //存在于hh7中
                                }else {
                                    CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                    crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                    crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                    crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                    crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                    crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                    crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                    crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                    noListMillSheetNo.add(crmMillSheetSplitInfo3);
                                    hh7.add(crmMillSheetSplitInfo3);
                                }
                            }else{
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                noListMillSheetNo.add(crmMillSheetSplitInfo3);
                                hh7.add(crmMillSheetSplitInfo3);
                                hh.add(crmMillSheetSplitInfo3);
                            }
                        } else {
                            CrmMillSheetSplitInfo crmMillSheetSplitInfo2 = new CrmMillSheetSplitInfo();
                            crmMillSheetSplitInfo2.setAcctName(crmMillSheetSplitInfo.getAcctName());
                            crmMillSheetSplitInfo2.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                            crmMillSheetSplitInfo2.setZchehao(crmMillSheetSplitInfo.getZchehao());
                            crmMillSheetSplitInfo2.setZjishu(crmMillSheetSplitInfo.getZjishu());
                            crmMillSheetSplitInfo2.setZcharg(crmMillSheetSplitInfo.getZcharg());
                            crmMillSheetSplitInfo2.setSpecs(crmMillSheetSplitInfo.getSpecs());
                            crmMillSheetSplitInfo2.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                            CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                            crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                            crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                            crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                            crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                            crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                            crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                            crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                            noListMillSheetNo.add(crmMillSheetSplitInfo2);
                            noListMillSheetNo.add(crmMillSheetSplitInfo3);
                            //i--;
                            hh.add(crmMillSheetSplitInfo2);
                            hh7.add(crmMillSheetSplitInfo2);
                            hh7.add(crmMillSheetSplitInfo3);
                        }
                    }
                }
            }
        }



        //去除单位和质证书一致的数据
        for(int i=0;i<crmMillSheetSplitInfoList.size();i++){
            for(int j=0;j<noListMillSheetNo.size();j++){
                if(crmMillSheetSplitInfoList.get(i).getMillsheetNo().equals(noListMillSheetNo.get(j).getMillsheetNo())
                        &&crmMillSheetSplitInfoList.get(i).getSpecs().equals(noListMillSheetNo.get(j).getSpecs())
                        &&crmMillSheetSplitInfoList.get(i).getZcharg().equals(noListMillSheetNo.get(j).getZcharg())
                        &&crmMillSheetSplitInfoList.get(i).getZjishu().equals(noListMillSheetNo.get(j).getZjishu())
                        &&crmMillSheetSplitInfoList.get(i).getZchehao().equals(noListMillSheetNo.get(j).getZchehao())
                        &&crmMillSheetSplitInfoList.get(i).getSpiltCustomer().equals(noListMillSheetNo.get(j).getSpiltCustomer()))
                    crmMillSheetSplitInfoList.remove(i);
            }

        }

        //拿取重复元素到list然后拆分
        if (noListMillSheetNo.size() > 0) {
            Map<String, List<CrmMillSheetSplitInfo>> aMap = new HashMap<String, List<CrmMillSheetSplitInfo>>();
            for (CrmMillSheetSplitInfo crmMillSheetSplitInfo : noListMillSheetNo) {
                if (aMap.containsKey(crmMillSheetSplitInfo.getMillsheetNo())) {
                    List<CrmMillSheetSplitInfo> rList = aMap.get(crmMillSheetSplitInfo.getMillsheetNo());
                    rList.add(crmMillSheetSplitInfo);
                } else {
                    List<CrmMillSheetSplitInfo> rList = new ArrayList<CrmMillSheetSplitInfo>();
                    rList.add(crmMillSheetSplitInfo);
                    aMap.put(crmMillSheetSplitInfo.getMillsheetNo(), rList);
                }
            }
            Map<String, List<CrmMillSheetSplitInfo>> mm = new HashMap<String, List<CrmMillSheetSplitInfo>>();
            for (String key : aMap.keySet()) {
                Map<String, List<CrmMillSheetSplitInfo>> m = new HashMap<String, List<CrmMillSheetSplitInfo>>();
                for (CrmMillSheetSplitInfo agreementRecord1 : aMap.get(key)) {
                    if (m.containsKey(agreementRecord1.getSpiltCustomer())) {
                        List<CrmMillSheetSplitInfo> rList = m.get(agreementRecord1.getSpiltCustomer());
                        rList.add(agreementRecord1);
                    } else {
                        List<CrmMillSheetSplitInfo> rList = new ArrayList<CrmMillSheetSplitInfo>();
                        rList.add(agreementRecord1);
                        m.put(agreementRecord1.getSpiltCustomer(), rList);
                    }
                }
                for (String agreementRecord1 : m.keySet()) {
                    UUID uuid = UUID.randomUUID();
                    String str = uuid.toString();
                    String uuidStr = str.replace("-", "");
                    mm.put(uuidStr, m.get(agreementRecord1));
                }
            }
            List<CrmMillSheetSplitInfo> aa = new ArrayList<>();
            for (String agreementRecord1 : mm.keySet()) {
                System.out.println(agreementRecord1 + ":" + mm.get(agreementRecord1));
                CrmMillSheetSplitApplyVO crmMillSheetSplitApply = this.splitInsertNeed(mm.get(agreementRecord1));
                millSheetNos += ',' + crmMillSheetSplitApply.getMillsheetNo();
            }
        }






        //拆分质证书编号和拆分单位不一致的数据
        if (crmMillSheetSplitInfoList.size() > 0) {
            for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
                //查询一下最大质证书编号
                MillSheetHosts millSheetHosts = new MillSheetHosts();
                millSheetHosts.setMillSheetNo(cmssi.getMillsheetNo());
                List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
                /*String mill = "";
                if (url.get(0).getSplitMaxValue() == null) {
                    //质证书编号+00
                    mill = cmssi.getMillsheetNo() + "00";
                } else {
                    mill = url.get(0).getSplitMaxValue();
                }*/

                String mill = "";
                CrmMillSheetSplitInfo cmss1 = new CrmMillSheetSplitInfo();
                cmss1.setMillsheetNo(cmssi.getMillsheetNo());
                cmss1.setStatus("1");
                List<CrmMillSheetSplitInfo> alist = crmMillSheetSplitInfoMapper.findMillSheetNo(cmss1);
                if(alist.size()>0){
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("millsheetNo", cmssi.getMillsheetNo());
                    mill= crmMillSheetSplitInfoMapper.findMillSheetNoMax(map1);
                    System.out.println("拆分最大值"+mill);
                }else {
                    mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
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
                if (millSheetHosts2.get(0).getMillSheetType().equals("M")) {
                    crmMillSheetSplitApply1.setMillsheetType("Z");
                } else if (millSheetHosts2.get(0).getMillSheetType().equals("Z")) {
                    crmMillSheetSplitApply1.setMillsheetType("S");
                }
                crmMillSheetSplitApply1.setCreationTime(new Date());
                MillCoilInfo millCoilInfo = new MillCoilInfo();
                millCoilInfo.setMillSheetNo(cmssi.getMillsheetNo());
                millCoilInfo.setZcharg(cmssi.getZcharg());
                millCoilInfo.setSpecs(cmssi.getSpecs());
                //查询钢卷数据
                List<MillCoilInfo> millCoilInfos = coilInfoMapper.findInfo(millCoilInfo);
                String netWeight = "";
                System.out.println("************Long类型件数" + millCoilInfos.get(0).getZjishu() + "*******************");
                BigDecimal L = new BigDecimal(millCoilInfos.get(0).getZjishu());
                System.out.println("************总件数" + L + "*******************");
                BigDecimal b1 = millCoilInfos.get(0).getZlosmenge();
                System.out.println("************总重量" + b1 + "*******************");
                BigDecimal aa = new BigDecimal(1);
                /*if(a.compareTo(b)==0) 结果是true
                比较大小可以用 a.compareTo(b)
                返回值    -1 小于   0 等于    1 大于*/
                if (millCoilInfos.get(0).getSurplusZjishu().compareTo(aa) == 1) {
                    //四舍五入
                    BigDecimal a = b1.divide(L, 3, BigDecimal.ROUND_HALF_UP);
                    System.out.println("************净量" + a + "*******************");
                    cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                    System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
                } else {
                    //最后一件不四舍五入
                    BigDecimal a = b1.divide(L);
                    System.out.println("************净量" + a + "*******************");
                    cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                    System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
                }
                //售达方
                crmMillSheetSplitApply1.setZkunnr(millCoilInfos.get(0).getZkunnr());
                //产品类别
                crmMillSheetSplitApply1.setZcpmc(millCoilInfos.get(0).getZcpmc());
                crmMillSheetSplitApply1.setSpiltCustomer(cmssi.getSpiltCustomer());
                crmMillSheetSplitApply1.setStatus("1");
                crmMillSheetSplitApply1.setType("1");
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
                crmMillSheetSplitInfo.setZcpmc(millCoilInfos.get(0).getZcpmc());
                crmMillSheetSplitInfo.setSpecs(cmssi.getSpecs());
                crmMillSheetSplitInfo.setZchehao(cmssi.getZchehao());
                crmMillSheetSplitInfo.setZjishu(cmssi.getZjishu());
                crmMillSheetSplitInfo.setZcharg(cmssi.getZcharg());
                crmMillSheetSplitInfo.setStatus("1");
                crmMillSheetSplitInfo.setZlosmenge(cmssi.getZlosmenge());
                crmMillSheetSplitInfo.setType("1");
                crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
                //调用存储过程
                /*this.cunChuAll(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitInfo.getMillsheetType(),
                        cmssi.getZcharg(), cmssi.getZjishu(), cmssi.getZlosmenge(),
                        cmssi.getSpiltCustomer(), cmssi.getZchehao());*/
                this.cunChu(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitInfo.getMillsheetType(),
                        cmssi.getZcharg(),cmssi.getSpecs(), cmssi.getZjishu(), cmssi.getZlosmenge(),
                        cmssi.getSpiltCustomer());
                millSheetNos += ',' + newMillSheetNo;
            }
        }
        crmMillSheetSplitApplyVOS.setCode(1);
        crmMillSheetSplitApplyVOS.setMessage("拆分之后质证书编号为" + millSheetNos.substring(1));
        return crmMillSheetSplitApplyVOS;
    }


    //批导拆分（接收单位一致的拆到一起；接收单位一致车号不一致的分开拆）
    @Override
    public CrmMillSheetSplitApplyVO splitInsertSpecial(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList) {
        String acctName = crmMillSheetSplitApplyVOList.get(0).getAcctName();
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList = BeanCopyUtil.copyList(crmMillSheetSplitApplyVOList, CrmMillSheetSplitInfo.class);
        //重复的板卷号一点错误  板卷只有1件
        Map<String, Object> map = new HashMap<>();
        for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
            if (map.size() > 0) {
                if (map.containsKey(cmssi.getZcharg())) {
                    //板卷号重复
                    crmMillSheetSplitApplyVOS.setCode(-1);
                    crmMillSheetSplitApplyVOS.setMessage("excel中板卷号" + cmssi.getZcharg() + "数据重复，件数均为1件");
                    return crmMillSheetSplitApplyVOS;
                } else {
                    map.put(cmssi.getZcharg(), "1");
                }
            } else {
                map.put(cmssi.getZcharg(), "1");
            }
        }

        String findExist = "";
        String findAllow = "";
        String findVolume = "";
        String findNum = "";
        String findType = "";
        String findZcharg = "";
        for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
            //插入batchSplit表
            CrmBatchSplit crmBatchSplit = new CrmBatchSplit();
            crmBatchSplit.setCreatedBy(acctName);
            crmBatchSplit.setCreatedDt(new Date());
            crmBatchSplit.setSpiltCustomer(cmssi.getSpiltCustomer());
            crmBatchSplit.setZcharg(cmssi.getZcharg());
            crmBatchSplitMapper.insertSelective(crmBatchSplit);

            //判断是否存在此板卷号
            MillCoilInfo gg = new MillCoilInfo();
            gg.setZcharg(cmssi.getZcharg());
            List<MillCoilInfo> zchrags = coilInfoMapper.findVolumeNeed(gg);
            if (zchrags.size() > 0) {
                if (zchrags.size() > 1) {
                    //数据唯一
                    findZcharg += "," + zchrags.get(0).getZcharg() + "卷，数据错误。联系管理员处理！";
                } else {
                    //赋值
                    cmssi.setMillsheetNo(zchrags.get(0).getMillsheetNo());
                    cmssi.setZjishu((long) 1);
                    cmssi.setSpecs(zchrags.get(0).getSpecs());
                    //开始校验
                    MillSheetHosts millSheetHosts = new MillSheetHosts();
                    millSheetHosts.setMillSheetNo(cmssi.getMillsheetNo());
                    //质证书编号是否允许拆分
                    List<MillSheetHosts> alist = millSheetHostsMapper.findAllow(millSheetHosts);
                    if (alist.size() >0) {
                    } else {
                        findAllow += ",'" +cmssi.getZcharg()+"'卷的'"+ millSheetHosts.getMillSheetNo() + "'质证书状态不允许拆分";
                    }
                    //是否是孙质证书
                    List<MillSheetHosts> blist = millSheetHostsMapper.findType(millSheetHosts);
                    if (blist.size() > 0) {
                    } else {
                        findType += "," + millSheetHosts.getMillSheetNo() + "为孙质证书，不可拆分";
                    }

                    /*
                        如果指定的数与参数相等返回0。如果指定的数小于参数返回 -1。如果指定的数大于参数返回 1  compareTo 大于的意思
                        Integer x = 5;
                        System.out.println(x.compareTo(3));  1
                        System.out.println(x.compareTo(5));  0
                        System.out.println(x.compareTo(8));  -1
                    */
                    //该卷是否有量可拆
                    int i = zchrags.get(0).getSurplusZjishu().compareTo(BigDecimal.ZERO);
                    if (i == 1) {
                    } else if (i == -1) {
                        //小于0
                        findNum += "," + zchrags.get(0).getZcharg() + "'卷可拆数量为0";
                    } else if (i == 0) {
                        //等于0
                        findNum += "," + zchrags.get(0).getZcharg() + "'卷可拆数量为0";
                    }
                }
            } else {
                findZcharg += "," + cmssi.getZcharg() + "卷不存在";
            }

        }
        if(!findZcharg.equals("")){
            crmMillSheetSplitApplyVOS.setCode(-1);
            crmMillSheetSplitApplyVOS.setMessage(findZcharg.substring(1));
            return crmMillSheetSplitApplyVOS;
        }else {
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
        }
        String millSheetNos = "";
        List<CrmMillSheetSplitInfo> noListMillSheetNo = new ArrayList<>();
        List<CrmMillSheetSplitInfo> hh = new ArrayList<>();
        //剔除重复元素
        for (int i = 0; i < crmMillSheetSplitInfoList.size(); i++) {
            CrmMillSheetSplitInfo crmMillSheetSplitInfo = crmMillSheetSplitInfoList.get(i);
            for (int j = i + 1; j < crmMillSheetSplitInfoList.size(); j++) {
                CrmMillSheetSplitInfo crmMillSheetSplitInfo1 = crmMillSheetSplitInfoList.get(j);
                    if (crmMillSheetSplitInfo.getSpiltCustomer().equals(crmMillSheetSplitInfo1.getSpiltCustomer())&&crmMillSheetSplitInfo.getZchehao().equals(crmMillSheetSplitInfo1.getZchehao())) {
                        if (hh.size() > 0) {
                            String aa = "";
                            for (CrmMillSheetSplitInfo gg : hh) {
                                if (gg.getSpiltCustomer().equals(crmMillSheetSplitInfo1.getSpiltCustomer()) && gg.getZchehao().equals(crmMillSheetSplitInfo1.getZchehao())) {
                                    aa = "a";
                                }
                            }
                            if (!aa.equals("")) {
                                //重复数据
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                noListMillSheetNo.add(crmMillSheetSplitInfo3);
                            } else {
                                //可能质证书一样拆分单位不一样；
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo2 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo2.setAcctName(crmMillSheetSplitInfo.getAcctName());
                                crmMillSheetSplitInfo2.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                                crmMillSheetSplitInfo2.setZchehao(crmMillSheetSplitInfo.getZchehao());
                                crmMillSheetSplitInfo2.setZjishu(crmMillSheetSplitInfo.getZjishu());
                                crmMillSheetSplitInfo2.setZcharg(crmMillSheetSplitInfo.getZcharg());
                                crmMillSheetSplitInfo2.setSpecs(crmMillSheetSplitInfo.getSpecs());
                                crmMillSheetSplitInfo2.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                                CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                                crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                                crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                                crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                                crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                                crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                                crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                                crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                                noListMillSheetNo.add(crmMillSheetSplitInfo2);
                                noListMillSheetNo.add(crmMillSheetSplitInfo3);
                                hh.add(crmMillSheetSplitInfo2);
                            }
                        } else {
                            crmMillSheetSplitInfoList.remove(i);
                            crmMillSheetSplitInfoList.remove(i);
                            CrmMillSheetSplitInfo crmMillSheetSplitInfo2 = new CrmMillSheetSplitInfo();
                            crmMillSheetSplitInfo2.setAcctName(crmMillSheetSplitInfo.getAcctName());
                            crmMillSheetSplitInfo2.setMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
                            crmMillSheetSplitInfo2.setZchehao(crmMillSheetSplitInfo.getZchehao());
                            crmMillSheetSplitInfo2.setZjishu(crmMillSheetSplitInfo.getZjishu());
                            crmMillSheetSplitInfo2.setZcharg(crmMillSheetSplitInfo.getZcharg());
                            crmMillSheetSplitInfo2.setSpecs(crmMillSheetSplitInfo.getSpecs());
                            crmMillSheetSplitInfo2.setSpiltCustomer(crmMillSheetSplitInfo.getSpiltCustomer());
                            CrmMillSheetSplitInfo crmMillSheetSplitInfo3 = new CrmMillSheetSplitInfo();
                            crmMillSheetSplitInfo3.setAcctName(crmMillSheetSplitInfo1.getAcctName());
                            crmMillSheetSplitInfo3.setMillsheetNo(crmMillSheetSplitInfo1.getMillsheetNo());
                            crmMillSheetSplitInfo3.setZchehao(crmMillSheetSplitInfo1.getZchehao());
                            crmMillSheetSplitInfo3.setZjishu(crmMillSheetSplitInfo1.getZjishu());
                            crmMillSheetSplitInfo3.setZcharg(crmMillSheetSplitInfo1.getZcharg());
                            crmMillSheetSplitInfo3.setSpecs(crmMillSheetSplitInfo1.getSpecs());
                            crmMillSheetSplitInfo3.setSpiltCustomer(crmMillSheetSplitInfo1.getSpiltCustomer());
                            noListMillSheetNo.add(crmMillSheetSplitInfo2);
                            noListMillSheetNo.add(crmMillSheetSplitInfo3);
                            hh.add(crmMillSheetSplitInfo2);
                        }
                    }
            }
        }

        //去除单位和质证书一致的数据
        for(int i=0;i<crmMillSheetSplitInfoList.size();i++){
            for(int j=0;j<noListMillSheetNo.size();j++){
                if(crmMillSheetSplitInfoList.get(i).getMillsheetNo().equals(noListMillSheetNo.get(j).getMillsheetNo())
                        &&crmMillSheetSplitInfoList.get(i).getSpecs().equals(noListMillSheetNo.get(j).getSpecs())
                        &&crmMillSheetSplitInfoList.get(i).getZcharg().equals(noListMillSheetNo.get(j).getZcharg())
                        &&crmMillSheetSplitInfoList.get(i).getZjishu().equals(noListMillSheetNo.get(j).getZjishu())
                        &&crmMillSheetSplitInfoList.get(i).getZchehao().equals(noListMillSheetNo.get(j).getZchehao())
                        &&crmMillSheetSplitInfoList.get(i).getSpiltCustomer().equals(noListMillSheetNo.get(j).getSpiltCustomer()))
                    crmMillSheetSplitInfoList.remove(i);
            }

        }

        //拿取重复元素到list然后拆分
        if (noListMillSheetNo.size() > 0) {
            Map<String, List<CrmMillSheetSplitInfo>> aMap = new HashMap<String, List<CrmMillSheetSplitInfo>>();
            for (CrmMillSheetSplitInfo crmMillSheetSplitInfo : noListMillSheetNo) {
                if (aMap.containsKey(crmMillSheetSplitInfo.getSpiltCustomer())) {
                    List<CrmMillSheetSplitInfo> rList = aMap.get(crmMillSheetSplitInfo.getSpiltCustomer());
                    rList.add(crmMillSheetSplitInfo);
                } else {
                    List<CrmMillSheetSplitInfo> rList = new ArrayList<CrmMillSheetSplitInfo>();
                    rList.add(crmMillSheetSplitInfo);
                    aMap.put(crmMillSheetSplitInfo.getSpiltCustomer(), rList);
                }
            }
            Map<String, List<CrmMillSheetSplitInfo>> mm = new HashMap<String, List<CrmMillSheetSplitInfo>>();
            for (String key : aMap.keySet()) {
                Map<String, List<CrmMillSheetSplitInfo>> m = new HashMap<String, List<CrmMillSheetSplitInfo>>();
                for (CrmMillSheetSplitInfo agreementRecord1 : aMap.get(key)) {
                    if (m.containsKey(agreementRecord1.getZchehao())) {
                        List<CrmMillSheetSplitInfo> rList = m.get(agreementRecord1.getZchehao());
                        rList.add(agreementRecord1);
                    } else {
                        List<CrmMillSheetSplitInfo> rList = new ArrayList<CrmMillSheetSplitInfo>();
                        rList.add(agreementRecord1);
                        m.put(agreementRecord1.getZchehao(), rList);
                    }
                }
                for (String agreementRecord1 : m.keySet()) {
                    UUID uuid = UUID.randomUUID();
                    String str = uuid.toString();
                    String uuidStr = str.replace("-", "");
                    mm.put(uuidStr, m.get(agreementRecord1));
                }
            }
            List<CrmMillSheetSplitInfo> aa = new ArrayList<>();
            for (String agreementRecord1 : mm.keySet()) {
                System.out.println(agreementRecord1 + ":" + mm.get(agreementRecord1));
                CrmMillSheetSplitApplyVO crmMillSheetSplitApply = this.splitInsertNeed1(mm.get(agreementRecord1));
                millSheetNos += ',' + crmMillSheetSplitApply.getMillsheetNo();
            }
        }

        //拆分质证书编号和拆分单位不一致的数据
        if (crmMillSheetSplitInfoList.size() > 0) {
            for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
                //查询一下最大质证书编号
                MillSheetHosts millSheetHosts = new MillSheetHosts();
                millSheetHosts.setMillSheetNo(cmssi.getMillsheetNo());
                List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
              /*  String mill = "";
                if (url.get(0).getSplitMaxValue() == null) {
                    //质证书编号+00
                    mill = cmssi.getMillsheetNo() + "00";
                } else {
                    mill = url.get(0).getSplitMaxValue();
                }*/
                String mill = "";
                CrmMillSheetSplitInfo cmss1 = new CrmMillSheetSplitInfo();
                cmss1.setMillsheetNo(cmssi.getMillsheetNo());
                cmss1.setStatus("1");
                List<CrmMillSheetSplitInfo> alist = crmMillSheetSplitInfoMapper.findMillSheetNo(cmss1);
                if(alist.size()>0){
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("millsheetNo", cmssi.getMillsheetNo());
                    mill= crmMillSheetSplitInfoMapper.findMillSheetNoMax(map1);
                    System.out.println("拆分最大值"+mill);
                }else {
                    mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
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
                if (millSheetHosts2.get(0).getMillSheetType().equals("M")) {
                    crmMillSheetSplitApply1.setMillsheetType("Z");
                } else if (millSheetHosts2.get(0).getMillSheetType().equals("Z")) {
                    crmMillSheetSplitApply1.setMillsheetType("S");
                }
                crmMillSheetSplitApply1.setCreationTime(new Date());
                MillCoilInfo millCoilInfo = new MillCoilInfo();
                millCoilInfo.setMillSheetNo(cmssi.getMillsheetNo());
                millCoilInfo.setZcharg(cmssi.getZcharg());
                millCoilInfo.setSpecs(cmssi.getSpecs());
                //查询钢卷数据
                List<MillCoilInfo> millCoilInfos = coilInfoMapper.findInfo(millCoilInfo);
                String netWeight = "";
                System.out.println("************Long类型件数" + millCoilInfos.get(0).getZjishu() + "*******************");
                BigDecimal L = new BigDecimal(millCoilInfos.get(0).getZjishu());
                System.out.println("************总件数" + L + "*******************");
                BigDecimal b1 = millCoilInfos.get(0).getZlosmenge();
                System.out.println("************总重量" + b1 + "*******************");
                BigDecimal aa = new BigDecimal(1);
                /*if(a.compareTo(b)==0) 结果是true
                比较大小可以用 a.compareTo(b)
                返回值    -1 小于   0 等于    1 大于*/
                if (millCoilInfos.get(0).getSurplusZjishu().compareTo(aa) == 1) {
                    //四舍五入
                    BigDecimal a = b1.divide(L, 3, BigDecimal.ROUND_HALF_UP);
                    System.out.println("************净量" + a + "*******************");
                    cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                    System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
                } else {
                    //最后一件不四舍五入
                    BigDecimal a = b1.divide(L);
                    System.out.println("************净量" + a + "*******************");
                    cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                    System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
                }
                //售达方
                crmMillSheetSplitApply1.setZkunnr(millCoilInfos.get(0).getZkunnr());
                //产品类别
                crmMillSheetSplitApply1.setZcpmc(millCoilInfos.get(0).getZcpmc());
                crmMillSheetSplitApply1.setSpiltCustomer(cmssi.getSpiltCustomer());
                crmMillSheetSplitApply1.setStatus("1");
                crmMillSheetSplitApply1.setType("2");
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
                crmMillSheetSplitInfo.setZcpmc(millCoilInfos.get(0).getZcpmc());
                crmMillSheetSplitInfo.setSpecs(cmssi.getSpecs());
                crmMillSheetSplitInfo.setZchehao(cmssi.getZchehao());
                crmMillSheetSplitInfo.setZjishu(cmssi.getZjishu());
                crmMillSheetSplitInfo.setZcharg(cmssi.getZcharg());
                crmMillSheetSplitInfo.setStatus("1");
                crmMillSheetSplitInfo.setZlosmenge(cmssi.getZlosmenge());
                crmMillSheetSplitInfo.setType("2");
                crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
                //调用存储过程
                /*this.cunChuAll(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitInfo.getMillsheetType(),
                        cmssi.getZcharg(), cmssi.getZjishu(), cmssi.getZlosmenge(),
                        cmssi.getSpiltCustomer(), cmssi.getZchehao());*/
                this.cunChu(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitInfo.getMillsheetType(),
                        cmssi.getZcharg(),cmssi.getSpecs(),cmssi.getZjishu(), cmssi.getZlosmenge(),
                        cmssi.getSpiltCustomer());
                millSheetNos += ',' + newMillSheetNo;
            }
        }
        crmMillSheetSplitApplyVOS.setCode(1);
        crmMillSheetSplitApplyVOS.setMessage("拆分之后质证书编号为" + millSheetNos.substring(1));
        return crmMillSheetSplitApplyVOS;
    }

    //申请拆分数据插入（质证书和拆分单位一致）
    @Override
    public CrmMillSheetSplitApplyVO splitInsertNeed(List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList) {
        String acctName = crmMillSheetSplitInfoList.get(0).getAcctName();
        //查询一下最大质证书编号
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        millSheetHosts.setMillSheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
        /*String mill = "";
        if (url.get(0).getSplitMaxValue() == null) {
            //质证书编号+00
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
        } else {
            mill = url.get(0).getSplitMaxValue();
        }*/
        String mill = "";
        CrmMillSheetSplitInfo cmss1 = new CrmMillSheetSplitInfo();
        cmss1.setMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        cmss1.setStatus("1");
        List<CrmMillSheetSplitInfo> alist = crmMillSheetSplitInfoMapper.findMillSheetNo(cmss1);
        if(alist.size()>0){
            Map<String, Object> map = new HashMap<>();
            map.put("millsheetNo", crmMillSheetSplitInfoList.get(0).getMillsheetNo());
            mill= crmMillSheetSplitInfoMapper.findMillSheetNoMax(map);
            System.out.println("拆分最大值"+mill);
        }else {
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
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
        crmMillSheetSplitApply1.setFatherMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        //判断类型进行塞值
        MillSheetHosts millSheetHosts1 = new MillSheetHosts();
        millSheetHosts1.setMillSheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        List<MillSheetHosts> millSheetHosts2 = millSheetHostsMapper.findMillSheetType(millSheetHosts1);
        if (millSheetHosts2.get(0).getMillSheetType().equals("M")) {
            crmMillSheetSplitApply1.setMillsheetType("Z");
        } else if (millSheetHosts2.get(0).getMillSheetType().equals("Z")) {
            crmMillSheetSplitApply1.setMillsheetType("S");
        }
        crmMillSheetSplitApply1.setCreationTime(new Date());
        String zkunnr = "";
        String zcpmc = "";
        for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
            MillCoilInfo millCoilInfo = new MillCoilInfo();
            millCoilInfo.setMillSheetNo(cmssi.getMillsheetNo());
            millCoilInfo.setZcharg(cmssi.getZcharg());
            millCoilInfo.setSpecs(cmssi.getSpecs());
            //查询钢卷数据
            List<MillCoilInfo> millCoilInfos = coilInfoMapper.findInfo(millCoilInfo);
            zkunnr = millCoilInfos.get(0).getZkunnr();
            zcpmc = millCoilInfos.get(0).getZcpmc();
            String netWeight = "";
            System.out.println("************Long类型件数" + millCoilInfos.get(0).getZjishu() + "*******************");
            BigDecimal L = new BigDecimal(millCoilInfos.get(0).getZjishu());
            System.out.println("************总件数" + L + "*******************");
            BigDecimal b1 = millCoilInfos.get(0).getZlosmenge();
            System.out.println("************总重量" + b1 + "*******************");
            BigDecimal aa = new BigDecimal(1);
            /*if(a.compareTo(b)==0) 结果是true
              比较大小可以用 a.compareTo(b)
              返回值    -1 小于   0 等于    1 大于*/
            if (millCoilInfos.get(0).getSurplusZjishu().compareTo(aa) == 1) {
                //四舍五入
                BigDecimal a = b1.divide(L, 3, BigDecimal.ROUND_HALF_UP);
                System.out.println("************净量" + a + "*******************");
                cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
            } else {
                //最后一件不四舍五入
                BigDecimal a = b1.divide(L);
                System.out.println("************净量" + a + "*******************");
                cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
            }
        }
        //售达方
        crmMillSheetSplitApply1.setZkunnr(zkunnr);
        //产品类别
        crmMillSheetSplitApply1.setZcpmc(zcpmc);
        crmMillSheetSplitApply1.setSpiltCustomer(crmMillSheetSplitInfoList.get(0).getSpiltCustomer());
        crmMillSheetSplitApply1.setStatus("1");
        crmMillSheetSplitApply1.setType("1");
        crmMillSheetSplitApplyMapper.insertSelective(crmMillSheetSplitApply1);
        //添加明细表数据
        for (CrmMillSheetSplitInfo crmMillSheetSplitInfo : crmMillSheetSplitInfoList) {
            crmMillSheetSplitInfo.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
            crmMillSheetSplitInfo.setFatherMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
            crmMillSheetSplitInfo.setMillsheetNo(newMillSheetNo);
            crmMillSheetSplitInfo.setMillsheetType(crmMillSheetSplitApply1.getMillsheetType());
            crmMillSheetSplitInfo.setZkunnr(crmMillSheetSplitApply1.getZkunnr());
            crmMillSheetSplitInfo.setCreationTime(new Date());
            crmMillSheetSplitInfo.setCreatedDt(new Date());
            crmMillSheetSplitInfo.setCreatedBy(acctName);
            crmMillSheetSplitInfo.setZcpmc(crmMillSheetSplitApply1.getZcpmc());
            crmMillSheetSplitInfo.setSpecs(crmMillSheetSplitInfo.getSpecs());
            crmMillSheetSplitInfo.setZchehao(crmMillSheetSplitInfo.getZchehao());
            crmMillSheetSplitInfo.setStatus("1");
            crmMillSheetSplitInfo.setType("1");
            crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
            //调用存储过程
            Map<String, Object> map = new HashMap<String, Object>();
            /*this.cunChuAll(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitApply1.getMillsheetType(),
                    crmMillSheetSplitInfo.getZcharg(), crmMillSheetSplitInfo.getZjishu(), crmMillSheetSplitInfo.getZlosmenge(),
                    crmMillSheetSplitInfo.getSpiltCustomer(), crmMillSheetSplitInfo.getZchehao());*/
            this.cunChu(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitApply1.getMillsheetType(),
                    crmMillSheetSplitInfo.getZcharg(),crmMillSheetSplitInfo.getSpecs(), crmMillSheetSplitInfo.getZjishu(), crmMillSheetSplitInfo.getZlosmenge(),
                    crmMillSheetSplitInfo.getSpiltCustomer());
        }
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        crmMillSheetSplitApplyVOS.setMillsheetNo(newMillSheetNo);
        return crmMillSheetSplitApplyVOS;
    }


    //板卷类型拆分数据插入（拆分单位和车号）
    @Override
    public CrmMillSheetSplitApplyVO splitInsertNeed1(List<CrmMillSheetSplitInfo> crmMillSheetSplitInfoList) {
        String acctName = crmMillSheetSplitInfoList.get(0).getAcctName();
        //查询一下最大质证书编号
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        millSheetHosts.setMillSheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        List<MillSheetHosts> url = millSheetHostsMapper.findUrlList(millSheetHosts);
        /*String mill = "";
        if (url.get(0).getSplitMaxValue() == null) {
            //质证书编号+00
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
        } else {
            mill = url.get(0).getSplitMaxValue();
        }*/

        String mill = "";
        CrmMillSheetSplitInfo cmss1 = new CrmMillSheetSplitInfo();
        cmss1.setMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        cmss1.setStatus("1");
        List<CrmMillSheetSplitInfo> alist = crmMillSheetSplitInfoMapper.findMillSheetNo(cmss1);
        if(alist.size()>0){
            Map<String, Object> map = new HashMap<>();
            map.put("millsheetNo", crmMillSheetSplitInfoList.get(0).getMillsheetNo());
            mill= crmMillSheetSplitInfoMapper.findMillSheetNoMax(map);
            System.out.println("拆分最大值"+mill);
        }else {
            mill = crmMillSheetSplitInfoList.get(0).getMillsheetNo() + "00";
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
        crmMillSheetSplitApply1.setFatherMillsheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        //判断类型进行塞值
        MillSheetHosts millSheetHosts1 = new MillSheetHosts();
        millSheetHosts1.setMillSheetNo(crmMillSheetSplitInfoList.get(0).getMillsheetNo());
        List<MillSheetHosts> millSheetHosts2 = millSheetHostsMapper.findMillSheetType(millSheetHosts1);
        if (millSheetHosts2.get(0).getMillSheetType().equals("M")) {
            crmMillSheetSplitApply1.setMillsheetType("Z");
        } else if (millSheetHosts2.get(0).getMillSheetType().equals("Z")) {
            crmMillSheetSplitApply1.setMillsheetType("S");
        }
        crmMillSheetSplitApply1.setCreationTime(new Date());
        String zkunnr = "";
        String zcpmc = "";
        for (CrmMillSheetSplitInfo cmssi : crmMillSheetSplitInfoList) {
            MillCoilInfo millCoilInfo = new MillCoilInfo();
            millCoilInfo.setMillSheetNo(cmssi.getMillsheetNo());
            millCoilInfo.setZcharg(cmssi.getZcharg());
            millCoilInfo.setSpecs(cmssi.getSpecs());
            //查询钢卷数据
            List<MillCoilInfo> millCoilInfos = coilInfoMapper.findInfo(millCoilInfo);
            zkunnr = millCoilInfos.get(0).getZkunnr();
            zcpmc = millCoilInfos.get(0).getZcpmc();
            String netWeight = "";
            System.out.println("************Long类型件数" + millCoilInfos.get(0).getZjishu() + "*******************");
            BigDecimal L = new BigDecimal(millCoilInfos.get(0).getZjishu());
            System.out.println("************总件数" + L + "*******************");
            BigDecimal b1 = millCoilInfos.get(0).getZlosmenge();
            System.out.println("************总重量" + b1 + "*******************");
            BigDecimal aa = new BigDecimal(1);
            /*if(a.compareTo(b)==0) 结果是true
              比较大小可以用 a.compareTo(b)
              返回值    -1 小于   0 等于    1 大于*/
            if (millCoilInfos.get(0).getSurplusZjishu().compareTo(aa) == 1) {
                //四舍五入
                BigDecimal a = b1.divide(L, 3, BigDecimal.ROUND_HALF_UP);
                System.out.println("************净量" + a + "*******************");
                cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
            } else {
                //最后一件不四舍五入
                BigDecimal a = b1.divide(L);
                System.out.println("************净量" + a + "*******************");
                cmssi.setZlosmenge(a.multiply(new BigDecimal(cmssi.getZjishu())));
                System.out.println("*****净重乘件数*******+" + a.multiply(new BigDecimal(cmssi.getZjishu())) + "*******************");
            }
        }
        //售达方
        crmMillSheetSplitApply1.setZkunnr(zkunnr);
        //产品类别
        crmMillSheetSplitApply1.setZcpmc(zcpmc);
        crmMillSheetSplitApply1.setSpiltCustomer(crmMillSheetSplitInfoList.get(0).getSpiltCustomer());
        crmMillSheetSplitApply1.setStatus("1");
        crmMillSheetSplitApply1.setType("2");
        crmMillSheetSplitApplyMapper.insertSelective(crmMillSheetSplitApply1);
        //添加明细表数据
        for (CrmMillSheetSplitInfo crmMillSheetSplitInfo : crmMillSheetSplitInfoList) {
            crmMillSheetSplitInfo.setSplitApplyId(crmMillSheetSplitApply1.getSplitApplyId());
            crmMillSheetSplitInfo.setFatherMillsheetNo(crmMillSheetSplitInfo.getMillsheetNo());
            crmMillSheetSplitInfo.setMillsheetNo(newMillSheetNo);
            crmMillSheetSplitInfo.setMillsheetType(crmMillSheetSplitApply1.getMillsheetType());
            crmMillSheetSplitInfo.setZkunnr(crmMillSheetSplitApply1.getZkunnr());
            crmMillSheetSplitInfo.setCreationTime(new Date());
            crmMillSheetSplitInfo.setCreatedDt(new Date());
            crmMillSheetSplitInfo.setCreatedBy(acctName);
            crmMillSheetSplitInfo.setZcpmc(crmMillSheetSplitApply1.getZcpmc());
            crmMillSheetSplitInfo.setSpecs(crmMillSheetSplitInfo.getSpecs());
            crmMillSheetSplitInfo.setZchehao(crmMillSheetSplitInfo.getZchehao());
            crmMillSheetSplitInfo.setStatus("1");
            crmMillSheetSplitInfo.setType("2");
            crmMillSheetSplitInfoMapper.insertSelective(crmMillSheetSplitInfo);
            //调用存储过程
            Map<String, Object> map = new HashMap<String, Object>();
            /*this.cunChuAll(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitApply1.getMillsheetType(),
                    crmMillSheetSplitInfo.getZcharg(), crmMillSheetSplitInfo.getZjishu(), crmMillSheetSplitInfo.getZlosmenge(),
                    crmMillSheetSplitInfo.getSpiltCustomer(), crmMillSheetSplitInfo.getZchehao());*/
            this.cunChu(crmMillSheetSplitInfo.getFatherMillsheetNo(), newMillSheetNo, crmMillSheetSplitApply1.getMillsheetType(),
                    crmMillSheetSplitInfo.getZcharg(),crmMillSheetSplitInfo.getSpecs(), crmMillSheetSplitInfo.getZjishu(), crmMillSheetSplitInfo.getZlosmenge(),
                    crmMillSheetSplitInfo.getSpiltCustomer());
        }
        CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVOS = new CrmMillSheetSplitApplyVO();
        crmMillSheetSplitApplyVOS.setMillsheetNo(newMillSheetNo);
        return crmMillSheetSplitApplyVOS;
    }


    //调用存储过程（无车号）
    void cunChu(String inmillSheetNoOld, String inmillSheetNo, String inmillSheetType, String inzcharg,String inspecs,Long inzjishu, BigDecimal inzlosmenge, String inspiltCustomer) {
        String sql = "{call PRO_MILL_SPILT(?,?,?,?,?,?,?,?)}";
        Connection conn = null;
        //CallableStatement是用于执行SQL存储过程的接口
        CallableStatement call = null;
        try {
            conn = JDBCUtils.getConnection();
            call = conn.prepareCall(sql);
            //赋值
            call.setString(1, inmillSheetNoOld);
            call.setString(2, inmillSheetNo);
            call.setString(3, inmillSheetType);
            call.setString(4, inzcharg);
            call.setString(5, inspecs);
            call.setLong(6, inzjishu);
            call.setBigDecimal(7, inzlosmenge);
            call.setString(8, inspiltCustomer);
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
        } finally {
            JDBCUtils.release(conn, call, null);
        }

    }


    //调用存储过程（有车号）
    /*void cunChuAll(String inmillSheetNoOld, String inmillSheetNo, String inmillSheetType, String inzcharg, Long inzjishu, BigDecimal inzlosmenge, String inspiltCustomer, String inzchehao) {
        String sql = "{call PRO_MILL_SPILT_ALL(?,?,?,?,?,?,?,?)}";
        Connection conn = null;
        //CallableStatement是用于执行SQL存储过程的接口
        CallableStatement call = null;
        try {
            conn = JDBCUtils.getConnection();
            call = conn.prepareCall(sql);
            //赋值
            call.setString(1, inmillSheetNoOld);
            call.setString(2, inmillSheetNo);
            call.setString(3, inmillSheetType);
            call.setString(4, inzcharg);
            //call.setInt(5, inzjishu);
            call.setLong(5, inzjishu);
            //call.setInt(6, inzlosmenge);
            call.setBigDecimal(6, inzlosmenge);
            call.setString(7, inspiltCustomer);
            call.setString(8, inzchehao);
            //对于out参数，申明
            //call.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
            //call.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
            //call.registerOutParameter(4, oracle.jdbc.OracleTypes.VARCHAR);

            //调用
            call.execute();

            //取出结果
           *//* String name = call.getString(2);
            double sal = call.getDouble(3);
            String job = call.getString(4);

            System.out.println("工号为1110的员工信息:");

            System.out.println("姓名:"+name);
            System.out.println("薪水:"+sal);
            System.out.println("职位:"+job);*//*

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, call, null);
        }

    }*/


    /**
     * Object转BigDecimal类型-王雷-2018年5月14日09:56:26
     *
     * @param value 要转的object类型
     * @return 转成的BigDecimal类型数据
     */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }


    /**
     * 此方法实现JDBCTemplate
     * 返回的Map集合对数据的自动
     * 封装功能
     * List集合存储着一系列的MAP
     * 对象，obj为一个javaBean
     *
     * @param //listMap集合
     * @param //objjavaBean对象
     * @return
     */
    public List parse(List list, Class obj) {
        //生成集合
        ArrayList ary = new ArrayList();
        //遍历集合中的所有数据
        for (int i = 0; i < list.size(); i++) {
            try {
                ////生成对象实历 将MAP中的所有参数封装到对象中
                Object o = this.addProperty((Map) list.get(i), obj.newInstance());
                //把对象加入到集合中
                ary.add(o);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //返回封装好的集合
        return list;
    }


    /**
     * Map对象中的值为 name=aaa,value=bbb
     * 调用方法
     * addProperty(map,user);
     * 将自动将map中的值赋给user类
     * 此方法结合Spring框架的jdbcTemplete将非
     * 常有用
     *
     * @param //map存储着名称和值集合
     * @param //obj要封装的对象
     * @return封装好的对象
     */
    public Object addProperty(Map map, Object obj) {
        //遍历所有名称
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            //取得名称
            String name = it.next().toString();
            //取得值
            String value = map.get(name).toString();

            try {
                //取得值的类形
                Class type = PropertyUtils.getPropertyType(obj, name);

                if (type != null) {
                    //设置参数
                    PropertyUtils.setProperty(obj, name, ConvertUtils.convert(value, type));

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return obj;

    }

}
