package jq.steel.cs.services.cust.facade.service.category;

import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.cust.api.vo.CrmProductCategoryVO;

import java.util.List;

/**
 * @ClassName: CrmProductCategoryService
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/25 10:04
 */
public interface CrmProductCategoryService {

    /**
     * @param:  crmProductCategoryVO
     * @return:  ServiceResponse<PageDTO<CrmProductCategoryVO>>
     * @description:  产品分类分页查询
     * @author: lirunze
     * @Date: 2018/8/25
     */
    List<CrmProductCategoryVO> getPage(CrmProductCategoryVO crmProductCategoryVO);

    /**
     * @param: list
     * @return:
     * @description: 保存or提交产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> insertCrmProductCategory(List<CrmProductCategoryVO> list);

    /**
     * @param: crmProductCategoryVO
     * @return:
     * @description: 产品分类列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    List<CrmProductCategoryVO> getList();

    List<CrmProductCategoryVO> getIntroductList(CrmProductCategoryVO crmProductCategoryVO);
}
