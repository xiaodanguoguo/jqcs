package jq.steel.cs.services.base.facade.model;


import jq.steel.cs.services.base.api.enumvo.Status;

import java.util.Date;

public class SysDescription {

//    主鍵
    private Long id;

//    描述字段
    private String desField;

//    语言
    private String language;

//    描述
    private String description;

//    状态
    private Status status;

//    创建人
    private String createdBy;

//    创建时间
    private Date createdTime;

//    修改人
    private String updatedBy;

//    修改时间
    private Date updatedTime;

    private String opt; //标记是什么数据

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesField() {
        return desField;
    }

    public void setDesField(String desField) {
        this.desField = desField == null ? null : desField.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatus() {
    	if(status != null)
    		return status.getCode();
		return "";
	}

	public void setStatus(String code) {
		this.status = Status.getStatus(code);
	}

	public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}