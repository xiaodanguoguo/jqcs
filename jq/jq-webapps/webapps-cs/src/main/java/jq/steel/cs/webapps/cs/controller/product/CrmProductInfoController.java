package jq.steel.cs.webapps.cs.controller.product;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.CrmProductInfoApi;
import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;
import jq.steel.cs.webapps.cs.controller.file.FileUploadSringUtil;
import jq.steel.cs.webapps.cs.controller.file.UploadConfig;
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
 * @ClassName: CrmProductInfoController
 * @Description: 产品信息
 * @Author: lirunze
 * @CreateDate: 2018/8/20 15:47
 */
@RestController
@RequestMapping("/product/info")
public class CrmProductInfoController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmProductInfoApi crmProductInfoApi;

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    public JsonResponse<PageDTO<CrmProductInfoVO>> getPage(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息列表 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<CrmProductInfoVO>> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<PageDTO<CrmProductInfoVO>> serviceResponse = crmProductInfoApi
                    .getPage(jsonRequest);
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
    @RequestMapping(value = {"/detail"}, method = RequestMethod.POST)
    public JsonResponse<CrmProductInfoVO> detailPruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息详情 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmProductInfoVO> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<CrmProductInfoVO> serviceResponse = crmProductInfoApi
                    .getDetail(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                CrmProductInfoVO crmProductInfoVO = serviceResponse.getRetContent();
                List<String> thumbnailList = JsonUtil.parseObject(crmProductInfoVO.getThumbnail(), List.class);
                List<String> thumbnails = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(thumbnailList)) {
                    for (String s : thumbnailList) {
                        thumbnails.add(FileUploadSringUtil.addPath(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), s));
                    }
                }

                crmProductInfoVO.setThumbnailList(thumbnails);
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

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息删除
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> deletePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        logger.info("产品信息删除 = {}", JsonUtil.toJson(jsonRequest));

        try {
            jsonRequest.getReqBody().setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            jsonRequest.getReqBody().setUpdateBy(AssertContext.getAcctName());
            ServiceResponse<Boolean> serviceResponse = crmProductInfoApi
                    .deletePruduct(jsonRequest);
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
            logger.error("产品信息删除错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息删除错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息发布
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/issue"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> issuePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        logger.info("产品信息发布 = {}", JsonUtil.toJson(jsonRequest));

        try {
            jsonRequest.getReqBody().setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            jsonRequest.getReqBody().setUpdateBy(AssertContext.getAcctName());
            ServiceResponse<Boolean> serviceResponse = crmProductInfoApi
                    .issuePruduct(jsonRequest);
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
            logger.error("产品信息发布错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息发布错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息新增
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> insertPruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        logger.info("产品信息新增 = {}", JsonUtil.toJson(jsonRequest));

        try {
            CrmProductInfoVO vo = jsonRequest.getReqBody();
            List<String> thumbnailList = vo.getThumbnailList();
            List<String> thumbnails = new ArrayList<>();
            for (String s : thumbnailList) {
                thumbnails.add(FileUploadSringUtil.subString(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), s));
            }
            vo.setThumbnailList(thumbnails);

            vo.setCreateByid(Long.valueOf(AssertContext.getAcctId()));
            vo.setCreateBy(AssertContext.getAcctName());
            ServiceResponse<Boolean> serviceResponse = crmProductInfoApi
                    .insertPruduct(jsonRequest);
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
            logger.error("产品信息新增错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息新增错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息修改
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> updatePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        logger.info("产品信息修改 = {}", JsonUtil.toJson(jsonRequest));

        try {
            CrmProductInfoVO vo = jsonRequest.getReqBody();
            vo.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            vo.setUpdateBy(AssertContext.getAcctName());
            List<String> thumbnailList = vo.getThumbnailList();
            List<String> thumbnails = new ArrayList<>();
            for (String s : thumbnailList) {
                thumbnails.add(FileUploadSringUtil.subString(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), s));
            }
            vo.setThumbnailList(thumbnails);
            vo.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            vo.setUpdateBy(AssertContext.getAcctName());
            ServiceResponse<Boolean> serviceResponse = crmProductInfoApi
                    .updatePruduct(jsonRequest);
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
            logger.error("产品信息修改错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息修改错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 产品信息移动
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/move"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> movePruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        logger.info("产品信息移动 = {}", JsonUtil.toJson(jsonRequest));

        try {
            jsonRequest.getReqBody().setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            jsonRequest.getReqBody().setUpdateBy(AssertContext.getAcctName());
            ServiceResponse<Boolean> serviceResponse = crmProductInfoApi
                    .movePruduct(jsonRequest);
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
            logger.error("产品信息移动错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息移动错误 = {}", e);
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
    @RequestMapping(value = {"/introduct/list"}, method = RequestMethod.POST)
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
                        List<String> thumbnailList = JsonUtil.parseObject(crmProductInfoVO.getThumbnail(), List.class);
                        if (CollectionUtils.isNotEmpty(thumbnailList)) {
                            crmProductInfoVO.setThumbnail(FileUploadSringUtil.addPath(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), thumbnailList.get(0)));
                        }
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
     * @description: 产品首页推介列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/index/list"}, method = RequestMethod.POST)
    public JsonResponse<Map<String, List<String>>> getIndexPage() {
        logger.info("产品信息列表 = {}");
        JsonResponse<Map<String, List<String>>> jsonResponse = new JsonResponse<>();

        try {
            JsonRequest<CrmProductInfoVO> jsonRequest = new JsonRequest();
            CrmProductInfoVO crmProductInfo = new CrmProductInfoVO();
            crmProductInfo.setPageSize(10);
            jsonRequest.setReqBody(crmProductInfo);

            ServiceResponse<PageDTO<CrmProductInfoVO>> serviceResponse = crmProductInfoApi
                    .getIntroductPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                List<CrmProductInfoVO> list = serviceResponse.getRetContent().getResultData();
                List<String> result = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (CrmProductInfoVO crmProductInfoVO : list) {
                        List<String> thumbnailList = JsonUtil.parseObject(crmProductInfoVO.getThumbnail(), List.class);
                        if (CollectionUtils.isNotEmpty(thumbnailList)) {
                            result.add(FileUploadSringUtil.addPath(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), thumbnailList.get(0)));
                        }
                    }
                }
                Map<String, List<String>> map = new HashMap<>();
                map.put("resultData", result);
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
    @RequestMapping(value = {"/introduct/detail"}, method = RequestMethod.POST)
    public JsonResponse<CrmProductInfoVO> introductDetailPruduct(@RequestBody JsonRequest<CrmProductInfoVO> jsonRequest) {
        logger.info("产品信息详情 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmProductInfoVO> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<CrmProductInfoVO> serviceResponse = crmProductInfoApi
                    .getDetail(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                CrmProductInfoVO crmProductInfoVO = serviceResponse.getRetContent();
                List<String> thumbnailList = JsonUtil.parseObject(crmProductInfoVO.getThumbnail(), List.class);
                if (CollectionUtils.isNotEmpty(thumbnailList)) {
                    crmProductInfoVO.setThumbnail(FileUploadSringUtil.addPath(uploadConfig.getDomain() +"/"+ uploadConfig.getPathPattern(), thumbnailList.get(0)));
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
            logger.error("产品信息详情错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("产品信息详情错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }
}
