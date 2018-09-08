package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 资质信息
 * @Auther: wangyu
 */
public class CertInfoVO {
    private Long id;

    //供应商ID
    private Long supplierId;

    //证书编码
    private String certCode;

    //证书类型
    private String certType;

    //证书名称
    private String certName;

    //颁发机构
    private String issueOrgan;

    //证书有效期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date indate;

    //备注
    private String remark;

    //创建人
    private String createdBy;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createdTime;

    //修改人
    private String updatedBy;

    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updatedTime;

    private String opt; //前端字段

    private Byte colour; //颜色

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName == null ? null : certName.trim();
    }

    public String getIssueOrgan() {
        return issueOrgan;
    }

    public void setIssueOrgan(String issueOrgan) {
        this.issueOrgan = issueOrgan == null ? null : issueOrgan.trim();
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public Byte getColour() {
        return colour;
    }

    public void setColour(Byte colour) {
        this.colour = colour;
    }
}
