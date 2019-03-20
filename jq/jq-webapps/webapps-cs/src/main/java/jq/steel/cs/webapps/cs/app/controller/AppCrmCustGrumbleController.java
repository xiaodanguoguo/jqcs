package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.app.AppCrmCustGrumbleApi;
import jq.steel.cs.services.cust.api.vo.CrmCustGrumbleVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: AppCrmCustGrumbleController
 * @Description: 客户抱怨
 * @Author: lirunze
 * @CreateDate: 2018/9/7 13:23
 */
@RestController
@RequestMapping("/app/grumble")
public class AppCrmCustGrumbleController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private AppCrmCustGrumbleApi appCrmCustGrumbleApi;

    @Autowired
    private RoleInfoAPI roleInfoAPI;



    /**
     * @param:
     * @return:
     * @description:  添加客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/add")
    public JsonResponse<Integer> addCrmCustGrumble(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("添加客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();

        try {
            CrmCustGrumbleVO grumbleVO = jsonRequest.getReqBody();
            grumbleVO.setCreateByid(Long.parseLong(AssertContext.getAcctId()));
            grumbleVO.setCreateDt(new Date());
            //公司名称
            grumbleVO.setCustomer(AssertContext.getOrgName());
            ServiceResponse<Integer> serviceResponse = appCrmCustGrumbleApi.addCrmCustGrumble(jsonRequest);
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
            logger.error("添加客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("添加客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }



    /**
     * @param:
     * @return:
     * @description:  删除客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/delete")
    public JsonResponse<Integer> delete(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("删除客户抱怨/表扬信息 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<Integer> serviceResponse = appCrmCustGrumbleApi.delete(jsonRequest);
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
            logger.error("删除客户抱怨/表扬信息错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("删除客户抱怨/表扬信息错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }



    /**
     * @param:
     * @return:
     * @description:  修改----->反馈
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/update")
    public JsonResponse<Integer> update(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("修改客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();

        try {
            CrmCustGrumbleVO grumbleVO = jsonRequest.getReqBody();
            grumbleVO.setUpdateByid(Long.parseLong(AssertContext.getAcctId()));
            grumbleVO.setUpdateDt(new Date());
            ServiceResponse<Integer> serviceResponse = appCrmCustGrumbleApi.update(jsonRequest);
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
            logger.error("修改客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("修改客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }



    /**
     * @param:
     * @return:
     * @description:  修改----->反馈
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/updateState")
    public JsonResponse<Integer> updateState(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("修改客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        String orgType = AssertContext.getOrgType();
        String orgName = AssertContext.getOrgName();
        String acctId = AssertContext.getAcctId();
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        if (list.size()>0){
            jsonRequest.getReqBody().setFactorys(list);
        }else {
            jsonRequest.getReqBody().setFactorys(null);
        }
        jsonRequest.getReqBody().setOrgType(orgType);
        jsonRequest.getReqBody().setOrgName(orgName);
        //1销售公司 》》》酒钢管理员权限 不使用customer查询
        if(orgType.equals("2")||orgType.equals("3")||orgType.equals("4")){
            jsonRequest.getReqBody().setCustomer(AssertContext.getOrgName());
        }
        try {
            CrmCustGrumbleVO grumbleVO = jsonRequest.getReqBody();
            grumbleVO.setUpdateByid(Long.parseLong(AssertContext.getAcctId()));
            grumbleVO.setUpdateDt(new Date());
            ServiceResponse<Integer> serviceResponse = appCrmCustGrumbleApi.updateState(jsonRequest);
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
            logger.error("修改客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("修改客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }




    /**
     * @param:
     * @return:
     * @description:  查询客户抱怨/表扬
     * @author: wushibin
     * @Date: 2019/2/19
     */
    @RequestMapping("/findByPage")
    public JsonResponse<PageDTO<CrmCustGrumbleVO>> findByPage(@RequestBody JsonRequest<CrmCustGrumbleVO> jsonRequest) {
        logger.info("添加客户抱怨 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<CrmCustGrumbleVO>> jsonResponse = new JsonResponse<>();
        String orgType = AssertContext.getOrgType();
        String orgName = AssertContext.getOrgName();
        String acctId = AssertContext.getAcctId();
        ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
        List<String> list = new ArrayList<>();
        for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
            list.add(roleInfoVO.getRoleCode());
        }
        if (list.size()>0){
            jsonRequest.getReqBody().setFactorys(list);
        }else {
            jsonRequest.getReqBody().setFactorys(null);
        }
        jsonRequest.getReqBody().setOrgType(orgType);
        jsonRequest.getReqBody().setOrgName(orgName);
        //1销售公司 》》》酒钢管理员权限 不使用customer查询
        if(orgType.equals("2")||orgType.equals("3")||orgType.equals("4")){
            jsonRequest.getReqBody().setCustomer(AssertContext.getOrgName());
        }

        try {
            CrmCustGrumbleVO grumbleVO = jsonRequest.getReqBody();
            grumbleVO.setCreateByid(Long.parseLong(AssertContext.getAcctId()));
            grumbleVO.setCreateDt(new Date());
            ServiceResponse<PageDTO<CrmCustGrumbleVO>> serviceResponse = appCrmCustGrumbleApi.findByPage(jsonRequest);
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
            logger.error("添加客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        } catch (Exception e) {
            logger.error("添加客户抱怨错误 = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
