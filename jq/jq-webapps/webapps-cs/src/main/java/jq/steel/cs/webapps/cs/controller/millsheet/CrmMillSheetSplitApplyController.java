package jq.steel.cs.webapps.cs.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.controller.CrmMillSheetSplitApplyAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/split")
public class CrmMillSheetSplitApplyController {
    private final static Logger logger = LoggerFactory.getLogger(CrmMillSheetSplitApplyController.class);

    @Autowired
    private CrmMillSheetSplitApplyAPI crmMillSheetSplitApplyAPI;

    /**
     *  拆分页面（强制拆分）提交按钮
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/splitInsert",method = RequestMethod.POST)
    public JsonResponse<CrmMillSheetSplitApplyVO> splitInsert(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest){
        logger.info("{}",JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmMillSheetSplitApplyVO>  jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<CrmMillSheetSplitApplyVO> serviceResponse = crmMillSheetSplitApplyAPI.splitInsert(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());

        } catch (BusinessException e) {
            logger.error("提交报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
    /**
     *  拆分历史数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/splitFindAll",method = RequestMethod.POST)
    public  JsonResponse<List<CrmMillSheetSplitDetailVO>> splitFindAll (@RequestBody JsonRequest<CrmMillSheetSplitDetailVO> jsonRequest){
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmMillSheetSplitDetailVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<List<CrmMillSheetSplitDetailVO>> serviceResponse = crmMillSheetSplitApplyAPI.splitFindAll(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        }catch (BusinessException e){
            logger.error("查询报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }
}
