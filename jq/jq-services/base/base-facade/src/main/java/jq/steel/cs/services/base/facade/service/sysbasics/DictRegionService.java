package jq.steel.cs.services.base.facade.service.sysbasics;


import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.DictRegionVO;

import java.util.List;

/**
 * @Auther: zhaoyichen
 */
public interface DictRegionService {

    // 结构树 模糊查询
    List<DictRegionVO> listDictRegion(JsonRequest<DictRegionVO> jsonRequest);

    //结构树 父查子
    List<DictRegionVO> listDictRegionTree(JsonRequest<DictRegionVO> jsonRequest);

    //结构树 子查父
    List<DictRegionVO> listDictRegionTreeSon(JsonRequest<DictRegionVO> jsonRequest);


}
