package jq.steel.cs.services.cust.facade.service.millsheet;

import jq.steel.cs.services.cust.api.vo.MillSecurityInfoVO;

import javax.servlet.http.HttpServletRequest;

public interface MillSecurityInfoService {

    MillSecurityInfoVO fangWeiMa (MillSecurityInfoVO millSheetHostsVO,HttpServletRequest request);

    MillSecurityInfoVO fangWeiMa1 (MillSecurityInfoVO millSheetHostsVO,HttpServletRequest request);

    MillSecurityInfoVO fuJian (MillSecurityInfoVO millSheetHostsVO,HttpServletRequest request);

}
