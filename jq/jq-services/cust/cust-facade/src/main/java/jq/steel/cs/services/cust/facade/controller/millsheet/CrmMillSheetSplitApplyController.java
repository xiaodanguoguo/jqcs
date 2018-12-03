package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.facade.service.millsheet.CrmMillSheetSplitApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/split")
public class CrmMillSheetSplitApplyController {
    private static Logger logger = LoggerFactory.getLogger(CrmMillSheetSplitApplyController.class);


    @Autowired
    private CrmMillSheetSplitApplyService crmMillSheetSplitApplyService;

    /**
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/splitInsert")
    public ServiceResponse<CrmMillSheetSplitApplyVO> splitInsert(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest) {
        logger.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmMillSheetSplitApplyVO> serviceResponse = new ServiceResponse<>();
        List<CrmMillSheetSplitApplyVO> reqBody = jsonRequest.getReqBody();
        try {
            CrmMillSheetSplitApplyVO downUrl = crmMillSheetSplitApplyService.splitInsert(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        } catch (Exception e) {
            logger.error("保存 参数 失败 error = {}", e);

            throw new BusinessException("500");
        }
    }


    /**
     * 拆分历史详情数据回显
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/splitFindAll")
    public ServiceResponse<List<CrmMillSheetSplitDetailVO>> splitFindAll(@RequestBody JsonRequest<CrmMillSheetSplitDetailVO> jsonRequest) {
        logger.info("前台参数值", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<CrmMillSheetSplitDetailVO>> serviceResponse = new ServiceResponse<>();
        CrmMillSheetSplitDetailVO crmMillSheetSplitDetailVO = jsonRequest.getReqBody();

        try {
            List<CrmMillSheetSplitDetailVO> crmMillSheetSplitDetailVOList = crmMillSheetSplitApplyService.splitFindAll(crmMillSheetSplitDetailVO);
            serviceResponse.setRetContent(crmMillSheetSplitDetailVOList);
            return serviceResponse;
        } catch (Exception e) {
            //logger.error("历史详情数据查询出错");
            throw new BusinessException("500");
        }

    }


    /**
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/splitInsertAll")
    public ServiceResponse<CrmMillSheetSplitApplyVO> splitInsertAll(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest) {
        logger.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmMillSheetSplitApplyVO> serviceResponse = new ServiceResponse<>();
        List<CrmMillSheetSplitApplyVO> reqBody = jsonRequest.getReqBody();
        try {
            CrmMillSheetSplitApplyVO downUrl = crmMillSheetSplitApplyService.splitInsertAll(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        } catch (Exception e) {
            logger.error("保存 参数 失败 error = {}", e);

            throw new BusinessException("500");
        }
    }



    /**
     * 批量拆分(不锈钢厂+碳钢厂)
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/splitInsertSpecial")
    public ServiceResponse<CrmMillSheetSplitApplyVO> splitInsertSpecial(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest) {
        logger.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmMillSheetSplitApplyVO> serviceResponse = new ServiceResponse<>();
        List<CrmMillSheetSplitApplyVO> reqBody = jsonRequest.getReqBody();
        try {
            CrmMillSheetSplitApplyVO downUrl = crmMillSheetSplitApplyService.splitInsertSpecial(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        } catch (Exception e) {
            logger.error("保存 参数 失败 error = {}", e);

            throw new BusinessException("500");
        }
    }

}
