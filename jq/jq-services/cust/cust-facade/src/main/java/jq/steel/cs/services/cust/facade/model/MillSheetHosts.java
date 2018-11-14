package jq.steel.cs.services.cust.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MillSheetHosts {

    //建材0;非建材1
    private Integer jcFlag;

    //虚拟质证书
    private List<String> noMillSheetNos;

    public List<String> getNoMillSheetNos() {
        return noMillSheetNos;
    }

    public void setNoMillSheetNos(List<String> noMillSheetNos) {
        this.noMillSheetNos = noMillSheetNos;
    }

    private String acctName;

    //回退原因
    private String fallbackReason;

    // 驳回回退原因
    private String rejectionReason;

    //厂家code
    private List<String> deptCodes;

    public List<String> getDeptCodes() {
        return deptCodes;
    }

    public void setDeptCodes(List<String> deptCodes) {
        this.deptCodes = deptCodes;
    }

    private String states;
    //产线
    private String millLine;

    private String zkunnrs;

    private Integer operationType;

    private  String originalCarNo;

    private  String contractNo;

    private BigDecimal contractVolume;

    private String designation;

    //合同重量
    private BigDecimal totalZlosmenge;

    //发车日期
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;
    private String endDtStr;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;
    private String startDtStr;

    private List<String> millSheetNos;


    private String zkdauf;  //销售订单编号

    private int isSplit;

    private String splitMaxValue;

    private String millSheetPath;

    private String zcharg;

    private String zchehao;

    private String zhth;

    private String zph;

    private Long sid;

    private String state;

    private String dataState;

    private String millSheetNoOld;

    private String millSheetNo;

    private String specialNeed;

    private Integer printableNum;

    private Integer printedNum;

    private String millSheetUrl;

    private String millSheetName;

    private String deptCode;

    private Integer downableNum;

    private Integer downNum;

    private String chemState;

    private String phyState;

    private String haveState;

    private String splitState;

    private String millSheetType;

    private Short surplusBatch;

    private String zkunnr;

    private String zkunwe;

    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    //校验说明
    private String checkInstructions;
    //校验结果
    private Boolean isTrue;

    //zcpmc
    private  String zcpmc;
    private  String productName;

    //客户名称
    private  String spiltCustomer;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date lfdat;

    private  String orgName;        //组织名称

    //是否允许下载打印
    private String isAllow;

    public String getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(String isAllow) {
        this.isAllow = isAllow;
    }

    public Integer getJcFlag() {
        return jcFlag;
    }

    public void setJcFlag(Integer jcFlag) {
        this.jcFlag = jcFlag;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getLfdat() {
        return lfdat;
    }

    public void setLfdat(Date lfdat) {
        this.lfdat = lfdat;
    }

    public String getZkdauf() {
        return zkdauf;
    }

    public void setZkdauf(String zkdauf) { this.zkdauf = zkdauf; }

    public String getFallbackReason() {
        return fallbackReason;
    }

    public void setFallbackReason(String fallbackReason) {
        this.fallbackReason = fallbackReason;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getMillLine() {
        return millLine;
    }

    public void setMillLine(String millLine) {
        this.millLine = millLine;
    }

    public String getZkunnrs() {
        return zkunnrs;
    }

    public void setZkunnrs(String zkunnrs) {
        this.zkunnrs = zkunnrs;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOriginalCarNo() {
        return originalCarNo;
    }

    public void setOriginalCarNo(String originalCarNo) {
        this.originalCarNo = originalCarNo;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<String> getMillSheetNos() {
        return millSheetNos;
    }

    public void setMillSheetNos(List<String> millSheetNos) {
        this.millSheetNos = millSheetNos;
    }

    public BigDecimal getTotalZlosmenge() {
        return totalZlosmenge;
    }

    public void setTotalZlosmenge(BigDecimal totalZlosmenge) {
        this.totalZlosmenge = totalZlosmenge;
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

    public String getSpiltCustomer() {
        return spiltCustomer;
    }

    public void setSpiltCustomer(String spiltCustomer) {
        this.spiltCustomer = spiltCustomer;
    }

    public String getZcpmc() {
        return zcpmc;
    }

    public void setZcpmc(String zcpmc) {
        this.zcpmc = zcpmc;
    }

    public String getCheckInstructions() {
        return checkInstructions;
    }

    public void setCheckInstructions(String checkInstructions) {
        this.checkInstructions = checkInstructions;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }

    public int getIsSplit() {
        return isSplit;
    }

    public void setIsSplit(int isSplit) {
        this.isSplit = isSplit;
    }

    public String getSplitMaxValue() {
        return splitMaxValue;
    }

    public void setSplitMaxValue(String splitMaxValue) {
        this.splitMaxValue = splitMaxValue;
    }

    public String getMillSheetPath() {
        return millSheetPath;
    }

    public void setMillSheetPath(String millSheetPath) {
        this.millSheetPath = millSheetPath;
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg;
    }

    public String getZchehao() {
        return zchehao;
    }

    public void setZchehao(String zchehao) {
        this.zchehao = zchehao;
    }

    public String getZhth() {
        return zhth;
    }

    public void setZhth(String zhth) {
        this.zhth = zhth;
    }

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState == null ? null : dataState.trim();
    }

    public String getMillSheetNoOld() {
        return millSheetNoOld;
    }

    public void setMillSheetNoOld(String millSheetNoOld) {
        this.millSheetNoOld = millSheetNoOld == null ? null : millSheetNoOld.trim();
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo == null ? null : millSheetNo.trim();
    }

    public String getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(String specialNeed) {
        this.specialNeed = specialNeed == null ? null : specialNeed.trim();
    }

    public Integer getPrintableNum() {
        return printableNum;
    }

    public void setPrintableNum(Integer printableNum) {
        this.printableNum = printableNum;
    }

    public Integer getPrintedNum() {
        return printedNum;
    }

    public void setPrintedNum(Integer printedNum) {
        this.printedNum = printedNum;
    }

    public String getMillSheetUrl() {
        return millSheetUrl;
    }

    public void setMillSheetUrl(String millSheetUrl) {
        this.millSheetUrl = millSheetUrl == null ? null : millSheetUrl.trim();
    }

    public String getMillSheetName() {
        return millSheetName;
    }

    public void setMillSheetName(String millSheetName) {
        this.millSheetName = millSheetName == null ? null : millSheetName.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public Integer getDownableNum() {
        return downableNum;
    }

    public void setDownableNum(Integer downableNum) {
        this.downableNum = downableNum;
    }

    public Integer getDownNum() {
        return downNum;
    }

    public void setDownNum(Integer downNum) {
        this.downNum = downNum;
    }

    public String getChemState() {
        return chemState;
    }

    public void setChemState(String chemState) {
        this.chemState = chemState == null ? null : chemState.trim();
    }

    public String getPhyState() {
        return phyState;
    }

    public void setPhyState(String phyState) {
        this.phyState = phyState == null ? null : phyState.trim();
    }

    public String getHaveState() {
        return haveState;
    }

    public void setHaveState(String haveState) {
        this.haveState = haveState == null ? null : haveState.trim();
    }

    public String getSplitState() {
        return splitState;
    }

    public void setSplitState(String splitState) {
        this.splitState = splitState == null ? null : splitState.trim();
    }

    public String getMillSheetType() {
        return millSheetType;
    }

    public void setMillSheetType(String millSheetType) {
        this.millSheetType = millSheetType == null ? null : millSheetType.trim();
    }

    public Short getSurplusBatch() {
        return surplusBatch;
    }

    public void setSurplusBatch(Short surplusBatch) {
        this.surplusBatch = surplusBatch;
    }

    public String getZkunnr() {
        return zkunnr;
    }

    public void setZkunnr(String zkunnr) {
        this.zkunnr = zkunnr == null ? null : zkunnr.trim();
    }

    public String getZkunwe() {
        return zkunwe;
    }

    public void setZkunwe(String zkunwe) {
        this.zkunwe = zkunwe == null ? null : zkunwe.trim();
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