package jq.steel.cs.services.cust.facade.service.app;

import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;

/**
 * @ClassName: AppCrmCustGrumbleService
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 13:37
 */
public interface AppCrmCustGrumbleService {

    /**
     * @param:
     * @return:
     * @description:  添加客户抱怨
     * @author: lirunze
     * @Date: 2018/9/7
     */
    Integer addCrmCustGrumble(CrmCustGrumbleVO crmCustGrumbleVO);
}
