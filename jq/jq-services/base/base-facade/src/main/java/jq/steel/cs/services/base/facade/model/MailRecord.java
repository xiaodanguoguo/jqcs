package jq.steel.cs.services.base.facade.model;

import java.io.Serializable;
import java.util.Date;

/**
 * entity:MailRecord
 * 
 * @author gencode
 * @date 2017-9-19
 */
public class MailRecord implements Serializable {
	
	private static final long serialVersionUID = 686882270234841515L;
	
	private Long	recordId;		 /* 记录标识 */ 
	private Long	templateId;		 /* 模板标识 */ 
	private String	email;		 /* 收件人邮箱 */ 
	private String	mailTitle;		 /* 邮件标题 */ 
	private String	mailContent;		 /* 邮件内容 */ 
	private Integer	status;		 /* 状态 */ 
	private Date	createTime;		 /* 创建时间 */ 
	private Date	sendTime;		 /* 发送时间 */ 
	private Date	recvTime;		 /* 接收时间 */ 
	private String	errorCode;		 /* 错误编码 */

	private String errorMsg; /* 错误编码对应的原因 */

	private Integer mailTemplateType; /* 模板类型 */

	/* 模板名称 */
	private String templateName;


	private String templateTypeName;

	private String statusName;

	// Constructor
	public MailRecord() {
	}

	/**
	 * full Constructor
	 */
	public MailRecord(Long recordId, Long templateId, String email, String mailTitle, String mailContent, Integer status, Date createTime, Date sendTime, Date recvTime, String errorCode) {
		this.recordId = recordId;
		this.templateId = templateId;
		this.email = email;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		this.status = status;
		this.createTime = createTime;
		this.sendTime = sendTime;
		this.recvTime = recvTime;
		this.errorCode = errorCode;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(Date recvTime) {
		this.recvTime = recvTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "MailRecord [" + "recordId=" + recordId+ ", templateId=" + templateId+ ", email=" + email+ ", mailTitle=" + mailTitle+ ", mailContent=" + mailContent+ ", status=" + status+ ", createTime=" + createTime+ ", sendTime=" + sendTime+ ", recvTime=" + recvTime+ ", errorCode=" + errorCode+  "]";
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getMailTemplateType() {
		return mailTemplateType;
	}

	public void setMailTemplateType(Integer mailTemplateType) {
		this.mailTemplateType = mailTemplateType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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
}
