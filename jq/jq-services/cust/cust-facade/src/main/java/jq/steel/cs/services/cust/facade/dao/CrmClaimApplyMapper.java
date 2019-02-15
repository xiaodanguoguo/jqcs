package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmClaimCommentsVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoCountVO;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;

import java.util.List;
import java.util.Map;

public interface CrmClaimApplyMapper {
    int deleteByPrimaryKey(Long sid);

    int insert(CrmClaimApply record);

    int insertSelective(CrmClaimApply record);

    CrmClaimApply select(CrmClaimApply record);

   /* int updateByPrimaryKeySelective(CrmClaimApply record);

    int updateByPrimaryKey(CrmClaimApply record);*/

    List<CrmClaimApply> findByPage(CrmClaimApply record);

    int update(CrmClaimApply record);

    int delete(String claimNo);

    Integer findClaimNumMax(Map<String, Object> map);


    CrmClaimApply findByParams(CrmClaimApply record);

    CrmClaimApply findByParamsForApp(CrmClaimApply record);


    List<ObjectionTiBaoCountVO> getCount(CrmClaimApply crmClaimApply);

    List<CrmClaimApply> findTiBaoByPage(CrmClaimApply crmClaimApply);

    List<CrmClaimApply> findgenzongByPage(CrmClaimApply crmClaimApply);

    // 判断此条异议是否为已评价状态
    CrmClaimApply getByClaimNo(CrmClaimCommentsVO vo);

    //如果是已受理的状态,就使用查询受理人和受理人电话；已驳回状态，销售驳回原因和生产驳回原因的sql。
    List<CrmClaimApply> findTiBaoByPageAndAcceptanceState(CrmClaimApply apply);


    List<CrmClaimApply> findMillSheetByCus(CrmClaimApply apply);
    //app
    List<CrmClaimApply> findMillSheetByCusForApp(CrmClaimApply h);
    //app
    List<CrmClaimApply> findByPageForApp(CrmClaimApply crmClaimApply);
}