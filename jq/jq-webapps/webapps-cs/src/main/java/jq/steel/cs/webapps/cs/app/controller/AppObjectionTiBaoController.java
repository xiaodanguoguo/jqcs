package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.AcctAPI;
import jq.steel.cs.services.cust.api.controller.CrmCustomerInfoAPI;
import jq.steel.cs.services.cust.api.controller.CrmLastuserInfoAPI;
import jq.steel.cs.services.cust.api.controller.ObjectionTiBaoAPI;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoCountVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @param:
 * @return:
 * @description:  异议提报
 * @author: lirunze
 * @Date: 2018/9/7
 */
@RestController
@RequestMapping("/app/objectionTiBao")
public class AppObjectionTiBaoController {

    private final static Logger logger = LoggerFactory.getLogger(AppObjectionTiBaoController.class);

    @Autowired
    private ObjectionTiBaoAPI objectionTiBaoAPI;

    @Autowired
    private AcctAPI backMemberAPI;

    @Autowired
    private CrmLastuserInfoAPI crmLastuserInfoAPI;

    @Autowired
    private CrmCustomerInfoAPI crmCustomerInfoAPI;

    /**
     * @param:
     * @return:
     * @description: 根据不同状态计数
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping(value = "/count",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoCountVO> getCount(){
        logger.info("根据不同状态计数");
        JsonResponse<ObjectionTiBaoCountVO> jsonResponse = new JsonResponse<>();

        try {
            JsonRequest<ObjectionTiBaoVO> jsonRequest = new JsonRequest<>();
            ObjectionTiBaoVO objectionTiBaoVO = new ObjectionTiBaoVO();
            objectionTiBaoVO.setCreatedBy(AssertContext.getAcctId());
            // 判断是否有审核权限
            ServiceResponse<Map<String, String>> authResponse = backMemberAPI.getAcctAuth(AssertContext.getAcctId());
            Map<String, String> map = authResponse.getRetContent();
            if(map.get("50") != null){
                objectionTiBaoVO.setCreatedBy(null);
            }

            jsonRequest.setReqBody(objectionTiBaoVO);

            ServiceResponse<ObjectionTiBaoCountVO> serviceResponse = objectionTiBaoAPI.getCount(jsonRequest);
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
            logger.error("根据不同状态计数错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("根据不同状态计数错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }

    /**
     *  异议提报列表
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/submit/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findTiBaoByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findTiBaoByPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     *  异议跟踪列表
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/tailafter/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionTiBaoVO>> findgenzongByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionTiBaoVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = objectionTiBaoAPI.findgenzongByPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     *  新增查询/修改查询和详情查询和销售审核查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findDetails",method = RequestMethod.POST)
    public JsonResponse<ObjectionTiBaoVO> findDetails(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<ObjectionTiBaoVO> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<ObjectionTiBaoVO> response = objectionTiBaoAPI.findDetails(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else if (ServiceResponse.SUCCESS_CODE.equals("1")){
                jsonResponse.setRetCode("7777777");
                jsonResponse.setRetDesc(response.getRetMessage());
            }
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;

    }


    /**
     * 新增修改销售审核保存驳回通过  保存数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            ServiceResponse<Integer> response = objectionTiBaoAPI.update(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;

    }


    /**
     * 提交/删除
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public JsonResponse<Integer> submit(@RequestBody JsonRequest<List<ObjectionTiBaoVO>> jsonRequest) {
        logger.info("参数={}",JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().get(0).setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().get(0).setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().get(0).setPresentationUser(AssertContext.getAcctId());
            ServiceResponse<Integer> response = objectionTiBaoAPI.submit(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode()))
                jsonResponse.setRspBody(response.getRetContent());
                // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;

    }

    /**
     *订货单位
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/orderUnit/list",method = RequestMethod.POST)
    public JsonResponse<List<CrmCustomerInfoVO>>  findorderUnit(@RequestBody JsonRequest<CrmCustomerInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmCustomerInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setCreatedBy(AssertContext.getAcctId());
            ServiceResponse<List<CrmCustomerInfoVO>> serviceResponse = crmCustomerInfoAPI.findorderUnitList(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     *使用单位
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/unitOfUse/list",method = RequestMethod.POST)
    public JsonResponse<List<CrmLastuserInfoVO>>  findunitOfUse(@RequestBody JsonRequest<CrmLastuserInfoVO> jsonRequest){
        logger.info("分页={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmLastuserInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            jsonRequest.getReqBody().setCreatedBy(AssertContext.getAcctId());
            ServiceResponse<List<CrmLastuserInfoVO>> serviceResponse = crmLastuserInfoAPI.findunitOfUseList(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
