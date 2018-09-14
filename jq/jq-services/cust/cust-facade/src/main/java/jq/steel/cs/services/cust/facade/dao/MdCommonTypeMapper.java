package jq.steel.cs.services.cust.facade.dao;

import java.util.List;
import java.util.Map;
import jq.steel.cs.services.cust.facade.model.MdCommonType;


public interface MdCommonTypeMapper{
	public List<MdCommonType> findByConnect(Map<String, Object> params);
	public List<MdCommonType> findItemsByTypeId(Map<String, Object> params);
	MdCommonType find (MdCommonType record);
}
