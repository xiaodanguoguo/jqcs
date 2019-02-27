package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.dao.*;
import jq.steel.cs.services.cust.facade.model.*;
import jq.steel.cs.services.cust.facade.service.app.AppMillSheetHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppMillSheetHeadServiceImpl implements AppMillSheetHeadService {

    @Autowired
    private MillSheetHeadMapper millSheetHeadMapper;
    @Autowired
    private MillCoilInfoMapper millCoilInfoMapper;
    @Autowired
    private MillSheetHostsMapper millSheetHostsMapper;
    @Autowired
    private CrmMillSheetSplitInfoMapper crmMillSheetSplitInfoMapper;
    @Autowired
    private OrgInfoMapper orgInfoMapper;

    public MillSheetHeadVO getSheetHeadByMillSheetNo(String millSheetNo) {
        MillSheetHead millSheetHead = millSheetHeadMapper.selectByMillSheetNO(millSheetNo);
        MillSheetHeadVO vo = BeanCopyUtil.copy(millSheetHead, MillSheetHeadVO.class);
        return vo;
    }

    public MillCoilInfoVO getCoilByZcharg(MillCoilInfoVO vo) {
        MillCoilInfo millCoilInfo = millCoilInfoMapper.selectByZcharg(vo);
        MillCoilInfoVO mcivo = BeanCopyUtil.copy(millCoilInfo, MillCoilInfoVO.class);
        return mcivo;
    }

    public MillSheetHeadVO getByMillSheetNOWithCreateOrUpdate(String millSheetNo) {
        MillSheetHead millSheetHead = millSheetHostsMapper.selectByMillSheetNOWithCreateOrUpdate(millSheetNo);
        MillSheetHeadVO vo = BeanCopyUtil.copy(millSheetHead, MillSheetHeadVO.class);
        return vo;
    }


    @Override
    public PageDTO<MillSheetHostsVO> getSheetHostsMsg(MillCoilInfoVO vo) {
        String orgId = vo.getOrgId();
        String orgName = vo.getOrgName();
        try {
            if (vo.getZcharg() != null && vo.getZcharg() != "") {
                MillCoilInfo coilInfo = new MillCoilInfo();
                coilInfo.setZcharg(vo.getZcharg());
                List<MillCoilInfo> list = millCoilInfoMapper.findMillsheetNumber(coilInfo);
                if (list.size() > 0) {
                    List<String> idall = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        idall.add(list.get(i).getMillsheetNo());
                    }
                    vo.setMillSheetNos(idall);
                } else {
                    List<String> idall = new ArrayList<>();
                    idall.add("-99");
                    vo.setMillSheetNos(idall);
                }
            }
            PageDTOUtil.startPage(vo);
            List<MillSheetHosts> mshs = millSheetHostsMapper.getSheetHostsMsgHaveZcharg(vo);
            List<MillSheetHostsVO> vos = BeanCopyUtil.copyList(mshs, MillSheetHostsVO.class);
            PageDTO<MillSheetHostsVO> transform = PageDTOUtil.transform(vos, MillSheetHostsVO.class);
            for (MillSheetHostsVO millSheetHosts2 : transform.getResultData()) {
                if (millSheetHosts2.getJcFlag() != null) {
                    //判断是否允许下载(建材类不让下载)让打印
                    if (millSheetHosts2.getJcFlag() == 0) {
                        //根据组织获取该用户是否为信任用户
                        OrgInfo orgInfo = new OrgInfo();
                        orgInfo.setId(orgId);
                        List<OrgInfo> list = orgInfoMapper.findIdByCode(orgInfo);
                        if (list.size() > 0) {
                            if (list.get(0).getIndustrialCode() != null) {
                                if (list.get(0).getIndustrialCode().equals("1")) {
                                    //信任
                                    millSheetHosts2.setIsAllowDown("Y");
                                    millSheetHosts2.setIsAllowPrint("Y");
                                } else {
                                    millSheetHosts2.setIsAllowDown("N");
                                    millSheetHosts2.setIsAllowPrint("Y");
                                }
                            }
                        }

                    } else {
                        if (millSheetHosts2.getMillSheetType().equals("Z") || millSheetHosts2.getMillSheetType().equals("S")) {
                            if (millSheetHosts2.getSpiltCustomer().equals(orgName)) {
                                millSheetHosts2.setIsAllowDown("Y");
                                millSheetHosts2.setIsAllowPrint("Y");
                            } else {
                                //查询拆分单位下是否有账号有的话不让下载 没有的话让下载打印
                                OrgInfo orgInfo = new OrgInfo();
                                orgInfo.setOrgName(millSheetHosts2.getSpiltCustomer());
                                List<OrgInfo> list = orgInfoMapper.findIdByOrgName(orgInfo);
                                if (list.size() > 0) {
                                    millSheetHosts2.setIsAllowDown("Y");
                                    millSheetHosts2.setIsAllowPrint("Y");
                                } else {
                                    millSheetHosts2.setIsAllowDown("N");
                                    millSheetHosts2.setIsAllowPrint("N");
                                }
                            }
                        } else {
                            millSheetHosts2.setIsAllowDown("Y");
                            millSheetHosts2.setIsAllowPrint("Y");
                        }

                    }
                }
            }
            return transform;
        } finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public List<CrmMillSheetSplitInfoVO> getMillSheetForSaleCompany(CrmMillSheetSplitInfoVO vo) {

        List<CrmMillSheetSplitInfo> models = crmMillSheetSplitInfoMapper.findMillSheetForSaleCompany(vo);
        List<CrmMillSheetSplitInfoVO> vos = BeanCopyUtil.copyList(models, CrmMillSheetSplitInfoVO.class);
        return vos;
    }
}
