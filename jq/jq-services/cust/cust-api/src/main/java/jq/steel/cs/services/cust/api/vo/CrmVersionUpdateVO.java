package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName:      CrmVersionUpdateVO
 * @Description:
 * @Author:         lujiawei
 * @CreateDate:     2018/9/14 9:30
 * */
public class CrmVersionUpdateVO {
    private Long vid;

    private String versionNumber;   //版本号

    private String versionPath;

    private String versionContent;

    private BigDecimal updateWay;

    private BigDecimal createByid;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDt;

    private BigDecimal updateByid;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateDt;

    private String opt;

    private int pageNum = 1;

    private int pageSize = 10;

    public int getPageNum() { return pageNum; }

    public void setPageNum(int pageNum) { this.pageNum = pageNum; }

    public int getPageSize() { return pageSize; }

    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    private static final long serialVersionUID = 1L;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

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

    public String getVersionPath() {
        return versionPath;
    }

    public void setVersionPath(String versionPath) {
        this.versionPath = versionPath == null ? null : versionPath.trim();
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent == null ? null : versionContent.trim();
    }

    public BigDecimal getUpdateWay() {
        return updateWay;
    }

    public void setUpdateWay(BigDecimal updateWay) {
        this.updateWay = updateWay;
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
        sb.append(", vid=").append(vid);
        sb.append(", versionNumber=").append(versionNumber);
        sb.append(", versionPath=").append(versionPath);
        sb.append(", versionContent=").append(versionContent);
        sb.append(", updateWay=").append(updateWay);
        sb.append(", createByid=").append(createByid);
        sb.append(", createDt=").append(createDt);
        sb.append(", updateByid=").append(updateByid);
        sb.append(", updateDt=").append(updateDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
