package jq.steel.cs.services.cust.facade.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author zhangx
 *		组织机构表
 */
public class OrgInfo {

	private String id;

	private String id2;



	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	/**
	 * 上级机构
	 */
	private String parentId;

	private String parentName;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 机构代码
	 */
	private String orgCode;

	/**
	 * 机构名称
	 */
	private String orgName;



	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 0:停用;1:启用
	 */
	private String status;

	/**
	 * 创建人
	 */
	private String createdBy;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date createdTime;

	/**
	 * 修改人
	 */
	private String updatedBy;

	/**
	 * 修改时间
	 */
	private Date updatedTime;

	private OrgInfo parent;//parent

	// SAP编码
	private String sapCode;
	// 公司代码
	private String bukrs;
	// 客户类型
	private String orgType;
	// 电话
	private String tel;
	// 地址
	private String addr;

	private String email;

	// 注册类型
	private Integer regType;

	//0非信任，1信任
	private String industrialCode;

	public String getIndustrialCode() {
		return industrialCode;
	}

	public void setIndustrialCode(String industrialCode) {
		this.industrialCode = industrialCode;
	}

	//标识
	private Boolean isTrue;

	public Boolean getTrue() {
		return isTrue;
	}

	public void setTrue(Boolean aTrue) {
		isTrue = aTrue;
	}

	public OrgInfo getParent() {
		return parent;
	}

	public void setParent(OrgInfo parent) {
		this.parent = parent;
	}

	private List<OrgInfo> children = new ArrayList<OrgInfo>();

	public List<OrgInfo> getChildren() {
		return children;
	}

	public void setChildren(List<OrgInfo> children) {
		this.children = children;
	}
	public void addChild(OrgInfo orgInfo) {
		this.children.add(orgInfo);
	}

	private Integer pageSize = 10;

	private Integer pageNum =1;

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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public String getSapCode() {
		return sapCode;
	}

	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}
}
