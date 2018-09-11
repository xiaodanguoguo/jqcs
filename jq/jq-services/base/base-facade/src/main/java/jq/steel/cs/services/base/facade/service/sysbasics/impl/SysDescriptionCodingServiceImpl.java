package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import jq.steel.cs.services.base.facade.dao.SysDescriptionMapper;
import jq.steel.cs.services.base.facade.model.SysDescription;
import jq.steel.cs.services.base.facade.service.sysbasics.SysDescriptionCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MrLi on 2018/7/23.
 */
@Service
public class SysDescriptionCodingServiceImpl implements SysDescriptionCodingService {

//    private static Logger LOG = LoggerFactory.getLogger(SysDescriptionCodingServiceImpl.class);

    @Autowired
    private SysDescriptionMapper sysDescriptionMapper;

    @Override
    public List<SysDescription> listSysDescription(String keyword, Long status) {
    	return sysDescriptionMapper.find(keyword, status);
    }

	@Override
	public int addSysDescription(SysDescription sysDescription) {
		return sysDescriptionMapper.insertSelective(sysDescription);
	}

	@Override
	public int updSysDescription(SysDescription sysDescription) {
		return sysDescriptionMapper.updateByPrimaryKeySelective(sysDescription);
	}

	@Override
	public int delSysDescription(Long id) {
		return sysDescriptionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SysDescription getSysDescriptionByKey(Long id) {
		return sysDescriptionMapper.selectByPrimaryKey(id);
	}
}
