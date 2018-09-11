package jq.steel.cs.services.base.api.vo.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class PurchaseDemandInfoVO {
    private Long id;

    // 物料类型ID
    private Long matTypeId;

    // 申请单号
    private String applyNumber;

    // 客户ID
    private Long custId;

    // 客户名称
    private String custName;

    // 需求部门
    private String demandDept;

    // 计划类型 1月度计划、2季度计划、3年度计划
    private Byte planType;
    private String planTypeView;

    //紧急程度 1-般、2-加急
    private Byte emergencyDegree;
    private String emergencyDegreeView;

    // 申请人
    private String applyBy;

    // 申请时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    private Date applyTime;

    // 备注
    private String remark;

    // 状态 1-保存
    private Byte status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private List<PurchaseDetailVO> detailVOList;

    private int pageSize =10;

    private int pageNum = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatTypeId() {
        return matTypeId;
    }

    public void setMatTypeId(Long matTypeId) {
        this.matTypeId = matTypeId;
    }

    public String getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(String applyNumber) {
        this.applyNumber = applyNumber == null ? null : applyNumber.trim();
    }

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
        this.custName = custName;
    }

    public String getDemandDept() {
        return demandDept;
    }

    public void setDemandDept(String demandDept) {
        this.demandDept = demandDept == null ? null : demandDept.trim();
    }

    public Byte getPlanType() {
        return planType;
    }

    public void setPlanType(Byte planType) {
        this.planType = planType;

    }

    public Byte getEmergencyDegree() {
        return emergencyDegree;
    }

    public void setEmergencyDegree(Byte emergencyDegree) {
        this.emergencyDegree = emergencyDegree;
    }

    public String getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public List<PurchaseDetailVO> getDetailVOList() {
        return detailVOList;
    }

    public void setDetailVOList(List<PurchaseDetailVO> detailVOList) {
        this.detailVOList = detailVOList;
    }

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

    public String getPlanTypeView() {
        return planTypeView;
    }

    public void setPlanTypeView(String planTypeView) {
        this.planTypeView = planTypeView;
    }

    public String getEmergencyDegreeView() {
        return emergencyDegreeView;
    }

    public void setEmergencyDegreeView(String emergencyDegreeView) {
        this.emergencyDegreeView = emergencyDegreeView;
    }
}