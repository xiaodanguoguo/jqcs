package jq.steel.cs.services.cust.facade.service.millsheet;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CrmMillSheetSplitApplyService {

    CrmMillSheetSplitApplyVO splitInsert(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList,HttpServletRequest request);
    List<CrmMillSheetSplitDetailVO> splitFindAll(CrmMillSheetSplitDetailVO crmMillSheetSplitDetailVO);
    CrmMillSheetSplitApplyVO splitInsertAll(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList,HttpServletRequest request);
    CrmMillSheetSplitApplyVO splitInsertNeed(List<CrmMillSheetSplitInfo> record,String ip);
    CrmMillSheetSplitApplyVO splitInsertNeed1(List<CrmMillSheetSplitInfo> record,String ip);
    CrmMillSheetSplitApplyVO splitInsertSpecial(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOList,HttpServletRequest request);

}
