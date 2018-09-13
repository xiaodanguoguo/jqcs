package jq.steel.cs.services.cust.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CrmClaimInfo {

    //强制结案原因
    private  String reasonsForCompulsoryClosure;

    private String agreementUrl;

    private String agreementName;

    private String agreementState;

    private  String deptCode;

    private String manufactor;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;
    private String endDtStr;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;

    private String startDtStr;

    private Long sid;

    private String claimNo;

    private Long productId;

    private String productName;

    private String millSheetNo;

    private String customerId;

    private String customerName;

    private String custAddr;

    private String custEmpNo;

    private String custTel;

    private String lastUserId;

    private String lastUser;

    private String lastUserAddr;

    private String createEmpNo;

    private String lastUserTel;

    private String lastUserEmail;

    private String steelType;

    private String claimType;

    private String battenPlateNo;

    private String proProblem;

    private String designation;

    private String used;

    private String contractNo;

    private BigDecimal contractVolume;

    //private String sizeMark;

    private  String  specs;

    private BigDecimal originalWeight;

    private String orderNo;

    private String originalCarNo;

    private BigDecimal contractUnitPrice;

    private BigDecimal objectionNum;

    private String endProcessingTech;

    private String claimDesc;

    private String claimReasion;

    private String claimState;

    private String filePath;

    private String checkState;

    private String rejectReason;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private Integer optionType;

    private Integer optionStuts;

    //说明是否设置默认联系人
    private String explain;

    private Integer dissentingUnit;

    //调查状态
    private  String inquireState;

    //提报时间
    private Date presentationDate;

    //提报人
    private  String presentationUser;

    public String getReasonsForCompulsoryClosure() {
        return reasonsForCompulsoryClosure;
    }

    public void setReasonsForCompulsoryClosure(String reasonsForCompulsoryClosure) {
        this.reasonsForCompulsoryClosure = reasonsForCompulsoryClosure;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getAgreementState() {
        return agreementState;
    }

    public void setAgreementState(String agreementState) {
        this.agreementState = agreementState;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }


    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Date getPresentationDate() {
        return presentationDate;
    }

    public void setPresentationDate(Date presentationDate) {
        this.presentationDate = presentationDate;
    }

    public String getPresentationUser() {
        return presentationUser;
    }

    public void setPresentationUser(String presentationUser) {
        this.presentationUser = presentationUser;
    }

    public String getEndDtStr() {
        return endDtStr;
    }

    public void setEndDtStr(String endDtStr) {
        this.endDtStr = endDtStr;
    }

    public String getStartDtStr() {
        return startDtStr;
    }

    public void setStartDtStr(String startDtStr) {
        this.startDtStr = startDtStr;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getInquireState() {
        return inquireState;
    }

    public void setInquireState(String inquireState) {
        this.inquireState = inquireState;
    }

    public Integer getDissentingUnit() {
        return dissentingUnit;
    }

    public void setDissentingUnit(Integer dissentingUnit) {
        this.dissentingUnit = dissentingUnit;
    }


    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getOptionStuts() {
        return optionStuts;
    }

    public void setOptionStuts(Integer optionStuts) {
        this.optionStuts = optionStuts;
    }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo == null ? null : claimNo.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo == null ? null : millSheetNo.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustAddr() {
        return custAddr;
    }

    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr == null ? null : custAddr.trim();
    }

    public String getCustEmpNo() {
        return custEmpNo;
    }

    public void setCustEmpNo(String custEmpNo) {
        this.custEmpNo = custEmpNo == null ? null : custEmpNo.trim();
    }

    public String getCustTel() {
        return custTel;
    }

    public void setCustTel(String custTel) {
        this.custTel = custTel == null ? null : custTel.trim();
    }

    public String getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId == null ? null : lastUserId.trim();
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public String getLastUserAddr() {
        return lastUserAddr;
    }

    public void setLastUserAddr(String lastUserAddr) {
        this.lastUserAddr = lastUserAddr == null ? null : lastUserAddr.trim();
    }

    public String getCreateEmpNo() {
        return createEmpNo;
    }

    public void setCreateEmpNo(String createEmpNo) {
        this.createEmpNo = createEmpNo == null ? null : createEmpNo.trim();
    }

    public String getLastUserTel() {
        return lastUserTel;
    }

    public void setLastUserTel(String lastUserTel) {
        this.lastUserTel = lastUserTel == null ? null : lastUserTel.trim();
    }

    public String getLastUserEmail() {
        return lastUserEmail;
    }

    public void setLastUserEmail(String lastUserEmail) {
        this.lastUserEmail = lastUserEmail == null ? null : lastUserEmail.trim();
    }

    public String getSteelType() {
        return steelType;
    }

    public void setSteelType(String steelType) {
        this.steelType = steelType == null ? null : steelType.trim();
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType == null ? null : claimType.trim();
    }

    public String getBattenPlateNo() {
        return battenPlateNo;
    }

    public void setBattenPlateNo(String battenPlateNo) {
        this.battenPlateNo = battenPlateNo == null ? null : battenPlateNo.trim();
    }

    public String getProProblem() {
        return proProblem;
    }

    public void setProProblem(String proProblem) {
        this.proProblem = proProblem == null ? null : proProblem.trim();
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation == null ? null : designation.trim();
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used == null ? null : used.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public BigDecimal getContractVolume() {
        return contractVolume;
    }

    public void setContractVolume(BigDecimal contractVolume) {
        this.contractVolume = contractVolume;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public BigDecimal getOriginalWeight() {
        return originalWeight;
    }

    public void setOriginalWeight(BigDecimal originalWeight) {
        this.originalWeight = originalWeight;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOriginalCarNo() {
        return originalCarNo;
    }

    public void setOriginalCarNo(String originalCarNo) {
        this.originalCarNo = originalCarNo == null ? null : originalCarNo.trim();
    }

    public BigDecimal getContractUnitPrice() {
        return contractUnitPrice;
    }

    public void setContractUnitPrice(BigDecimal contractUnitPrice) {
        this.contractUnitPrice = contractUnitPrice;
    }

    public BigDecimal getObjectionNum() {
        return objectionNum;
    }

    public void setObjectionNum(BigDecimal objectionNum) {
        this.objectionNum = objectionNum;
    }

    public String getEndProcessingTech() {
        return endProcessingTech;
    }

    public void setEndProcessingTech(String endProcessingTech) {
        this.endProcessingTech = endProcessingTech == null ? null : endProcessingTech.trim();
    }

    public String getClaimDesc() {
        return claimDesc;
    }

    public void setClaimDesc(String claimDesc) {
        this.claimDesc = claimDesc == null ? null : claimDesc.trim();
    }

    public String getClaimReasion() {
        return claimReasion;
    }

    public void setClaimReasion(String claimReasion) {
        this.claimReasion = claimReasion == null ? null : claimReasion.trim();
    }

    public String getClaimState() {
        return claimState;
    }

    public void setClaimState(String claimState) {
        this.claimState = claimState == null ? null : claimState.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState == null ? null : checkState.trim();
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
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
}