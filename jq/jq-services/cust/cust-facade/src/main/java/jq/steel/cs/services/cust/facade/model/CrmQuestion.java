package jq.steel.cs.services.cust.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CrmQuestion {
    // 问卷ID
    private Long qid;
    // 信息编号
    private String messageNumber;
    // 问卷标题
    private String questionTitle;
    // 问卷状态 1新建、2已发布 3 已结束
    private String questionStatus;
    // 结束时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;
    private String endDtStr;
    // 下发范围
    private String pushRegion;
    // 创建时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDt;
    private String createDtStr;

    private String createBy;

    private Date updateDt;

    private String updateBy;

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public String getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(String messageNumber) {
        this.messageNumber = messageNumber == null ? null : messageNumber.trim();
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle == null ? null : questionTitle.trim();
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus == null ? null : questionStatus.trim();
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

    public String getPushRegion() {
        return pushRegion;
    }

    public void setPushRegion(String pushRegion) {
        this.pushRegion = pushRegion == null ? null : pushRegion.trim();
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getCreateDtStr() {
        return createDtStr;
    }

    public void setCreateDtStr(String createDtStr) {
        this.createDtStr = createDtStr;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}