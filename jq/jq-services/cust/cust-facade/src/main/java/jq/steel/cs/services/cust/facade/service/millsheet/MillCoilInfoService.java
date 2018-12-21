package jq.steel.cs.services.cust.facade.service.millsheet;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;

import java.util.List;

public interface MillCoilInfoService {
    //拆分申请（强制拆分）数据查询
    PageDTO<MillCoilInfoVO> splitFind(MillCoilInfoVO millSheetHostsVO);

    //查询是否有质证书编号
    MillCoilInfoVO findIsTrue(MillCoilInfoVO millCoilInfoVO);


    //根据当前用户中的orgCode查询出对应的钢卷并分页
    PageDTO<MillCoilInfoVO>  getCoilsByCurrentUser(String orgCode, MillCoilInfoVO vo);

    List<CrmMillCoilInfoVO> getCoilDetail(CrmMillCoilInfoVO reqBody);
}
