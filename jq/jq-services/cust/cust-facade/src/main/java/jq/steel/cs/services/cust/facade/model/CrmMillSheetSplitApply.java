package jq.steel.cs.services.cust.facade.model;

import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CrmMillSheetSplitApply {

    //private List<CrmMillSheetSplitInfo> crmMillSheetSplitInfos;

    private List<CrmMillSheetSplitInfoVO> crmMillSheetSplitInfoVOS;

    public List<CrmMillSheetSplitInfoVO> getCrmMillSheetSplitInfoVOS() {
        return crmMillSheetSplitInfoVOS;
    }

    public void setCrmMillSheetSplitInfoVOS(List<CrmMillSheetSplitInfoVO> crmMillSheetSplitInfoVOS) {
        this.crmMillSheetSplitInfoVOS = crmMillSheetSplitInfoVOS;
    }

    private Long splitApplyId;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String millsheetNo;

    private String fatherMillsheetNo;

    //private String qualityCertificateType;
    private String millSheetType;

    private String saleParty;

    private Date creationTime;

    private String productCategory;

    private String shipToParty;

    private String status;

    //批/板/卷号
    private  String zcharg;

    //批次件次
    private  Long zjishu;

    //批次重量
    private BigDecimal zlosmenge;

    //订货单位
    private  String spiltCustomer;


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

    public String getSpiltCustomer() {
        return spiltCustomer;
    }

    public void setSpiltCustomer(String spiltCustomer) {
        this.spiltCustomer = spiltCustomer;
    }

    public Long getSplitApplyId() {
        return splitApplyId;
    }

    public void setSplitApplyId(Long splitApplyId) {
        this.splitApplyId = splitApplyId;
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

    public String getMillsheetNo() {
        return millsheetNo;
    }

    public void setMillsheetNo(String millsheetNo) {
        this.millsheetNo = millsheetNo == null ? null : millsheetNo.trim();
    }

    public String getFatherMillsheetNo() {
        return fatherMillsheetNo;
    }

    public void setFatherMillsheetNo(String fatherMillsheetNo) {
        this.fatherMillsheetNo = fatherMillsheetNo == null ? null : fatherMillsheetNo.trim();
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
        this.saleParty = saleParty == null ? null : saleParty.trim();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory == null ? null : productCategory.trim();
    }

    public String getShipToParty() {
        return shipToParty;
    }

    public void setShipToParty(String shipToParty) {
        this.shipToParty = shipToParty == null ? null : shipToParty.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}