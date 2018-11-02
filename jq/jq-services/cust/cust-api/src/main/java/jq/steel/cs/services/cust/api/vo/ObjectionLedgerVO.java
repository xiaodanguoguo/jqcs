package jq.steel.cs.services.cust.api.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

//异议统计台账
public class ObjectionLedgerVO {

    private  String  specs;


    private BigDecimal handlerUser;

    private BigDecimal handlerTime;

    private BigDecimal handlerResults;

    private String IsTrack;

    private String pd;

    private String at;

    private String at1;

    private String ct;

    //客户评价
    private String evaluate;

    //产线(生产工序)
    private String millLine;

    private String deptCode;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;
    private String endDtStr;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;
    private String startDtStr;

    private String claimState;

    private Long sid;

    private int pageNum = 1;

    private int pageSize = 10;

    private String claimNo;

    private String lastUser;

    private String lastUserAddr;

    private String createEmpNo;

    private String lastUserTel;

    private String customerName;

    private String custEmpNo;

    private String custTel;

    private String endProcessingTech;

    private String used;

    private BigDecimal originalWeight;

    private String millSheetNo;

    private String battenPlateNo;

    private String designation;

    private BigDecimal objectionNum;

    private String claimReason;

    private String proProblem;

    private String claimType;

    private String logisticsProcess;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date productDt;

    private String handingSuggestion;

    //异议确认量
    private  BigDecimal objectionConfirmation;

    //受理时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date admissibilityTime;

    //结案时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date closingTime;

    //结案人
    private String closingUser;

    //备注
    private String memo;

    //车号
    private String originalCarNo;

    //赔偿金额（小写）
    private BigDecimal agreementAmount;

    //到达时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date arrivalTime;

    //处理周期
    private String cycle;


    private String  defectName;

    private String zcpmc;

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public String getZcpmc() {
        return zcpmc;
    }

    public void setZcpmc(String zcpmc) {
        this.zcpmc = zcpmc;
    }

    public BigDecimal getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(BigDecimal handlerUser) {
        this.handlerUser = handlerUser;
    }

    public BigDecimal getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(BigDecimal handlerTime) {
        this.handlerTime = handlerTime;
    }

    public BigDecimal getHandlerResults() {
        return handlerResults;
    }

    public void setHandlerResults(BigDecimal handlerResults) {
        this.handlerResults = handlerResults;
    }

    public String getIsTrack() {
        return IsTrack;
    }

    public void setIsTrack(String isTrack) {
        IsTrack = isTrack;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getAt1() {
        return at1;
    }

    public void setAt1(String at1) {
        this.at1 = at1;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getMillLine() {
        return millLine;
    }

    public void setMillLine(String millLine) {
        this.millLine = millLine;
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

    public String getClaimState() {
        return claimState;
    }

    public void setClaimState(String claimState) {
        this.claimState = claimState;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
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

    public BigDecimal getAgreementAmount() {
        return agreementAmount;
    }

    public void setAgreementAmount(BigDecimal agreementAmount) {
        this.agreementAmount = agreementAmount;
    }

    public String getOriginalCarNo() {
        return originalCarNo;
    }

    public void setOriginalCarNo(String originalCarNo) {
        this.originalCarNo = originalCarNo;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getEndProcessingTech() {
        return endProcessingTech;
    }

    public void setEndProcessingTech(String endProcessingTech) {
        this.endProcessingTech = endProcessingTech;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public BigDecimal getOriginalWeight() {
        return originalWeight;
    }

    public void setOriginalWeight(BigDecimal originalWeight) {
        this.originalWeight = originalWeight;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo;
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

    public BigDecimal getObjectionNum() {
        return objectionNum;
    }

    public void setObjectionNum(BigDecimal objectionNum) {
        this.objectionNum = objectionNum;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public String getProProblem() {
        return proProblem;
    }

    public void setProProblem(String proProblem) {
        this.proProblem = proProblem;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getLogisticsProcess() {
        return logisticsProcess;
    }

    public void setLogisticsProcess(String logisticsProcess) {
        this.logisticsProcess = logisticsProcess;
    }

    public Date getProductDt() {
        return productDt;
    }

    public void setProductDt(Date productDt) {
        this.productDt = productDt;
    }

    public String getHandingSuggestion() {
        return handingSuggestion;
    }

    public void setHandingSuggestion(String handingSuggestion) {
        this.handingSuggestion = handingSuggestion;
    }

    public BigDecimal getObjectionConfirmation() {
        return objectionConfirmation;
    }

    public void setObjectionConfirmation(BigDecimal objectionConfirmation) {
        this.objectionConfirmation = objectionConfirmation;
    }

    public Date getAdmissibilityTime() {
        return admissibilityTime;
    }

    public void setAdmissibilityTime(Date admissibilityTime) {
        this.admissibilityTime = admissibilityTime;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
