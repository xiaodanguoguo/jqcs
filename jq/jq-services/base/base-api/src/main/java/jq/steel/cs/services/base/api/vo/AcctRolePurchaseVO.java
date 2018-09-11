package jq.steel.cs.services.base.api.vo;

import java.util.Date;

/**
 * @Auther: zhaoyuhang
 */
public class AcctRolePurchaseVO {

    private Long rolePurchaseId;

    private Long roleId;

    private Long purchaseType;

    private Byte status;

    private String createdBy;

    private Date createdTime;

    private RoleInfoVO roleInfo;

    private String opt;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Long getRolePurchaseId() {
        return rolePurchaseId;
    }

    public void setRolePurchaseId(Long rolePurchaseId) {
        this.rolePurchaseId = rolePurchaseId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Long purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public RoleInfoVO getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfoVO roleInfo) {
        this.roleInfo = roleInfo;
    }
}
