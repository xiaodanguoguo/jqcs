package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SupplierRecommendVO {
    private Long id;

    private Long supplierId;

    private String recoApplyNumber;

    private Byte applyFormStatus;

    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createdTime;

    private String updatedBy;
    
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getRecoApplyNumber() {
        return recoApplyNumber;
    }

    public void setRecoApplyNumber(String recoApplyNumber) {
        this.recoApplyNumber = recoApplyNumber == null ? null : recoApplyNumber.trim();
    }

    public Byte getApplyFormStatus() {
        return applyFormStatus;
    }

    public void setApplyFormStatus(Byte applyFormStatus) {
        this.applyFormStatus = applyFormStatus;
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
}