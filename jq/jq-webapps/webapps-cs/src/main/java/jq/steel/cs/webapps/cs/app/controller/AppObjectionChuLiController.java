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
