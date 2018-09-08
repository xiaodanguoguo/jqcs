package jq.steel.cs.services.base.api.vo;

import jq.steel.cs.services.base.api.enumvo.Status;

import java.util.Date;

public class ProcurementInfoVO {

	private Long id;

	/**
	 * 组织机构ID
	 */
	private String orgId;

	/**
	 * 组织名称
	 */
	private String orgName;

	/**
	 * 采购种类
	 */
	private String dictType;

	/**
	 * 状态
	 */
	private Status status;

	/**
	 * 创建人
	 */
	private String createdBy;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 修改人
	 */
	private String updateBy;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 是否删除
	 */
	private Integer isDelete;

	/**
	 * 自荐申请单号
	 */
	private String recoApplyNumber;

	/**
	 * 申请单状态
	 */
	private Integer applyFormStatus;

    private Integer pageSize = 10;

    public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	private Integer pageNum =5;

	public String getStatus() {
		if (this.status != null)
			return status.getCode();
		return "";
	}

	public void setStatus(String code) {
		this.status = Status.getStatus(code);

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getRecoApplyNumber() {
		return recoApplyNumber;
	}

	public void setRecoApplyNumber(String recoApplyNumber) {
		this.recoApplyNumber = recoApplyNumber;
	}

	public Integer getApplyFormStatus() {
		return applyFormStatus;
	}

	public void setApplyFormStatus(Integer applyFormStatus) {
		this.applyFormStatus = applyFormStatus;
	}

}
