package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class CrmAgreementSteps {
    private Long sid;

    private String caseCode;

    private Long confirmBy;

    private Date confirmDt;

    private String confirmStatus;

    private String confirmInfo;

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

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public Long getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(Long confirmBy) {
        this.confirmBy = confirmBy;
    }

    public Date getConfirmDt() {
        return confirmDt;
    }

    public void setConfirmDt(Date confirmDt) {
        this.confirmDt = confirmDt;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus == null ? null : confirmStatus.trim();
    }

    public String getConfirmInfo() {
        return confirmInfo;
    }

    public void setConfirmInfo(String confirmInfo) {
        this.confirmInfo = confirmInfo == null ? null : confirmInfo.trim();
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