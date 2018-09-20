package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetRebackApplyVO;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetRebackApply;

import java.util.List;

public interface CrmMillSheetRebackApplyMapper {

    List<CrmMillSheetRebackApply> find(CrmMillSheetRebackApply record);

    int insert(CrmMillSheetRebackApply record);

    int insertSelective(CrmMillSheetRebackApply record);
}