package jq.steel.cs.services.cust.facade.service.app;

import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillLabelVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:	AppMillLabelService
 * @author 			luajiwe
 * @date 			2018-9-19 15:30
 */
public interface AppMillLabelService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<MillLabelVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<MillLabelVO> select(MillLabelVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public MillLabelVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(MillLabelVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(MillLabelVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(MillLabelVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(MillLabelVO record);

	/**
	 * 根据主键删除
	 * 
	 * @param key
	 * @return Integer
	 */
    public Integer deleteByPrimaryKey(Long key);
	
	/**
	 * 根据主键批量删除
	 * 
	 * @param keys
	 * @return Integer
	 */
    public Integer deleteByPrimaryKeys(Set<Long> keys);


	/**
     * 批量 保存、修改、删除
     * @param meMaterielTypeVOs
     * @return
     */
    //public Integer keep(List<MillLabelVO> millLabelVOs);

	/**
	 *
	 * @param jsonRequest 根据
	 * @return
	 */
	List<CrmMillCoilInfoVO> queryByQrCode(JsonRequest<String> jsonRequest);



}