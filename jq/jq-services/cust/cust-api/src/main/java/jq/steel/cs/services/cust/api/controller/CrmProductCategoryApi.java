package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmProductCategoryVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: CrmProductCategoryApi
 * @Description: 产品分类
 * @Author: lirunze
 * @CreateDate: 2018/8/24 15:20
 */
@FeignClient(value = "${ser.name.cust}") //这个是服务名
public interface CrmProductCategoryApi {

    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<PageDTO<CrmProductCategoryVO>>
     * @description:  产品分类查询
     * @author: lirunze
     * @Date: 2018/8/25
     */
    @RequestMapping("/product/category/list")
    ServiceResponse<List<CrmProductCategoryVO>> getPage(@RequestBody JsonRequest<CrmProductCategoryVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 保存产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/category/save")
    ServiceResponse<Boolean> insertCrmProductCategory(@RequestBody JsonRequest<List<CrmProductCategoryVO>> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品分类列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/category/down/list")
    ServiceResponse<List<CrmProductCategoryVO>> getList();

    /**
     * @param: jsonRequest
     * @return:
     * @description: 提交产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/category/submit")
    ServiceResponse<Boolean> submitCrmProductCategory(JsonRequest<List<CrmProductCategoryVO>> jsonRequest);

    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<PageDTO<CrmProductCategoryVO>>
     * @description:  产品分类推介查询
     * @author: lirunze
     * @Date: 2018/8/25
     */
    @RequestMapping("/product/category/introduct/list")
    ServiceResponse<List<CrmProductCategoryVO>> getIntroductList();
}
