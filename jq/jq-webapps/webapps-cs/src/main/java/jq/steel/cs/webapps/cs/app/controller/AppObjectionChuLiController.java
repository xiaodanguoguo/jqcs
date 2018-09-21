package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.ObjectionChuLiAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/objectionChuli")
public class AppObjectionChuLiController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private ObjectionChuLiAPI objectionChuLiAPI;

    @RequestMapping(value = "/findByPageAndState",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionChuLiVO>> findByPage(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        JsonResponse<PageDTO<ObjectionChuLiVO>> jsonResponse = new JsonResponse<>();
        logger.info("异议处理列表", JsonUtil.toJson(jsonRequest));
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
            jsonRequest.getReqBody().setClaimState("HANDLE");
            /**
             * 调查报告状态 INQUIRE_STATE CRM_CLAIM_INFO
             OUTSTART 外部调查开始 ,
             TRACK 已跟踪 ,
             OUTEND 外部调查结束 ,
             INSTART 内部调查开始 ,
             INEND调查结束 ,
             CONFIRM 已确认
             */
            jsonRequest.getReqBody().setInquireState("CONFIRM");

            /**
             * 协议书状态   AGREEMENT_STATE  CRM_AGREEMENT_INFO
             <
             编辑中:EDIT,
             已完成:COMPLETE,
             已审核:EXMINE
             >
             */
            jsonRequest.getReqBody().setAgreementState("COMPLETE");

            ServiceResponse<PageDTO<ObjectionChuLiVO>> byPage = objectionChuLiAPI.findByPage(jsonRequest);
            PageDTO<ObjectionChuLiVO> retContent = byPage.getRetContent();
            jsonResponse.setRspBody(retContent);


        } catch (BusinessException e) {
            logger.error("异议处理列表", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     *  协议书保存/提交/审核
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/agreementUpdate",method = RequestMethod.POST)
    public JsonResponse<Integer> agreementUpdate(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        logger.info("协议书保存/提交/审核", JsonUtil.toJson(jsonRequest));
        try {
            ServiceResponse<Integer> result = objectionChuLiAPI.agreementUpdate(jsonRequest);
            Integer retContent = result.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("协议书保存/提交/审核", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
