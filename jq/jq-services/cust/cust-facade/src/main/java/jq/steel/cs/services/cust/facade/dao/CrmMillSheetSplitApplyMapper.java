package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitApply;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;

import java.util.List;
import java.util.Map;

public interface CrmMillSheetSplitApplyMapper {


    int insert(CrmMillSheetSplitApply record);

    int insertSelective(CrmMillSheetSplitApply record);

    List<CrmMillSheetSplitApply> find(CrmMillSheetSplitApply record);

    List<CrmMillSheetSplitApply> findFmillSheet(CrmMillSheetSplitApply record);

    //存储过程
    void getDynamicSeq(Map<String,Object> map);



    void updateStatus(CrmMillSheetSplitApply record);

}