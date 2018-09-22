package jq.steel.cs.services.cust.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CrmClaimOutInquire {

    private  String parentSid;

    private String claimState1;

    public String getClaimState1() {
        return claimState1;
    }

    public void setClaimState1(String claimState1) {
        this.claimState1 = claimState1;
    }

    //生产工艺过程调查
    private  String productionProcessInvestigati;

    //生产工艺过程调查photo
    private  String productionProcessPhoto;

    //质量等级
    private  String qualityGrade;

    //原判定结果
    private  String originalJudgementResult;

    //表面结构
    private  String surfaceStructure;

    //生产工艺过程调查TEXT
    private  String productionProcessText;

    //生产工艺过程调查富文本
    private  String productionProcessAll;

    private String outInquireFact;

    private String claimConfirm;

    private String claimVerdict;

    private String improvement;

    private String memo;

    private String fieldConclusionText;

    private String fieldConclusionPhoto;

    private  String objectionConfirmation;

    private  String reportPictures;

    //销售经理建议
    private  String salesManagerSuggests;
    //调查事实阐述photo
    private  String inquireInfoPhoto;
    //调查事实阐述text
    private  String inquireInfoText;
    //调查事实阐述富文本
    private  String inquireInfoAll;

    private  String zph;
    //产品大类
    private  String zcpmc;

    private  String deptCode;

    private  String specs;

    private Long sid;

    private String claimNo;

    private String logisticsProcess;

    private String endProcessingTech;

    //private String defectName;

    private Date productDt;

    private String shift;

    private String fieldConclusion;

    private String userRequirement;

    private String handingSuggestion;

    private String inquireState;

    private String followReason;

    private String createBy;

    private Date createDt;

    private String updateBy;

    private Date updateDt;

    private Integer version;

    private String inquireInfo;

    private Integer amountOfUse;


    /*********************************************************************************/



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


    private BigDecimal originalWeight;

    private String orderNo;

    private String originalCarNo;

    private BigDecimal contractUnitPrice;

    private BigDecimal objectionNum;

    private String claimDesc;

    private String claimReason;

    private String steelType;

    private String proProblem;

    private String proDetail;

    private String claimState;

    private String filePath;

    private String rejectReason;



    private String claimType;

    private Integer optionType;

    private Integer dissentingUnit;

    //受理时间
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

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;
    private String endDtStr;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;
    private String startDtStr;

    /*************************************************************************************/

    //外部调查人
    private String externalLnvestigator;
    //外部调查时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private  Date externalLnvestigationDate;
    //内部调查人
    private  String internalLnvestigator;
    //内部调查时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date internalLnvestigationDate;

    public String getParentSid() {
        return parentSid;
    }

    public void setParentSid(String parentSid) {
        this.parentSid = parentSid;
    }

    public String getProductionProcessInvestigati() {
        return productionProcessInvestigati;
    }

    public void setProductionProcessInvestigati(String productionProcessInvestigati) {
        this.productionProcessInvestigati = productionProcessInvestigati;
    }

    public String getProductionProcessPhoto() {
        return productionProcessPhoto;
    }

    public void setProductionProcessPhoto(String productionProcessPhoto) {
        this.productionProcessPhoto = productionProcessPhoto;
    }

    public String getQualityGrade() {
        return qualityGrade;
    }

    public void setQualityGrade(String qualityGrade) {
        this.qualityGrade = qualityGrade;
    }

    public String getOriginalJudgementResult() {
        return originalJudgementResult;
    }

    public void setOriginalJudgementResult(String originalJudgementResult) {
        this.originalJudgementResult = originalJudgementResult;
    }

    public String getSurfaceStructure() {
        return surfaceStructure;
    }

    public void setSurfaceStructure(String surfaceStructure) {
        this.surfaceStructure = surfaceStructure;
    }

    public String getProductionProcessText() {
        return productionProcessText;
    }

    public void setProductionProcessText(String productionProcessText) {
        this.productionProcessText = productionProcessText;
    }

    public String getProductionProcessAll() {
        return productionProcessAll;
    }

    public void setProductionProcessAll(String productionProcessAll) {
        this.productionProcessAll = productionProcessAll;
    }

    public String getOutInquireFact() {
        return outInquireFact;
    }

    public void setOutInquireFact(String outInquireFact) {
        this.outInquireFact = outInquireFact;
    }

    public String getClaimConfirm() {
        return claimConfirm;
    }

    public void setClaimConfirm(String claimConfirm) {
        this.claimConfirm = claimConfirm;
    }

    public String getClaimVerdict() {
        return claimVerdict;
    }

    public void setClaimVerdict(String claimVerdict) {
        this.claimVerdict = claimVerdict;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFieldConclusionText() {
        return fieldConclusionText;
    }

    public void setFieldConclusionText(String fieldConclusionText) {
        this.fieldConclusionText = fieldConclusionText;
    }

    public String getFieldConclusionPhoto() {
        return fieldConclusionPhoto;
    }

    public void setFieldConclusionPhoto(String fieldConclusionPhoto) {
        this.fieldConclusionPhoto = fieldConclusionPhoto;
    }

    public String getObjectionConfirmation() {
        return objectionConfirmation;
    }

    public void setObjectionConfirmation(String objectionConfirmation) {
        this.objectionConfirmation = objectionConfirmation;
    }

    public String getReportPictures() {
        return reportPictures;
    }

    public void setReportPictures(String reportPictures) {
        this.reportPictures = reportPictures;
    }

    public String getSalesManagerSuggests() {
        return salesManagerSuggests;
    }

    public void setSalesManagerSuggests(String salesManagerSuggests) {
        this.salesManagerSuggests = salesManagerSuggests;
    }

    public String getInquireInfoPhoto() {
        return inquireInfoPhoto;
    }

    public void setInquireInfoPhoto(String inquireInfoPhoto) {
        this.inquireInfoPhoto = inquireInfoPhoto;
    }

    public String getInquireInfoText() {
        return inquireInfoText;
    }

    public void setInquireInfoText(String inquireInfoText) {
        this.inquireInfoText = inquireInfoText;
    }

    public String getInquireInfoAll() {
        return inquireInfoAll;
    }

    public void setInquireInfoAll(String inquireInfoAll) {
        this.inquireInfoAll = inquireInfoAll;
    }

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph;
    }

    public String getZcpmc() {
        return zcpmc;
    }

    public void setZcpmc(String zcpmc) {
        this.zcpmc = zcpmc;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getEndDtStr() {
        return endDtStr;
    }

    public void setEndDtStr(String endDtStr) {
        this.endDtStr = endDtStr;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getStartDtStr() {
        return startDtStr;
    }

    public void setStartDtStr(String startDtStr) {
        this.startDtStr = startDtStr;
    }

    public Integer getAmountOfUse() {
        return amountOfUse;
    }

    public void setAmountOfUse(Integer amountOfUse) {
        this.amountOfUse = amountOfUse;
    }

    public String getExternalLnvestigator() {
        return externalLnvestigator;
    }

    public void setExternalLnvestigator(String externalLnvestigator) {
        this.externalLnvestigator = externalLnvestigator;
    }

    public Date getExternalLnvestigationDate() {
        return externalLnvestigationDate;
    }

    public void setExternalLnvestigationDate(Date externalLnvestigationDate) {
        this.externalLnvestigationDate = externalLnvestigationDate;
    }

    public String getInternalLnvestigator() {
        return internalLnvestigator;
    }

    public void setInternalLnvestigator(String internalLnvestigator) {
        this.internalLnvestigator = internalLnvestigator;
    }

    public Date getInternalLnvestigationDate() {
        return internalLnvestigationDate;
    }

    public void setInternalLnvestigationDate(Date internalLnvestigationDate) {
        this.internalLnvestigationDate = internalLnvestigationDate;
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

    public String getLogisticsProcess() {
        return logisticsProcess;
    }

    public void setLogisticsProcess(String logisticsProcess) {
        this.logisticsProcess = logisticsProcess == null ? null : logisticsProcess.trim();
    }

    public String getEndProcessingTech() {
        return endProcessingTech;
    }

    public void setEndProcessingTech(String endProcessingTech) {
        this.endProcessingTech = endProcessingTech == null ? null : endProcessingTech.trim();
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
        this.shift = shift == null ? null : shift.trim();
    }

    public String getFieldConclusion() {
        return fieldConclusion;
    }

    public void setFieldConclusion(String fieldConclusion) {
        this.fieldConclusion = fieldConclusion == null ? null : fieldConclusion.trim();
    }

    public String getUserRequirement() {
        return userRequirement;
    }

    public void setUserRequirement(String userRequirement) {
        this.userRequirement = userRequirement == null ? null : userRequirement.trim();
    }

    public String getHandingSuggestion() {
        return handingSuggestion;
    }

    public void setHandingSuggestion(String handingSuggestion) {
        this.handingSuggestion = handingSuggestion == null ? null : handingSuggestion.trim();
    }

    public String getInquireState() {
        return inquireState;
    }

    public void setInquireState(String inquireState) {
        this.inquireState = inquireState == null ? null : inquireState.trim();
    }

    public String getFollowReason() {
        return followReason;
    }

    public void setFollowReason(String followReason) {
        this.followReason = followReason == null ? null : followReason.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getInquireInfo() {
        return inquireInfo;
    }

    public void setInquireInfo(String inquireInfo) {
        this.inquireInfo = inquireInfo == null ? null : inquireInfo.trim();
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
        this.productName = productName;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo;
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

    public String getCustAddr() {
        return custAddr;
    }

    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr;
    }

    public String getCustEmpNo() {
        return custEmpNo;
    }

    public void setCustEmpNo(String custEmpNo) {
        this.custEmpNo = custEmpNo;
    }

    public String getCustTel() {
        return custTel;
    }

    public void setCustTel(String custTel) {
        this.custTel = custTel;
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

    public String getLastUserAddr() {
        return lastUserAddr;
    }

    public void setLastUserAddr(String lastUserAddr) {
        this.lastUserAddr = lastUserAddr;
    }

    public String getCreateEmpNo() {
        return createEmpNo;
    }

    public void setCreateEmpNo(String createEmpNo) {
        this.createEmpNo = createEmpNo;
    }

    public String getLastUserTel() {
        return lastUserTel;
    }

    public void setLastUserTel(String lastUserTel) {
        this.lastUserTel = lastUserTel;
    }

    public String getLastUserEmail() {
        return lastUserEmail;
    }

    public void setLastUserEmail(String lastUserEmail) {
        this.lastUserEmail = lastUserEmail;
    }

    public String getBattenPlateNo() {
        return battenPlateNo;
    }

    public void setBattenPlateNo(String battenPlateNo) {
        this.battenPlateNo = battenPlateNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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
        this.orderNo = orderNo;
    }

    public String getOriginalCarNo() {
        return originalCarNo;
    }

    public void setOriginalCarNo(String originalCarNo) {
        this.originalCarNo = originalCarNo;
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

    public String getClaimDesc() {
        return claimDesc;
    }

    public void setClaimDesc(String claimDesc) {
        this.claimDesc = claimDesc;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public String getSteelType() {
        return steelType;
    }

    public void setSteelType(String steelType) {
        this.steelType = steelType;
    }

    public String getProProblem() {
        return proProblem;
    }

    public void setProProblem(String proProblem) {
        this.proProblem = proProblem;
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

    public String getClaimState() {
        return claimState;
    }

    public void setClaimState(String claimState) {
        this.claimState = claimState;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public Integer getDissentingUnit() {
        return dissentingUnit;
    }

    public void setDissentingUnit(Integer dissentingUnit) {
        this.dissentingUnit = dissentingUnit;
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
}