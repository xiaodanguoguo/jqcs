package jq.steel.cs.services.cust.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CrmClaimApply {

    private String phone;

    private String acctName;

    //提报日期导出字段转换
    private String ast;

    //结案时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date closingTime;

    //结案人
    private String closingUser;

    private  String parentSid;
    //调查状态
    private  String inquireState;

    private String manufactor;

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    //规格
    private String specs;

    //产品名称
    private  String zcpmc;

    //牌号
    private String zph;

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

    private String battenPlateNo;

    private String designation;

    private String used;

    private String contractNo;

    private BigDecimal contractVolume;

    private String sizeMark;

    private BigDecimal originalWeight;

    private String orderNo;

    private String originalCarNo;

    private BigDecimal contractUnitPrice;

    private BigDecimal objectionNum;

    private String endProcessingTech;

    private String claimDesc;

    private String claimReason;

    private String steelType;

    private String proProblem;

    private String proDetail;

    private String claimState;

    private String filePath;

    private String rejectReason;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String claimType;

    private Integer optionStuts;

    private Integer optionType;

    private Integer dissentingUnit;

    //受理时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date admissibilityTime;

    //提报时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date presentationDate;

    //受理人
    private  String admissibilityUser;

    //提报人
    private  String presentationUser;

    //强制结案原因
    private  String reasonsForCompulsoryClosure;

    //管理单位代码（1000：不锈钢厂2000：炼轧厂2200：碳钢薄板厂3000：榆钢工厂）
    private  String deptCode;

    //质量异议报告图片
    private String reportPictures;

    //异议确认量
    private  String objectionConfirmation;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAst() {
        return ast;
    }

    public void setAst(String ast) {
        this.ast = ast;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getClosingUser() {
        return closingUser;
    }

    public void setClosingUser(String closingUser) {
        this.closingUser = closingUser;
    }

    public String getParentSid() {
        return parentSid;
    }

    public void setParentSid(String parentSid) {
        this.parentSid = parentSid;
    }

    public String getReportPictures() {
        return reportPictures;
    }

    public void setReportPictures(String reportPictures) {
        this.reportPictures = reportPictures;
    }

    public String getObjectionConfirmation() {
        return objectionConfirmation;
    }

    public void setObjectionConfirmation(String objectionConfirmation) {
        this.objectionConfirmation = objectionConfirmation;
    }

    public String getInquireState() {
        return inquireState;
    }

    public void setInquireState(String inquireState) {
        this.inquireState = inquireState;
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

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Date getAdmissibilityTime() {
        return admissibilityTime;
    }

    public void setAdmissibilityTime(Date admissibilityTime) {
        this.admissibilityTime = admissibilityTime;
    }

    public Date getPresentationDate() {
        return presentationDate;
    }

    public void setPresentationDate(Date presentationDate) {
        this.presentationDate = presentationDate;
    }

    public String getAdmissibilityUser() {
        return admissibilityUser;
    }

    public void setAdmissibilityUser(String admissibilityUser) {
        this.admissibilityUser = admissibilityUser;
    }

    public String getPresentationUser() {
        return presentationUser;
    }

    public void setPresentationUser(String presentationUser) {
        this.presentationUser = presentationUser;
    }

    public String getReasonsForCompulsoryClosure() {
        return reasonsForCompulsoryClosure;
    }

    public void setReasonsForCompulsoryClosure(String reasonsForCompulsoryClosure) {
        this.reasonsForCompulsoryClosure = reasonsForCompulsoryClosure;
    }

    public Integer getDissentingUnit() {
        return dissentingUnit;
    }

    public void setDissentingUnit(Integer dissentingUnit) {
        this.dissentingUnit = dissentingUnit;
    }

    //说明是否设置默认联系人
    private String explain;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public Integer getOptionStuts() {
        return optionStuts;
    }

    public void setOptionStuts(Integer optionStuts) {
        this.optionStuts = optionStuts;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
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

    public String getBattenPlateNo() {
        return battenPlateNo;
    }

    public void setBattenPlateNo(String battenPlateNo) {
        this.battenPlateNo = battenPlateNo == null ? null : battenPlateNo.trim();
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

    public String getSizeMark() {
        return sizeMark;
    }

    public void setSizeMark(String sizeMark) {
        this.sizeMark = sizeMark == null ? null : sizeMark.trim();
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

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason == null ? null : claimReason.trim();
    }

    public String getSteelType() {
        return steelType;
    }

    public void setSteelType(String steelType) {
        this.steelType = steelType == null ? null : steelType.trim();
    }

    public String getProProblem() {
        return proProblem;
    }

    public void setProProblem(String proProblem) {
        this.proProblem = proProblem == null ? null : proProblem.trim();
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail == null ? null : proDetail.trim();
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

    @Override
    public String toString() {
        return "CrmClaimApply{" +
                "parentSid='" + parentSid + '\'' +
                ", inquireState='" + inquireState + '\'' +
                ", manufactor='" + manufactor + '\'' +
                ", specs='" + specs + '\'' +
                ", zcpmc='" + zcpmc + '\'' +
                ", zph='" + zph + '\'' +
                ", endDt=" + endDt +
                ", endDtStr='" + endDtStr + '\'' +
                ", startDt=" + startDt +
                ", startDtStr='" + startDtStr + '\'' +
                ", sid=" + sid +
                ", claimNo='" + claimNo + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", millSheetNo='" + millSheetNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", custAddr='" + custAddr + '\'' +
                ", custEmpNo='" + custEmpNo + '\'' +
                ", custTel='" + custTel + '\'' +
                ", lastUserId='" + lastUserId + '\'' +
                ", lastUser='" + lastUser + '\'' +
                ", lastUserAddr='" + lastUserAddr + '\'' +
                ", createEmpNo='" + createEmpNo + '\'' +
                ", lastUserTel='" + lastUserTel + '\'' +
                ", lastUserEmail='" + lastUserEmail + '\'' +
                ", battenPlateNo='" + battenPlateNo + '\'' +
                ", designation='" + designation + '\'' +
                ", used='" + used + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", contractVolume=" + contractVolume +
                ", sizeMark='" + sizeMark + '\'' +
                ", originalWeight=" + originalWeight +
                ", orderNo='" + orderNo + '\'' +
                ", originalCarNo='" + originalCarNo + '\'' +
                ", contractUnitPrice=" + contractUnitPrice +
                ", objectionNum=" + objectionNum +
                ", endProcessingTech='" + endProcessingTech + '\'' +
                ", claimDesc='" + claimDesc + '\'' +
                ", claimReason='" + claimReason + '\'' +
                ", steelType='" + steelType + '\'' +
                ", proProblem='" + proProblem + '\'' +
                ", proDetail='" + proDetail + '\'' +
                ", claimState='" + claimState + '\'' +
                ", filePath='" + filePath + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDt=" + createdDt +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDt=" + updatedDt +
                ", version=" + version +
                ", claimType='" + claimType + '\'' +
                ", optionStuts=" + optionStuts +
                ", optionType=" + optionType +
                ", dissentingUnit=" + dissentingUnit +
                ", admissibilityTime=" + admissibilityTime +
                ", presentationDate=" + presentationDate +
                ", admissibilityUser='" + admissibilityUser + '\'' +
                ", presentationUser='" + presentationUser + '\'' +
                ", reasonsForCompulsoryClosure='" + reasonsForCompulsoryClosure + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", reportPictures='" + reportPictures + '\'' +
                ", objectionConfirmation='" + objectionConfirmation + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}