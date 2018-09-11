package jq.steel.cs.services.cust.api.vo;

/**
 * @ClassName: ObjectionTiBaoCountVO
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/9/7 20:48
 */
public class ObjectionTiBaoCountVO {

    // 新建
    private Integer created;
    // 已提报
    private Integer present;
    // 已受理
    private Integer acceptance;
    // 已驳回
    private Integer reject;
    // 调查中
    private Integer investigation;
    // 处理中
    private Integer handle;
    // 已结案
    private Integer end;
    // 已评价
    private Integer evaluate;

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public Integer getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Integer acceptance) {
        this.acceptance = acceptance;
    }

    public Integer getReject() {
        return reject;
    }

    public void setReject(Integer reject) {
        this.reject = reject;
    }

    public Integer getInvestigation() {
        return investigation;
    }

    public void setInvestigation(Integer investigation) {
        this.investigation = investigation;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }
}
