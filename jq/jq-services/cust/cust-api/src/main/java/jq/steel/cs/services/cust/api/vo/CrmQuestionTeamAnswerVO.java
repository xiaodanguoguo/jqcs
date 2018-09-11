package jq.steel.cs.services.cust.api.vo;

public class CrmQuestionTeamAnswerVO {
    // 问题答案选项ID
    private Long questionItemAnswerId;
    // 问卷ID
    private Long qid;
    // 问卷问题ID
    private Long questionItemId;
    // 问题答案选项分数
    private Integer answerValue;
    // 问题答案选项标题
    private String answerTitle;

    public Long getQuestionItemAnswerId() {
        return questionItemAnswerId;
    }

    public void setQuestionItemAnswerId(Long questionItemAnswerId) {
        this.questionItemAnswerId = questionItemAnswerId;
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

    public Integer getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(Integer answerValue) {
        this.answerValue = answerValue;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle == null ? null : answerTitle.trim();
    }
}