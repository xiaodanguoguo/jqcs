package jq.steel.cs.services.cust.api.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CrmClaimCommentsVO {

    private  String orgCode;

    private  String orgName;

    private Long sid;

    private String claimNo;

    private BigDecimal handlerUser;

    private BigDecimal handlerTime;

    private BigDecimal handlerResults;

    private String createBy;

    private Date createDt;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
