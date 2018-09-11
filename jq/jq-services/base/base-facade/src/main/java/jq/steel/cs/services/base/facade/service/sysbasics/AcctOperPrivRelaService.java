package jq.steel.cs.services.base.facade.service.sysbasics;


import jq.steel.cs.services.base.api.vo.AcctOperPrivRelaVO;

/**
 * @Auther: zhaoyuhang
 */
public interface AcctOperPrivRelaService {

    Integer delAcctOperPrivRela(AcctOperPrivRelaVO jsonRequest);

    Integer addAcctOperPrivRela(AcctOperPrivRelaVO jsonRequest);
}
