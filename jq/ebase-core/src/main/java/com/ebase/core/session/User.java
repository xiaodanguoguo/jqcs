package com.ebase.core.session;

import java.util.Date;

/**
 *  用户对象
 * @Auther: wangyu
 */
public class User {

    //用户标识
    private Long userId;

    //客户标识
    private Long custId;

    //账户标识
    private String acctId;

    // 账户名称
    private String acctName;

    //用户名
    private String userName;

    //手机号
    private String phoneNo;

    //昵称
    private String nickname;

    //状态
    private Byte status;

    //注册时间
    private Date regTime;

    //更新时间
    private Date updateDate;

    //用户密码
    private String password;

    //用户类型
    private Byte userType;

    //密码最后修改时间
    private Date lastPasswdModDate;

    //最后登录时间
    private Date lastLoginTime;

    //邮箱
    private String email;

    //版本号
    private String version;

    //密钥标识
    private String secretKeyId;

    //脱敏手机号码
    private String unsensitivePhoneNo;

    //脱敏邮箱
    private String unsensitiveEmail;

    //脱敏用户名
    private String unsensitiveUserName;

    //用户头像地址
    private String userIconUrl;

    //组织机构ID
    private String orgId;

    //组织机构名称(公司名称)
    private String orgName;

    private String orgCode;

    private String companyId;

    private String orgType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Date getLastPasswdModDate() {
        return lastPasswdModDate;
    }

    public void setLastPasswdModDate(Date lastPasswdModDate) {
        this.lastPasswdModDate = lastPasswdModDate;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSecretKeyId() {
        return secretKeyId;
    }

    public void setSecretKeyId(String secretKeyId) {
        this.secretKeyId = secretKeyId;
    }

    public String getUnsensitivePhoneNo() {
        return unsensitivePhoneNo;
    }

    public void setUnsensitivePhoneNo(String unsensitivePhoneNo) {
        this.unsensitivePhoneNo = unsensitivePhoneNo;
    }

    public String getUnsensitiveEmail() {
        return unsensitiveEmail;
    }

    public void setUnsensitiveEmail(String unsensitiveEmail) {
        this.unsensitiveEmail = unsensitiveEmail;
    }

    public String getUnsensitiveUserName() {
        return unsensitiveUserName;
    }

    public void setUnsensitiveUserName(String unsensitiveUserName) {
        this.unsensitiveUserName = unsensitiveUserName;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}
