package jq.steel.cs.services.cust.facade.service.product;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;

/**
 * @ClassName: CrmProductInfoService
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/25 14:32
 */
public interface CrmProductInfoService {

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    PageDTO<CrmProductInfoVO> getPage(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息详情
     * @author: lirunze
     * @Date: 2018/8/20
     */
    CrmProductInfoVO getDetail(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息删除
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> deletePruduct(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息发布
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> issuePruduct(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息新增
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> insertPruduct(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息修改
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> updatePruduct(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品信息移动
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> movePruduct(CrmProductInfoVO crmProductInfoVO);

    /**
     * @param: crmProductInfoVO
     * @return:
     * @description: 产品推介信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    PageDTO<CrmProductInfoVO> getIntroductPage(CrmProductInfoVO crmProductInfoVO);

    PageDTO<CrmProductInfoVO> getIntroductIndexPage(CrmProductInfoVO crmProductInfoVO);
}
