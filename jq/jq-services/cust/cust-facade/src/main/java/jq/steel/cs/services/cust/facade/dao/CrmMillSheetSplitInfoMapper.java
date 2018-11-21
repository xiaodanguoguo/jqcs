package jq.steel.cs.services.cust.facade.dao;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitInfo;

import java.util.List;
import java.util.Map;

public interface CrmMillSheetSplitInfoMapper {

    int insert(CrmMillSheetSplitInfo record);

    int insertSelective(CrmMillSheetSplitInfo record);

    List<CrmMillSheetSplitInfo> findByParams(CrmMillSheetSplitInfo record);

    void getDynamicSeq(Map<String,Object> map);

    List<CrmMillSheetSplitInfo> findMillSheetForSaleCompany(CrmMillSheetSplitInfoVO vo);

    void  updateStatus(CrmMillSheetSplitInfo record);
}