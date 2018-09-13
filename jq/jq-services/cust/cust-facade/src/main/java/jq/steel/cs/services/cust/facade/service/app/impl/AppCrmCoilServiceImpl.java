package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillChemistryData;
import jq.steel.cs.services.cust.facade.model.CrmMillCoilInfo;
import jq.steel.cs.services.cust.facade.model.CrmMillPhysicsData;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.service.app.AppCrmCoilService;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            CrmMillCoilInfo crmMillCoilInfo = coilInfoMap.get(coilInfo.getZcharg());
            coilInfo.setListForMillPhysicsData(crmMillCoilInfo.getListForMillPhysicsData());
        }

        List<CrmMillCoilInfoVO> listVO = BeanCopyUtil.copyList(coilAndChemistryInfo, CrmMillCoilInfoVO.class);

        return listVO;
    }
}
