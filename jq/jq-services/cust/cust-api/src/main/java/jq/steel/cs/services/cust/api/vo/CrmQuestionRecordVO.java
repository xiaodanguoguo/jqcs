package jq.steel.cs.services.cust.api.vo;

import java.util.Date;
import java.util.List;

public class CrmQuestionRecordVO {
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

    private Long acctId;

    private String orgType;

    private List<CrmQuestionRecordDetailVO> crmQuestionRecordDetailVOList;

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

    public List<CrmQuestionRecordDetailVO> getCrmQuestionRecordDetailVOList() {
        return crmQuestionRecordDetailVOList;
    }

    public void setCrmQuestionRecordDetailVOList(List<CrmQuestionRecordDetailVO> crmQuestionRecordDetailVOList) {
        this.crmQuestionRecordDetailVOList = crmQuestionRecordDetailVOList;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}