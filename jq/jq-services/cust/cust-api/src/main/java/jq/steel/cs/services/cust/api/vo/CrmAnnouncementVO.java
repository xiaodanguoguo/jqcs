package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
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

    private BigDecimal annType;

    private BigDecimal createByid;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDt;

    private BigDecimal updateByid;

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
