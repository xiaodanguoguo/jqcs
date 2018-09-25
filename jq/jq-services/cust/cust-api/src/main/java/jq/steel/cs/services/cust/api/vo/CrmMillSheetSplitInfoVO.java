package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CrmMillSheetSplitInfoVO implements Serializable {

    //规格
    private String specs;
    //产品类别
    private String zcpmc;

    //炉 (批) 号
    private String zlph;

    //批/板/卷号
    private  String zcharg;

    //批次件次
    private  Long zjishu;

    //批次重量
    private BigDecimal zlosmenge;

    //送达方
    private  String shipToParty;

    //售达方
    private String zkunnr;

    private  String orgCode;

    private  String orgName;

    private Long splitApplyId;

    private Long millsheetDetailId;

    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String millsheetNo;

    private String fatherMillsheetNo;

    private String millSheetType;
    //private String qualityCertificateType;

    private String saleParty;

    private Date creationTime;

    private String furnaceNumber;


    private BigDecimal weight;

    private String licenseNumber;

    private String productCategory;

    private String specifications;

    //zchehao
    private String zchehao;

    public String getZchehao() {
        return zchehao;
    }

    public void setZchehao(String zchehao) {
        this.zchehao = zchehao;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getZcpmc() {
        return zcpmc;
    }

    public void setZcpmc(String zcpmc) {
        this.zcpmc = zcpmc;
    }

    public String getZlph() {
        return zlph;
    }

    public void setZlph(String zlph) {
        this.zlph = zlph;
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg;
    }

    public Long getZjishu() {
        return zjishu;
    }

    public void setZjishu(Long zjishu) {
        this.zjishu = zjishu;
    }

    public BigDecimal getZlosmenge() {
        return zlosmenge;
    }

    public void setZlosmenge(BigDecimal zlosmenge) {
        this.zlosmenge = zlosmenge;
    }

    public String getShipToParty() {
        return shipToParty;
    }

    public void setShipToParty(String shipToParty) {
        this.shipToParty = shipToParty;
    }

    public String getZkunnr() {
        return zkunnr;
    }

    public void setZkunnr(String zkunnr) {
        this.zkunnr = zkunnr;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getSplitApplyId() {
        return splitApplyId;
    }

    public void setSplitApplyId(Long splitApplyId) {
        this.splitApplyId = splitApplyId;
    }

    public Long getMillsheetDetailId() {
        return millsheetDetailId;
    }

    public void setMillsheetDetailId(Long millsheetDetailId) {
        this.millsheetDetailId = millsheetDetailId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        this.updatedBy = updatedBy;
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

    public String getMillsheetNo() {
        return millsheetNo;
    }

    public void setMillsheetNo(String millsheetNo) {
        this.millsheetNo = millsheetNo;
    }

    public String getFatherMillsheetNo() {
        return fatherMillsheetNo;
    }

    public void setFatherMillsheetNo(String fatherMillsheetNo) {
        this.fatherMillsheetNo = fatherMillsheetNo;
    }

    public String getMillSheetType() {
        return millSheetType;
    }

    public void setMillSheetType(String millSheetType) {
        this.millSheetType = millSheetType;
    }

    public String getSaleParty() {
        return saleParty;
    }

    public void setSaleParty(String saleParty) {
        this.saleParty = saleParty;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getFurnaceNumber() {
        return furnaceNumber;
    }

    public void setFurnaceNumber(String furnaceNumber) {
        this.furnaceNumber = furnaceNumber;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
