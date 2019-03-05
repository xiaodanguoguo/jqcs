package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ObjectionTiBaoVO {

    private String agentId;

    private String agentName;

    private String agentAddr;

    private String agentEmpNo;

    private String agentTel;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentAddr() {
        return agentAddr;
    }

    public void setAgentAddr(String agentAddr) {
        this.agentAddr = agentAddr;
    }

    public String getAgentEmpNo() {
        return agentEmpNo;
    }

    public void setAgentEmpNo(String agentEmpNo) {
        this.agentEmpNo = agentEmpNo;
    }

    public String getAgentTel() {
        return agentTel;
    }

    public void setAgentTel(String agentTel) {
        this.agentTel = agentTel;
    }

    //app计数
    private  Integer countForApp;

    public Integer getCountForApp() {
        return countForApp;
    }

    public void setCountForApp(Integer countForApp) {
        this.countForApp = countForApp;
    }

    //校验编码
    private Integer checkCode;

    //厂家code
    private List<String> deptCodes;

    public List<String> getDeptCodes() {
        return deptCodes;
    }

    public void setDeptCodes(List<String> deptCodes) {
        this.deptCodes = deptCodes;
    }

    private String phone;

    //提报日期导出字段转换
    private String ast;

    //质量异议报告图片
    private String reportPictures;

    //异议确认量
    private  BigDecimal objectionConfirmation;

    //规格
    private String specs;

    //产品名称
    private  String zcpmc;

    //牌号
    private String zph;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;

    private Long sid;

    private String claimNo;

    private Long productId;

    private String productName; //诉赔产品名称

    private String millSheetNo;

    private String customerId;

    private List<String>  customerIds;

    private String customerName;

    private String custAddr;

    private String custEmpNo;

    private String custTel;

    private String lastUserId;

    private String lastUser;

    private String lastUserAddr;

    private String createEmpNo;     //联系人

    private String lastUserTel;     //联系人电话

    private String lastUserEmail;

    private String battenPlateNo;

    private String designation;     //产品牌号

    private String used;

    private String contractNo;  //合同号

    private BigDecimal contractVolume;

    private String sizeMark;    //规格

    private BigDecimal originalWeight;

    private String orderNo;

    private String originalCarNo;

    private BigDecimal contractUnitPrice;

    private BigDecimal objectionNum;    //异议量

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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String claimType;

    private int pageNum = 1;

    private int pageSize = 10;

    private Integer optionStuts;

    private Integer optionType;

    //说明是否设置默认联系人
    private String explain;

    private Integer dissentingUnit;

    //协议书地址
    private  String agreementPath;

    //异议提报详情页面润乾地址
    private  String url;

    //受理时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date admissibilityTime;

    //提报时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
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

    private String manufactor;

    private String acctName;

    private  String orgType;

    private  String orgCode;

    private  String orgName;           //当前用户对应的组织

    private  String agreementName;

    //润乾地址
    private  String report;

    private Integer count;

    public Integer getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(Integer checkCode) {
        this.checkCode = checkCode;
    }

    public List<String> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<String> customerIds) {
        this.customerIds = customerIds;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

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

    public String getReportPictures() {
        return reportPictures;
    }

    public void setReportPictures(String reportPictures) {
        this.reportPictures = reportPictures;
    }

    public BigDecimal getObjectionConfirmation() {
        return objectionConfirmation;
    }

    public void setObjectionConfirmation(BigDecimal objectionConfirmation) {
        this.objectionConfirmation = objectionConfirmation;
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
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

    public String getInquireState() {
        return inquireState;
    }

    public void setInquireState(String inquireState) {
        this.inquireState = inquireState;
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



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAgreementPath() {
        return agreementPath;
    }

    public void setAgreementPath(String agreementPath) {
        this.agreementPath = agreementPath;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    //--------------------添加字段,10.27,lujiawei--------------------------
    private String needUser ;               //受理人

    private Date tibaoAdmissibilityTime ;   //受理时间

    private String productionRejectReason ; //生产驳回原因

    private String mobilePhone ;            //受理人电话

    public String getNeedUser() {
        return needUser;
    }

    public void setNeedUser(String needUser) {
        this.needUser = needUser;
    }

    public Date getTibaoAdmissibilityTime() {
        return tibaoAdmissibilityTime;
    }

    public void setTibaoAdmissibilityTime(Date tibaoAdmissibilityTime) {
        this.tibaoAdmissibilityTime = tibaoAdmissibilityTime;
    }

    public String getProductionRejectReason() {
        return productionRejectReason;
    }

    public void setProductionRejectReason(String productionRejectReason) {
        this.productionRejectReason = productionRejectReason;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    //--------------------10.31,添加字段,lujiaei----------------
    private String name ;                   //受理人姓名

    public String getName() {  return name;  }

    public void setName(String name) { this.name = name;  }
}
