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

@RestController
@RequestMapping("/app/objectionDiaoCha")
public class AppObjectionDiaoChaController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private ObjectionDiaoChaAPI objectionDiaoChaAPI;

    @RequestMapping(value = "/findByPageAndState",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionDiaoChaVO>> findByPage(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        JsonResponse<PageDTO<ObjectionDiaoChaVO>> jsonResponse = new JsonResponse<>();
        logger.info("确认书审核列表", JsonUtil.toJson(jsonRequest));
        try {
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
