package jq.steel.cs.services.cust.facade.service.app;

import jq.steel.cs.services.cust.api.vo.CrmAnnouncementVO;

import java.util.List;
import java.util.Set;

/**
 * dal Interface:CrmAnnouncement
 * @author 
 * @date 2018-9-14
 */
public interface AppCrmAnnouncementService {

	/**
	 * 查询全部
	 * 
	 * @param
	 * @return List
	 */
    public List<CrmAnnouncementVO> selectAll();

	/**
	 * 条件查询
	 * 
	 * @param record
	 * @return List
	 */
    public List<CrmAnnouncementVO> select(CrmAnnouncementVO record);

	/**
	 * 查询一条
	 * 
	 * @param key
	 * @return VO
	 */
    public CrmAnnouncementVO selectByPrimaryKey(Long key);

	/**
	 * 增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insert(CrmAnnouncementVO record);
    
    /**
	 * 非空增加
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer insertSelective(CrmAnnouncementVO record);
	
	/**
	 * 根据主键更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKey(CrmAnnouncementVO record);
    
    /**
	 * 根据主键非空更新
	 * 
	 * @param record
	 * @return Integer
	 */
    public Integer updateByPrimaryKeySelective(CrmAnnouncementVO record);

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
     * @param crmAnnouncementVOs
     * @return
     */
    public Integer keep(List<CrmAnnouncementVO> crmAnnouncementVOs);

	public CrmAnnouncementVO getNewAnnouncement();
}