package jq.steel.cs.webapps.cs.controller.category;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.CrmProductCategoryApi;
import jq.steel.cs.services.cust.api.vo.CrmProductCategoryVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CrmProductCategory
 * @Description: 产品分类
 * @Author: lirunze
 * @CreateDate: 2018/8/20 15:36
 */
@RestController
@RequestMapping("/product/category")
public class CrmProductCategoryController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmProductCategoryApi crmProductCategoryApi;

    @Autowired
    private RoleInfoAPI roleInfoAPI;

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品分类列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    public JsonResponse<Map<String, List<CrmProductCategoryVO>>> getPage(@RequestBody JsonRequest<CrmProductCategoryVO> jsonRequest) {
        logger.info("产品分类列表 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Map<String, List<CrmProductCategoryVO>>> jsonResponse = new JsonResponse<>();

        try {
            List<String> codes = this.getRoleCode();

            if (CollectionUtils.isEmpty(codes)) {
                jsonResponse.setRetCode("0000004");
            }

            jsonRequest.getReqBody().setFactoryCodes(codes);
            ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = crmProductCategoryApi
                    .getPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                Map<String, List<CrmProductCategoryVO>> map = new HashMap<>();
                map.put("resultData", serviceResponse.getRetContent());
                jsonResponse.setRspBody(map);
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        } catch (BusinessException e) {
            logger.error("产品分类列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品分类列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 保存or提交产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> insertCrmProductCategory(@RequestBody JsonRequest<List<CrmProductCategoryVO>> jsonRequest) {
        logger.info("保存产品分类 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();

        try {
            String code = "";
            List<String> codes = this.getRoleCode();

            if (CollectionUtils.isNotEmpty(codes)) {
                code = codes.get(0);
            } else {
                jsonResponse.setRetCode("0000004");
                return jsonResponse;
            }
            List<CrmProductCategoryVO> list = jsonRequest.getReqBody();
            if (CollectionUtils.isNotEmpty(list)) {
                for (CrmProductCategoryVO crmProductCategoryVO : list) {
                    crmProductCategoryVO.setCreateByid(Long.valueOf(AssertContext.getAcctId()));
                    crmProductCategoryVO.setCreateBy(AssertContext.getAcctName());
                    crmProductCategoryVO.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
                    crmProductCategoryVO.setUpdateBy(AssertContext.getAcctName());
                    crmProductCategoryVO.setFactory(code);
                }
            }

            ServiceResponse<Boolean> serviceResponse = crmProductCategoryApi
                    .insertCrmProductCategory(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        } catch (BusinessException e) {
            logger.error("保存产品分类错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("保存产品分类错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 保存or提交产品分类
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/submit"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> submitCrmProductCategory(@RequestBody JsonRequest<List<CrmProductCategoryVO>> jsonRequest) {
        logger.info("保存产品分类 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        String orgId = AssertContext.getOrgId();
        try {
            String code = "";
            List<String> codes = this.getRoleCode();

            if (CollectionUtils.isNotEmpty(codes)) {
                code = codes.get(0);
            } else {
                jsonResponse.setRetCode("0000004");
                return jsonResponse;
            }
            List<CrmProductCategoryVO> list = jsonRequest.getReqBody();
            if (CollectionUtils.isNotEmpty(list)) {
                for (CrmProductCategoryVO crmProductCategoryVO : list) {
                    crmProductCategoryVO.setCreateByid(Long.valueOf(AssertContext.getAcctId()));
                    crmProductCategoryVO.setCreateBy(AssertContext.getAcctName());
                    crmProductCategoryVO.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
                    crmProductCategoryVO.setUpdateBy(AssertContext.getAcctName());

                    //2019-03-22  管理员不修改厂别
                    if(orgId.equals("1001")||orgId.equals("1")){
                    }else {
                        crmProductCategoryVO.setFactory(code);
                    }

                }
            }
            ServiceResponse<Boolean> serviceResponse = crmProductCategoryApi
                    .submitCrmProductCategory(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        } catch (BusinessException e) {
            logger.error("保存产品分类错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("保存产品分类错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品分类列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/down/list"}, method = RequestMethod.POST)
    public JsonResponse<List<CrmProductCategoryVO>> getList() {
        logger.info("产品分类列表");
        JsonResponse<List<CrmProductCategoryVO>> jsonResponse = new JsonResponse<>();

        try {
            List<String> codes = this.getRoleCode();
            if (CollectionUtils.isEmpty(codes)) {
                jsonResponse.setRetCode("0000004");
                return jsonResponse;
            }
            ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = crmProductCategoryApi
                    .getList(codes);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        } catch (BusinessException e) {
            logger.error("产品分类列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品分类列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品分类推介列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/introduct/list"}, method = RequestMethod.POST)
    public JsonResponse<Map<String, List<CrmProductCategoryVO>>> getIntroductList(@RequestBody JsonRequest<CrmProductCategoryVO> jsonRequest) {
        logger.info("产品分类列表");
        JsonResponse<Map<String, List<CrmProductCategoryVO>>> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = crmProductCategoryApi
                    .getIntroductList(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                Map<String, List<CrmProductCategoryVO>> map = new HashMap<>();
                map.put("resultData", serviceResponse.getRetContent());
                jsonResponse.setRspBody(map);
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                }
            }
        } catch (BusinessException e) {
            logger.error("产品分类列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品分类列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    private List<String> getRoleCode() {
        String acctId = AssertContext.getAcctId();
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }

        return list;
    }
}
