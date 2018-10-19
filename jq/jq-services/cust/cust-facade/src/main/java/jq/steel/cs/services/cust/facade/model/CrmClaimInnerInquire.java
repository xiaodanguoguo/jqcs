package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class CrmClaimInnerInquire {

    //内部提交人
    private String submitter;

    //完成时间
    private Date completionTime;

    private String acctName;

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

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
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

    @Override
    public String toString() {
        return "CrmClaimInnerInquire{" +
                "productionProcessInvestigati='" + productionProcessInvestigati + '\'' +
                ", productionProcessPhoto='" + productionProcessPhoto + '\'' +
                ", qualityGrade='" + qualityGrade + '\'' +
                ", originalJudgementResult='" + originalJudgementResult + '\'' +
                ", surfaceStructure='" + surfaceStructure + '\'' +
                ", productionProcessText='" + productionProcessText + '\'' +
                ", productionProcessAll='" + productionProcessAll + '\'' +
                ", sid=" + sid +
                ", claimNo='" + claimNo + '\'' +
                ", logisticsProcess='" + logisticsProcess + '\'' +
                ", endProcessingTech='" + endProcessingTech + '\'' +
                ", outInquireFact='" + outInquireFact + '\'' +
                ", claimConfirm='" + claimConfirm + '\'' +
                ", claimVerdict='" + claimVerdict + '\'' +
                ", improvement='" + improvement + '\'' +
                ", inquireState='" + inquireState + '\'' +
                ", memo='" + memo + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDt=" + createDt +
                ", updateBy='" + updateBy + '\'' +
                ", updateDt=" + updateDt +
                ", version=" + version +
                ", optionType=" + optionType +
                '}';
    }
}