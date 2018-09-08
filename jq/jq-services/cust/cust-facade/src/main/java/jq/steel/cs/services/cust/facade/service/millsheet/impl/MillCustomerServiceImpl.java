package jq.steel.cs.services.cust.facade.service.millsheet.impl;

import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.MillCustomerVO;
import jq.steel.cs.services.cust.facade.dao.MillCustomerMapper;
import jq.steel.cs.services.cust.facade.model.MillCustomer;
import jq.steel.cs.services.cust.facade.service.millsheet.MillCustomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MillCustomerServiceImpl implements MillCustomeService{

    @Autowired
    private MillCustomerMapper millCustomerMapper;

    public List<MillCustomerVO> findList(){
        MillCustomerVO millCustomerVO = new MillCustomerVO();
        List<MillCustomer> customers = millCustomerMapper.findList();
        List<MillCustomerVO> customerVOS = BeanCopyUtil.copyList(customers,MillCustomerVO.class);
        return customerVOS;
    }

}
