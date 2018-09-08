package jq.steel.cs.services.base.facade.model;

import java.util.Date;

public class DictTransactionType {
    private Long id;

    private String typeCode;

    private String typeName;

    private Byte isUsing;

    private Byte isReceive;

    private Byte isWarehousing;

    private Byte isRecoil;

    private Byte isReconciliations;

    private Byte isReturn;

    private Byte isReturnToReceive;

    private Byte status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Byte getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Byte isUsing) {
        this.isUsing = isUsing;
    }

    public Byte getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Byte isReceive) {
        this.isReceive = isReceive;
    }

    public Byte getIsWarehousing() {
        return isWarehousing;
    }

    public void setIsWarehousing(Byte isWarehousing) {
        this.isWarehousing = isWarehousing;
    }

    public Byte getIsRecoil() {
        return isRecoil;
    }

    public void setIsRecoil(Byte isRecoil) {
        this.isRecoil = isRecoil;
    }

    public Byte getIsReconciliations() {
        return isReconciliations;
    }

    public void setIsReconciliations(Byte isReconciliations) {
        this.isReconciliations = isReconciliations;
    }

    public Byte getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Byte isReturn) {
        this.isReturn = isReturn;
    }

    public Byte getIsReturnToReceive() {
        return isReturnToReceive;
    }

    public void setIsReturnToReceive(Byte isReturnToReceive) {
        this.isReturnToReceive = isReturnToReceive;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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