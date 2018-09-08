package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 供货范围表
 * @Auther: wangyu
 */
public class SupplyScopeVO {
    private Long id;

    //供应商ID
    private Long supplierId;

    //物料类型
    private Long matTypeId;

    //物料大类码
    private String hugeCode;

    //物料大类名称
    private String hugeMaterialName;

    //物料中类码
    private String middleCode;

    //物料中类名称
    private String middleMaterialName;

    //物料页类码
    private String littleCode;

    //物料页类名称
    private String littleMaterialName;

    //物料代码
    private String materielCode;

    //物料名称
    private String materialNameCn;

    //备注
    private String remark;

    //是否冻结
    private Byte freeze;

    //创建人
    private String createdBy;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createdTime;

    //修改人
    private String updatedBy;

    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updatedTime;

    private String opt; //前端字段

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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getMatTypeId() {
        return matTypeId;
    }

    public void setMatTypeId(Long matTypeId) {
        this.matTypeId = matTypeId;
    }

    public String getHugeCode() {
        return hugeCode;
    }

    public void setHugeCode(String hugeCode) {
        this.hugeCode = hugeCode == null ? null : hugeCode.trim();
    }

    public String getHugeMaterialName() {
        return hugeMaterialName;
    }

    public void setHugeMaterialName(String hugeMaterialName) {
        this.hugeMaterialName = hugeMaterialName == null ? null : hugeMaterialName.trim();
    }

    public String getMiddleCode() {
        return middleCode;
    }

    public void setMiddleCode(String middleCode) {
        this.middleCode = middleCode == null ? null : middleCode.trim();
    }

    public String getMiddleMaterialName() {
        return middleMaterialName;
    }

    public void setMiddleMaterialName(String middleMaterialName) {
        this.middleMaterialName = middleMaterialName == null ? null : middleMaterialName.trim();
    }

    public String getLittleCode() {
        return littleCode;
    }

    public void setLittleCode(String littleCode) {
        this.littleCode = littleCode == null ? null : littleCode.trim();
    }

    public String getLittleMaterialName() {
        return littleMaterialName;
    }

    public void setLittleMaterialName(String littleMaterialName) {
        this.littleMaterialName = littleMaterialName == null ? null : littleMaterialName.trim();
    }

    public String getMaterielCode() {
        return materielCode;
    }

    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode == null ? null : materielCode.trim();
    }

    public String getMaterialNameCn() {
        return materialNameCn;
    }

    public void setMaterialNameCn(String materialNameCn) {
        this.materialNameCn = materialNameCn == null ? null : materialNameCn.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getFreeze() {
        return freeze;
    }

    public void setFreeze(Byte freeze) {
        this.freeze = freeze;
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
