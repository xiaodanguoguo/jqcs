package jq.steel.cs.services.cust.facade.service.millsheet;

import java.util.List;
import java.util.Map;
import jq.steel.cs.services.cust.facade.model.MdCommonType;


public interface MdCommonTypeService{

	public List<MdCommonType> findByConnect(Map<String, Object> params);
	public List<MdCommonType> findItemsByTypeId(Map<String, Object> params);
	public List<MdCommonType> findItemsByTypeId(String typeId);
	
	/**
	 * 
	 * 通过TypeName获取TypeId
	 * @author maoguoqing
	 * @version 2018年9月4日
	 * @param 
	 * @return 
	 * @throws
	 */
	public List<MdCommonType> findTypeIdByTypeName(Map<String, Object> params);
	
}
