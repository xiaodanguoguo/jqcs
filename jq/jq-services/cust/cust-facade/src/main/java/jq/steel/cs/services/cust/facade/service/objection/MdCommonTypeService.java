package jq.steel.cs.services.cust.facade.service.objection;

import java.util.List;
import java.util.Map;

import jq.steel.cs.services.cust.api.vo.MdCommonTypeVO;
import jq.steel.cs.services.cust.facade.model.MdCommonType;


public interface MdCommonTypeService{

	public List<MdCommonType> findByConnect(Map<String, Object> params);
	//public List<MdCommonType> findItemsByTypeId(Map<String, Object> params);
	public List<MdCommonType> findItemsByTypeId(String typeId);

	List<MdCommonTypeVO> findItemsByTypeId(MdCommonTypeVO record);
}
