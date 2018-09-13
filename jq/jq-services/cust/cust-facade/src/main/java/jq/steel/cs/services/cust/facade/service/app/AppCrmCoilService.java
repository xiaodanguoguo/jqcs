package jq.steel.cs.services.cust.facade.service.app;

import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;

import java.math.BigDecimal;
import java.util.List;
/**
 * @ClassName:      AppCrmCoilService
 * @Description:    质证书明细查询
 * @Author:         lujiawei
 * @CreateDate:     2018/9/13 10:00
 */
public interface AppCrmCoilService {
    /**
     * 查询质证书明细
     * @return
     */
    List<CrmMillCoilInfoVO> getListByMillSheet(String millSheetNo , Integer showFlag);

}
