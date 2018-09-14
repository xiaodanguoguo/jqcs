package jq.steel.cs.services.cust.facade.service.objection.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.MdCommonTypeVO;
import jq.steel.cs.services.cust.facade.dao.MdCommonTypeMapper;
import jq.steel.cs.services.cust.facade.model.MdCommonType;
import jq.steel.cs.services.cust.facade.service.objection.MdCommonTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MdCommonTypeServiceImpl implements MdCommonTypeService{
	@Autowired
	private MdCommonTypeMapper mdCommonTypeMapper;

	public List<MdCommonType> findByConnect(Map<String, Object> params) {
		 return this.mdCommonTypeMapper.findByConnect(params);
	 }

	public List<MdCommonType> findItemsByTypeId(Map<String, Object> params) {
		 return this.mdCommonTypeMapper.findItemsByTypeId(params);
	 }

	@Override
	public List<MdCommonType> findItemsByTypeId(String typeId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("typeId", typeId);
		return this.mdCommonTypeMapper.findItemsByTypeId(params);
	}

	@Override
	public List<MdCommonTypeVO> findItemsByTypeId(MdCommonTypeVO record) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("typeId", record.getTypeId());
		List<MdCommonType> mdCommonTypes =  this.mdCommonTypeMapper.findItemsByTypeId(params);
		List<MdCommonTypeVO> mdCommonTypeVOS = BeanCopyUtil.copyList(mdCommonTypes,MdCommonTypeVO.class);
		return mdCommonTypeVOS;
	}
}
