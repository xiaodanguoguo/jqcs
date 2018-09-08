package jq.steel.cs.services.base.api.vo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class PurchaseDetailVO {
    private Long id;

    // 采购需求ID

    private Long purchaseDemandId;

    // 物料大类ID
    private Long matHugeId;

    //物料大类名称
    private String matHugeName;

    // 物料中类ID
    private Long matMiddleId;

    // 物料中类名称
    private String matMiddleName;

    // 物料页类ID
    private Long matLittleId;

    // 物料页类名称
    private String matLittleName;

    // 物料ID
    private Long materialId;

    // 物料名称
    private String materialNameCn;

    // 规格型号
    private String specModel;

    // 物料材质
    private String materialTexture;

    // 物料描述
    private String materialDesc;

    // 计量单位
    private String unit;

    // 申报数量
    private String declareCount;

    // 要货期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    private Date arrivalDate;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private List<Integer> ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseDemandId() {
        return purchaseDemandId;
    }

    public void setPurchaseDemandId(Long purchaseDemandId) {
        this.purchaseDemandId = purchaseDemandId;
    }

    public Long getMatHugeId() {
        return matHugeId;
    }

    public void setMatHugeId(Long matHugeId) {
        this.matHugeId = matHugeId;
    }

    public String getMatHugeName() {
        return matHugeName;
    }

    public void setMatHugeName(String matHugeName) {
        this.matHugeName = matHugeName;
    }

    public Long getMatMiddleId() {
        return matMiddleId;
    }

    public void setMatMiddleId(Long matMiddleId) {
        this.matMiddleId = matMiddleId;
    }

    public String getMatMiddleName() {
        return matMiddleName;
    }

    public void setMatMiddleName(String matMiddleName) {
        this.matMiddleName = matMiddleName;
    }

    public Long getMatLittleId() {
        return matLittleId;
    }

    public void setMatLittleId(Long matLittleId) {
        this.matLittleId = matLittleId;
    }

    public String getMatLittleName() {
        return matLittleName;
    }

    public void setMatLittleName(String matLittleName) {
        this.matLittleName = matLittleName;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialNameCn() {
        return materialNameCn;
    }

    public void setMaterialNameCn(String materialNameCn) {
        this.materialNameCn = materialNameCn;
    }

    public String getSpecModel() {
        return specModel;
    }

    public void setSpecModel(String specModel) {
        this.specModel = specModel == null ? null : specModel.trim();
    }

    public String getMaterialTexture() {
        return materialTexture;
    }

    public void setMaterialTexture(String materialTexture) {
        this.materialTexture = materialTexture == null ? null : materialTexture.trim();
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc == null ? null : materialDesc.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getDeclareCount() {
        return declareCount;
    }

    public void setDeclareCount(String declareCount) {
        this.declareCount = declareCount;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
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

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}