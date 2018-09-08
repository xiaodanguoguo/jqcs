package jq.steel.cs.services.base.api.vo;

import java.util.Date;

public class UserInfoVO {
	private Long userId;

	private Long custId;

	private Long acctId;

	private String userName;

	private String phoneNo;

	private String nickname;

	private Byte status;

	private Date regTime;

	private Date updateDate;

	private String password;

	// 1是企业，2是用户
	private Byte userType;

	private Date lastPasswdModDate;

	private Date lastLoginTime;

	private String email;

	private String version;

	private String secretKeyId;

	private String unsensitivePhoneNo;

	private String unsensitiveEmail;

	private String unsensitiveUserName;

	private String userIconUrl;

	private Long orgId;

	private String orgName;

	private String createdBy;

	private Date createdTime;

	private String updatedBy;

	private Date updatedTime;

	// 新添加字段
	// 邮箱链接 加密Url
	private String valiURL;
	// 验证码
	private String valiCode;
	// 旧密码
	private String oldPassword;
	// 跳转URL
	private String jumpURL;
	// 跳转类型 1表示 邮箱跳转，2表示短信验证成功跳转
	private String skipType;
	// 加密字段
	private String encrypt;

	public String getSkipType() {
		return skipType;
	}

	public void setSkipType(String skipType) {
		this.skipType = skipType;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	public String getJumpURL() {
		return jumpURL;
	}

	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getValiURL() {
		return valiURL;
	}

	public void setValiURL(String valiURL) {
		this.valiURL = valiURL;
	}

	public String getValiCode() {
		return valiCode;
	}

	public void setValiCode(String valiCode) {
		this.valiCode = valiCode;
	}

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

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo == null ? null : phoneNo.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
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
		this.password = password == null ? null : password.trim();
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
		this.email = email == null ? null : email.trim();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version == null ? null : version.trim();
	}

	public String getSecretKeyId() {
		return secretKeyId;
	}

	public void setSecretKeyId(String secretKeyId) {
		this.secretKeyId = secretKeyId == null ? null : secretKeyId.trim();
	}

	public String getUnsensitivePhoneNo() {
		return unsensitivePhoneNo;
	}

	public void setUnsensitivePhoneNo(String unsensitivePhoneNo) {
		this.unsensitivePhoneNo = unsensitivePhoneNo == null ? null : unsensitivePhoneNo.trim();
	}

	public String getUnsensitiveEmail() {
		return unsensitiveEmail;
	}

	public void setUnsensitiveEmail(String unsensitiveEmail) {
		this.unsensitiveEmail = unsensitiveEmail == null ? null : unsensitiveEmail.trim();
	}

	public String getUnsensitiveUserName() {
		return unsensitiveUserName;
	}

	public void setUnsensitiveUserName(String unsensitiveUserName) {
		this.unsensitiveUserName = unsensitiveUserName == null ? null : unsensitiveUserName.trim();
	}

	public String getUserIconUrl() {
		return userIconUrl;
	}

	public void setUserIconUrl(String userIconUrl) {
		this.userIconUrl = userIconUrl == null ? null : userIconUrl.trim();
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
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