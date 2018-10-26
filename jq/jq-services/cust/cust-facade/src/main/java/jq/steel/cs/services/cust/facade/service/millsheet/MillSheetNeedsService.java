package jq.steel.cs.services.cust.facade.service.millsheet;

import jq.steel.cs.services.cust.api.vo.MillSheetNeedsVO;
import jq.steel.cs.services.cust.facade.model.MillSheetNeeds;

import java.util.List;

public interface MillSheetNeedsService {

    //查询特殊需求文件地址
    List<MillSheetNeedsVO> findByType(MillSheetNeedsVO record);
}
