package jq.steel.cs.services.base.facade.service.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.DictUnitTypeVO;

import java.util.List;

/**
 * @Auther: zhaotairan
 */

public interface DictUnitTypeService {

    PageDTO<DictUnitTypeVO> dictUnitTypeController(JsonRequest<DictUnitTypeVO> jsonRequest);

    JsonResponse dictUnitTypeKeep(JsonRequest<List<DictUnitTypeVO>> jsonRequest);
}
