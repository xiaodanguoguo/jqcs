package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: CrmProductInfoApi
 * @Description: 产品信息
 * @Author: lirunze
 * @CreateDate: 2018/8/24 15:23
 */
@FeignClient(value = "${ser.name.cust}") //这个是服务名
public interface CrmProductInfoApi {

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/list")
    ServiceResponse<PageDTO<CrmProductInfoVO>> getPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息详情
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/detail")
    ServiceResponse<CrmProductInfoVO> getDetail(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息删除
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/delete")
    ServiceResponse<Boolean> deletePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息发布
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/issue")
    ServiceResponse<Boolean> issuePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息新增
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/add")
    ServiceResponse<Boolean> insertPruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息修改
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/update")
    ServiceResponse<Boolean> updatePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息移动
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/move")
    ServiceResponse<Boolean> movePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/product/info/introduct/list")
    ServiceResponse<PageDTO<CrmProductInfoVO>> getIntroductPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest);
}
