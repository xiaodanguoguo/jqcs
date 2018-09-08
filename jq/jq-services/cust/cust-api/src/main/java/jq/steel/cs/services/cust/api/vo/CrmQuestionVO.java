package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class CrmQuestionVO {
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
    // 下发范围
    private String pushRegion;
    // 下发范围接收参数
    private List<String> pushRegions;
    // 创建时间
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDt;

    private String createBy;

    private Date updateDt;

    private String updateBy;
    // 调查问卷问题项集合
    private List<CrmQuestionItemVO> crmQuestionItemVOList;
    // qid集合
    private List<Long> qids;
    // 操作类别
    private String opt;
    // 客户类型
    private String orgType;
    // 用户id
    private Long acctId;

    private Integer count;

    private int pageNum = 1;
    private int pageSize = 10;

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

    public String getPushRegion() {
        return pushRegion;
    }

    public void setPushRegion(String pushRegion) {
        this.pushRegion = pushRegion == null ? null : pushRegion.trim();
    }

    public List<String> getPushRegions() {
        return pushRegions;
    }

    public void setPushRegions(List<String> pushRegions) {
        this.pushRegions = pushRegions;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
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

    public List<CrmQuestionItemVO> getCrmQuestionItemVOList() {
        return crmQuestionItemVOList;
    }

    public void setCrmQuestionItemVOList(List<CrmQuestionItemVO> crmQuestionItemVOList) {
        this.crmQuestionItemVOList = crmQuestionItemVOList;
    }

    public List<Long> getQids() {
        return qids;
    }

    public void setQids(List<Long> qids) {
        this.qids = qids;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
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

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }
}