package jq.steel.cs.services.cust.facade.service.question;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.cust.api.vo.CrmQuestionRecordVO;
import jq.steel.cs.services.cust.api.vo.CrmQuestionVO;

import java.util.List;

/**
 * @ClassName: CrmQuestionService
 * @Description: 问卷调查
 * @Author: lirunze
 * @CreateDate: 2018/8/21 13:23
 */
public interface CrmQuestionService {

    /**
     * @param: crmQuestionVO
     * @return: ServiceResponse<PageDTO<CrmQuestionVO>>
     * @description:  调查问卷列表
     * @author: lirunze
     * @Date: 2018/8/21
     */
    PageDTO<CrmQuestionVO> getPage(CrmQuestionVO crmQuestionVO);

    /**
     * @param:  crmQuestionVOList
     * @return:  ServiceResponse<Boolean>
     * @description:  保存调查问卷
     * @author: lirunze
     * @Date: 2018/8/23
     */
    ServiceResponse<Boolean> saveQuestion(List<CrmQuestionVO> crmQuestionVOList);

    /**
     * @param:  crmQuestionItemVOList
     * @return:  ServiceResponse<Boolean>
     * @description:  保存调查问卷明细
     * @author: lirunze
     * @Date: 2018/8/23
     */
    ServiceResponse<Boolean> saveDetail(CrmQuestionVO questionVO);

    /**
     * @param: crmQuestionVO
     * @return: SrviceResponse<Boolean>
     * @description: 问卷下发
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<Boolean> sentDownQuestion(CrmQuestionVO crmQuestionVO);


    /**
     * @param: crmQuestionVO
     * @return:  ServiceResponse<CrmQuestionVO>
     * @description: 预览
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<CrmQuestionVO> getPreview(CrmQuestionVO crmQuestionVO);

    /**
     * @param: crmQuestionRecordVO
     * @return:
     * @description: 问卷提交
     * @author: lirunze
     * @Date: 2018/8/20
     */
    ServiceResponse<CrmQuestionVO> insertCrmQuestionRecord(CrmQuestionRecordVO crmQuestionRecordVO);

    /**
     * @param:
     * @return:
     * @description: 下拉列表
     * @author: lirunze
     * @Date: 2018/8/20
     */
    List<CrmQuestionVO> getList();

    /**
     * @param: jsonRequest
     * @return:
     * @description: 是否有调查问卷
     * @author: lirunze
     * @Date: 2018/8/20
     */
    CrmQuestionVO getQuestion(CrmQuestionVO crmQuestionVO);
}
