package jq.steel.cs.services.base.api.vo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PurchaseDetailExcelVO {

    //物料大类名称
    private String matHugeName;

    // 物料中类名称
    private String matMiddleName;

    // 物料页类名称
    private String matLittleName;

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

    public String getMatHugeName() {
        return matHugeName;
    }

    public void setMatHugeName(String matHugeName) {
        this.matHugeName = matHugeName;
    }


    public String getMatMiddleName() {
        return matMiddleName;
    }

    public void setMatMiddleName(String matMiddleName) {
        this.matMiddleName = matMiddleName;
    }

    public String getMatLittleName() {
        return matLittleName;
    }

    public void setMatLittleName(String matLittleName) {
        this.matLittleName = matLittleName;
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
}