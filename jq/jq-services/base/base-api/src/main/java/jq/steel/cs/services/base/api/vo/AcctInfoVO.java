package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @Auther: wangyu
 */
public class AcctInfoVO {

    //id
    private Long acctId;
    private List<Long> acctIds;

    //企业表示
    private Long entId;

    //登录来源
    private String loginSource;

    //账号名称 描述
    private String acctTitle;

    //账号密码
    private String acctPassword;
    //新密码
    private String newAcctPassword;

    //账号编码
    private String acctCode;

    //姓名
    private String name;

    //部门
    private String dept;

    //手机号
    private String mobilePhone;

    //电子邮件
    private String email;

    //注册时间
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date registerTime;



    //最后登录时间
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date lastLoginTime;

    //是否删除
    private Byte isDelete;

    //修改人
    private String updatedBy;

    //修改时间
    @JsonFormat(timezone = "yyyy-MM-dd")
    private Date updatedTime;

    //状态
    private Byte status;

    //生效时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;
    private String startTimeView;

    //失效时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date entTime;
    private String entTimeView;

    // --- 集团信息
    //集团标识
    private Long aCompanyId;

    private String oInfoId;

    //集团名称
    private String companyName;

    //集团编码
    private String companyCode;

    private String orgTitle;

    private String orgId; //组织类型

    private String orgName;

    private Long roleId;

    private List<RoleInfoVO> roleArr;//角色集合

    private OrgInfoVO orgInfo =new OrgInfoVO();//组织集合

    private  List<OrgInfoVO> OrgArr;

    private String Opt;

    private  String existence;

    private List<AcctInfoVO> RoleInfo;

    private Long acctType;

    private Long purchaseType;          //1执行采购员2寻源采购员

    private String address;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Long purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getoInfoId() {
        return oInfoId;
    }

    public void setoInfoId(String oInfoId) {
        this.oInfoId = oInfoId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<AcctInfoVO> getRoleInfo() {
        return RoleInfo;
    }

    public void setRoleInfo(List<AcctInfoVO> roleInfo) {
        RoleInfo = roleInfo;
    }

    public String getExistence() {
        return existence;
    }

    public void setExistence(String existence) {
        this.existence = existence;
    }

    private int pageSize =10;

    private int pageNum = 1;

    private Long id;
    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    private String oInfoName;

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

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public List<Long> getAcctIds() {
        return acctIds;
    }

    public void setAcctIds(List<Long> acctIds) {
        this.acctIds = acctIds;
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
        this.loginSource = loginSource;
    }

    public String getAcctTitle() {
        return acctTitle;
    }

    public void setAcctTitle(String acctTitle) {
        this.acctTitle = acctTitle;
    }

    public String getAcctPassword() {
        return acctPassword;
    }

    public void setAcctPassword(String acctPassword) {
        this.acctPassword = acctPassword;
    }

    public String getNewAcctPassword() {
        return newAcctPassword;
    }

    public void setNewAcctPassword(String newAcctPassword) {
        this.newAcctPassword = newAcctPassword;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        this.updatedBy = updatedBy;
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

    public String getStartTimeView() {
        return startTimeView;
    }

    public void setStartTimeView(String startTimeView) {
        this.startTimeView = startTimeView;
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public String getEntTimeView() {
        return entTimeView;
    }

    public void setEntTimeView(String entTimeView) {
        this.entTimeView = entTimeView;
    }

    public Long getaCompanyId() {
        return aCompanyId;
    }

    public void setaCompanyId(Long aCompanyId) {
        this.aCompanyId = aCompanyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public List<RoleInfoVO> getRoleArr() {
        return roleArr;
    }

    public void setRoleArr(List<RoleInfoVO> roleArr) {
        this.roleArr = roleArr;
    }

    public OrgInfoVO getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfoVO orgInfo) {
        this.orgInfo = orgInfo;
    }

    public List<OrgInfoVO> getOrgArr() {
        return OrgArr;
    }

    public void setOrgArr(List<OrgInfoVO> orgArr) {
        OrgArr = orgArr;
    }

    public String getOpt() {
        return Opt;
    }

    public void setOpt(String opt) {
        Opt = opt;
    }

    public String getoInfoName() {
        return oInfoName;
    }

    public void setoInfoName(String oInfoName) {
        this.oInfoName = oInfoName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
