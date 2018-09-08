package jq.steel.cs.services.base.api.vo;

import java.util.Date;

public class CustInfoVO {
    private Long custId;            // 客户标识

    private String custName;        // 客户名称(企业名称) 个人用户默认为用户名

    private Byte custType;          // 客户类型

    private String userName;        // 用户名

    private String phoneNo;         // 电话号

    private String certNo;          // 身份证号

    private String creditCode;      // 统一社会信用代码

    private String email;           // 邮箱

    private String createdBy;       // 创建人

    private Date createdTime;       // 创建时间

    private String updatedBy;       // 修改人

    private Date updatedTime;       // 修改时间

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public Byte getCustType() {
        return custType;
    }

    public void setCustType(Byte custType) {
        this.custType = custType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}