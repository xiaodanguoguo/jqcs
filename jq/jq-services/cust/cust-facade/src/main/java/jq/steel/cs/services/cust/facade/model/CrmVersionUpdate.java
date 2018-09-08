package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class CrmVersionUpdate {
    private Long vid;

    private String versionNumber;

    private String versionContent;

    private Short updateWay;

    private Long createByid;

    private Date createDt;

    private Long updateByid;

    private Date updateDt;

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent == null ? null : versionContent.trim();
    }

    public Short getUpdateWay() {
        return updateWay;
    }

    public void setUpdateWay(Short updateWay) {
        this.updateWay = updateWay;
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