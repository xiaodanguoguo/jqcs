package jq.steel.cs.services.cust.facade.service.app;

import jq.steel.cs.services.cust.api.vo.CrmVersionUpdateVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:CrmVersionUpdate
 * @author 
 * @date 2018-9-13
 */
public interface AppCrmVersionUpdateService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<CrmVersionUpdateVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<CrmVersionUpdateVO> select(CrmVersionUpdateVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public CrmVersionUpdateVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(CrmVersionUpdateVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(CrmVersionUpdateVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(CrmVersionUpdateVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(CrmVersionUpdateVO record);

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
    public Integer keep(List<CrmVersionUpdateVO> crmVersionUpdateVOs);

	/**
	 * 返回最新的版本信息
	 * @return
	 */
	public CrmVersionUpdateVO getNewVerson();
}