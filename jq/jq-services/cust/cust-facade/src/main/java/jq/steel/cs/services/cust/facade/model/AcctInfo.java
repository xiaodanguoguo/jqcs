package jq.steel.cs.services.cust.facade.model;

import java.util.Date;

public class AcctInfo {
    private Long acctId;        //账户标识

    private Long entId;         //企业标识

    private String loginSource; //登录来源

    private String acctTitle;     //账户名称

    private String acctPassword;      //账户密码

    private String acctCode;           //账户编码

    private String name;            //姓名

    private String dept;            //部门

    private String mobilePhone;     //手机号

    private String email;           //电子邮件

    private Date registerTime;      //注册时间

    private Date lastLoginTime;     //最后登录时间

    private Byte isDelete;             //是否删除

    private String updatedBy;       //修改人

    private Date updatedTime;       //修改时间

    private Byte status;            //状态

    private Date startTime;            //生效时间

    private Date entTime;           //失效时间

    private String existence; //  增 删 改 状态

    private String orgId;  //组织类型

    private String orgTitle;

    private Long aCompanyId;

    private Long roleId;//角色ID
    private String RoleTitle;//角色名称

    private String oInfoId;//组织id
    private String oInfoName;//组织名字

    private Long acctType;

    private Long purchaseType;      //1执行采购员2寻源采购员

    // 注册类型
    private Integer regType;

    public Long getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Long purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Long getAcctType() {
        return acctType;
    }

    public void setAcctType(Long acctType) {
        this.acctType = acctType;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setoInfoId(String oInfoId) {
        this.oInfoId = oInfoId;
    }

    public String getExistence() {
        return existence;
    }

    public void setExistence(String existence) {
        this.existence = existence;
    }

    public String getoInfoId() {
        return oInfoId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource == null ? null : loginSource.trim();
    }

    public String getAcctTitle() {
        return acctTitle;
    }

    public void setAcctTitle(String acctTitle) {
        this.acctTitle = acctTitle == null ? null : acctTitle.trim();
    }

    public String getAcctPassword() {
        return acctPassword;
    }

    public void setAcctPassword(String acctPassword) {
        this.acctPassword = acctPassword == null ? null : acctPassword.trim();
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode == null ? null : acctCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public Long getaCompanyId() {
        return aCompanyId;
    }

    public void setaCompanyId(Long aCompanyId) {
        this.aCompanyId = aCompanyId;
    }



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleTitle() {
        return RoleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        RoleTitle = roleTitle;
    }

    public String getoInfoName() {
        return oInfoName;
    }

    public void setoInfoName(String oInfoName) {
        this.oInfoName = oInfoName;
    }


    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }
}