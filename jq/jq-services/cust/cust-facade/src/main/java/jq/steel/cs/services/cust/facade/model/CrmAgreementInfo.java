package jq.steel.cs.services.cust.facade.model;

import java.math.BigDecimal;
import java.util.Date;

public class CrmAgreementInfo {

    private  String objectionConfirmation;
    //投诉内容描述
    private String claimDesc;

    private String millSheetNo;


    private Long sid;

    private String claimNo;

    private String agreementUrl;

    private String agreementName;

    private String agreementState;

    private Integer downloadableNum;

    private Integer downloadedNum;

    //协议内容
    private String agreementContent;


    //异议确认量
    private BigDecimal agreementNum;

    //赔偿金额（小写）
    private BigDecimal agreementAmount;

    //赔偿金额（大写)
    private String agreementAmountUpper;

    //驳回原因
    private String rejectReason;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String agreementUrlName;


    //外部字段

    private String customerId;

    private String customerName;

    private String lastUserId;

    private String lastUser;

    private String endProcessingTech;
    private String defectName;

    private Date productDt;
    private String shift;

    private String userRequirement;

    private String handingSuggestion;

    //物流过程
    private String logisticsProcess;

    //车号
    private String originalCarNo;

    //质证书编号
    private String millsheetNo;

    //合同号
    private String contractNo;

    //现场结论
    private String fieldConclusion;

    //甲方
    private String deptName;

    //甲方code
    private String deptCode;

    //操作类型
    private Integer optionStuts;


    //协议书图片地址
    private  String  claimNoUrl;

    public String getObjectionConfirmation() {
        return objectionConfirmation;
    }

    public void setObjectionConfirmation(String objectionConfirmation) {
        this.objectionConfirmation = objectionConfirmation;
    }

    public String getClaimDesc() {
        return claimDesc;
    }

    public void setClaimDesc(String claimDesc) {
        this.claimDesc = claimDesc;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo;
    }

    public String getEndProcessingTech() {
        return endProcessingTech;
    }

    public void setEndProcessingTech(String endProcessingTech) {
        this.endProcessingTech = endProcessingTech;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public Date getProductDt() {
        return productDt;
    }

    public void setProductDt(Date productDt) {
        this.productDt = productDt;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getUserRequirement() {
        return userRequirement;
    }

    public void setUserRequirement(String userRequirement) {
        this.userRequirement = userRequirement;
    }

    public String getHandingSuggestion() {
        return handingSuggestion;
    }

    public void setHandingSuggestion(String handingSuggestion) {
        this.handingSuggestion = handingSuggestion;
    }

    public String getClaimNoUrl() {
        return claimNoUrl;
    }

    public void setClaimNoUrl(String claimNoUrl) {
        this.claimNoUrl = claimNoUrl;
    }

    public Integer getOptionStuts() {
        return optionStuts;
    }

    public void setOptionStuts(Integer optionStuts) {
        this.optionStuts = optionStuts;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public String getLogisticsProcess() {
        return logisticsProcess;
    }

    public void setLogisticsProcess(String logisticsProcess) {
        this.logisticsProcess = logisticsProcess;
    }

    public String getOriginalCarNo() {
        return originalCarNo;
    }

    public void setOriginalCarNo(String originalCarNo) {
        this.originalCarNo = originalCarNo;
    }

    public String getMillsheetNo() {
        return millsheetNo;
    }

    public void setMillsheetNo(String millsheetNo) {
        this.millsheetNo = millsheetNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getFieldConclusion() {
        return fieldConclusion;
    }

    public void setFieldConclusion(String fieldConclusion) {
        this.fieldConclusion = fieldConclusion;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getAgreementUrlName() {
        return agreementUrlName;
    }

    public void setAgreementUrlName(String agreementUrlName) {
        this.agreementUrlName = agreementUrlName;
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
        this.claimNo = claimNo;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl == null ? null : agreementUrl.trim();
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
        this.agreementState = agreementState == null ? null : agreementState.trim();
    }

    public Integer getDownloadableNum() {
        return downloadableNum;
    }

    public void setDownloadableNum(Integer downloadableNum) {
        this.downloadableNum = downloadableNum;
    }

    public Integer getDownloadedNum() {
        return downloadedNum;
    }

    public void setDownloadedNum(Integer downloadedNum) {
        this.downloadedNum = downloadedNum;
    }

    public String getAgreementContent() {
        return agreementContent;
    }

    public void setAgreementContent(String agreementContent) {
        this.agreementContent = agreementContent == null ? null : agreementContent.trim();
    }

    public BigDecimal getAgreementNum() {
        return agreementNum;
    }

    public void setAgreementNum(BigDecimal agreementNum) {
        this.agreementNum = agreementNum;
    }

    public BigDecimal getAgreementAmount() {
        return agreementAmount;
    }

    public void setAgreementAmount(BigDecimal agreementAmount) {
        this.agreementAmount = agreementAmount;
    }

    public String getAgreementAmountUpper() {
        return agreementAmountUpper;
    }

    public void setAgreementAmountUpper(String agreementAmountUpper) {
        this.agreementAmountUpper = agreementAmountUpper == null ? null : agreementAmountUpper.trim();
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