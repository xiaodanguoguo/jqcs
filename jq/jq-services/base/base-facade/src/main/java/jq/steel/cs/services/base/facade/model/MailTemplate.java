package jq.steel.cs.services.base.facade.model;

import java.io.Serializable;
import java.util.Date;

/**
 * entity:MailTemplate
 * 
 * @author gencode
 * @date 2017-9-19
 */
public class MailTemplate implements Serializable {
	
	private static final long serialVersionUID = -5302204159015946940L;
	
	private Long	templateId;		 /* 模板标识 */ 
	private Long	senderId;		 /* 发送配置标识 */ 
	private String	templateCode;		 /* 模板代码 */ 
	private String	templateName;		 /* 模板名称 */ 
	private String	templateContent;		 /* 模板内容 */ 
	private Integer	mailTemplateType;		 /* 邮件模板类型 */ 
	private Integer	status;		 /* 状态 */ 
	private Integer	timeout;		 /* 超时时间 */ 
	private Date	createTime;		 /* 创建时间 */ 
	private String	memo;		 /* 备注 */

	private String templateTypeName;
	private String statusName;

	private Integer pageSize = 10;

	private Integer pageNum = 1;

	// Constructor
	public MailTemplate() {
	}

	/**
	 * full Constructor
	 */
	public MailTemplate(Long templateId, Long senderId, String templateCode, String templateName, String templateContent, Integer mailTemplateType, Integer status, Integer timeout, Date createTime) {
		this.templateId = templateId;
		this.senderId = senderId;
		this.templateCode = templateCode;
		this.templateName = templateName;
		this.templateContent = templateContent;
		this.mailTemplateType = mailTemplateType;
		this.status = status;
		this.timeout = timeout;
		this.createTime = createTime;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public Integer getMailTemplateType() {
		return mailTemplateType;
	}

	public void setMailTemplateType(Integer mailTemplateType) {
		this.mailTemplateType = mailTemplateType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "MailTemplate [" + "templateId=" + templateId+ ", senderId=" + senderId+ ", templateCode=" + templateCode+ ", templateName=" + templateName+ ", templateContent=" + templateContent+ ", mailTemplateType=" + mailTemplateType+ ", status=" + status+ ", timeout=" + timeout+ ", createTime=" + createTime+  "]";
	}

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
