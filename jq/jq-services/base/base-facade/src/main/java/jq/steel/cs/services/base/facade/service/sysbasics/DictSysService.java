package jq.steel.cs.services.base.facade.service.sysbasics;

import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.DictSysVO;

import java.util.List;

/**
 * @Auther: zhaotairan
 */
public interface DictSysService {
    PageDTO<DictSysVO> dictSysList(JsonRequest<DictSysVO> jsonRequest);

    JsonResponse dictSysKeep(JsonRequest<List<DictSysVO>> jsonRequest);

    PageDTO<DictSysVO> dictSysListByType(JsonRequest<DictSysVO> jsonRequest);

    List<DictSysVO> dictSysByTypeList(DictSysVO dictSysVO);
}
