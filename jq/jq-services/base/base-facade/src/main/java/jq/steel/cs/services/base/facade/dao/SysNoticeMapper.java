package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.api.vo.SysNoticeVO;
import jq.steel.cs.services.base.facade.model.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysNoticeMapper {
	int deleteByPrimaryKey(Long keys);
	
	int deleteByPrimaryKeys(@Param("keys") Set<Long> keys);

	int insert(SysNotice record);

	int insertSelective(SysNotice record);

	SysNotice selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysNotice record);

	int updateByPrimaryKey(SysNotice record);

	List<SysNotice> find(SysNoticeVO sysNoticeVo);

	List<SysNotice> selectSysNoticeAll(Map<String, Object> map);
}