package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MillSheetHostsVO implements Serializable {


    //建材0;非建材1
    private Integer jcFlag;

    //是否允许下载打印
    private String isAllow;

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
    //（销售界面）客户名称
    private String zkunnrs;

    //打印地址
    private  String  report;

    private Integer operationType;
    //是否回退
    private String isReback;


    private  String originalCarNo;

    private  String contractNo;

    private BigDecimal contractVolume;

    private String designation;


    //合同重量
    private BigDecimal totalZlosmenge;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;

    //合同号
    private String zhth;

    //车号
    private String zchehao;

    //牌号
    private String zph;

    private String millSheetPath;

    private int isSplit;

    private Long sid;

    private String state;       //质证书状态

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

    private String zkdauf;//销售订单编号

    //客户名称/客户编码
    private String zkunnr;

    private String zkunwe;

    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private int pageNum = 1;

    private int pageSize = 10;

    //校验说明
    private String checkInstructions;
    //校验结果
    private Boolean isTrue;

    //产品名称
    private  String zcpmc;

    //批/板/卷号
    private String zcharg;

    private  String orgCode;

    private  String orgName;        //组织名称

    //客户名称
    private  String spiltCustomer;

    private  String productName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date lfdat;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date startDtStr; //开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date endDtStr;  //结束时间


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

    public String getZkdauf() {
        return zkdauf;
    }

    public void setZkdauf(String zkdauf) {
        this.zkdauf = zkdauf;
    }

    public Date getLfdat() {
        return lfdat;
    }

    public void setLfdat(Date lfdat) {
        this.lfdat = lfdat;
    }

    public Date getStartDtStr() {
        return startDtStr;
    }

    public void setStartDtStr(Date startDtStr) {
        this.startDtStr = startDtStr;
    }

    public Date getEndDtStr() {
        return endDtStr;
    }

    public void setEndDtStr(Date endDtStr) {
        this.endDtStr = endDtStr;
    }

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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getIsReback() {
        return isReback;
    }

    public void setIsReback(String isReback) {
        this.isReback = isReback;
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

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getSpiltCustomer() {
        return spiltCustomer;
    }

    public void setSpiltCustomer(String spiltCustomer) {
        this.spiltCustomer = spiltCustomer;
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

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph;
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg;
    }

    private List<MillCoilInfoVO> millSheetNos;

    public List<MillCoilInfoVO> getMillSheetNos() {
        return millSheetNos;
    }

    public void setMillSheetNos(List<MillCoilInfoVO> millSheetNos) {
        this.millSheetNos = millSheetNos;
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

    public String getMillSheetPath() {
        return millSheetPath;
    }

    public void setMillSheetPath(String millSheetPath) {
        this.millSheetPath = millSheetPath;
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
        this.state = state;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getMillSheetNoOld() {
        return millSheetNoOld;
    }

    public void setMillSheetNoOld(String millSheetNoOld) {
        this.millSheetNoOld = millSheetNoOld;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo;
    }

    public String getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(String specialNeed) {
        this.specialNeed = specialNeed;
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
        this.millSheetUrl = millSheetUrl;
    }

    public String getMillSheetName() {
        return millSheetName;
    }

    public void setMillSheetName(String millSheetName) {
        this.millSheetName = millSheetName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
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
        this.chemState = chemState;
    }

    public String getPhyState() {
        return phyState;
    }

    public void setPhyState(String phyState) {
        this.phyState = phyState;
    }

    public String getHaveState() {
        return haveState;
    }

    public void setHaveState(String haveState) {
        this.haveState = haveState;
    }

    public String getSplitState() {
        return splitState;
    }

    public void setSplitState(String splitState) {
        this.splitState = splitState;
    }

    public String getMillSheetType() {
        return millSheetType;
    }

    public void setMillSheetType(String millSheetType) {
        this.millSheetType = millSheetType;
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
        this.zkunnr = zkunnr;
    }

    public String getZkunwe() {
        return zkunwe;
    }

    public void setZkunwe(String zkunwe) {
        this.zkunwe = zkunwe;
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
}
