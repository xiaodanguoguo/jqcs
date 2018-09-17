package jq.steel.cs.services.cust.facade.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CrmAnnouncement implements Serializable {
    private Long aid;

    private String annName;

    private BigDecimal annType;

    private BigDecimal createByid;

    private Date createDt;

    private BigDecimal updateByid;

    private Date updateDt;

    private static final long serialVersionUID = 1L;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getAnnName() {
        return annName;
    }

    public void setAnnName(String annName) {
        this.annName = annName == null ? null : annName.trim();
    }

    public BigDecimal getAnnType() {
        return annType;
    }

    public void setAnnType(BigDecimal annType) {
        this.annType = annType;
    }

    public BigDecimal getCreateByid() {
        return createByid;
    }

    public void setCreateByid(BigDecimal createByid) {
        this.createByid = createByid;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public BigDecimal getUpdateByid() {
        return updateByid;
    }

    public void setUpdateByid(BigDecimal updateByid) {
        this.updateByid = updateByid;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", annName=").append(annName);
        sb.append(", annType=").append(annType);
        sb.append(", createByid=").append(createByid);
        sb.append(", createDt=").append(createDt);
        sb.append(", updateByid=").append(updateByid);
        sb.append(", updateDt=").append(updateDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}