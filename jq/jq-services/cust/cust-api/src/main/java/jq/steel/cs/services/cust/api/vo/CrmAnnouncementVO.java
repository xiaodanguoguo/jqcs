package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @ClassName:      CrmAnnouncementVO
 * @Description:
 * @Author:         lujiawei
 * @CreateDate:     2018/9/14/13:30
 */
public class CrmAnnouncementVO {
    private Long aid;

    private String annName;

    private Long annType;

    private String annTitle;

    private Long createByid;

    private String createBy;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDt;

    private Long updateByid;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateDt;

    private int pageSize = 10;

    private int pageNum = 1;

    private String opt;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    private static final long serialVersionUID = 1L;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

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

    public Long getAnnType() {
        return annType;
    }

    public void setAnnType(Long annType) {
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

    public String getAnnTitle() {
        return annTitle;
    }

    public void setAnnTitle(String annTitle) {
        this.annTitle = annTitle;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
