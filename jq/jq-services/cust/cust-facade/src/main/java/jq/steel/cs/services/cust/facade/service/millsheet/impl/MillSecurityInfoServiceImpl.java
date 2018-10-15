package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.AssertContext;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.MillSecurityInfoVO;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetCheckLogMapper;
import jq.steel.cs.services.cust.facade.dao.MillSecurityInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetCheckLog;
import jq.steel.cs.services.cust.facade.model.MillSecurityInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSecurityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
//import com.esa2000.PfxSignShell;

@Service
public class MillSecurityInfoServiceImpl implements MillSecurityInfoService {

    @Autowired
    private MillSecurityInfoMapper millSecurityInfoMapper;
    @Autowired
    private MillSheetHostsMapper millSheetHostsMapper;
    @Autowired
    private CrmMillSheetCheckLogMapper crmMillSheetCheckLogMapper;

    @Override
    public MillSecurityInfoVO fangWeiMa(MillSecurityInfoVO millSecurityInfoVO,HttpServletRequest request) {
        String orgCode = millSecurityInfoVO.getOrgCode();
        //转换mdel
        MillSheetHosts millSheetHosts = new MillSheetHosts();
        BeanCopyUtil.copy(millSecurityInfoVO,millSheetHosts);
        MillSecurityInfo millSecurityInfo = new MillSecurityInfo();
        BeanCopyUtil.copy(millSecurityInfoVO,millSecurityInfo);
        List<MillSheetHosts> millSheetByPage = millSheetHostsMapper.findList(millSheetHosts);
        //日志表
        CrmMillSheetCheckLog crmMillSheetCheckLog = new CrmMillSheetCheckLog();
        crmMillSheetCheckLog.setCheckDt(new Date());
        crmMillSheetCheckLog.setType((short) 1);
        crmMillSheetCheckLog.setSecurityCode(millSecurityInfoVO.getSecurityCode());
        crmMillSheetCheckLog.setMillSheetNo(millSecurityInfoVO.getMillSheetNo());
        String ip=request.getRemoteAddr();
        crmMillSheetCheckLog.setIpAddr(ip);
        crmMillSheetCheckLog.setVerifier(orgCode);
        crmMillSheetCheckLogMapper.insertSelective(crmMillSheetCheckLog);
        if (millSheetByPage.size()>0){
            List<MillSecurityInfo> millSecurityInfos = millSecurityInfoMapper.findByParams(millSecurityInfo);
            if (millSecurityInfos.size()>0){
                int coCheckNum = millSecurityInfos.get(0).getCoCheckNum()+1;
                int checkNum = millSecurityInfos.get(0).getCheckNum()+1;
                //判断是否登录来区别首页验证与系统内验证
                if(orgCode!=null){
                    if(millSecurityInfos.get(0).getCoCheckNum()>=millSecurityInfos.get(0).getCoCheckNumMax()){
                        millSecurityInfoVO.setExplain("此质证书超过验证次数");
                    }else{
                        millSecurityInfo.setUpdatedBy(AssertContext.getAcctName());
                        millSecurityInfo.setUpdatedDt(new Date());
                        millSecurityInfo.setCoCheckNum((short) coCheckNum);
                        millSecurityInfoMapper.updateByPrimaryKeySelective(millSecurityInfo);
                        millSecurityInfoVO.setExplain("此质证书"+millSecurityInfos.get(0).getMillSheetNo()+"已成功验证"+coCheckNum+"次");
                    }
                }else {
                    if(millSecurityInfos.get(0).getCheckNum()>=millSecurityInfos.get(0).getCheckNumMax()){
                        millSecurityInfoVO.setExplain("此质证书超过验证次数");
                    }else{
                        millSecurityInfo.setUpdatedBy(AssertContext.getAcctName());
                        millSecurityInfo.setUpdatedDt(new Date());
                        millSecurityInfo.setCheckNum((short) checkNum);
                        millSecurityInfoMapper.updateByPrimaryKeySelective(millSecurityInfo);
                        millSecurityInfoVO.setExplain("此质证书"+millSecurityInfos.get(0).getMillSheetNo()+"已成功验证"+checkNum+"次");
                    }
                }

            }else {
                millSecurityInfoVO.setExplain("此质证书防伪码"+millSecurityInfoVO.getSecurityCode()+"有误");
            }
        }else {
            millSecurityInfoVO.setExplain("此质证书编号"+millSecurityInfoVO.getMillSheetNo()+"有误");
        }
        //MillSecurityInfoVO millSecurityInfoVO1 = new MillSecurityInfoVO();
       // millSecurityInfoVO1.setExplain(millSecurityInfo.getExplain());
        return millSecurityInfoVO;
    }


    @Override
    public MillSecurityInfoVO fuJian(MillSecurityInfoVO millSecurityInfoVO, HttpServletRequest request) {
        MillSecurityInfo millSecurityInfo = new MillSecurityInfo();
        BeanCopyUtil.copy(millSecurityInfoVO,millSecurityInfo);
       /* PfxSignShell signShell = new PfxSignShell(); // 验证PDF文件内的签名是否有效
        String url = millSecurityInfo.getFileUrl();
        if(signShell.verifySign(url)){
            signShell.close();
            boolean success = deleteDir(new File(millSecurityInfo.getFileUrl()));
            if (success) {
                System.out.println("Successfully deleted populated directory: " + millSecurityInfo.getFileUrl());
            } else {
                System.out.println("Failed to delete populated directory: " + millSecurityInfo.getFileUrl());
            }
            millSecurityInfo.setExplain("文档内签名有效");
        }else {
            millSecurityInfo.setExplain("文档内签名被篡改");
        }*/
        BeanCopyUtil.copy(millSecurityInfo,millSecurityInfoVO);
        return millSecurityInfoVO;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir
     *            将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     *         deletion fails, the method stops attempting to delete and returns
     *         "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
