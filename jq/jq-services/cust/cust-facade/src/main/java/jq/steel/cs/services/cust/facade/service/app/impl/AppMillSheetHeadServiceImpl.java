package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.dao.MillSheetHeadMapper;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.model.MillSheetHead;
import jq.steel.cs.services.cust.facade.service.app.AppMillSheetHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppMillSheetHeadServiceImpl implements AppMillSheetHeadService {

    @Autowired
    private MillSheetHeadMapper millSheetHeadMapper;
    @Autowired
    private MillCoilInfoMapper millCoilInfoMapper;

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
        MillSheetHead millSheetHead = millSheetHeadMapper.selectByMillSheetNOWithCreateOrUpdate(millSheetNo);
        MillSheetHeadVO vo = BeanCopyUtil.copy(millSheetHead, MillSheetHeadVO.class);
        return vo;
    }
}
