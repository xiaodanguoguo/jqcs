package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHead;
import jq.steel.cs.services.cust.facade.service.app.AppCrmCoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AppCrmCoilServiceImpl
 * @Description: 质证书明细查询
 * @Author: lujiawei
 * @CreateDate: 2018/9/13 10:00
 */
@Service
public class AppCrmCoilServiceImpl implements AppCrmCoilService {

    @Autowired
    MillCoilInfoMapper millCoilInfoMapper;

    //质证书明细查询
    public List<CrmMillCoilInfoVO> getListByMillSheet(String millSheetNo, Integer showFlag) {
        CrmMillSheetDetailVO vo1 = new CrmMillSheetDetailVO(millSheetNo, showFlag);
        List<CrmMillCoilInfo> coilAndPhysicsInfo = millCoilInfoMapper.getPhysicsListByMillSheet(vo1);
        List<CrmMillCoilInfo> coilAndChemistryInfo = millCoilInfoMapper.getChemistryListByMillSheet(vo1);

        Map<String, CrmMillCoilInfo> coilInfoMap = new HashMap<String, CrmMillCoilInfo>();
        for (CrmMillCoilInfo coilInfo : coilAndPhysicsInfo) {
            coilInfoMap.put(coilInfo.getZcharg(), coilInfo);
        }

        for (CrmMillCoilInfo coilInfo : coilAndChemistryInfo) {
            if (coilInfoMap.get(coilInfo.getZcharg()) != null) {
                CrmMillCoilInfo crmMillCoilInfo = coilInfoMap.get(coilInfo.getZcharg());
                coilInfo.setListForMillPhysicsData(crmMillCoilInfo.getListForMillPhysicsData());
            }
        }
        List<CrmMillCoilInfoVO> listVO = BeanCopyUtil.copyList(coilAndChemistryInfo, CrmMillCoilInfoVO.class);
        return listVO;
    }

    //扫描二维码,查询质证书信息和质证书下的钢卷信息以及对应的钢卷物理,化学信息
    public List<CrmMillCoilInfoVO> getListByByQrCode(String millSheetNo, Integer showFlag) {
        CrmMillSheetDetailVO vo1 = new CrmMillSheetDetailVO(millSheetNo, showFlag);
        List<CrmMillCoilInfo> coilAndPhysicsInfo = millCoilInfoMapper.getPhysicsListByQrCode(vo1);
        List<CrmMillCoilInfo> coilAndChemistryInfo = millCoilInfoMapper.getChemistryListByByQrCode(vo1);

        List<CrmMillCoilInfoVO> listVO = new ArrayList<>();
        Map<String, CrmMillCoilInfo> coilInfoMap = new HashMap<String, CrmMillCoilInfo>();
        for (CrmMillCoilInfo coilInfo : coilAndPhysicsInfo) {
            coilInfoMap.put(coilInfo.getZcharg(), coilInfo);
        }

        for (int i = 0; i < coilAndChemistryInfo.size(); i++) {
            CrmMillCoilInfo coilInfo = coilAndChemistryInfo.get(i);
            if (coilInfoMap.get(coilInfo.getZcharg()) != null) {
                CrmMillCoilInfo crmMillCoilInfo = coilInfoMap.get(coilInfo.getZcharg());
                coilInfo.setListForMillPhysicsData(crmMillCoilInfo.getListForMillPhysicsData());

                CrmMillCoilInfoVO vo = BeanCopyUtil.copy(coilInfo, CrmMillCoilInfoVO.class);
                MillSheetHead msh1 = coilInfo.getMillSheetHead();
                MillSheetHeadVO msh2 = new MillSheetHeadVO();

                msh2.setMillSheetNo(msh1.getMillSheetNo());
                msh2.setName(msh1.getName());
                msh2.setZkunwe(msh1.getZkunwe());
                msh2.setZcpmc(msh1.getZcpmc());
                msh2.setZph(msh1.getZph());
                msh2.setZzxbz(msh1.getZzxbz());
                msh2.setZchehao(msh1.getZchehao());
                msh2.setZdaozhan(msh1.getZdaozhan());
                msh2.setZjhzt(msh1.getZjhzt());
                vo.setMillSheetHead(msh2);
                listVO.add(vo);
            }
        }
        return listVO;
    }
}
