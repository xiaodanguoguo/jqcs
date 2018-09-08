package jq.steel.cs.services.cust.facade.model;

public class CrmQuestionRecordDetail {
    // 答题记录明细ID
    private Long recordDetailId;
    // 答题记录ID
    private Long answerRecordId;
    // 问卷ID
    private Long qid;
    // 问题项ID
    private Long questionItemId;
    // 答案项ID
    private Long questionItemAnswerId;
    // 答案分值
    private Integer answerValue;

    public Long getRecordDetailId() {
        return recordDetailId;
    }

    public void setRecordDetailId(Long recordDetailId) {
        this.recordDetailId = recordDetailId;
    }

    public Long getAnswerRecordId() {
        return answerRecordId;
    }

    public void setAnswerRecordId(Long answerRecordId) {
        this.answerRecordId = answerRecordId;
    }

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public Long getQuestionItemId() {
        return questionItemId;
    }

    public void setQuestionItemId(Long questionItemId) {
        this.questionItemId = questionItemId;
    }

    public Long getQuestionItemAnswerId() {
        return questionItemAnswerId;
    }

    public void setQuestionItemAnswerId(Long questionItemAnswerId) {
        this.questionItemAnswerId = questionItemAnswerId;
    }

    public Integer getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(Integer answerValue) {
        this.answerValue = answerValue;
    }
}