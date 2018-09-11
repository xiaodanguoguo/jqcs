package jq.steel.cs.services.base.facade.service.sysbasics;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.base.api.vo.SysNoticeVO;
import jq.steel.cs.services.base.facade.model.SysNotice;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MrLi on 2018/7/19.
 */
public interface SysNoticeCodingService {

	/**
	 * 添加
	 * 
	 * @param sysNotice
	 * @return sysNotice
	 */
	SysNotice addSysNotice(SysNotice sysNotice);

	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	int updSysNotice(SysNotice sysNotice);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	int delSysNotice(Long id);
	
	/**
	 * 批量删除
	 * @param keys
	 * @return
	 */
	int deleteByPrimaryKeys(Set<Long> keys);

	/**
	 * 明细查询
	 * 
	 * @param id
	 * @return
	 */
	SysNotice getSysNoticeByKey(Long id);

	/**
	 * 查询全部
	 * 
	 * @param jsonRequest
	 * @return
	 */
	List<SysNotice> getSysNoticeAll(Map<String, Object> map);

	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	PageDTO<SysNoticeVO> listSysNotice(Map<String, Object> map);

}
