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

import java.util.Date;
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
        crmCustGrumble.setCreateDt(new Date());
        //  线/棒/板材 2000(炼轧)      不锈钢  1000    碳钢钢带  2200
        if(crmCustGrumble.getCategoryName().equals("线/棒/板材")){
            crmCustGrumble.setFactory("2000");
        }else if(crmCustGrumble.getCategoryName().equals("不锈钢")){
            crmCustGrumble.setFactory("1000");
        }else if(crmCustGrumble.getCategoryName().equals("碳钢钢带")){
            crmCustGrumble.setFactory("2200");
        }
        crmCustGrumble.setState("待反馈");
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
        crmCustGrumble.setState("已反馈");
        return crmCustGrumbleMapper.updateByPrimaryKeySelective(crmCustGrumble);
    }


    //查看过后就是已反馈
    @Override
    public Integer updateState(CrmCustGrumbleVO crmCustGrumbleVO) {
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
        List<CrmCustGrumble> crmCustGrumbles = crmCustGrumbleMapper.findByPage(crmCustGrumble);
        for(CrmCustGrumble crmCustGrumble1:crmCustGrumbles){
            CrmCustGrumble crmCustGrumble2 = new CrmCustGrumble();
            crmCustGrumble2.setCid(crmCustGrumble1.getCid());
            crmCustGrumble2.setIsLook("1");
            crmCustGrumbleMapper.updateState(crmCustGrumble2);
        }

        return 1;
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
            if (crmCustGrumble.getOrgType()!=null){
                if (crmCustGrumble.getOrgType().equals("5")) {
                    crmCustGrumble.setCustomer(null);
                }
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
