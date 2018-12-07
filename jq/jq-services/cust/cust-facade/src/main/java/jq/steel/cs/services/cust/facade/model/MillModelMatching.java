package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class MillModelMatching {
    private Long sid;

    private String millSheetNo;

    private String modelName;

    private String companyId;

    private String prodClass;

    private String millSheetType;

    private Short totalNumColumns;

    private Short chNumColumn;

    private Short pyNumColumn;

    private Short fixedColumn3;

    private Short fixedColumn1;

    private Short fixedColumn2;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Short version;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo == null ? null : millSheetNo.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getProdClass() {
        return prodClass;
    }

    public void setProdClass(String prodClass) {
        this.prodClass = prodClass == null ? null : prodClass.trim();
    }

    public String getMillSheetType() {
        return millSheetType;
    }

    public void setMillSheetType(String millSheetType) {
        this.millSheetType = millSheetType == null ? null : millSheetType.trim();
    }

    public Short getTotalNumColumns() {
        return totalNumColumns;
    }

    public void setTotalNumColumns(Short totalNumColumns) {
        this.totalNumColumns = totalNumColumns;
    }

    public Short getChNumColumn() {
        return chNumColumn;
    }

    public void setChNumColumn(Short chNumColumn) {
        this.chNumColumn = chNumColumn;
    }

    public Short getPyNumColumn() {
        return pyNumColumn;
    }

    public void setPyNumColumn(Short pyNumColumn) {
        this.pyNumColumn = pyNumColumn;
    }

    public Short getFixedColumn3() {
        return fixedColumn3;
    }

    public void setFixedColumn3(Short fixedColumn3) {
        this.fixedColumn3 = fixedColumn3;
    }

    public Short getFixedColumn1() {
        return fixedColumn1;
    }

    public void setFixedColumn1(Short fixedColumn1) {
        this.fixedColumn1 = fixedColumn1;
    }

    public Short getFixedColumn2() {
        return fixedColumn2;
    }

    public void setFixedColumn2(Short fixedColumn2) {
        this.fixedColumn2 = fixedColumn2;
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

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }
}