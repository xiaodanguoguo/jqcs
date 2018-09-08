package jq.steel.cs.services.cust.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CrmMillSheetSplitApplyVO implements Serializable {

    //送达方
    private String shipToParty;

    private  String orgCode;

    private  String orgName;

    //质证书拆分申请明细表
    private List<CrmMillSheetSplitInfoVO> crmMillSheetSplitInfoVOS;

    private Long splitApplyId;

    private Long millsheetDetailId;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String millsheetNo;

    private String fatherMillsheetNo;

    private String qualityCertificateType;

    private String saleParty;

    private Date creationTime;

    private String furnaceNumber;

    private Integer order;

    private BigDecimal weight;

    private String licenseNumber;

    private String productCategory;

    private String specifications;


    //批/板/卷号
    private  String zcharg;

    //批次件次
    private  String zjishu;

    //批次重量
    private  String zlosmenge;

    //订货单位
    private  String spiltCustomer;

    public String getShipToParty() {
        return shipToParty;
    }

    public void setShipToParty(String shipToParty) {
        this.shipToParty = shipToParty;
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg;
    }

    public String getZjishu() {
        return zjishu;
    }

    public void setZjishu(String zjishu) {
        this.zjishu = zjishu;
    }

    public String getZlosmenge() {
        return zlosmenge;
    }

    public void setZlosmenge(String zlosmenge) {
        this.zlosmenge = zlosmenge;
    }

    public String getSpiltCustomer() {
        return spiltCustomer;
    }

    public void setSpiltCustomer(String spiltCustomer) {
        this.spiltCustomer = spiltCustomer;
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

    public List<CrmMillSheetSplitInfoVO> getCrmMillSheetSplitInfoVOS() {
        return crmMillSheetSplitInfoVOS;
    }

    public void setCrmMillSheetSplitInfoVOS(List<CrmMillSheetSplitInfoVO> crmMillSheetSplitInfoVOS) {
        this.crmMillSheetSplitInfoVOS = crmMillSheetSplitInfoVOS;
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

    public String getQualityCertificateType() {
        return qualityCertificateType;
    }

    public void setQualityCertificateType(String qualityCertificateType) {
        this.qualityCertificateType = qualityCertificateType;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
