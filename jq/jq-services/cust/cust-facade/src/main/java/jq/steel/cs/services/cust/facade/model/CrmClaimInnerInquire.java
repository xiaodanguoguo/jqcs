package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class CrmClaimInnerInquire {

    private Long sid;

    private String claimNo;

    private String logisticsProcess;

    private String endProcessingTech;

    private String outInquireFact;

    private String claimConfirm;

    private String claimVerdict;

    private String improvement;

    private String inquireState;

    private String memo;

    private String createBy;

    private Date createDt;

    private String updateBy;

    private Date updateDt;

    private Integer version;

    //操作类型
    private  Integer optionType;

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

    public String getOutInquireFact() {
        return outInquireFact;
    }

    public void setOutInquireFact(String outInquireFact) {
        this.outInquireFact = outInquireFact == null ? null : outInquireFact.trim();
    }

    public String getClaimConfirm() {
        return claimConfirm;
    }

    public void setClaimConfirm(String claimConfirm) {
        this.claimConfirm = claimConfirm == null ? null : claimConfirm.trim();
    }

    public String getClaimVerdict() {
        return claimVerdict;
    }

    public void setClaimVerdict(String claimVerdict) {
        this.claimVerdict = claimVerdict == null ? null : claimVerdict.trim();
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement == null ? null : improvement.trim();
    }

    public String getInquireState() {
        return inquireState;
    }

    public void setInquireState(String inquireState) {
        this.inquireState = inquireState == null ? null : inquireState.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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
}