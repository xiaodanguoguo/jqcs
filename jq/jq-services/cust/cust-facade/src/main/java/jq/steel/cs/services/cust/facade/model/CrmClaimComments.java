package jq.steel.cs.services.cust.facade.model;

import java.math.BigDecimal;
import java.util.Date;

public class CrmClaimComments {
    private Long sid;

    private String claimNo;

    private BigDecimal handlerUser;

    private BigDecimal handlerTime;

    private BigDecimal handlerResults;

    private String createBy;

    private Date createDt;

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

    public BigDecimal getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(BigDecimal handlerUser) {
        this.handlerUser = handlerUser;
    }

    public BigDecimal getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(BigDecimal handlerTime) {
        this.handlerTime = handlerTime;
    }

    public BigDecimal getHandlerResults() {
        return handlerResults;
    }

    public void setHandlerResults(BigDecimal handlerResults) {
        this.handlerResults = handlerResults;
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
}