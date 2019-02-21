package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.controller.RoleInfoAPI;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.cust.api.controller.MillSheetHostsAPI;
import jq.steel.cs.services.cust.api.controller.app.AppMillSheetHostsDetailAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/millsheet")
public class AppMillSheetHostsDetailController {

    private final static Logger logger = LoggerFactory.getLogger(AppMillSheetHostsDetailController.class);

    @Autowired
    private AppMillSheetHostsDetailAPI appMillSheetHostsDetailAPI;

    @Autowired
    private MillSheetHostsAPI millSheetHostsAPI;

    @Autowired
    private RoleInfoAPI roleInfoAPI;

    @RequestMapping(value = "/findMillSheetDeatilBySheet", method = RequestMethod.POST)
    public JsonResponse<List<CrmMillCoilInfoVO>> getCoilDetailByMillSheet(@RequestBody JsonRequest<CrmMillSheetDetailVO> jsonRequest) {
        JsonResponse<List<CrmMillCoilInfoVO>> jsonResponse = new JsonResponse<>();
        if (jsonRequest == null && jsonRequest.getReqBody().getMillSheetNo() == null && jsonRequest.getReqBody().getShowFlag() == null) {
            jsonResponse.setRetCode("非法操作");
            return jsonResponse;
        }

        try {
            jsonRequest.getReqBody().setShowFlag(1);
            ServiceResponse<List<CrmMillCoilInfoVO>> coilDetailByMillSheet = appMillSheetHostsDetailAPI.getCoilDetailByMillSheet(jsonRequest);
            List<CrmMillCoilInfoVO> listAll = coilDetailByMillSheet.getRetContent();
            jsonResponse.setRspBody(listAll);
        } catch (Exception e) {
            logger.error("查询错误:", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/findMillSheetByPage", method = RequestMethod.POST)
    public JsonResponse<PageDTO<MillSheetHostsVO>> findMillSheetByPage(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest) {
        JsonResponse<PageDTO<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
        try {
            String acctId = AssertContext.getAcctId();
            String orgId = AssertContext.getOrgId();
            String orgType = AssertContext.getOrgType();
            ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
            List<String> list = new ArrayList<>();
            for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
                list.add(roleInfoVO.getRoleCode());
            }
            if (list.size()>0){
                jsonRequest.getReqBody().setDeptCodes(list);
            }else {
                jsonRequest.getReqBody().setDeptCodes(null);
            }
            //组织名称
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setOrgId(orgId);
            jsonRequest.getReqBody().setOrgType(orgType);

            //by lujiawei
            //ServiceResponse<PageDTO<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findMillSheetByPage(jsonRequest);



            //组织名称  orgtype是5 为厂级领导  设置orgName为null 拿deptCode查询
            //orgtype 1 销售公司  改为zkunner查询
            if(orgType.equals("1")){
                jsonRequest.getReqBody().setZkunnr(AssertContext.getOrgName());
            }


            // by wushibin
            ServiceResponse<PageDTO<MillSheetHostsVO>> serviceResponse = millSheetHostsAPI.findMillSheetByPage1(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                } else {
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
     * 条件查询
     */
    @RequestMapping(value = "/getMillSheetMsg", method = RequestMethod.POST)
    public JsonResponse<List<MillSheetHostsVO>> getMillSheetByMsg(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest) {
        JsonResponse<List<MillSheetHostsVO>> jsonResponse = new JsonResponse<>();
        try {
            String acctId = AssertContext.getAcctId();
            String orgId = AssertContext.getOrgId();
            String orgType = AssertContext.getOrgType();
            ServiceResponse<List<RoleInfoVO>>  listServiceResponse = roleInfoAPI.getRoleCodeByAcctId(acctId);
            List<String> list = new ArrayList<>();
            for (RoleInfoVO roleInfoVO:listServiceResponse.getRetContent()){
                list.add(roleInfoVO.getRoleCode());
            }
            if (list.size()>0){
                jsonRequest.getReqBody().setDeptCodes(list);
            }else {
                jsonRequest.getReqBody().setDeptCodes(null);
            }
            //组织名称  orgtype是5 为厂级领导  设置orgName为null 拿deptCode查询
            //orgtype 1 销售公司  改为zkunner查询
            if(orgType.equals("5")){
            }else if(orgType.equals("1")){
                jsonRequest.getReqBody().setZkunnr(AssertContext.getOrgName());
            }else{
                jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            }

            jsonRequest.getReqBody().setOrgId(orgId);
            jsonRequest.getReqBody().setOrgType(orgType);
            ServiceResponse<List<MillSheetHostsVO>> vos = appMillSheetHostsDetailAPI.getSheetMsg(jsonRequest);
            jsonResponse.setRspBody(vos.getRetContent());
        } catch (BusinessException e) {
            logger.error("查询错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
