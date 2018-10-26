package jq.steel.cs.services.cust.api.vo;

import java.util.Date;

public class MillSheetNeedsVO {
    private Long sid;

    private String millSheetNo;

    private String speNeedUrl;

    private String speNeedName;

    private String speRealName;

    private String speNeedSize;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String type;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo == null ? null : millSheetNo.trim();
    }

    public String getSpeNeedUrl() {
        return speNeedUrl;
    }

    public void setSpeNeedUrl(String speNeedUrl) {
        this.speNeedUrl = speNeedUrl == null ? null : speNeedUrl.trim();
    }

    public String getSpeNeedName() {
        return speNeedName;
    }

    public void setSpeNeedName(String speNeedName) {
        this.speNeedName = speNeedName == null ? null : speNeedName.trim();
    }

    public String getSpeRealName() {
        return speRealName;
    }

    public void setSpeRealName(String speRealName) {
        this.speRealName = speRealName == null ? null : speRealName.trim();
    }

    public String getSpeNeedSize() {
        return speNeedSize;
    }

    public void setSpeNeedSize(String speNeedSize) {
        this.speNeedSize = speNeedSize == null ? null : speNeedSize.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}