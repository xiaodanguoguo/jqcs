package jq.steel.cs.services.base.facade.service.sysbasics;


import jq.steel.cs.services.base.facade.model.SysDescription;

import java.util.List;

/**
 * Created by MrLi on 2018/7/23.
 */
public interface SysDescriptionCodingService {
	
	/**
	 * 分页查询
	 * @param jsonRequest
	 * @return
	 */
	List<SysDescription> listSysDescription(String keyword, Long status);
//    PageDTO<SysDescriptionVO> listSysDescription(JsonRequest<SysDescriptionVO> jsonRequest);

	/**
	 * 添加
	 * @param sysDescription
	 * @return
	 */
    int addSysDescription(SysDescription sysDescription);
    
    /**
     * 修改
     * @param sysDescription
     * @return
     */
    int updSysDescription(SysDescription sysDescription);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int delSysDescription(Long id);
    
    /**
     * 明细查询
     * @param id
     * @return
     */
    SysDescription getSysDescriptionByKey(Long id);

}
