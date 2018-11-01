package jq.steel.cs.services.cust.facade.service.app;

import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;

public interface AppMillSheetHeadService {
    /**
     * 通过质证书编号查质证书信息<关联了异议表>
     * @param millSheetNo
     * @return
     */
    MillSheetHeadVO getSheetHeadByMillSheetNo(String millSheetNo);

    /**
     * 根据卷号查询卷的相关信息
     * @param zcharg
     * @return
     */
    MillCoilInfoVO getCoilByZcharg(MillCoilInfoVO vo);

    /**
     * 通过质证书编号查质证书信息<单表>
     * @param millSheetNo
     * @return
     */
    MillSheetHeadVO getByMillSheetNOWithCreateOrUpdate(String millSheetNo);
}
