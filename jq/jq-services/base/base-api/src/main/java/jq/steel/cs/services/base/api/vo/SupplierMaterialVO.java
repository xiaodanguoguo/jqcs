package jq.steel.cs.services.base.api.vo;

import java.util.Date;

/**
 * 资质信息
 * @Auther: wangyu
 */
public class SupplierMaterialVO {
    private Long id;

    private Long supId;

    private Long materialId;

    private Byte supType;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private EntClientInfoVO entClientInfo;

    private String opt;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupId() {
        return supId;
    }

    public void setSupId(Long supId) {
        this.supId = supId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Byte getSupType() {
        return supType;
    }

    public void setSupType(Byte supType) {
        this.supType = supType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public EntClientInfoVO getEntClientInfo() {
        return entClientInfo;
    }

    public void setEntClientInfo(EntClientInfoVO entClientInfo) {
        this.entClientInfo = entClientInfo;
    }
}
