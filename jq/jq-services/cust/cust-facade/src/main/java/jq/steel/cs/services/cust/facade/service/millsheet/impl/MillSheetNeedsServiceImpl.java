package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.api.vo.MillSheetNeedsVO;
import jq.steel.cs.services.cust.facade.dao.MillSheetNeedsMapper;
import jq.steel.cs.services.cust.facade.model.MillSheetNeeds;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MillSheetNeedsServiceImpl implements MillSheetNeedsService{

    @Autowired
    private MillSheetNeedsMapper millSheetNeedsMapper;

    @Override
    public List<MillSheetNeedsVO> findByType(MillSheetNeedsVO record) {
        MillSheetNeeds millSheetNeeds = new MillSheetNeeds();
        BeanCopyUtil.copy(record,millSheetNeeds);
        List<MillSheetNeeds> millSheetNeedsList =millSheetNeedsMapper.findByType(millSheetNeeds);
        List<MillSheetNeedsVO> millSheetNeedsVOS = BeanCopyUtil.copyList(millSheetNeedsList, MillSheetNeedsVO.class);
        return millSheetNeedsVOS;
    }
}
