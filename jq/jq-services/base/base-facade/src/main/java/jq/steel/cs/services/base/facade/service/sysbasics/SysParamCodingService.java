package jq.steel.cs.services.base.facade.service.sysbasics;


import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.base.api.vo.SysParamVO;

import java.util.List;

/**
 * @Auther: wangyu
 */
public interface SysParamCodingService {

    PageDTO<SysParamVO> listSysParam(SysParamVO sysParamVO);

    Boolean keepSysParam(List<SysParamVO> sysParamVOs);

    Integer delSysParam(SysParamVO sysParamVO);
}
