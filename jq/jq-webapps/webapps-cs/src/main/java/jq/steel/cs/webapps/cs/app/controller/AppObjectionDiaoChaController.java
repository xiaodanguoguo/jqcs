package jq.steel.cs.webapps.cs.app.controller;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
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
    public JsonResponse<Integer> update(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        logger.info("内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）", JsonUtil.toJson(jsonRequest));
        try {
            ServiceResponse<Integer> byPage = objectionDiaoChaAPI.update(jsonRequest);
            Integer retContent = byPage.getRetContent();
            jsonResponse.setRspBody(retContent);
        } catch (BusinessException e) {
            logger.error("内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
}
