package jq.steel.cs.services.cust.facade.service.app.impl;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import jq.steel.cs.services.cust.facade.dao.CrmCustGrumbleMapper;
import jq.steel.cs.services.cust.facade.model.CrmCustGrumble;
import jq.steel.cs.services.cust.facade.service.app.AppCrmCustGrumbleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
