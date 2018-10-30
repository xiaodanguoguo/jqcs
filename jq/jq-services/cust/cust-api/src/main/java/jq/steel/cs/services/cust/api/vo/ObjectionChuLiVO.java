package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ObjectionChuLiVO {

    private  BigDecimal objectionConfirmation;

    //厂家code
    private List<String> deptCodes;

    public List<String> getDeptCodes() {
        return deptCodes;
    }

    public void setDeptCodes(List<String> deptCodes) {
        this.deptCodes = deptCodes;
    }

    //提报日期导出字段转换
    private String ast;

    //过期原因
    private String expiredReason;

    //结案时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date closingTime;

    //是否跟踪
    private String IsTrack;

    //是否允许上传
    private String isUpload;

    //现场结论
    private String fieldConclusion;

    private String claimState1;

    //过期标识
    private String expiredSign;

    public BigDecimal getObjectionConfirmation() {
        return objectionConfirmation;
    }

    public void setObjectionConfirmation(BigDecimal objectionConfirmation) {
        this.objectionConfirmation = objectionConfirmation;
    }

    public String getExpiredSign() {
        return expiredSign;
    }

    public void setExpiredSign(String expiredSign) {
        this.expiredSign = expiredSign;
    }

    public String getExpiredReason() {
        return expiredReason;
    }

    public void setExpiredReason(String expiredReason) {
        this.expiredReason = expiredReason;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getIsTrack() {
        return IsTrack;
    }

    public void setIsTrack(String isTrack) {
        IsTrack = isTrack;
    }

    public String getFieldConclusion() {
        return fieldConclusion;
    }

    public void setFieldConclusion(String fieldConclusion) {
        this.fieldConclusion = fieldConclusion;
    }

    public String getClaimState1() {
        return claimState1;
    }

    public void setClaimState1(String claimState1) {
        this.claimState1 = claimState1;
    }

    private List claimNos;

    public List getClaimNos() {
        return claimNos;
    }

    public void setClaimNos(List claimNos) {
        this.claimNos = claimNos;
    }

    //协议内容
    private String agreementContent;


    //异议确认量
    private BigDecimal agreementNum;

    //赔偿金额（小写）
    private BigDecimal agreementAmount;

    //赔偿金额（大写)
    private String agreementAmountUpper;

    //private  String templateType;
    private  Integer templateType;

    private String agreementUrl;

    private String agreementName;

    private String agreementState;

    //产品名称
    private  String zcpmc;

    //牌号
    private String zph;

    private  String orgCode;

    private  String orgName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;

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

    private String contractNo;  //合同编号

    private BigDecimal contractVolume;

    //private String sizeMark;

    private  String specs;

    private BigDecimal originalWeight;

    private String orderNo;

    private String originalCarNo;

    private BigDecimal contractUnitPrice;

    private BigDecimal objectionNum;    //异议编号

    private String claimDesc;           //异议内容描述

    private String claimReason;

    private String steelType;

    private String proProblem;

    private String proDetail;

    private String claimState;

    private String filePath;

    private String rejectReason;    //驳回原因

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String claimType;

    private int pageNum = 1;

    private int pageSize = 10;

    private Integer optionStuts;

    private Integer optionType;

    private Integer dissentingUnit; //异议单位

    //协议书地址
    private  String agreementPath;

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

    //调查状态
    private  String inquireState;


    //甲方
    private String deptName;

    private String manufactor;

    //润乾地址
    private  String report;

    //物流过程
    private String logisticsProcess;

    private String endProcessingTech;
    private String defectName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date productDt;
    private String shift;

    private String userRequirement;

    private String handingSuggestion;

    private String acctName;
    //备注
    private String memo;

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

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getAgreementContent() {
        return agreementContent;
    }

    public void setAgreementContent(String agreementContent) {
        this.agreementContent = agreementContent;
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
        this.agreementAmountUpper = agreementAmountUpper;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getLogisticsProcess() {
        return logisticsProcess;
    }

    public void setLogisticsProcess(String logisticsProcess) {
        this.logisticsProcess = logisticsProcess;
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getEndProcessingTech() {
        return endProcessingTech;
    }

    public void setEndProcessingTech(String endProcessingTech) {
        this.endProcessingTech = endProcessingTech;
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

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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


    public Integer getDissentingUnit() {
        return dissentingUnit;
    }

    public void setDissentingUnit(Integer dissentingUnit) {
        this.dissentingUnit = dissentingUnit;
    }

    public String getAgreementPath() {
        return agreementPath;
    }

    public void setAgreementPath(String agreementPath) {
        this.agreementPath = agreementPath;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getInquireState() {
        return inquireState;
    }

    public void setInquireState(String inquireState) {
        this.inquireState = inquireState;
    }




    //----------其他表额外的字段,10.24添加_lujiawei---------------------------------
    //车号
    private String zchehao;
    //调查事实阐述
    private String inquireInfo;

    //原因分析及结论
    private String claimVerdict;
    //合同号
    private String zhth;

    public String getClaimVerdict() {
        return claimVerdict;
    }

    public void setClaimVerdict(String claimVerdict) {
        this.claimVerdict = claimVerdict;
    }

    public String getZhth() {
        return zhth;
    }

    public void setZhth(String zhth) {
        this.zhth = zhth;
    }

    public String getZchehao() {
        return zchehao;
    }

    public void setZchehao(String zchehao) {
        this.zchehao = zchehao;
    }

    public String getInquireInfo() {
        return inquireInfo;
    }

    public void setInquireInfo(String inquireInfo) {
        this.inquireInfo = inquireInfo;
    }


    //--------------------------添加字段10.26,lujiawei------------------------------------------------
    private String fieldConclusionText; //现场结论

    public String getFieldConclusionText() {
        return fieldConclusionText;
    }

    public void setFieldConclusionText(String fieldConclusionText) {
        this.fieldConclusionText = fieldConclusionText;
    }

}
