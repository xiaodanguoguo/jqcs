package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.dao.MillCoilInfoMapper;
import jq.steel.cs.services.cust.facade.model.MillCoilInfo;
import jq.steel.cs.services.cust.facade.service.millsheet.MillCoilInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MillCoilInfoServiceImpl implements MillCoilInfoService{

    @Autowired
    private MillCoilInfoMapper millCoilInfoMapper;

    @Override
    public PageDTO<MillCoilInfoVO> splitFind(MillCoilInfoVO millCoilInfoVO) {
        try {
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
}
