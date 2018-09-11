package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.page.PageDTO;
import jq.steel.cs.services.base.api.vo.SysNoticeVO;
import jq.steel.cs.services.base.facade.dao.SysNoticeMapper;
import jq.steel.cs.services.base.facade.model.SysNotice;
import jq.steel.cs.services.base.facade.service.sysbasics.SysNoticeCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MrLi on 2018/7/19.
 */
@Service
public class SysNoticeCodingServiceImpl implements SysNoticeCodingService {

	@Autowired
	private SysNoticeMapper sysNoticeMapper;

	@Override
	public PageDTO<SysNoticeVO> listSysNotice(Map<String, Object> map) {

		/*
		 * Date stTime = (Date) map.get("stTime"); if (stTime != null) {
		 * stTime.setHours(00); stTime.setMinutes(00); stTime.setSeconds(00); }
		 * 
		 * Date enTime = (Date) map.get("enTime"); if (enTime != null) {
		 * enTime.setHours(23); enTime.setMinutes(59); enTime.setSeconds(59); }
		 * 
		 * try { PageDTOUtil.startPage(reqBody); List<SysNotice> list =
		 * sysNoticeMapper.find(reqBody);
		 * 
		 * PageDTO<SysNotice> page = PageDTOUtil.transform(list);
		 * 
		 * // 转换 PageDTO<SysNoticeVO> pageVo = new PageDTO<>(page.getPageNum(),
		 * page.getPageSize()); pageVo.setTotal(page.getTotal());
		 * List<SysNotice> resultData = page.getResultData();
		 * 
		 * List<SysNoticeVO> result = BeanCopyUtil.copyList(resultData,
		 * SysNoticeVO.class); pageVo.setResultData(result); return pageVo; }
		 * finally { PageDTOUtil.endPage(); }
		 */
		return null;
	}

	@Override
	public SysNotice addSysNotice(SysNotice sysNotice) {
		sysNotice.setCreatedTime(new Date());
		sysNotice.setUpdatedTime(new Date());
		sysNoticeMapper.insertSelective(sysNotice);
		return sysNotice;
	}

	@Override
	public int delSysNotice(Long id) {
		int i = sysNoticeMapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public List<SysNotice> getSysNoticeAll(Map<String, Object> map) {
		return sysNoticeMapper.selectSysNoticeAll(map);
	}

	@Override
	public SysNotice getSysNoticeByKey(Long id) {
		return sysNoticeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updSysNotice(SysNotice sysNotice) {
		int i = sysNoticeMapper.updateByPrimaryKeySelective(sysNotice);
		return i;
	}

	@Override
	public int deleteByPrimaryKeys(Set<Long> keys) {
		int i = sysNoticeMapper.deleteByPrimaryKeys(keys);
		return i;
	}
}
