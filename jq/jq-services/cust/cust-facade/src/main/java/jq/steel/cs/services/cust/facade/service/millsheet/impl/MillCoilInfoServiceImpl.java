package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.StringUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.service.millsheet.MillCoilInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MillCoilInfoServiceImpl implements MillCoilInfoService{

    @Autowired
    private MillCoilInfoMapper millCoilInfoMapper;

    @Override
    public PageDTO<MillCoilInfoVO> splitFind(MillCoilInfoVO millCoilInfoVO) {
        try {
            //分页改为1000
            millCoilInfoVO.setPageSize(1000);
            //转换mdel
            MillCoilInfo millCoilInfo = new MillCoilInfo();
            BeanCopyUtil.copy(millCoilInfoVO,millCoilInfo);
            PageDTOUtil.startPage(millCoilInfoVO);
            List<MillCoilInfo> millCoilInfos = millCoilInfoMapper.splitFind(millCoilInfo);
            //转换返回对象
            List<MillCoilInfoVO> millCoilInfoVOS = BeanCopyUtil.copyList(millCoilInfos, MillCoilInfoVO.class);
            // 分页对象
            PageDTO<MillCoilInfoVO> transform = PageDTOUtil.transform(millCoilInfoVOS);
            //转换

            return transform;
        }finally {
            PageDTOUtil.endPage();
        }
    }


    //诉赔界面校验批板卷号输入正确与否
    @Override
    public MillCoilInfoVO findIsTrue(MillCoilInfoVO millCoilInfoVO) {
        //转换mdel
        MillCoilInfo millCoilInfo = new MillCoilInfo();
        BeanCopyUtil.copy(millCoilInfoVO,millCoilInfo);
        List<MillCoilInfo> list = millCoilInfoMapper.findIsTrue(millCoilInfo);
        if (list.size()>0){
            millCoilInfo.setTrue(true);
            list.get(0).setTrue(true);
            BeanCopyUtil.copy(list.get(0),millCoilInfoVO);
        }else {
            millCoilInfo.setTrue(false);
            millCoilInfo.setCheckInstructions("请核实批/板/卷号"+millCoilInfoVO.getZcharg());
            BeanCopyUtil.copy(millCoilInfo,millCoilInfoVO);
        }

        return millCoilInfoVO;
    }



    //分页
    public PageDTO<MillCoilInfoVO> getCoilsByCurrentUser(String orgCode, MillCoilInfoVO vo) {
        try {
            //转换mdel
            MillCoilInfo millCoilInfo = new MillCoilInfo();
            BeanCopyUtil.copy(vo, millCoilInfo);
            millCoilInfo.setZkunnr(orgCode);
            PageDTOUtil.startPage(vo);
            List<MillCoilInfo> coilList = millCoilInfoMapper.queryForListByCurrentUser(millCoilInfo);
            //转换返回对象
            List<MillCoilInfoVO> coilVOs = BeanCopyUtil.copyList(coilList, MillCoilInfoVO.class);
            // 分页对象
            PageDTO<MillCoilInfoVO> transform = PageDTOUtil.transform(coilVOs);
            return transform;

        } finally {
            PageDTOUtil.endPage();
        }
    }

    //查询钢卷明细<物理/化学数据>
    public List<CrmMillCoilInfoVO> getCoilDetail(CrmMillCoilInfoVO reqBody) {

        CrmMillCoilInfo crmCoilInfo = new CrmMillCoilInfo();

        BeanCopyUtil.copy(reqBody,crmCoilInfo);

        //物理
        List<CrmMillCoilInfo> coilAndPhysicsInfo = millCoilInfoMapper.getPhysicsInfoListByCoil(crmCoilInfo);
        //化学
        List<CrmMillCoilInfo> coilAndChemistryInfo = millCoilInfoMapper.getChemistryListByCoil(crmCoilInfo);

        Map<String, CrmMillCoilInfo> coilInfoMap = new HashMap<String, CrmMillCoilInfo>();
        for (CrmMillCoilInfo coilInfo : coilAndPhysicsInfo) {
            coilInfoMap.put(coilInfo.getZcharg(), coilInfo);
            coilInfo.getListForMillPhysicsData().forEach(crmMillPhysicsData -> {
                if (StringUtil.isNotEmpty(crmMillPhysicsData.getKurztext())) {
                    crmMillPhysicsData.setKurztext(crmMillPhysicsData.getKurztext()
                        .replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));
                }
                if (StringUtil.isNotEmpty(crmMillPhysicsData.getOriginalInput())) {
                    crmMillPhysicsData.setOriginalInput(crmMillPhysicsData.getOriginalInput()
                            .replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));
                }

            });
        }

        for (CrmMillCoilInfo coilInfo : coilAndChemistryInfo) {
            CrmMillCoilInfo crmMillCoilInfo = coilInfoMap.get(coilInfo.getZcharg());
            coilInfo.setListForMillPhysicsData(crmMillCoilInfo.getListForMillPhysicsData());
            coilInfo.getListForMillChemistryData().forEach(crmMillChemistryData -> {
                if (StringUtil.isNotEmpty(crmMillChemistryData.getKurztext())) {
                    crmMillChemistryData.setKurztext(crmMillChemistryData.getKurztext()
                            .replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));
                }

                if (StringUtil.isNotEmpty(crmMillChemistryData.getOriginal_input())) {
                    crmMillChemistryData.setOriginal_input(crmMillChemistryData.getOriginal_input()
                            .replaceAll("(\\\r\\\n|\\\r|\\\n|\\\n\\\r)", ""));
                }

            });
        }

        List<CrmMillCoilInfoVO> listVO = BeanCopyUtil.copyList(coilAndChemistryInfo, CrmMillCoilInfoVO.class);

        return listVO;

    }
}
