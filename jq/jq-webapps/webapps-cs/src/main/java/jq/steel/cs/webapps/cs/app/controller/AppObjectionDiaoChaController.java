package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.ObjectionChuLiAPI;
import jq.steel.cs.services.cust.api.controller.ObjectionDiaoChaAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 确认书列表、审核
 */
@RestController
@RequestMapping("/app/objectionDiaoCha")
public class AppObjectionDiaoChaController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private ObjectionDiaoChaAPI objectionDiaoChaAPI;

    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionDiaoChaVO>> findByPage(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        JsonResponse<PageDTO<ObjectionDiaoChaVO>> jsonResponse = new JsonResponse<>();
        logger.info("确认书审核列表", JsonUtil.toJson(jsonRequest));
        try {
            /**
             * 异议状态	 CLAIM_STATE CRM_CLAIM_INFO
             NEW 新建,PRESENT已提报 ,
             ACCEPTANCE 已受理 ,
             BEJECT 已驳回 ,
             INVESTIGATION调查中,
             HANDLE处理中 ,
             END已结案 ,
             EVALUATE 已评价 ,
             ADOPT销售审核通过
             */
            jsonRequest.getReqBody().setClaimState("INVESTIGATION");
            /**
             * 调查报告状态 INQUIRE_STATE CRM_CLAIM_INFO
             OUTSTART 外部调查开始 ,
             TRACK 已跟踪 ,
             OUTEND 外部调查结束 ,
             INSTART 内部调查开始 ,
             INEND调查结束 ,
             CONFIRM 已确认
             */
            jsonRequest.getReqBody().setInquireState("INEND");

            ServiceResponse<PageDTO<ObjectionDiaoChaVO>> byPage = objectionDiaoChaAPI.findByPage(jsonRequest);
            PageDTO<ObjectionDiaoChaVO> retContent = byPage.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("确认书审核列表", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }


    /**
     *  内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JsonResponse<ObjectionDiaoChaVO> update(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        JsonResponse<ObjectionDiaoChaVO> jsonResponse = new JsonResponse<>();
        logger.info("内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）", JsonUtil.toJson(jsonRequest));
        try {
            ServiceResponse<ObjectionDiaoChaVO> byPage = objectionDiaoChaAPI.update(jsonRequest);
            ObjectionDiaoChaVO retContent = byPage.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }




    /**
     *  内部调查/外部调查开始结束状态修改
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateState",method = RequestMethod.POST)
    public JsonResponse<Integer> updateState(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) {
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            // 根据service层返回的编码做不同的操作
            jsonRequest.getReqBody().setOrgCode(AssertContext.getOrgCode());
            jsonRequest.getReqBody().setOrgName(AssertContext.getOrgName());
            jsonRequest.getReqBody().setAcctName(AssertContext.getAcctName());
            jsonRequest.getReqBody().setAcctId(AssertContext.getAcctId());
            ServiceResponse<Integer> response = objectionDiaoChaAPI.updateState(jsonRequest);
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
}
