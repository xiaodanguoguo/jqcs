package jq.steel.cs.services.base.facade.model;


import jq.steel.cs.services.base.api.vo.OrgInfoVO;

import java.util.Date;
import java.util.List;

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

    private List<RoleInfo> roleArr;//角色集合

    private OrgInfo orgInfo =new OrgInfo();//组织集合

    private String orgName;

    //关联个集团对象
    private CompanyInfo companyInfo;

    //一对多 关联角色
    private List<RoleInfo> RoleInfo;

    //一对多 中间明细
    private List<AcctRoleReal> arr;

    private  List<OrgInfoVO> OrgArr;

    private Long acctType;

    private Long purchaseType;      //1执行采购员2寻源采购员

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

    public List<OrgInfoVO> getOrgArr() {
        return OrgArr;
    }

    public void setOrgArr(List<OrgInfoVO> orgArr) {
        OrgArr = orgArr;
    }

    public List<AcctRoleReal> getArr() {
        return arr;
    }

    public void setArr(List<AcctRoleReal> arr) {
        this.arr = arr;
    }

    public List<RoleInfo> getRoleInfo() {
        return RoleInfo;
    }

    public void setRoleInfo(List<RoleInfo> roleInfo) {
        RoleInfo = roleInfo;
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

    public CompanyInfo getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
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


    public List<RoleInfo> getRoleArr() {
        return roleArr;
    }

    public void setRoleArr(List<RoleInfo> roleArr) {
        this.roleArr = roleArr;
    }

    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}