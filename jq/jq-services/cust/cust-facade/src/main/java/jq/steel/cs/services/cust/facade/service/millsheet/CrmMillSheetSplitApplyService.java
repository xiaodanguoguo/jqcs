package jq.steel.cs.services.cust.facade.service.millsheet;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitInfo;

import java.util.List;
import java.util.Map;

public interface CrmMillSheetSplitApplyService {

    CrmMillSheetSplitApplyVO splitInsert(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList);
    List<CrmMillSheetSplitDetailVO> splitFindAll(CrmMillSheetSplitDetailVO crmMillSheetSplitDetailVO);
    CrmMillSheetSplitApplyVO splitInsertAll(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList);
    CrmMillSheetSplitApplyVO splitInsertNeed(List<CrmMillSheetSplitInfo> record);
    CrmMillSheetSplitApplyVO splitInsertNeed1(List<CrmMillSheetSplitInfo> record);
    CrmMillSheetSplitApplyVO splitInsertSpecial(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList);

}
