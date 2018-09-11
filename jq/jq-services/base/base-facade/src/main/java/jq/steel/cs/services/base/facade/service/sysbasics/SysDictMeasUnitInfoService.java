package jq.steel.cs.services.base.facade.service.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.SysDictMeasUnitInfoVO;

import java.util.List;

public interface SysDictMeasUnitInfoService {

    PageDTO<SysDictMeasUnitInfoVO> listSysDictMeasUnitInfo(JsonRequest<SysDictMeasUnitInfoVO> jsonRequest);

    JsonResponse keepSysDictMeasUnitInfo(JsonRequest<List<SysDictMeasUnitInfoVO>> jsonRequest);

    JsonResponse delSysDictMeasUnitInfo(JsonRequest<SysDictMeasUnitInfoVO> jsonRequest);
}
