package jq.steel.cs.services.cust.facade.model;

import java.util.Date;
import java.util.List;

public class CrmQuestionRecord {
    // 答题记录ID
    private Long answerRecordId;
    // 问卷ID
    private Long qid;
    // 总得分
    private Integer recordValue;
    // 答题时间
    private Date recordDt;
    // 答题人ID
    private Long recordBy;
    // 答题人名称
    private String recordByName;
    // 备注
    private String memo;
    // 问卷ids
    List<Long> questionIds;

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

    public Integer getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(Integer recordValue) {
        this.recordValue = recordValue;
    }

    public Date getRecordDt() {
        return recordDt;
    }

    public void setRecordDt(Date recordDt) {
        this.recordDt = recordDt;
    }

    public Long getRecordBy() {
        return recordBy;
    }

    public void setRecordBy(Long recordBy) {
        this.recordBy = recordBy;
    }

    public String getRecordByName() {
        return recordByName;
    }

    public void setRecordByName(String recordByName) {
        this.recordByName = recordByName == null ? null : recordByName.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }
}