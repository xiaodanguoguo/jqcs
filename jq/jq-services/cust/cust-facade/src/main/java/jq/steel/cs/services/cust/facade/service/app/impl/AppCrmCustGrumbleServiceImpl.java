package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.DateFormatUtil;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import jq.steel.cs.services.cust.facade.dao.CrmCustGrumbleMapper;
import jq.steel.cs.services.cust.facade.model.CrmCustGrumble;
import jq.steel.cs.services.cust.facade.service.app.AppCrmCustGrumbleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: AppCrmCustGrumbleServiceImpl
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 13:44
 */
@Service("appCrmCustGrumbleService")
public class AppCrmCustGrumbleServiceImpl implements AppCrmCustGrumbleService {

    @Autowired
    private CrmCustGrumbleMapper crmCustGrumbleMapper;

    @Override
    public Integer addCrmCustGrumble(CrmCustGrumbleVO crmCustGrumbleVO) {
        CrmCustGrumble crmCustGrumble = new CrmCustGrumble();
        BeanCopyUtil.copy(crmCustGrumbleVO, crmCustGrumble);
        return crmCustGrumbleMapper.insertSelective(crmCustGrumble);
    }

    @Override
    public Integer delete(CrmCustGrumbleVO crmCustGrumbleVO) {
        CrmCustGrumble crmCustGrumble = new CrmCustGrumble();
        BeanCopyUtil.copy(crmCustGrumbleVO, crmCustGrumble);
        return crmCustGrumbleMapper.deleteByPrimaryKey(crmCustGrumble.getCid());
    }

    @Override
    public Integer update(CrmCustGrumbleVO crmCustGrumbleVO) {
        CrmCustGrumble crmCustGrumble = new CrmCustGrumble();
        BeanCopyUtil.copy(crmCustGrumbleVO, crmCustGrumble);
        return crmCustGrumbleMapper.updateByPrimaryKeySelective(crmCustGrumble);
    }

    @Override
    public PageDTO<CrmCustGrumbleVO> findByPage(CrmCustGrumbleVO crmCustGrumbleVO) {
        try {
            CrmCustGrumble crmCustGrumble = new CrmCustGrumble();
            BeanCopyUtil.copy(crmCustGrumbleVO, crmCustGrumble);
            if (crmCustGrumble.getFactory() != null && crmCustGrumble.getFactory() != "") {
                crmCustGrumble.setFactorys(null);
            }
            crmCustGrumble.setCustomer(crmCustGrumbleVO.getOrgName());
            //如果orgType为5为厂级领导 设置customer为null
            if (crmCustGrumble.getOrgType().equals("5")) {
                crmCustGrumble.setCustomer(null);
            }
            PageDTOUtil.startPage(crmCustGrumbleVO);
            String startDtStr = DateFormatUtil.getStartDateStr(crmCustGrumble.getStartDt());
            crmCustGrumble.setStartDtStr(startDtStr);
            String endDtStr = DateFormatUtil.getEndDateStr(crmCustGrumble.getEndDt());
            crmCustGrumble.setEndDtStr(endDtStr);
            List<CrmCustGrumble> crmCustGrumbles = crmCustGrumbleMapper.findByPage(crmCustGrumble);
            List<CrmCustGrumbleVO> crmCustGrumbleVOS = BeanCopyUtil.copyList(crmCustGrumbles, CrmCustGrumbleVO.class);
            PageDTO<CrmCustGrumbleVO> transform = PageDTOUtil.transform(crmCustGrumbleVOS);
            return transform;
        } finally {
            PageDTOUtil.endPage();
        }
    }
}
