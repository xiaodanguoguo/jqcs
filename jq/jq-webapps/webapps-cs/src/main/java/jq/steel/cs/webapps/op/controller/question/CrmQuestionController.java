package jq.steel.cs.webapps.op.controller.question;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.CrmQuestionApi;
import jq.steel.cs.services.cust.api.vo.CrmQuestionRecordVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionVO;
import jq.steel.cs.services.cust.api.vo.CrmStatisticsTitle;
import jq.steel.cs.services.cust.api.vo.CrmStatisticsValue;
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
 * @ClassName: CrmQuestionController
 * @Description: 问卷调查
 * @Author: lirunze
 * @CreateDate: 2018/8/20 10:52
 */
@RestController
@RequestMapping("/question")
public class CrmQuestionController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmQuestionApi crmQuestionApi;

    /**
     * @param: jsonRequest
     * @return: JsonResponse<PageDTO<CrmQuestionVO>>
     * @description: 调查问卷列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/list"}, method = RequestMethod.POST)
    public JsonResponse<PageDTO<CrmQuestionVO>> getPage(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("调查问卷列表 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<CrmQuestionVO>> jsonResponse = new JsonResponse<>();
        try {

            ServiceResponse<PageDTO<CrmQuestionVO>> serviceResponse = crmQuestionApi
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
            logger.error("调查问卷列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);

        } catch (Exception e) {
            logger.error("调查问卷列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);

        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 保存调查问卷
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public JsonResponse<Boolean> saveQuestion(@RequestBody JsonRequest<List<CrmQuestionVO>> jsonRequest) {
        logger.info("保存调查问卷 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<Boolean> serviceResponse = crmQuestionApi
                    .saveQuestion(jsonRequest);
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
            logger.error("保存调查问卷错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("保存调查问卷错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

        /**
         * @param: jsonRequest
         * @return:
         * @description: 保存调查问卷明细
         * @author: lirunze
         * @Date: 2018/8/20
         */
    @RequestMapping(value = {"/saveDetail"}, method = RequestMethod.POST)
    public JsonResponse insertQuestionDetail(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("保存调查问卷明细 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<Boolean> serviceResponse = crmQuestionApi
                    .saveDetail(jsonRequest);
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
            logger.error("保存调查问卷明细错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("保存调查问卷明细错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 问卷下发
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/sentDown"}, method = RequestMethod.POST)
    public JsonResponse sentDownQuestion(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("问卷下发 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<Boolean> serviceResponse = crmQuestionApi
                    .sentDownQuestion(jsonRequest);
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
            logger.error("错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 预览
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/preview"}, method = RequestMethod.POST)
    public JsonResponse<CrmQuestionVO> preview(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("预览 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmQuestionVO> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<CrmQuestionVO> serviceResponse = crmQuestionApi
                    .preview(jsonRequest);
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
            logger.error("预览错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("预览错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 问卷提交
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/submit"}, method = RequestMethod.POST)
    public JsonResponse<CrmQuestionVO> submit(@RequestBody JsonRequest<CrmQuestionRecordVO> jsonRequest) {
        logger.info("问卷提交 = {}");
        JsonResponse<CrmQuestionVO> jsonResponse = new JsonResponse<>();

        try {
            CrmQuestionRecordVO crmQuestionRecordVO = jsonRequest.getReqBody();
            crmQuestionRecordVO.setOrgType(AssertContext.getOrgType());
            crmQuestionRecordVO.setAcctId(Long.valueOf(AssertContext.getAcctId()));
            crmQuestionRecordVO.setRecordBy(Long.valueOf(AssertContext.getAcctId()));
            crmQuestionRecordVO.setRecordByName(AssertContext.getAcctName());
            jsonRequest.setReqBody(crmQuestionRecordVO);
            ServiceResponse<CrmQuestionVO> serviceResponse = crmQuestionApi
                    .submit(jsonRequest);
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
            logger.error("问卷提交错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("问卷提交错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 下拉列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/down/list"}, method = RequestMethod.POST)
    public JsonResponse<List<CrmQuestionVO>> getList(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("下拉列表 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmQuestionVO>> jsonResponse = new JsonResponse<>();

        try {
            ServiceResponse<List<CrmQuestionVO>> serviceResponse = crmQuestionApi
                    .getList(jsonRequest);
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
            logger.error("下拉列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("下拉列表错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 统计
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/statistics"}, method = RequestMethod.POST)
    public JsonResponse<Map<String, Object>> getStatistics(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        JsonResponse<Map<String, Object>> jsonResponse = new JsonResponse<>();
        try {
//            ServiceResponse<PageDTO<CrmQuestionVO>> serviceResponse = T
//                    .getDetail(jsonRequest);
//            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
//                response.setRspBody(serviceResponse.getRetContent());
//            } else {
//                if (serviceResponse.isHasError()) {
//                    response.setRetCode(JsonResponse.SYS_EXCEPTION);
//                }
//            }
            Map<String, Object> map = new HashMap<>();
            CrmStatisticsTitle crmStatisticsTitle = new CrmStatisticsTitle();
            crmStatisticsTitle.setRecordByNameStr("客户");
            List<Map<String, String>> titleRecordList = new ArrayList<>();
            Map<String, String> titleRecordMap = new HashMap<>();
            titleRecordMap.put("1","棒材拉伸度是否满足使用要求？");
            titleRecordList.add(titleRecordMap);
            crmStatisticsTitle.setRecordStr(titleRecordList);
            crmStatisticsTitle.setRecordValueStr("总分");
            crmStatisticsTitle.setAverageStr("平均分");
            crmStatisticsTitle.setSatisfactionStr("满意度");
            map.put("title", crmStatisticsTitle);

            List<CrmStatisticsValue> valueList = new ArrayList<>();
            CrmStatisticsValue crmStatisticsValue = new CrmStatisticsValue();
            crmStatisticsValue.setRecordByName("大客户");
            List<Map<String, Integer>> valueRecordList = new ArrayList<>();
            Map<String, Integer> valueRecordMap = new HashMap<>();
            valueRecordMap.put("1",10);
            valueRecordList.add(valueRecordMap);
            valueList.add(crmStatisticsValue);
            crmStatisticsValue.setRecords(valueRecordList);
            crmStatisticsValue.setRecordValue(10);
            crmStatisticsValue.setAverage("10");
            crmStatisticsValue.setSatisfaction(1);
            map.put("resultData", valueList);

            jsonResponse.setRspBody(map);

        } catch (BusinessException e) {
            logger.error("错误 = {}", e);
        }

        return jsonResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 是否有调查问卷
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/have/question"}, method = RequestMethod.POST)
    public JsonResponse<CrmQuestionVO> haveQuestion() {
        logger.info("是否有调查问卷 = {}");
        JsonResponse<CrmQuestionVO> jsonResponse = new JsonResponse<>();

        try {
            JsonRequest<CrmQuestionVO> jsonRequest = new JsonRequest<>();
            CrmQuestionVO vo = new CrmQuestionVO();
            vo.setOrgType(AssertContext.getOrgType());
            vo.setAcctId(Long.valueOf(AssertContext.getAcctId()));
            jsonRequest.setReqBody(vo);
            ServiceResponse<CrmQuestionVO> serviceResponse = crmQuestionApi.haveQuestion(jsonRequest);
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
            logger.error("是否有调查问卷错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("是否有调查问卷错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }
}
