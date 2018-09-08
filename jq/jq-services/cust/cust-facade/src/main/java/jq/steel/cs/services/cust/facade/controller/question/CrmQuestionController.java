package jq.steel.cs.services.cust.facade.controller.question;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmQuestionRecordVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionVO;
import jq.steel.cs.services.cust.facade.service.question.CrmQuestionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CrmQuestionController
 * @Description: 问卷调查
 * @Author: lirunze
 * @CreateDate: 2018/8/21 13:04
 */
@RestController
@RequestMapping("/question")
public class CrmQuestionController {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmQuestionService crmQuestionService;

    /**
     * @param: jsonRequest
     * @return: ServiceResponse<PageDTO<CrmQuestionVO>>
     * @description:  调查问卷列表
     * @author: lirunze
     * @Date: 2018/8/21
     */
    @RequestMapping("/list")
    ServiceResponse<PageDTO<CrmQuestionVO>> getPage(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("调查问卷列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<CrmQuestionVO>> serviceResponse = new ServiceResponse<>();

        try {
            CrmQuestionVO crmQuestionVO = jsonRequest.getReqBody();
            PageDTO<CrmQuestionVO> page = crmQuestionService.getPage(crmQuestionVO);
            serviceResponse.setRetContent(page);
        } catch (Exception e) {
            logger.error("调查问卷列表错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }


    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<Boolean>
     * @description:  保存调查问卷
     * @author: lirunze
     * @Date: 2018/8/23
     */
    @RequestMapping("/save")
    ServiceResponse<Boolean> saveQuestion(@RequestBody JsonRequest<List<CrmQuestionVO>> jsonRequest) {
        logger.info("保存调查问卷 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            List<CrmQuestionVO> crmQuestionVOList = jsonRequest.getReqBody();
            serviceResponse = crmQuestionService.saveQuestion(crmQuestionVOList);
        } catch (Exception e) {
            logger.error("保存调查问卷错误 = {}", e);

            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<Boolean>
     * @description:  保存调查问卷明细
     * @author: lirunze
     * @Date: 2018/8/23
     */
    @RequestMapping("/saveDetail")
    ServiceResponse<Boolean> saveDetail(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("保存调查问卷明细 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmQuestionVO questionVO = jsonRequest.getReqBody();
            serviceResponse = crmQuestionService.saveDetail(questionVO);
        } catch (Exception e) {
            logger.error("保存调查问卷明细错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return: ServiceResponse<Boolean>
     * @description: 问卷下发
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/sentDown")
    ServiceResponse<Boolean> sentDownQuestion(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("问卷下发 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        try {
            CrmQuestionVO crmQuestionVO = jsonRequest.getReqBody();
            serviceResponse = crmQuestionService.sentDownQuestion(crmQuestionVO);

        } catch (Exception e) {
            logger.error("问卷下发错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 预览
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/preview")
    ServiceResponse<CrmQuestionVO> preview(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("预览 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmQuestionVO> serviceResponse = new ServiceResponse<>();

        try {
            CrmQuestionVO crmQuestionVO = jsonRequest.getReqBody();
            serviceResponse = crmQuestionService.getPreview(crmQuestionVO);
        } catch (Exception e) {
            logger.error("预览错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 问卷提交
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/submit")
    ServiceResponse<CrmQuestionVO> submit(@RequestBody JsonRequest<CrmQuestionRecordVO> jsonRequest) {
        logger.info("问卷提交 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmQuestionVO> serviceResponse = new ServiceResponse<>();

        try {
            CrmQuestionRecordVO crmQuestionRecordVO = jsonRequest.getReqBody();
            serviceResponse = crmQuestionService.insertCrmQuestionRecord(crmQuestionRecordVO);
        } catch (Exception e) {
            logger.error("问卷提交错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 下拉列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/down/list")
    ServiceResponse<List<CrmQuestionVO>> getList(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("下拉列表 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<CrmQuestionVO>> serviceResponse = new ServiceResponse<>();

        try {
            List<CrmQuestionVO> list = crmQuestionService.getList();
            serviceResponse.setRetContent(list);
        } catch (Exception e) {
            logger.error("下拉列表错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }

    /**
     * @param: jsonRequest
     * @return:
     * @description: 是否有调查问卷
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/have/question"}, method = RequestMethod.POST)
    ServiceResponse<CrmQuestionVO> haveQuestion(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest) {
        logger.info("是否有调查问卷 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<CrmQuestionVO> serviceResponse = new ServiceResponse<>();

        try {
            CrmQuestionVO crmQuestionVO = jsonRequest.getReqBody();
            CrmQuestionVO questionVO = crmQuestionService.getQuestion(crmQuestionVO);
            serviceResponse.setRetContent(questionVO);
        } catch (Exception e) {
            logger.error("是否有调查问卷错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }
}
