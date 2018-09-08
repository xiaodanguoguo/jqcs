package jq.steel.cs.services.cust.facade.model;

public class CrmQuestionItem {
    // 问题项ID
    private Long questionItemId;
    // 问卷ID
    private Long qid;
    // 问题项标题
    private String itemTitle;

    public Long getQuestionItemId() {
        return questionItemId;
    }

    public void setQuestionItemId(Long questionItemId) {
        this.questionItemId = questionItemId;
    }

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle == null ? null : itemTitle.trim();
    }
}