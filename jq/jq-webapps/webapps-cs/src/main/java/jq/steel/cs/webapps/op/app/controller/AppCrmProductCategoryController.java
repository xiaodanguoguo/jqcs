package jq.steel.cs.webapps.op.app.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.CrmProductCategoryApi;
import jq.steel.cs.services.cust.api.controller.CrmProductInfoApi;
import jq.steel.cs.services.cust.api.vo.CrmProductCategoryVO;
import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;
import jq.steel.cs.webapps.op.controller.file.FileUploadSringUtil;
import jq.steel.cs.webapps.op.controller.file.UploadConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CrmProductCategory
 * @Description: 产品分类
 * @Author: lirunze
 * @CreateDate: 2018/8/20 15:36
 */
@RestController
@RequestMapping("/app/product/category")
public class AppCrmProductCategoryController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmProductCategoryApi crmProductCategoryApi;

    @Autowired
    private CrmProductInfoApi crmProductInfoApi;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品分类推介列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/category/introduct/list"}, method = RequestMethod.POST)
    public JsonResponse<List<CrmProductCategoryVO>> getIntroductList() {
        logger.info("产品分类列表");
        JsonResponse<List<CrmProductCategoryVO>> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<List<CrmProductCategoryVO>> serviceResponse = crmProductCategoryApi
                    .getIntroductList();
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
     * @description: 产品信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/info/introduct/list"}, method = RequestMethod.POST)
    public JsonResponse<PageDTO<CrmProductInfoVO>> getIntroductPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息列表 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<CrmProductInfoVO>> jsonResponse = new JsonResponse<>();

        try {
            jsonRequest.getReqBody().setPageSize(9);
            ServiceResponse<PageDTO<CrmProductInfoVO>> serviceResponse = crmProductInfoApi
                    .getIntroductPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                List<CrmProductInfoVO> list = serviceResponse.getRetContent().getResultData();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (CrmProductInfoVO crmProductInfoVO : list) {
                        List<String> thumbnailList = JsonUtil.parseObject(crmProductInfoVO.getProductManual(), List.class);
                        crmProductInfoVO.setThumbnail(FileUploadSringUtil.addPath(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), thumbnailList.get(0)));
                    }
                }
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
            logger.error("产品信息列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息详情
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/info/introduct/detail"}, method = RequestMethod.POST)
    public JsonResponse<CrmProductInfoVO> introductDetailPruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息详情 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmProductInfoVO> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<CrmProductInfoVO> serviceResponse = crmProductInfoApi
                    .getDetail(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                CrmProductInfoVO crmProductInfoVO = serviceResponse.getRetContent();
                List<String> thumbnailList = JsonUtil.parseObject(crmProductInfoVO.getProductManual(), List.class);
                crmProductInfoVO.setThumbnail(FileUploadSringUtil.addPath(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), thumbnailList.get(0)));
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
            logger.error("产品信息详情错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息详情错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }
}
