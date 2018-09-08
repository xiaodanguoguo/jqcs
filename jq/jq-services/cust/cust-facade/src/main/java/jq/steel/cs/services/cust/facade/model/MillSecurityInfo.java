package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class MillSecurityInfo {

    //附件地址
    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    private  String explain;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
    private Long sid;

    private String millSheetNo;

    private String securityCode;

    private Short enCheckNum;

    private Short enCheckNumMax;

    private Short coCheckNum;

    private Short coCheckNumMax;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

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

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode == null ? null : securityCode.trim();
    }

    public Short getEnCheckNum() {
        return enCheckNum;
    }

    public void setEnCheckNum(Short enCheckNum) {
        this.enCheckNum = enCheckNum;
    }

    public Short getEnCheckNumMax() {
        return enCheckNumMax;
    }

    public void setEnCheckNumMax(Short enCheckNumMax) {
        this.enCheckNumMax = enCheckNumMax;
    }

    public Short getCoCheckNum() {
        return coCheckNum;
    }

    public void setCoCheckNum(Short coCheckNum) {
        this.coCheckNum = coCheckNum;
    }

    public Short getCoCheckNumMax() {
        return coCheckNumMax;
    }

    public void setCoCheckNumMax(Short coCheckNumMax) {
        this.coCheckNumMax = coCheckNumMax;
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
}