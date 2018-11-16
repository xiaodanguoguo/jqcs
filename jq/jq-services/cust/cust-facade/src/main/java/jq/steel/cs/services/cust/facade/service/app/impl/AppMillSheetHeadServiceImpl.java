package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.dao.CrmMillSheetSplitInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHeadMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHostsMapper;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitInfo;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHead;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.app.AppMillSheetHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<MillSheetHostsVO> getSheetHostsMsg(MillCoilInfoVO vo) {
        if(vo.getZcharg() != null && !("".equals(vo.getZcharg().trim()))){
            List<MillSheetHosts> mshs = millSheetHostsMapper.getSheetHostsMsgHaveZcharg(vo);
            List<MillSheetHostsVO> vos = BeanCopyUtil.copyList(mshs, MillSheetHostsVO.class);
            return vos;
        }else {
            List<MillSheetHosts> mshs = millSheetHostsMapper.getSheetHostsMsgNoZcharg(vo);
            List<MillSheetHostsVO> vos = BeanCopyUtil.copyList(mshs, MillSheetHostsVO.class);
            return vos;
        }
    }

    @Override
    public List<CrmMillSheetSplitInfoVO> getMillSheetForSaleCompany(CrmMillSheetSplitInfoVO vo) {

        List<CrmMillSheetSplitInfo> models = crmMillSheetSplitInfoMapper.findMillSheetForSaleCompany(vo);
        List<CrmMillSheetSplitInfoVO> vos = BeanCopyUtil.copyList(models, CrmMillSheetSplitInfoVO.class);
        return vos;
    }
}
