package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class MillSheetExpand {
    private Long sid;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String millSheetNo;

    private Integer physicsS;

    private Integer physicsE;

    private Integer chemistryS;

    private Integer chemistryE;

    private String cheShow;

    private String phyShowActive;

    private Integer pageNo;

    private String bzfsshow;

    private String phyShow3;

    private String phyShow5;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo == null ? null : millSheetNo.trim();
    }

    public Integer getPhysicsS() {
        return physicsS;
    }

    public void setPhysicsS(Integer physicsS) {
        this.physicsS = physicsS;
    }

    public Integer getPhysicsE() {
        return physicsE;
    }

    public void setPhysicsE(Integer physicsE) {
        this.physicsE = physicsE;
    }

    public Integer getChemistryS() {
        return chemistryS;
    }

    public void setChemistryS(Integer chemistryS) {
        this.chemistryS = chemistryS;
    }

    public Integer getChemistryE() {
        return chemistryE;
    }

    public void setChemistryE(Integer chemistryE) {
        this.chemistryE = chemistryE;
    }

    public String getCheShow() {
        return cheShow;
    }

    public void setCheShow(String cheShow) {
        this.cheShow = cheShow == null ? null : cheShow.trim();
    }

    public String getPhyShowActive() {
        return phyShowActive;
    }

    public void setPhyShowActive(String phyShowActive) {
        this.phyShowActive = phyShowActive == null ? null : phyShowActive.trim();
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getBzfsshow() {
        return bzfsshow;
    }

    public void setBzfsshow(String bzfsshow) {
        this.bzfsshow = bzfsshow == null ? null : bzfsshow.trim();
    }

    public String getPhyShow3() {
        return phyShow3;
    }

    public void setPhyShow3(String phyShow3) {
        this.phyShow3 = phyShow3 == null ? null : phyShow3.trim();
    }

    public String getPhyShow5() {
        return phyShow5;
    }

    public void setPhyShow5(String phyShow5) {
        this.phyShow5 = phyShow5 == null ? null : phyShow5.trim();
    }
}