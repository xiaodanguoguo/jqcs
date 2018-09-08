package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.MailTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dal Interface:MailTemplate
 * 
 * @author gencode
 * @date 2017-9-19
 */
public interface MailTemplateDao {

	Integer insert(MailTemplate record);

	Integer insertSelective(MailTemplate record);

	Integer delete(MailTemplate record);

	Integer deleteByPrimaryKey(@Param("templateId") Long templateId);

	Integer updateByPrimaryKey(MailTemplate record);

	List<MailTemplate> findAll();

	List<MailTemplate> find(MailTemplate record);

	Integer getCount(MailTemplate record);

	MailTemplate getTemplateByCode(@Param("templateCode") String templateCode);

	MailTemplate getByPrimaryKey(@Param("templateId") Long templateId);

}