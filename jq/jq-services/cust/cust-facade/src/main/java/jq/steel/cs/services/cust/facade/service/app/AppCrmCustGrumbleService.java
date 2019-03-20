package jq.steel.cs.services.cust.facade.service.app;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;

import java.util.List;

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


    //删除
    Integer delete(CrmCustGrumbleVO crmCustGrumbleVO);

    //修改
    Integer update(CrmCustGrumbleVO crmCustGrumbleVO);

    //修改
    Integer updateState(CrmCustGrumbleVO crmCustGrumbleVO);


    //查询
    PageDTO<CrmCustGrumbleVO> findByPage(CrmCustGrumbleVO crmCustGrumbleVO );
}
