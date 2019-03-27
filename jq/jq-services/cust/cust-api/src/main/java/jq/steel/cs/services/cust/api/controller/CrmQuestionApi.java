package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmQuestionRecordVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @ClassName: CrmQuestionApi
 * @Description: 问卷调查
 * @Author: lirunze
 * @CreateDate: 2018/8/21 10:33
 */
@FeignClient(value = "${ser.name.cust}") //这个是服务名
public interface CrmQuestionApi {
    /**
     * @param: jsonRequest
     * @return: ServiceResponse<PageDTO<CrmQuestionVO>>
     * @description:  调查问卷列表
     * @author: lirunze
     * @Date: 2018/8/21
     */
    @RequestMapping("/question/list")
    ServiceResponse<PageDTO<CrmQuestionVO>> getPage(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);


    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<Boolean>
     * @description:  保存调查问卷
     * @author: lirunze
     * @Date: 2018/8/23
     */
    @RequestMapping("/question/save")
    ServiceResponse<Boolean> saveQuestion(@RequestBody JsonRequest<List<CrmQuestionVO>> jsonRequest);

    /**
     * @param:  jsonRequest
     * @return:  ServiceResponse<Boolean>
     * @description:  保存调查问卷明细
     * @author: lirunze
     * @Date: 2018/8/23
     */
    @RequestMapping("/question/saveDetail")
    ServiceResponse<Boolean> saveDetail(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 问卷下发
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/question/sentDown")
    ServiceResponse<Boolean> sentDownQuestion(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 预览
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/question/preview")
    ServiceResponse<CrmQuestionVO> preview(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 问卷提交
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/question/submit")
    ServiceResponse<CrmQuestionVO> submit(@RequestBody JsonRequest<CrmQuestionRecordVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 下拉列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping("/question/down/list")
    ServiceResponse<List<CrmQuestionVO>> getList(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);

    /**
     * @param: jsonRequest
     * @return:
     * @description: 是否有调查问卷
     * @author: lirunze
     * @Date: 2018/8/20
     */
    @RequestMapping(value = {"/question/have/question"}, method = RequestMethod.POST)
    ServiceResponse<CrmQuestionVO>  haveQuestion(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);





    /**
     * @param: jsonRequest
     * @return: ServiceResponse<PageDTO<CrmQuestionVO>>
     * @description:  统计查询返回qid
     * @author: lirunze
     * @Date: 2018/8/21
     */
    @RequestMapping("/question/findList")
    ServiceResponse<List<CrmQuestionVO>> findList(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);


    /**
     * @param: jsonRequest
     * @return: ServiceResponse<PageDTO<CrmQuestionVO>>
     * @description:  统计查询返回qid
     * @author: lirunze
     * @Date: 2018/8/21
     */
    @RequestMapping("/question/findList1")
    ServiceResponse<CrmQuestionVO> findList1(@RequestBody JsonRequest<CrmQuestionVO> jsonRequest);
}
