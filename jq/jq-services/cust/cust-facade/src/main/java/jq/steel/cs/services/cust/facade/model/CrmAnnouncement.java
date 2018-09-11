package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class CrmAnnouncement {
    private Long aid;

    private String annName;

    private Short annType;

    private Long createByid;

    private Date createDt;

    private Long updateByid;

    private Date updateDt;

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

    public Short getAnnType() {
        return annType;
    }

    public void setAnnType(Short annType) {
        this.annType = annType;
    }

    public Long getCreateByid() {
        return createByid;
    }

    public void setCreateByid(Long createByid) {
        this.createByid = createByid;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Long getUpdateByid() {
        return updateByid;
    }

    public void setUpdateByid(Long updateByid) {
        this.updateByid = updateByid;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }
}